package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.ChatActorNetworkServicePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.database.ChatActorNetworkServiceDao;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatActorNetworkServiceManager implements ChatManager {

    private final ChatActorNetworkServiceDao chatActorNetworkServiceDao;
    private final ChatActorNetworkServicePluginRoot pluginRoot;

    /**
     * Executor
     */
    private final ExecutorService executorService;

    public ChatActorNetworkServiceManager(final ChatActorNetworkServiceDao chatActorNetworkServiceDao,
                                          final ChatActorNetworkServicePluginRoot pluginRoot) {

        this.chatActorNetworkServiceDao = chatActorNetworkServiceDao;
        this.pluginRoot = pluginRoot;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void exposeIdentity(ChatExposingData chatExposingData) throws CantExposeIdentityException {

        try {

            pluginRoot.registerActor(
                    chatExposingData.getPublicKey(),
                    chatExposingData.getAlias(),
                    chatExposingData.getAlias(),
                    extraDataToJson(chatExposingData),
                    chatExposingData.getLocation(),
                    Actors.CHAT,
                    chatExposingData.getImage(),
                    chatExposingData.getRefreshInterval(), chatExposingData.getAccurancy()
            );

        } catch (final ActorAlreadyRegisteredException | CantRegisterActorException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateIdentity(ChatExposingData chatExposingData) throws CantExposeIdentityException {
        try {

            pluginRoot.updateRegisteredActor(
                    chatExposingData.getPublicKey(),
                    chatExposingData.getAlias(),
                    chatExposingData.getAlias(),
                    null,
                    extraDataToJson(chatExposingData),
                    chatExposingData.getImage()
            );
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void exposeIdentities(Collection<ChatExposingData> chatExposingDataList) throws CantExposeIdentitiesException {

        try {

            for (final ChatExposingData exposingData : chatExposingDataList)
                this.exposeIdentity(exposingData);

        } catch (final CantExposeIdentityException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public ChatSearch getSearch() {
        return new ChatActorNetworkServiceSearch(pluginRoot);
    }

    @Override
    public void requestConnection(ChatConnectionInformation chatConnectionInformation) throws CantRequestConnectionException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;
            final RequestType type = RequestType.SENT;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            chatActorNetworkServiceDao.createConnectionRequest(
                    chatConnectionInformation,
                    state,
                    type,
                    action
            );

            sendMessage(
                    buildJsonRequestMessage(chatConnectionInformation),
                    chatConnectionInformation.getSenderPublicKey(),
                    chatConnectionInformation.getSenderActorType(),
                    chatConnectionInformation.getDestinationPublicKey(),
                    Actors.CHAT
            );

        } catch (final CantRequestConnectionException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void disconnect(UUID requestId) throws CantDisconnectException, ConnectionRequestNotFoundException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            chatActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            ChatConnectionRequest chat = chatActorNetworkServiceDao.getConnectionRequest(requestId);

            if (chat.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(chat),
                        chat.getDestinationPublicKey(),
                        Actors.CHAT,
                        chat.getSenderPublicKey(),
                        chat.getSenderActorType()
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(chat),
                        chat.getSenderPublicKey(),
                        chat.getSenderActorType(),
                        chat.getDestinationPublicKey(),
                        Actors.CHAT
                );
            }

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException {
        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            chatActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            ChatConnectionRequest chat = chatActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(chat),
                    chat.getDestinationPublicKey(),
                    Actors.CHAT,
                    chat.getSenderPublicKey(),
                    chat.getSenderActorType()
            );

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e) {
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void cancelConnection(UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException {

    }

    @Override
    public void acceptConnection(UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            chatActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            ChatConnectionRequest chat = chatActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(chat),
                    chat.getDestinationPublicKey(),
                    Actors.CHAT,
                    chat.getSenderPublicKey(),
                    chat.getSenderActorType()
            );

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e) {
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ChatConnectionRequest> listPendingConnectionNews(Actors actorType) throws CantListPendingConnectionRequestsException {
        try {

            return chatActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ChatConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            return chatActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        try {

            chatActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public boolean isActorOnline(String publicKey) {
        return pluginRoot.isActorOnline(publicKey);
    }

    private void sendMessage(final String jsonMessage,
                             final String identityPublicKey,
                             final Actors identityType,
                             final String actorPublicKey,
                             final Actors actorType) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    ActorProfile sender = new ActorProfile();
                    sender.setActorType(identityType.getCode());
                    sender.setIdentityPublicKey(identityPublicKey);

                    ActorProfile receiver = new ActorProfile();
                    receiver.setActorType(actorType.getCode());
                    receiver.setIdentityPublicKey(actorPublicKey);

                    pluginRoot.sendNewMessage(
                            sender,
                            receiver,
                            jsonMessage
                    );
                } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

    private String buildJsonInformationMessage(final ChatConnectionRequest aer) {

        return new InformationMessage(
                aer.getRequestId(),
                aer.getRequestAction()
        ).toJson();
    }

    private String buildJsonRequestMessage(final ChatConnectionInformation aer) {

        return new RequestMessage(
                aer.getConnectionId(),
                aer.getSenderPublicKey(),
                aer.getSenderActorType(),
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                ConnectionRequestAction.REQUEST,
                aer.getSendingTime()
        ).toJson();
    }

    private String extraDataToJson(ChatExposingData chatExposingData) {
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.COUNTRY, chatExposingData.getCountry());
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.STATE, chatExposingData.getState());
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.CITY, chatExposingData.getCity());
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.STATUS, chatExposingData.getStatus());

        return gson.toJson(jsonObjectContent);
    }

}
