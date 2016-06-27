/*
 * @#Credential  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.security;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.security.Credential</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement
public class Credential {

    private String user;
    private String password;

    public Credential(){
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
