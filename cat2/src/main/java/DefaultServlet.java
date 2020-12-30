import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = "Hello, OrangeCat! GET";
        writer(content, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = "Hello, OrangeCat! POST";
        writer(content, resp);

    }

    private void writer(String content, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("{\"result\" : \"" + content + "\"}");
        System.out.println(content);
        printWriter.close();
    }
}
