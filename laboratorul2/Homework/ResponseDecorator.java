package org.example.laboratorul2.homework;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ResponseDecorator", urlPatterns = {"/*"})
public class ResponseDecorator implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        ResponseWrapper wrapper = new ResponseWrapper(httpResponse);
        chain.doFilter(request, wrapper);

        String content = wrapper.toString();

        ServletContext context = httpRequest.getServletContext();
        String prelude = (String) context.getAttribute("prelude");
        String coda = (String) context.getAttribute("coda");

        StringBuilder modifiedContent = new StringBuilder();

        if (prelude != null) {
            modifiedContent.append(prelude + "\n");
        }

        modifiedContent.append(content);

        if (coda != null) {
            modifiedContent.append(coda + "\n");
        }

        PrintWriter out = response.getWriter();
        out.write(String.valueOf(modifiedContent));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
