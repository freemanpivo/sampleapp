package com.freemanpivo.insurancechallenge.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@Component
public class RequestInterceptor extends OncePerRequestFilter {
    private static final String ID_HEADER = "X-Trace-Id";
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        try {
            final String token;

            if (!StringUtils.isEmpty(request.getHeader(ID_HEADER))) {
                token = request.getHeader(ID_HEADER);
            } else {
                token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            }

            MDC.put("requestId", token);
            response.addHeader(ID_HEADER, token);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("requestId");
        }
    }
}
