package connector.http;

import util.ParameterMap;
import util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class HttpProcessor {

    private HttpConnector httpConnector;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    HttpProcessor(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    public void process(Socket socket) {
        SocketInputStream socketInputStream = null;
        OutputStream outputStream = null;
        try {
            //获取流
            socketInputStream = new SocketInputStream(socket.getInputStream(), 2048);
            outputStream = socket.getOutputStream();

            //创建request与response
            httpRequest = new HttpRequest(socketInputStream);
            httpResponse = new HttpResponse(outputStream);
            httpResponse.setRequest(httpRequest);

            //解析、填充
            parseRequest(socketInputStream, outputStream);
            parseHeaders(socketInputStream);
            parseParameters();

            //请求分发
            if (httpRequest.getRequestURI().startsWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(httpRequest, httpResponse);
            } else {
                StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                staticResourceProcessor.process(httpRequest, httpResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parseRequest(SocketInputStream socketInputStream, OutputStream outputStream)
            throws IOException, ServletException {
        HttpRequestLine httpRequestLine = new HttpRequestLine();
        socketInputStream.readRequestLine(httpRequestLine);
        String method = new String(httpRequestLine.method, 0, httpRequestLine.methodEnd);
        String uri = null;
        String protocol = new String(httpRequestLine.protocol, 0, httpRequestLine.protocolEnd);

        if (method.length()<1) {
            throw new ServletException("HTTP请求缺少方法");
        } else if (httpRequestLine.uriEnd < 1) {
            throw new ServletException("HTTP请求缺少路径");
        }
        int question = httpRequestLine.indexOf("?");
        if (question > 0) {
            httpRequest.setQueryString(new String(httpRequestLine.uri,
                    question+1, httpRequestLine.uriEnd+question-1));
            uri = new String(httpRequestLine.uri, 0, question);
        } else {
            httpRequest.setQueryString(null);
            uri = new String(httpRequestLine.uri, 0, httpRequestLine.uriEnd);
        }

        //如果uri不以/开头，则从"://"后第一个/开始截取，没有则为空字符串
        if (! uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            if (pos != -1) {
                pos = uri.indexOf("/", pos+3);
                uri = pos==-1 ? "" : uri.substring(pos);
            }
        }

        //解析jsessionid，如果存在，在uri中去掉
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon);
            int semicolon2 = rest.indexOf(';');
            if (semicolon2 >= 0) {
                httpRequest.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                httpRequest.setRequestedSessionId(rest);
                rest = "";
            }
            httpRequest.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            httpRequest.setRequestedSessionId(null);
            httpRequest.setRequestedSessionURL(false);
        }

        //标准化uri
        String normalizedUri = normalize(uri);
        //填充值
        httpRequest.setMethod(method);
        if (normalizedUri != null) {
            httpRequest.setURI(normalizedUri);
        } else {
            httpRequest.setURI(uri);
        }
        httpRequest.setProtocol(protocol);
        if (normalizedUri == null) {
            throw new ServletException("非法的URI：" + uri);
        }

        httpRequest.setScheme(httpConnector.getScheme());
    }

    private String normalize(String uri) {
        return uri;
    }

    private void parseHeaders(SocketInputStream socketInputStream) throws IOException, ServletException {
        while (true) {
            HttpHeader httpHeader = new HttpHeader();
            socketInputStream.readHeader(httpHeader);
            if (httpHeader.nameEnd == 0) {
                if (httpHeader.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException("请求头名称不能为空");
                }
            }
            String name = new String(httpHeader.name, 0, httpHeader.nameEnd);
            String value = new String(httpHeader.value, 0, httpHeader.valueEnd);
            httpRequest.addHeader(name, value);

            if ("content-type".equals(name)) {
                httpRequest.setContentType(value);
            } else if ("content-length".equals(name)) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e) {
                    throw new ServletException("请求头content-length值格式不正确");
                }
                httpRequest.setContentLength(n);
            } else if ("cookie".equals(name)) {
                Cookie[] cookies = RequestUtil.parseCookieHeader(value);
                for (int i=0; i<cookies.length; i++) {
                    if ("jsessionid".equals(cookies[i].getName())) {
                        if (! httpRequest.isRequestedSessionIdFromCookie()) {
                            httpRequest.setRequestedSessionId(cookies[i].getValue());
                            httpRequest.setRequestedSessionURL(false);
                            httpRequest.setRequestedSessionCookie(true);
                        }
                    }
                }
            }
        }
    }

    private boolean parsed = false;
    private void parseParameters() {
        if (parsed) return;
        ParameterMap results = (ParameterMap) httpRequest.getParameterMap();
        if (results == null) {
            results = new ParameterMap();
        }
        results.setLocked(false);

        //编码
        String encoding = httpRequest.getCharacterEncoding();
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }

        //解析queryString中的参数
        try {
            RequestUtil.parseParameters(results, httpRequest.getQueryString(), encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //解析请求体中的参数
        String contentType = httpRequest.getContentType();
        if (contentType == null) {
            contentType = "";
        }
        int semicolon = contentType.indexOf(';');
        if (semicolon >= 0) {
            contentType = contentType.substring(0, semicolon).trim();
        } else {
            contentType = contentType.trim();
        }
        if ("POST".equals(httpRequest.getMethod())
                && httpRequest.getContentLength()>0
                && "application/x-www-form-urlencoded".equals(contentType)) {
            try {
                int max = httpRequest.getContentLength();
                int len = 0;
                byte[] buf = new byte[max];
                SocketInputStream is = httpRequest.getSocketInputStream();
                while (len < max) {
                    int next = is.read(buf, len, max-len);
                    if (next < 0) {
                        break;
                    }
                    len += next;
                }
                is.close();
                if (len < max) {
                    throw new RuntimeException("contentLength长度不匹配");
                }
                RequestUtil.parseParameters(results, buf, encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException("请求体读取失败");
            }
        }

        //解析后更改状态，并填充
        parsed = true;
        results.setLocked(true);
        httpRequest.setParameterMap(results);
    }
}
