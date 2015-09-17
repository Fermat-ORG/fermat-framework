package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 * @version 1.0
 */
public class WalletContactsMiddlewareRegistry implements WalletContactsRegistry {

    private ErrorManager errorManager;

    private LogManager logManager;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    public WalletContactsMiddlewareRegistry(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * i will initialize the database for working with the registry
     *
     * @throws CantInitializeWalletContactsMiddlewareDatabaseException if something goes wrong
     */
    public void initialize() throws CantInitializeWalletContactsMiddlewareDatabaseException {
        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry initializing...", null, null);

        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(pluginDatabaseSystem, pluginId);
        walletContactsMiddlewareDao.initialize();

        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry initialized Successfully.", null, null);
    }

    @Override
    public WalletContactRecord createWalletContact(String actorPublicKey,
                                                   String actorAlias,
                                                   String actorFirstName,
                                                   String actorLastName,
                                                   Actors actorType,
                                                   List<CryptoAddress> cryptoAddresses,
                                                   String walletPublicKey) throws CantCreateWalletContactException {
        try {

            UUID contactId = UUID.randomUUID();

            return walletContactsMiddlewareDao.createWalletContact(
                    contactId,
                    actorPublicKey,
                    actorAlias,
                    actorFirstName,
                    actorLastName,
                    actorType,
                    cryptoAddresses,
                    walletPublicKey
            );

        } catch (CantCreateWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId,
                                    String actorAlias,
                                    String actorFirstName,
                                    String actorLastName,
                                    List<CryptoAddress> cryptoAddresses) throws CantUpdateWalletContactException, WalletContactNotFoundException {
        try {
            walletContactsMiddlewareDao.updateWalletContact(new WalletContactsMiddlewareRecord(contactId, actorAlias, actorFirstName, actorLastName, cryptoAddresses));
        } catch (CantUpdateWalletContactException | WalletContactNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException, WalletContactNotFoundException {
        try {
            walletContactsMiddlewareDao.deleteWalletContact(contactId);
        } catch (CantDeleteWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactsSearch searchWalletContact(String walletPublicKey) {
        return new WalletContactsMiddlewareSearch(errorManager, walletContactsMiddlewareDao, walletPublicKey);
    }

    @Override
    public WalletContactRecord getWalletContactByActorAndWalletPublicKey(String actorPublicKey,
                                                                         String walletPublicKey) throws CantGetWalletContactException, WalletContactNotFoundException {
        try {
            return walletContactsMiddlewareDao.findWalletContactByActorAndWalletPublicKey(actorPublicKey, walletPublicKey);
        } catch (CantGetWalletContactException | WalletContactNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException, WalletContactNotFoundException {
        try {
            return walletContactsMiddlewareDao.findWalletContactByContactId(contactId);
        } catch (CantGetWalletContactException | WalletContactNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord getWalletContactByAliasAndWalletPublicKey(String actorAlias,
                                                                         String walletPublicKey) throws CantGetWalletContactException, WalletContactNotFoundException {
        try {
            return walletContactsMiddlewareDao.findWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
        } catch (CantGetWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (WalletContactNotFoundException e){
            throw e;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }
}