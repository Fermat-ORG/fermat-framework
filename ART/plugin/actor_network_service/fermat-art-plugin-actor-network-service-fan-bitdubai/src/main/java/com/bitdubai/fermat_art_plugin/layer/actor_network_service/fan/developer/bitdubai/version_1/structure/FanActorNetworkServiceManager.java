package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantFindRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.FanActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto broker actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class FanActorNetworkServiceManager implements FanManager {

    private final CommunicationsClientConnection            communicationsClientConnection    ;
    private final FanActorNetworkServiceDao fanActorNetworkServiceDao;
    private final FanActorNetworkServicePluginRoot pluginRoot                        ;
    private final ErrorManager                              errorManager                      ;
    private final PluginVersionReference                    pluginVersionReference            ;

    /**
     * Executor
     */
    ExecutorService executorService;

    private PlatformComponentProfile platformComponentProfile;

    public FanActorNetworkServiceManager(final CommunicationsClientConnection communicationsClientConnection,
                                         final FanActorNetworkServiceDao fanActorNetworkServiceDao,
                                         final FanActorNetworkServicePluginRoot pluginRoot,
                                         final ErrorManager errorManager,
                                         final PluginVersionReference pluginVersionReference) {

        this.communicationsClientConnection     = communicationsClientConnection    ;
        this.fanActorNetworkServiceDao = fanActorNetworkServiceDao;
        this.pluginRoot                         = pluginRoot                        ;
        this.errorManager                       = errorManager                      ;
        this.pluginVersionReference             = pluginVersionReference            ;
        this.executorService                    = Executors.newFixedThreadPool(3)   ;
    }

    private ConcurrentHashMap<String, FanExposingData> fansToExpose;

    @Override
    public final void exposeIdentity(final FanExposingData fan) throws CantExposeIdentityException {

        try {

            if (!isRegistered()) {

                addFansToExpose(fan);

            } else {

                final String imageString = Base64.encodeToString(fan.getExtraData().getBytes(), Base64.DEFAULT);

                final PlatformComponentProfile actorPlatformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        fan.getPublicKey(),
                        (fan.getAlias()),
                        (fan.getAlias().toLowerCase()),
                        NetworkServiceType.FAN_ACTOR,
                        PlatformComponentType.ART_FAN,
                        imageString
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                if (fansToExpose != null && fansToExpose.containsKey(fan.getPublicKey()))
                    fansToExpose.remove(fan.getPublicKey());
            }

        } catch (final CantRegisterComponentException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateIdentity(FanExposingData actor) throws CantExposeIdentityException {
        try {
            if (isRegistered()) {

                final String imageString = Base64.encodeToString(actor.getExtraData().getBytes(), Base64.DEFAULT);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actor.getPublicKey(),
                        (actor.getAlias()),
                        (actor.getAlias().toLowerCase() + "_" + this.platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.FAN_ACTOR,
                        PlatformComponentType.ART_FAN,
                        imageString);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(platformComponentProfile.getNetworkServiceType(), platformComponentProfile);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                },"ART ACTOR NS ARTIST UPDATE IDENTITY");

                thread.start();
            }
        }catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    private void addFansToExpose(final FanExposingData fanExposingData) {

        if (fansToExpose == null)
            fansToExpose = new ConcurrentHashMap<>();

        fansToExpose.putIfAbsent(fanExposingData.getPublicKey(), fanExposingData);
    }

    @Override
    public final void exposeIdentities(final Collection<FanExposingData> artistExposingDataList) throws CantExposeIdentitiesException {

        try {

            for (final FanExposingData artistExposingData : artistExposingDataList)
                this.exposeIdentity(artistExposingData);

        } catch (final CantExposeIdentityException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    private boolean isRegistered() {
        return platformComponentProfile != null;
    }

    public final void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;

        if (platformComponentProfile != null && fansToExpose != null && !fansToExpose.isEmpty()) {

            try {

                this.exposeIdentities(fansToExpose.values());

            } catch (final CantExposeIdentitiesException e){

                errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public final ActorSearch<FanExposingData> getSearch() {
        return new FanActorNetworkServiceSearch(communicationsClientConnection, errorManager, pluginVersionReference);
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public final void requestConnection(final FanConnectionInformation fanConnectionInformation) throws CantRequestConnectionException {

        try {

            final ProtocolState state  = ProtocolState          .PROCESSING_SEND;
            final RequestType type   = RequestType            .SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST        ;

            fanActorNetworkServiceDao.createConnectionRequest(
                    fanConnectionInformation,
                    state,
                    type,
                    action
            );

            sendMessage(
                    buildJsonRequestMessage(fanConnectionInformation),
                    fanConnectionInformation.getSenderPublicKey(),
                    fanConnectionInformation.getSenderActorType(),
                    fanConnectionInformation.getDestinationPublicKey(),
                    fanConnectionInformation.getDestinationActorType()
            );

        } catch (final CantRequestConnectionException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : DISCONNECT.
     */
    @Override
    public final void disconnect(final UUID requestId) throws CantDisconnectException,
            ConnectionRequestNotFoundException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            fanActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            FanConnectionRequest connectionRequest = fanActorNetworkServiceDao.getConnectionRequest(requestId);

            if (connectionRequest.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(connectionRequest),
                        connectionRequest.getDestinationPublicKey(),
                        connectionRequest.getDestinationActorType(),
                        connectionRequest.getSenderPublicKey(),
                        connectionRequest.getSenderActorType()
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(connectionRequest),
                        connectionRequest.getSenderPublicKey(),
                        connectionRequest.getSenderActorType(),
                        connectionRequest.getDestinationPublicKey(),
                        connectionRequest.getDestinationActorType()
                );
            }

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException,
            ConnectionRequestNotFoundException {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            fanActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            FanConnectionRequest connectionRequest = fanActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    connectionRequest.getDestinationActorType(),
                    connectionRequest.getSenderPublicKey(),
                    connectionRequest.getSenderActorType()
            );

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : CANCEL.
     * - Protocol State: PROCESSING_SEND.
     *
     * We must to validate if the record is in PENDING_REMOTE_ACTION.
     */
    @Override
    public final void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException,
            ConnectionRequestNotFoundException  {

    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException,
            ConnectionRequestNotFoundException  {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            fanActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            FanConnectionRequest connectionRequest = fanActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    connectionRequest.getDestinationActorType(),
                    connectionRequest.getSenderPublicKey(),
                    connectionRequest.getSenderActorType()
            );

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request news with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<FanConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType) throws CantListPendingConnectionRequestsException {

        try {

            return fanActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request updates with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<FanConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            return fanActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        try {

            fanActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final PlatformComponentType identityType     ,
                             final String actorPublicKey   ,
                             final PlatformComponentType actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    pluginRoot.sendNewMessage(
                            pluginRoot.getProfileSenderToRequestConnection(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    identityType
                            ),
                            pluginRoot.getProfileDestinationToRequestConnection(
                                    actorPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    actorType
                            ),
                            jsonMessage
                    );
                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }


    private String buildJsonInformationMessage(final FanConnectionRequest aer) {

        return new InformationMessage(
                aer.getRequestId(),
                aer.getRequestAction()
        ).toJson();
    }

    private String buildJsonRequestMessage(final FanConnectionInformation aer) {

        return new RequestMessage(
                aer.getConnectionId(),
                aer.getSenderPublicKey(),
                aer.getSenderActorType(),
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                aer.getDestinationActorType(),
                ConnectionRequestAction.REQUEST,
                aer.getSendingTime()
        ).toJson();
    }

}
