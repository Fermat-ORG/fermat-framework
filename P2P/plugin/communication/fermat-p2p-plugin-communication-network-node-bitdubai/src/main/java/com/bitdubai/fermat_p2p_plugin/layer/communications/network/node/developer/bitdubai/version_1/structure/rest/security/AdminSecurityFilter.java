/*
 * @#SecurityFilter  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;

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
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;

/**
 * The Class <code>SecurityFilter</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AdminSecurityFilter implements Filter {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(AdminSecurityFilter.class));

    /**
     * (non-javadoc)
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("init");
    }

    /**
     * (non-javadoc)
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.debug("doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        LOG.debug("Authorization = " + httpRequest.getHeader("Authorization"));

        final String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.contains("Bearer ")) {
            LOG.error("Missing or invalid Authorization header.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
            return;
        }

        try {

            String token = authHeader.substring("Bearer ".length(), authHeader.length());
            LOG.debug("token = " + token);

            final Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode(JWTManager.getKey())).parseClaimsJws(token).getBody();
            if (claims.getSubject().equals(ConfigurationManager.getValue(ConfigurationManager.USER))){
                chain.doFilter(request, response);
            }

        }
        catch (final SignatureException e) {
            LOG.error( "Invalid token: "+e.getMessage());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: "+e.getMessage());
            return;
        }
    }

    /**
     * (non-javadoc)
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        LOG.info("destroy");
    }
}
