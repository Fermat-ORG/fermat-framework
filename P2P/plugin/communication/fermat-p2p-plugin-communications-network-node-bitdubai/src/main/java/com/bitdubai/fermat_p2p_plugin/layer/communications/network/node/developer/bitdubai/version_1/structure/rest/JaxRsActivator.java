/*
 * @#JaxRsActivator.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.JaxRsActivator</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ApplicationPath("/rest/api/v1")
public class JaxRsActivator extends Application {

    @Inject
    private Instance<RestFulServices> services;

    @Override
    public Set<Class<?>> getClasses() {

        final Set<Class<?>> resourceList = new LinkedHashSet<Class<?>>();

        for (RestFulServices restFulServices: services) {
            resourceList.add(restFulServices.getClass());
        }

        return resourceList;
    }

}