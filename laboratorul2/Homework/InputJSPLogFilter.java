package org.example.laboratorul2.homework;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = {"/input.jsp"})
public class InputJSPLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String ipAddress = request.getRemoteAddr();
        System.out.println("Request to input.jsp - IP: " + ipAddress + ", Time: " + new Date());

        chain.doFilter(req, res);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}