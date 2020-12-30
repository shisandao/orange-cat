package connector.http;

import javax.servlet.ServletException;
import java.io.IOException;

public class ServletProcessor {

    ServletProcessor() {
    }

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException {
        ServerServlet servlet = new ServerServlet();
        servlet.service(new RequestFacade(httpRequest), new ResponseFacade(httpResponse));
    }

}
