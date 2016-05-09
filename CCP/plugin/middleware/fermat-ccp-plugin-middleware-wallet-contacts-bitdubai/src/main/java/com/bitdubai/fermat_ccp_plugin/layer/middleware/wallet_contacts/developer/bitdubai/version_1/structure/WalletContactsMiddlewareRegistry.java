package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
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
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
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
    private final Broadcaster            broadcaster;

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    public WalletContactsMiddlewareRegistry(final CryptoAddressesManager cryptoAddressesManager,
                                            final ErrorManager           errorManager          ,
                                            final LogManager             logManager            ,
                                            final PluginDatabaseSystem   pluginDatabaseSystem  ,
                                            final UUID                   pluginId              ,
                                            final Broadcaster            broadcaster
                                            ) {

        this.cryptoAddressesManager = cryptoAddressesManager;
        this.errorManager           = errorManager          ;
        this.logManager             = logManager            ;
        this.pluginDatabaseSystem   = pluginDatabaseSystem  ;
        this.pluginId               = pluginId              ;
        this.broadcaster            = broadcaster ;
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
                                                   HashMap<BlockchainNetworkType,CryptoAddress> cryptoAddresses,
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
                                                   String              walletPublicKey,
                                                   BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException {
        try {

            UUID contactId = UUID.randomUUID();

            return walletContactsMiddlewareDao.createWalletContact(
                    contactId      ,
                    actorPublicKey ,
                    actorAlias     ,
                    actorFirstName ,
                    actorLastName  ,
                    actorType      ,
                    new HashMap<BlockchainNetworkType, CryptoAddress>(),
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
                                    HashMap<BlockchainNetworkType,CryptoAddress> cryptoAddresses) throws CantUpdateWalletContactException,
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

        } catch (WalletContactNotFoundException | CantGetWalletContactException e){
            throw e;
        } catch (Exception e){
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
                                                CryptoAddress cryptoAddress,
                                                BlockchainNetworkType blockchainNetworkType) throws CantAddCryptoAddressException ,
                                                                                    WalletContactNotFoundException {

        try {
            // check if exists
            WalletContactRecord walletContactRecord =  walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
            // add crypto address
            walletContactRecord.getCryptoAddresses().put(blockchainNetworkType, cryptoAddress);
            walletContactsMiddlewareDao.updateWalletContact(walletContactRecord);

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
                                                   CryptoAddress cryptoAddress,
                                                   BlockchainNetworkType blockchainNetworkType) throws CantDeleteCryptoAddressException,
                                                                                       WalletContactNotFoundException  {

        try {
            // check if exists
            walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
            // delete crypto address
            walletContactsMiddlewareDao.deleteCryptoAddress(
                    contactId,
                    cryptoAddress,
                    blockchainNetworkType
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
                                                CryptoAddress cryptoAddress,
                                                BlockchainNetworkType blockchainNetworkType) throws CantAddCryptoAddressException,
            WalletContactNotFoundException {

        try {
            // get contact id if exists
            WalletContactRecord walletContactRecord = walletContactsMiddlewareDao.findWalletContactByActorAndWalletPublicKey(
                    actorPublicKey,
                    walletPublicKey
            );

            walletContactRecord.getCryptoAddresses().put(blockchainNetworkType,cryptoAddress);
            // add crypto address
            walletContactsMiddlewareDao.updateWalletContact(walletContactRecord);

        } catch ( CantGetWalletContactException e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            try {
                throw e;
            } catch (CantGetWalletContactException e1) {
                e1.printStackTrace();
            }
        } catch (WalletContactNotFoundException e){

            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddCryptoAddressException(CantAddCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    public void handleCryptoAddressesNewsEvent() throws CantHandleCryptoAddressesNewsEventException {

        try {
            final List<CryptoAddressRequest> list = cryptoAddressesManager.listAllPendingRequests();


            for (final CryptoAddressRequest request : list) {


                if (request.getCryptoAddressDealer().equals(CryptoAddressDealers.CRYPTO_WALLET)) {

                    switch (request.getAction()){
                        case ACCEPT:
                            this.handleCryptoAddressReceivedEvent(request);
                            break;
                        case DENY:
                            this.handleCryptoAddressDeniedEvent(request);
                            break;
                        case RECEIVED:
                            this.handleCryptoAddressReceivedEvent(request);
                            break;
                        default:
                            //TODO: mejorar esto que es una mierda por favor
//                            if(request.getCryptoAddress()!=null) {
//                                if(request.getCryptoAddress().getAddress()!=null)
//                                    this.handleCryptoAddressReceivedEvent(request);
//                            }
                            break;
                    }
                    if(request.getCryptoAddress()!=null) {
                        if (request.getCryptoAddress().getAddress() != null)
                            if (request.getIdentityTypeResponding() == Actors.CCP_INTRA_WALLET_USER)
                                cryptoAddressesManager.markReceivedRequest(request.getRequestId());
                    }

                }

            }


        } catch(CantListPendingCryptoAddressRequestsException |
                CantHandleCryptoAddressDeniedActionException |
                CantHandleCryptoAddressReceivedActionException e) {

            throw new CantHandleCryptoAddressesNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        } catch (CantConfirmAddressExchangeRequestException e) {
            e.printStackTrace();
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
                // SI ES WALLET CONTACT ES NULL PASA DIRECTO A CONFIRMAR EL ADDRESS YA QUE ES SOLO UN REQUEST DE ADDRESS
                // SI POR EL CONTRARIO NO ES NULL ACTUALIZA EL CONTACTO CON EL ADDRESS
                if (walletContactRecord != null){

                    this.addCryptoAddressToWalletContact(
                            walletContactRecord.getContactId(),
                            request.getCryptoAddress(),
                            request.getBlockchainNetworkType()
                    );

                    System.out.println("----------------------------\n" +
                            "ACTUALIZO ADDRESS PARA EL CONTACTO :" + walletContactRecord.getContactId()
                            + "\n-------------------------------------------------");

                    walletContactsMiddlewareDao.updateCompatibility(
                            walletContactRecord.getContactId(),
                            Compatibility.COMPATIBLE
                    );

                    this.broadcaster.publish(BroadcasterType.UPDATE_VIEW, walletContactRecord.getWalletPublicKey(),walletContactRecord.getContactId().toString() );

                }



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
                        request.getCryptoAddress(),
                        request.getBlockchainNetworkType()
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
