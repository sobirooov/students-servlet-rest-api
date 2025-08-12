package uz.azamjon.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IpBlackListFilter implements Filter {
    public void  init(FilterConfig filterConfig) throws ServletException {

    }
    private List<String> ipBlackList;

    {
        ipBlackList = new ArrayList<>();
        ipBlackList.add("192.168.100.120");
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress);
        if (ipBlackList.contains(ipAddress)) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else  {
            chain.doFilter(request, response);
        }

    }
    public void destroy() {}

}
