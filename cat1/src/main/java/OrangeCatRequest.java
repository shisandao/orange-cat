import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrangeCatRequest {

    private InputStream inputStream;
    private String url;
    private String method;
    private String agree;
    private String content;

    OrangeCatRequest(){
        super();
    };

    OrangeCatRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        parse();
    }

    public void parse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"GBK"));

        int lineNum = 0;
        String line = "";
        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                System.out.println("");
                //请求体
                parseContent(br);
                break;
            }
            if (lineNum++ == 0) {
                //首行
                parseFirstLine(line);
            } else {
                //请求头
                parseHeader(line);
            }
        }
    }

    private void parseFirstLine(String line) {
        String[] first = line.split("\\s");
        method = first[0];
        url = first[1];
        agree = first[2];
        System.out.println(line);
    }

    private void parseHeader(String line) {
        System.out.println(line);
    }

    private void parseContent(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] bt = new char[1024];
        while (br.ready())  {
            br.read(bt);
            sb.append(bt);
        };
        this.content = sb.toString();
        System.out.println(content);
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "OrangeCatRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
