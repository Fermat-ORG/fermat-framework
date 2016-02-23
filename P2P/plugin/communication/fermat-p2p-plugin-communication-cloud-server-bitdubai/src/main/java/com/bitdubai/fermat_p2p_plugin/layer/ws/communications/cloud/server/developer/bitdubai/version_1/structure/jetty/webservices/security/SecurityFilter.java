/*
 * @#SecurityFilter  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.security;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;

/**
 * The Class <code>SecurityFilter</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SecurityFilter implements Filter {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(SecurityFilter.class));


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.info("doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LOG.info("Auth-Token = " + httpRequest.getHeader("Auth-Token"));

        final String authHeader = httpRequest.getHeader("Auth-Token");
        if (authHeader == null) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        try {

            LOG.info("authHeader = " + authHeader);
            final Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode(JWTManager.getKey().getPrivateKey())).parseClaimsJws(authHeader).getBody();
            LOG.info("user = " + claims.getSubject());
        }
        catch (final SignatureException e) {
            throw new ServletException("Invalid token.");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOG.info("destroy");
    }
}
