import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class RequestFacade implements HttpServletRequest {

    private HttpServletRequest request;

    RequestFacade(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getAuthType() {
        return this.request.getAuthType();
    }

    @Override
    public Cookie[] getCookies() {
        return this.request.getCookies();
    }

    @Override
    public long getDateHeader(String s) {
        return this.request.getDateHeader(s);
    }

    @Override
    public String getHeader(String s) {
        return this.request.getHeader(s);
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return this.request.getHeaders(s);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return this.request.getHeaderNames();
    }

    @Override
    public int getIntHeader(String s) {
        return this.request.getIntHeader(s);
    }

    @Override
    public String getMethod() {
        return this.request.getMethod();
    }

    @Override
    public String getPathInfo() {
        return this.request.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return this.request.getPathTranslated();
    }

    @Override
    public String getContextPath() {
        return this.request.getContextPath();
    }

    @Override
    public String getQueryString() {
        return this.request.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return this.request.getRemoteUser();
    }

    @Override
    public boolean isUserInRole(String s) {
        return this.request.isUserInRole(s);
    }

    @Override
    public Principal getUserPrincipal() {
        return this.request.getUserPrincipal();
    }

    @Override
    public String getRequestedSessionId() {
        return this.request.getRequestedSessionId();
    }

    @Override
    public String getRequestURI() {
        return this.request.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return this.request.getRequestURL();
    }

    @Override
    public String getServletPath() {
        return this.request.getServletPath();
    }

    @Override
    public HttpSession getSession(boolean b) {
        return this.request.getSession(b);
    }

    @Override
    public HttpSession getSession() {
        return this.request.getSession();
    }

    @Override
    public String changeSessionId() {
        return this.request.changeSessionId();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return this.request.isRequestedSessionIdValid();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.request.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.request.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return this.request.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return this.request.authenticate(httpServletResponse);
    }

    @Override
    public void login(String s, String s1) throws ServletException {
        this.request.login(s, s1);
    }

    @Override
    public void logout() throws ServletException {
        this.request.logout();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return this.request.getParts();
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        return this.request.getPart(s);
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return this.request.upgrade(aClass);
    }

    @Override
    public Object getAttribute(String s) {
        return this.request.getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(s);
    }

    @Override
    public int getContentLength() {
        return this.request.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return this.request.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return this.request.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }

    @Override
    public String getParameter(String s) {
        return this.request.getParameter(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String s) {
        return this.request.getParameterValues(s);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return this.request.getProtocol();
    }

    @Override
    public String getScheme() {
        return this.request.getScheme();
    }

    @Override
    public String getServerName() {
        return this.request.getServerName();
    }

    @Override
    public int getServerPort() {
        return this.request.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return this.request.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return this.request.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }

    @Override
    public void setAttribute(String s, Object o) {
        this.request.setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        this.request.removeAttribute(s);
    }

    @Override
    public Locale getLocale() {
        return this.request.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return this.request.getLocales();
    }

    @Override
    public boolean isSecure() {
        return this.request.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return this.request.getRequestDispatcher(s);
    }

    @Override
    public String getRealPath(String s) {
        return this.request.getRealPath(s);
    }

    @Override
    public int getRemotePort() {
        return this.request.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return this.request.getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return this.request.getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return this.request.getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return this.request.getServletContext();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return this.request.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return this.request.startAsync(servletRequest, servletResponse);
    }

    @Override
    public boolean isAsyncStarted() {
        return this.request.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return this.request.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return this.request.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return this.request.getDispatcherType();
    }
}
