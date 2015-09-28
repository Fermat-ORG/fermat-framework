package com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.NetworkServiceConnectionManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 30.12.14.
 * Update by Roberto Requena - (rart3001@gmail.com) on 28/09/15.
 */
public interface NetworkService {

    /**
     * Get the Id
     *
     * @return UUID
     */
    public UUID getId();

    /*
     * Get the RemoteNetworkServicesRegisteredList, this method may be called after
     * the <code>requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters</code> and the
     * <code>CompleteRequestListComponentRegisteredNotificationEvent</code> is raised.
     *
     * @return List<PlatformComponentProfile>
     */
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList();

    /**
     * Make a request of the Remote Network Services Registered in the cloud server, that
     * match with the DiscoveryQueryParameters pass as parameters.
     *
     * @param discoveryQueryParameters
     */
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters);


    /**
     * Get the NetworkServiceConnectionManager
     * @return NetworkServiceConnectionManager
     */
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager();



}
