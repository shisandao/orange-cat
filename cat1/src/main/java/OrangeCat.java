import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OrangeCat {

    private Integer port = 8080;
    OrangeCat() {
    }
    OrangeCat(Integer port) {
        this.port = port;
    }

    public void start() {
        System.out.println("橘猫启动中。。。");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("橘猫已启动");
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    InputStream inputStream = socket.getInputStream();

                    OrangeCatRequest orangeCatRequest = new OrangeCatRequest(inputStream);
                    if (null == orangeCatRequest.getMethod()) continue;
                    OutputStream outputStream = socket.getOutputStream();
                    OrangeCatResponse orangeCatResponse = new OrangeCatResponse(outputStream);

                    String context = "";
                    if (orangeCatRequest.getMethod().equalsIgnoreCase("GET")) {
                        context = "Hello, OrangeCat! GET";
                    } else if (orangeCatRequest.getMethod().equalsIgnoreCase("POST")) {
                        context = "Hello, OrangeCat! POST";
                    }
                    orangeCatResponse.write(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrangeCat orangeCat = new OrangeCat(8080);
        orangeCat.start();
    }

}
