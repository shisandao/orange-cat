package connector.http;

import javax.servlet.ServletException;
import java.io.IOException;

public class StaticResourceProcessor {

    StaticResourceProcessor() {
    }

    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws ServletException, IOException {
        StaticResourceServlet servlet = new StaticResourceServlet();
        servlet.service(new RequestFacade(httpRequest), new ResponseFacade(httpResponse));
    }

}
