package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.Actors;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.AvailableNodes;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.ConfigurationService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.DeveloperDatabaseResource;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.HelloResource;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.Monitoring;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.NetworkData;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.Nodes;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.OnlineComponents;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.Profiles;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.UserAuth;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

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

   /* @Inject
    private Instance<RestFulServices> services;

    @Override
    public Set<Class<?>> getClasses() {

        final Set<Class<?>> resourceList = new LinkedHashSet<Class<?>>();

        for (RestFulServices restFulServices: services) {
            resourceList.add(restFulServices.getClass());
        }

        return resourceList;
    } */

    private static final ImmutableSet services = ImmutableSet.of(
            AvailableNodes.class,
            HelloResource.class,
            OnlineComponents.class,
            Profiles.class,
            Nodes.class,
            NetworkData.class,
            Monitoring.class,
            UserAuth.class,
            ConfigurationService.class,
            com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services.Databases.class,
            Actors.class
    );

    @Override
    public Set<Class<?>> getClasses() {
        return services;
    }

}