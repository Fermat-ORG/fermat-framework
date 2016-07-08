/*
 * @#SecurityFilter  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.google.gson.Gson;

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
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/07/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientRestApiSecurityFilter implements Filter {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(NetworkClientRestApiSecurityFilter.class));

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public NetworkClientRestApiSecurityFilter(){
        super();
        this.daoFactory  = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        this.gson = GsonProvider.getGson();
    }

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

        try {

            LOG.debug("Authorization = " + httpRequest.getHeader("Authorization"));

            final String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.contains("Bearer ")) {
                LOG.error("Missing or invalid Authorization header.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
                return;
            }

            String clientIdentityPublicKey = authHeader.substring("Bearer ".length(), authHeader.length());
            LOG.debug("clientIdentityPublicKey = " + clientIdentityPublicKey);

            CheckedInClient checkedInClient = daoFactory.getCheckedInClientDao().findById(clientIdentityPublicKey);
            LOG.debug("checkedInClient = " + checkedInClient);

            if (checkedInClient != null){
                chain.doFilter(request, response);
            }else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client Identity PublicKey not Authorize.");
            }

        } catch (final Exception e) {
            LOG.error( "Error in token: "+e.getMessage());
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
