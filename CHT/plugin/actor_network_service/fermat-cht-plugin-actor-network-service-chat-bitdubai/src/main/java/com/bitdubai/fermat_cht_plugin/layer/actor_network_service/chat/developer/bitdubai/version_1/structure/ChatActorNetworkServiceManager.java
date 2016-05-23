package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public class ChatActorNetworkServiceManager implements ChatManager {


    private final CommunicationsClientConnection communicationsClientConnection;
    private final ChatActorNetworkServiceDao chatActorNetworkServiceDao;
    private final ChatActorNetworkServicePluginRoot pluginRoot;
    private final PluginVersionReference pluginVersionReference;

    /**
     * Executor
     */
    ExecutorService executorService;

    private PlatformComponentProfile platformComponentProfile;

    public ChatActorNetworkServiceManager(final CommunicationsClientConnection communicationsClientConnection,
                                          final ChatActorNetworkServiceDao chatActorNetworkServiceDao,
                                          final ChatActorNetworkServicePluginRoot pluginRoot,
                                          final PluginVersionReference pluginVersionReference) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.chatActorNetworkServiceDao = chatActorNetworkServiceDao;
        this.pluginRoot = pluginRoot;
        this.pluginVersionReference = pluginVersionReference;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    private ConcurrentHashMap<String, ChatExposingData> chatToExpose;

    @Override
    public void exposeIdentity(ChatExposingData chatExposingData) throws CantExposeIdentityException {

        try {

            if (!isRegistered()) {

                addChatToExpose(chatExposingData);

            } else {

                final PlatformComponentProfile actorPlatformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        chatExposingData.getPublicKey(),
                        (chatExposingData.getAlias()),
                        (chatExposingData.getAlias().toLowerCase() + "_" + platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.ACTOR_CHAT,
                        PlatformComponentType.ACTOR_CHAT,
                        extraDataToJson(chatExposingData)
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                addChatToExpose(chatExposingData);
            }

        } catch (final CantRegisterComponentException e) {

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
            if (isRegistered()) {


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        chatExposingData.getPublicKey(),
                        (chatExposingData.getAlias()),
                        (chatExposingData.getAlias().toLowerCase() + "_" + this.platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CHAT,
                        extraDataToJson(chatExposingData));

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(platformComponentProfile.getNetworkServiceType(), platformComponentProfile);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
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

        } catch (final CantExposeIdentityException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public ChatSearch getSearch() {
        return new ChatActorNetworkServiceSearch(communicationsClientConnection, pluginRoot.getErrorManager(), pluginVersionReference);
    }




    @Override
    public void requestConnection(ChatConnectionInformation chatConnectionInformation) throws CantRequestConnectionException {

        try {

            final ProtocolState           state  = ProtocolState.PROCESSING_SEND;
            final RequestType             type   = RequestType.SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST        ;

            chatActorNetworkServiceDao.createConnectionRequest(
                    chatConnectionInformation,
                    state            ,
                    type             ,
                    action
            );

            sendMessage(
                    buildJsonRequestMessage(chatConnectionInformation),
                    chatConnectionInformation.getSenderPublicKey(),
                    chatConnectionInformation.getSenderActorType(),
                    chatConnectionInformation.getDestinationPublicKey(),
                    Actors.CHAT
            );

        } catch (final CantRequestConnectionException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

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

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

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

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

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

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ChatConnectionRequest> listPendingConnectionNews(Actors actorType) throws CantListPendingConnectionRequestsException {
        try {

            return chatActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ChatConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            return chatActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        try {

            chatActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }






    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final Actors identityType     ,
                             final String actorPublicKey   ,
                             final Actors actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    pluginRoot.sendNewMessage(
                            pluginRoot.getProfileSenderToRequestConnection(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(identityType)
                            ),
                            pluginRoot.getProfileDestinationToRequestConnection(
                                    actorPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(actorType)
                            ),
                            jsonMessage
                    );
                } catch (CantSendMessageException | InvalidParameterException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

    public final void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;

        if (platformComponentProfile != null && chatToExpose != null && !chatToExpose.isEmpty()) {

            try {

                this.exposeIdentities(chatToExpose.values());

            } catch (final CantExposeIdentitiesException e){

                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case CHAT    : return PlatformComponentType.ACTOR_CHAT  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }

    private boolean isRegistered() {
        return platformComponentProfile != null;
    }

    private void addChatToExpose(final ChatExposingData chatExposingData) {

        if (chatToExpose == null)
            chatToExpose = new ConcurrentHashMap<>();

        chatToExpose.putIfAbsent(chatExposingData.getPublicKey(), chatExposingData);
    }

    public final void exposeIdentitiesInWait() throws CantExposeIdentityException {
        if(!Validate.isObjectNull(chatToExpose) && chatToExpose.size() > 0){
            for (ChatExposingData chatExposingData :
                    chatToExpose.values()) {
                exposeIdentity(chatExposingData);

            }
        }
    }

    public final boolean areIdentitiesToExpose(){
        return (!Validate.isObjectNull(chatToExpose) && chatToExpose.size() > 0);
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

    private String extraDataToJson(ChatExposingData chatExposingData){
        Gson gson = new Gson();
        JsonObject jsonObjectContent = new JsonObject();
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.IMG, Base64.encodeToString(chatExposingData.getImage(), Base64.DEFAULT));
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.COUNTRY, chatExposingData.getCountry());
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.STATE, chatExposingData.getState());
        jsonObjectContent.addProperty(ChatExtraDataJsonAttNames.CITY, chatExposingData.getCity());

        return gson.toJson(jsonObjectContent);
    }
}
