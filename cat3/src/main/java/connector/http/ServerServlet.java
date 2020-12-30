package connector.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ServerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        write(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        write(req, resp);
    }

    private void write(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type:text/html;charset=utf-8");
        pw.println("");

        pw.println("");
        pw.println("Method");
        pw.println(req.getMethod());

        pw.println("");
        pw.println("Parameters");
        Map parameters = req.getParameterMap();
        for (Object key : parameters.keySet()) {
            pw.println(key+":"+parameters.get(key));
        }

        pw.println("");
        pw.println("Query String");
        pw.println(req.getQueryString());

        pw.println("");
        pw.println("Request URI");
        pw.println(req.getRequestURI());
    }
}
