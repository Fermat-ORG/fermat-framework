package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressDeniedActionException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedActionException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewsEventException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>WalletContactsMiddlewareRegistry</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 * @version 1.0
 */
public class WalletContactsMiddlewareRegistry implements WalletContactsRegistry {

    private final CryptoAddressesManager cryptoAddressesManager;
    private final ErrorManager errorManager          ;
    private final LogManager             logManager            ;
    private final PluginDatabaseSystem   pluginDatabaseSystem  ;
    private final UUID                   pluginId              ;

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    public WalletContactsMiddlewareRegistry(final CryptoAddressesManager cryptoAddressesManager,
                                            final ErrorManager           errorManager          ,
                                            final LogManager             logManager            ,
                                            final PluginDatabaseSystem   pluginDatabaseSystem  ,
                                            final UUID                   pluginId              ) {

        this.cryptoAddressesManager = cryptoAddressesManager;
        this.errorManager           = errorManager          ;
        this.logManager             = logManager            ;
        this.pluginDatabaseSystem   = pluginDatabaseSystem  ;
        this.pluginId               = pluginId              ;
    }

    public void initialize() throws CantInitializeWalletContactsMiddlewareDatabaseException {

        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry initializing...", null, null);

        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(pluginDatabaseSystem, pluginId);
        walletContactsMiddlewareDao.initialize();

        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry initialized Successfully.", null, null);
    }

    @Override
    public WalletContactRecord createWalletContact(String              actorPublicKey ,
                                                   String              actorAlias     ,
                                                   String              actorFirstName ,
                                                   String              actorLastName  ,
                                                   Actors              actorType      ,
                                                   List<CryptoAddress> cryptoAddresses,
                                                   String              walletPublicKey) throws CantCreateWalletContactException {
        try {

            UUID contactId = UUID.randomUUID();

            return walletContactsMiddlewareDao.createWalletContact(
                    contactId      ,
                    actorPublicKey ,
                    actorAlias     ,
                    actorFirstName ,
                    actorLastName  ,
                    actorType      ,
                    cryptoAddresses,
                    walletPublicKey
            );

        } catch (CantCreateWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord createWalletContact(String              actorPublicKey ,
                                                   String              actorAlias     ,
                                                   String              actorFirstName ,
                                                   String              actorLastName  ,
                                                   Actors              actorType      ,
                                                   String              walletPublicKey) throws CantCreateWalletContactException {
        try {

            UUID contactId = UUID.randomUUID();

            return walletContactsMiddlewareDao.createWalletContact(
                    contactId      ,
                    actorPublicKey ,
                    actorAlias     ,
                    actorFirstName ,
                    actorLastName  ,
                    actorType      ,
                    new ArrayList<CryptoAddress>(),
                    walletPublicKey
            );

        } catch (CantCreateWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateWalletContact(UUID                contactId      ,
                                    String              actorAlias     ,
                                    String              actorFirstName ,
                                    String              actorLastName  ,
                                    List<CryptoAddress> cryptoAddresses) throws CantUpdateWalletContactException,
                                                                                WalletContactNotFoundException  {

        try {
            walletContactsMiddlewareDao.updateWalletContact(
                    new WalletContactsMiddlewareRecord(
                            contactId,
                            actorAlias,
                            actorFirstName,
                            actorLastName,
                            cryptoAddresses
                    )
            );
        } catch (CantUpdateWalletContactException |
                 WalletContactNotFoundException   e ){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException,
                                                           WalletContactNotFoundException  {

        try {
            walletContactsMiddlewareDao.deleteWalletContact(contactId);

        } catch (CantDeleteWalletContactException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactsSearch searchWalletContact(String walletPublicKey) {

        return new WalletContactsMiddlewareSearch(
                errorManager               ,
                walletContactsMiddlewareDao,
                walletPublicKey
        );
    }

    @Override
    public WalletContactRecord getWalletContactByActorAndWalletPublicKey(String actorPublicKey ,
                                                                         String walletPublicKey) throws CantGetWalletContactException ,
                                                                                                        WalletContactNotFoundException {

        try {

            return walletContactsMiddlewareDao.findWalletContactByActorAndWalletPublicKey(
                    actorPublicKey,
                    walletPublicKey
            );

        } catch (CantGetWalletContactException |
                 WalletContactNotFoundException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException,
                                                                                  WalletContactNotFoundException {

        try {
            return walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
        } catch (CantGetWalletContactException  |
                 WalletContactNotFoundException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord getWalletContactByAliasAndWalletPublicKey(String actorAlias     ,
                                                                         String walletPublicKey) throws CantGetWalletContactException, WalletContactNotFoundException {

        try {
            return walletContactsMiddlewareDao.findWalletContactByAliasAndWalletPublicKey(
                    actorAlias,
                    walletPublicKey
            );

        } catch (CantGetWalletContactException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (WalletContactNotFoundException e){

            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void addCryptoAddressToWalletContact(UUID          contactId    ,
                                                CryptoAddress cryptoAddress) throws CantAddCryptoAddressException ,
                                                                                    WalletContactNotFoundException {

        try {
            // check if exists
            walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
            // add crypto address
            walletContactsMiddlewareDao.addCryptoAddress(
                    contactId,
                    cryptoAddress
            );

        } catch (CantAddCryptoAddressException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (CantGetWalletContactException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        } catch (WalletContactNotFoundException e){

            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteCryptoAddressToWalletContact(UUID          contactId    ,
                                                   CryptoAddress cryptoAddress) throws CantDeleteCryptoAddressException,
                                                                                       WalletContactNotFoundException  {

        try {
            // check if exists
            walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
            // delete crypto address
            walletContactsMiddlewareDao.deleteCryptoAddress(
                    contactId,
                    cryptoAddress
            );

        } catch (CantDeleteCryptoAddressException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (WalletContactNotFoundException e){

            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteCryptoAddressException(CantDeleteCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void addCryptoAddressToWalletContact(String        actorPublicKey ,
                                                String        walletPublicKey,
                                                CryptoAddress cryptoAddress  ) throws CantAddCryptoAddressException,
                                                                                      WalletContactNotFoundException {

        try {
            // get contact id if exists
            WalletContactRecord walletContactRecord = walletContactsMiddlewareDao.findWalletContactByActorAndWalletPublicKey(
                    actorPublicKey,
                    walletPublicKey
            );
            // add crypto address
            walletContactsMiddlewareDao.addCryptoAddress(
                    walletContactRecord.getContactId(),
                    cryptoAddress
            );

        } catch (CantAddCryptoAddressException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (WalletContactNotFoundException e){

            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    public void handleCryptoAddressesNewsEvent() throws CantHandleCryptoAddressesNewsEventException {

        try {
            final List<CryptoAddressRequest> list = cryptoAddressesManager.listPendingCryptoAddressRequests();

            System.out.println("----------------------------\n" +
                    "WALLET CONTACT MIDDLEWARE  : handleCryptoAddressesNewsEvent " + list.size()
                    + "\n-------------------------------------------------");

            for (final CryptoAddressRequest request : list) {

                if (request.getAction().equals(RequestAction.ACCEPT))
                    this.handleCryptoAddressReceivedEvent(request);

                if (request.getAction().equals(RequestAction.DENY))
                    this.handleCryptoAddressDeniedEvent(request);

            }

        } catch(CantListPendingCryptoAddressRequestsException |
                CantHandleCryptoAddressDeniedActionException |
                CantHandleCryptoAddressReceivedActionException e) {

            throw new CantHandleCryptoAddressesNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        }
    }

    public void handleCryptoAddressReceivedEvent(final CryptoAddressRequest request) throws CantHandleCryptoAddressReceivedActionException {

        try {
            try {
                // search wallet contact
                // if i can't find it (WalletContactNotFound) i confirm the request ....
                // else i add the crypto address received.
                WalletContactRecord walletContactRecord = this.getWalletContactByActorAndWalletPublicKey(
                        request.getIdentityPublicKeyResponding(),
                        request.getWalletPublicKey()
                );

                this.addCryptoAddressToWalletContact(
                    walletContactRecord.getContactId(),
                    request.getCryptoAddress()
                );

                System.out.println("----------------------------\n" +
                        "ACTUALIZO ADDRESS PARA EL CONTACTO :" +  walletContactRecord.getContactId()
                        + "\n-------------------------------------------------");

                walletContactsMiddlewareDao.updateCompatibility(
                        walletContactRecord.getContactId(),
                        Compatibility.COMPATIBLE
                );

                cryptoAddressesManager.confirmAddressExchangeRequest(request.getRequestId());

            } catch (WalletContactNotFoundException e) {

                // TODO IF I DON'T FIND A WALLET CONTACT, I DELETE THE REQUEST ???
                cryptoAddressesManager.confirmAddressExchangeRequest(request.getRequestId());
            } catch (CantAddCryptoAddressException e) {

                throw new CantHandleCryptoAddressReceivedActionException(e, "Can't add the crypto address to the existent wallet contact.");
            } catch (CantGetWalletContactException e) {

                throw new CantHandleCryptoAddressReceivedActionException(e, "Can't get wallet contact.");
            }
        } catch (PendingRequestNotFoundException e) {
            // TODO what to do here?
            throw new CantHandleCryptoAddressReceivedActionException(e, "Can't find the pending request when confirming.");
        } catch (CantConfirmAddressExchangeRequestException e) {

            throw new CantHandleCryptoAddressReceivedActionException(e, "Can't confirm address exchange request.");
        } catch (CantUpdateWalletContactException e) {

            throw new CantHandleCryptoAddressReceivedActionException(e, "Can't update wallet contact.");
        }
    }

    public void handleCryptoAddressDeniedEvent(final CryptoAddressRequest request) throws CantHandleCryptoAddressDeniedActionException {

        try {
            try {
                // search wallet contact
                // if i can't find it (WalletContactNotFound) i confirm the request ....
                // else i add the crypto address received.
                WalletContactRecord walletContactRecord = this.getWalletContactByActorAndWalletPublicKey(
                        request.getIdentityPublicKeyRequesting(),
                        request.getWalletPublicKey()
                );

                this.addCryptoAddressToWalletContact(
                        walletContactRecord.getContactId(),
                        request.getCryptoAddress()
                );

                walletContactsMiddlewareDao.updateCompatibility(
                        walletContactRecord.getContactId(),
                        Compatibility.INCOMPATIBLE
                );

                cryptoAddressesManager.confirmAddressExchangeRequest(request.getRequestId());

            } catch (WalletContactNotFoundException e) {

                // TODO IF I DON'T FIND A WALLET CONTACT, I DELETE THE REQUEST ???
                cryptoAddressesManager.confirmAddressExchangeRequest(request.getRequestId());
            } catch (CantAddCryptoAddressException e) {

                throw new CantHandleCryptoAddressDeniedActionException(e, "Can't add the crypto address to the existent wallet contact.");
            } catch (CantGetWalletContactException e) {

                throw new CantHandleCryptoAddressDeniedActionException(e, "Can't get wallet contact.");
            }
        } catch (PendingRequestNotFoundException e) {
            // TODO what to do here?
            throw new CantHandleCryptoAddressDeniedActionException(e, "Can't find the pending request when confirming.");
        } catch (CantConfirmAddressExchangeRequestException e) {

            throw new CantHandleCryptoAddressDeniedActionException(e, "Can't confirm address exchange request.");
        } catch (CantUpdateWalletContactException e) {

            throw new CantHandleCryptoAddressDeniedActionException(e, "Can't update wallet contact.");
        }
    }
}
