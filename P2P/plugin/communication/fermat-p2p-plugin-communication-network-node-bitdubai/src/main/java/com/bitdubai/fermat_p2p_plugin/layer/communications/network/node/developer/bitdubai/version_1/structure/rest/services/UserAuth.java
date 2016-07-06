/*
 * @#UserWebService.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security.Credential;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security.JWTManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.google.gson.Gson;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;


/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.User</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/user")
public class UserAuth {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(UserAuth.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public UserAuth() {
        super();
        this.gson = GsonProvider.getGson();
    }

    @GET
    @GZIP
    public String isActive() {
        return "The User WebService is running ...";
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credential credential) {

        LOG.debug("Executing login()");
        LoginResponse loginResponse;

       if (credential.getUser().equals(ConfigurationManager.getValue(ConfigurationManager.USER)) && credential.getPassword().equals(ConfigurationManager.getValue(ConfigurationManager.PASSWORD))){

           Date expirationDate = new Date(JWTManager.getExpirationTime());
           String authToken = Jwts.builder().setSubject(credential.getUser()).setIssuedAt(new Date()).setExpiration(expirationDate).setIssuer("/fermat/api/monitoring/").signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(JWTManager.getKey())).compact();
           loginResponse = new LoginResponse(Boolean.TRUE, "Login process success", authToken);

        }else {
            loginResponse = new LoginResponse(Boolean.FALSE, "Login process fail", "");
        }

        return Response.status(200).entity(gson.toJson(loginResponse)).build();

    }

    /**
     * Represent the response of the
     * login process
     */
    @XmlRootElement
    private static class LoginResponse {

        public Boolean success;
        public String message;
        public String authToken;

        public LoginResponse(Boolean success, String message, String authToken) {
            this.success = success;
            this.message = message;
            this.authToken = authToken;
        }
    }

}
