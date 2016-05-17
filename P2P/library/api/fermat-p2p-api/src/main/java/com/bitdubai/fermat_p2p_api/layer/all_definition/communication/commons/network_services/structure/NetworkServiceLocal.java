package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.OutgoingMessagesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories.NetworkServiceMessageFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Observable;
import java.util.Observer;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure.NetworkServiceLocal</code>
 * represents the remote network services locally
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkServiceLocal implements Observer {

    private NetworkServiceConnectionManager networkServiceConnectionManager;
    private Profile                         remoteComponentProfile         ;
    private ErrorManager                    errorManager                   ;
    private OutgoingMessagesDao             outgoingMessagesDao            ;

    public NetworkServiceLocal(final NetworkServiceConnectionManager networkServiceConnectionManager,
                               final Profile                         remoteComponentProfile         ,
                               final ErrorManager                    errorManager                   ) {

        this.networkServiceConnectionManager = networkServiceConnectionManager                         ;
        this.remoteComponentProfile          = remoteComponentProfile                                  ;
        this.errorManager                    = errorManager                                            ;
        this.outgoingMessagesDao             = networkServiceConnectionManager.getOutgoingMessagesDao();
    }

    public void sendMessage(final String  messageContent) {

        try {

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessageFactory.buildNetworkServiceMessage(
                    this.networkServiceConnectionManager.getNetworkServiceRoot().getProfile(),
                    (NetworkServiceProfile) remoteComponentProfile,
                    messageContent,
                    MessageContentType.TEXT
            );
            /*
             * Save to the data base table
             */
            outgoingMessagesDao.create(networkServiceMessage);

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(networkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }
    }

    public void sendMessage(final ActorProfile sender        ,
                            final String       messageContent) {

        try {

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessageFactory.buildNetworkServiceMessage(
                    sender,
                    (ActorProfile) remoteComponentProfile,
                    this.networkServiceConnectionManager.getNetworkServiceRoot().getProfile(),
                    messageContent,
                    MessageContentType.TEXT
            );
            /*
             * Save to the data base table
             */
            outgoingMessagesDao.create(networkServiceMessage);

        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedPluginException(networkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message. Error reason: " + e.getMessage()));
        }
    }


    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage received
     */
    private void onMessageReceived(NetworkServiceMessage incomingMessage) {

        try {

            /*
             * process the new message receive
             */
            networkServiceConnectionManager.getNetworkServiceRoot().onNewMessageReceived(incomingMessage);

            incomingMessage.setFermatMessagesStatus(FermatMessagesStatus.READ);
            networkServiceConnectionManager.getIncomingMessagesDao().update(incomingMessage);

        } catch (CantUpdateRecordDataBaseException | RecordNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called automatically when CommunicationNetworkServiceRemoteAgent (Observable object) update the database
     * when new message is received
     *
     * @param observable the observable object
     * @param data       the data update
     */
    @Override
    public void update(Observable observable, Object data) {

        //Validate and process
        if (data instanceof NetworkServiceMessage)
            onMessageReceived((NetworkServiceMessage) data);
    }
}
