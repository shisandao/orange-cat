import java.io.InputStream;
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
                    OutputStream outputStream = socket.getOutputStream();

                    OrangeCatRequest orangeCatRequest = new OrangeCatRequest(inputStream);
                    if (null == orangeCatRequest.getMethod()) continue;
                    OrangeCatResponse orangeCatResponse = new OrangeCatResponse(outputStream);

                    DefaultServlet defaultServlet = new DefaultServlet();
                    defaultServlet.service(new RequestFacade(orangeCatRequest), new ResponseFacade(orangeCatResponse));
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
