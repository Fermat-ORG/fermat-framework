/*
 * @#ApplicationResources.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ApplicationResources</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 10/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ApplicationResources extends Application {

    private static final ImmutableSet services = ImmutableSet.of(
            ExampleResourceImpl.class,
            ComponentRegisteredListWebService.class
    );

    @Override
    public Set<Class<?>> getClasses() {
        return services;
    }
}
