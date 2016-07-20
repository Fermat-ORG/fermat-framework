package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.IncomingMessagesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessagesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure.NetworkServiceConnectionManager</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 * //TODO: León esto no tiene nada de manager..., revisá lo que haces.
 */
public final class NetworkServiceConnectionManager {

    private AbstractNetworkService networkServiceRoot;

    private IncomingMessagesDao incomingMessagesDao;
    private OutgoingMessagesDao outgoingMessagesDao;

    public NetworkServiceConnectionManager(final AbstractNetworkService networkServiceRoot) {

        this.networkServiceRoot = networkServiceRoot;

        this.incomingMessagesDao = new IncomingMessagesDao(networkServiceRoot.getDataBase());
        this.outgoingMessagesDao = new OutgoingMessagesDao(networkServiceRoot.getDataBase());
    }

    public void connectTo(NetworkServiceProfile remoteNetworkService) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null, // actorType
                    null, // alias
                    null, // distance
                    null, // extraData
                    remoteNetworkService.getIdentityPublicKey(), // IdentityPublicKey
                    null, // location
                    0, // max
                    null, // name
                    remoteNetworkService.getNetworkServiceType(), // this is filter in the Node if was a NetworkService or an Actor who realized the request discovery
                    0, // offset
                    networkServiceRoot.getProfile().getNetworkServiceType() // this is the NetworkService Intermediate who handle the request
            );

            networkServiceRoot.getConnection().actorTraceDiscoveryQuery(discoveryQueryParameters);

        } catch (Exception e) {
            networkServiceRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e
            );
        }


    }

    public void connectTo(ActorProfile applicantNetworkService) throws CantEstablishConnectionException {

        try {

            networkServiceRoot.getConnection().callActor(networkServiceRoot.getProfile(), applicantNetworkService);

        } catch (Exception e) {
            networkServiceRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e
            );
        }
    }

    /**
     * Get the OutgoingMessageDao
     * @return OutgoingMessageDao
     */
    public OutgoingMessagesDao getOutgoingMessagesDao() {
        return outgoingMessagesDao;
    }

    /**
     * Get the IncomingMessageDao
     * @return IncomingMessageDao
     */
    public IncomingMessagesDao getIncomingMessagesDao() {
        return incomingMessagesDao;
    }

    /**
     * Get the NetworkServiceRoot
     * @return AbstractNetworkServiceBase
     */
    public AbstractNetworkService getNetworkServiceRoot() {
        return networkServiceRoot;
    }
}