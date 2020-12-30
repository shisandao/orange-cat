import java.io.IOException;
import java.io.OutputStream;

public class OrangeCatResponse {

    private OutputStream outputStream;

    OrangeCatResponse(){
        super();
    };

    OrangeCatResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    //将文本转换为字节流
    public void write(String content) throws IOException {
        //按照HTTP响应报文的格式写入
        String httpResponse = "HTTP/1.1 200 OK\n" +
                "Content-Type:application/json\n" +
                "\r\n" +
                "{\"result\" : \"" + content + "\"}\n";
        outputStream.write(httpResponse.getBytes());
        outputStream.close();
    }
}
