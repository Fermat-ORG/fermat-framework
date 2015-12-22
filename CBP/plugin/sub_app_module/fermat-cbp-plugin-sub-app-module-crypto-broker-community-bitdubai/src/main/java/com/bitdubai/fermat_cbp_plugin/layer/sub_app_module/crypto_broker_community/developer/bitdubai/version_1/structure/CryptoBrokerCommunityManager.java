package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetSelectedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.CryptoBrokerCommunityManager</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerCommunityManager implements CryptoBrokerCommunitySubAppModuleManager {

    private final CryptoBrokerIdentityManager        cryptoBrokerIdentityManager           ;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager    ;
    private final CryptoBrokerManager                cryptoBrokerActorNetworkServiceManager;
    private final ErrorManager                       errorManager                          ;
    private final PluginVersionReference             pluginVersionReference                ;

    public CryptoBrokerCommunityManager(final CryptoBrokerIdentityManager        cryptoBrokerIdentityManager           ,
                                        final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager    ,
                                        final CryptoBrokerManager                cryptoBrokerActorNetworkServiceManager,
                                        final ErrorManager                       errorManager                          ,
                                        final PluginVersionReference             pluginVersionReference                ) {

        this.cryptoBrokerIdentityManager            = cryptoBrokerIdentityManager           ;
        this.cryptoBrokerActorConnectionManager     = cryptoBrokerActorConnectionManager    ;
        this.cryptoBrokerActorNetworkServiceManager = cryptoBrokerActorNetworkServiceManager;
        this.errorManager                           = errorManager                          ;
        this.pluginVersionReference                 = pluginVersionReference                ;
    }

    @Override
    public List<CryptoBrokerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {

        try {

            final List<CryptoBrokerCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<CryptoBrokerIdentity> cryptoBrokerIdentities = cryptoBrokerIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final CryptoBrokerIdentity cbi : cryptoBrokerIdentities)
                selectableIdentities.add(new SelectableIdentity(cbi));

            return selectableIdentities;

        } catch (final CantListCryptoBrokerIdentitiesException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Error in DAO trying to list identities.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public CryptoBrokerSearch searchCryptoBroker() {
        return null;
    }

    @Override
    public void requestConnectionToCryptoBroker(final CryptoBrokerCommunitySelectableIdentity selectedIdentity     ,
                                                final CryptoBrokerInformation                 cryptoBrokerToContact) throws CantRequestConnectionException          ,
                                                                                                                            ActorConnectionAlreadyRequestedException,
                                                                                                                            ActorTypeNotSupportedException          {

        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey()   ,
                    selectedIdentity.getActorType()   ,
                    selectedIdentity.getAlias()       ,
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    cryptoBrokerToContact.getPublicKey()   ,
                    Actors.CBP_CRYPTO_BROKER               ,
                    cryptoBrokerToContact.getAlias()       ,
                    cryptoBrokerToContact.getProfileImage()
            );

            cryptoBrokerActorConnectionManager.requestConnection(
                    actorSending  ,
                    actorReceiving
            );

        } catch (final CantRequestActorConnectionException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Error trying to request the actor connection.");
        } catch (final UnsupportedActorTypeException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorTypeNotSupportedException(e, "", "Actor type is not supported.");
        } catch (final ConnectionAlreadyRequestedException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorConnectionAlreadyRequestedException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void acceptCryptoBroker(final UUID requestId) throws CantAcceptRequestException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.acceptConnection(requestId);

        } catch (final CantAcceptActorConnectionRequestException |
                       UnexpectedConnectionStateException        e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Error trying to accept the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(final UUID requestId) throws CryptoBrokerConnectionDenialFailedException,
                                                            ConnectionRequestNotFoundException         {

        try {

            cryptoBrokerActorConnectionManager.denyConnection(requestId);

        } catch (final CantDenyActorConnectionRequestException |
                       UnexpectedConnectionStateException      e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Error trying to deny the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerConnectionDenialFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void disconnectCryptoBroker(final UUID requestId) throws CryptoBrokerDisconnectingFailedException,
                                                                    ConnectionRequestNotFoundException      {

        try {

            cryptoBrokerActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException   |
                       UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerDisconnectingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelCryptoBroker(final UUID requestId) throws CryptoBrokerCancellingFailedException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorConnectionManager.cancelConnection(requestId);

        } catch (final CantCancelActorConnectionRequestException |
                       UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CryptoBrokerCancellingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerInformation> getAllCryptoBrokers(String identityPublicKey, int max, int offset) throws CantListCryptoBrokersException {
        return null;
    }

    @Override
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingYourAcceptance(String identityPublicKey, int max, int offset) throws CantListCryptoBrokersException {
        return null;
    }

    @Override
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingTheirAcceptance(String identityPublicKey, int max, int offset) throws CantListCryptoBrokersException {
        return null;
    }

    @Override
    public CryptoBrokerCommunitySelectableIdentity getActiveCryptoBrokerIdentity() throws CantGetSelectedIdentityException {
        return null;
    }

    @Override
    public int getCryptoBrokersWaitingYourAcceptanceCount() {
        return 0;
    }

    @Override
    public void updateCryptoBrokerIdentity(String identityPublicKey, String identityAlias, String identityPhrase, byte[] profileImage) throws CantUpdateIdentityException {

    }

    @Override
    public void deleteCryptoBrokerIdentity(String identityPublicKey) throws CantListCryptoBrokersException {

    }

    @Override
    public boolean isActorConnected(String publicKey) throws CantValidateConnectionStateException {
        return false;
    }

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public CryptoBrokerCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        // todo implement
        return null;
    }
}
