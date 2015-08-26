package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 * @version 1.0
 */
public class WalletContactsMiddlewareRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, WalletContactsRegistry {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variable
     */
    private UUID pluginId;

    /*
     * Represents the DAO i'll be working with
     */
    WalletContactsMiddlewareDao walletContactsMiddlewareDao;


    public void initialize() throws CantInitializeWalletContactsDatabaseException {
        /**
         * I will try to create and initialize a new  DAO
         */
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(pluginDatabaseSystem);
        walletContactsMiddlewareDao.initializeDatabase(pluginId, pluginId.toString());

    }

    /**
     * bring all contacts from the selected wallet
     * @param walletPublicKey wallet's id
     */
    @Override
    public List<WalletContactRecord> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException {
        List<WalletContactRecord> walletContactRecords;
        try {
            walletContactRecords = walletContactsMiddlewareDao.findAll(walletPublicKey);
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
        return walletContactRecords;
    }

    /**
     * bring all contacts from the selected wallet limited by parameter
     * @param walletPublicKey wallet's id
     * @param max number of entities to get
     * @param offset position in the list for pagination
     * @return List<WalletContactRecord> walletsContacts with all attributes
     * @throws CantGetAllWalletContactsException
     */
    @Override
    public List<WalletContactRecord> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException {
        List<WalletContactRecord> walletContactRecords;
        try {
            walletContactRecords = walletContactsMiddlewareDao.findAllScrolling(walletPublicKey, max, offset);
            return walletContactRecords;
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    /**
     * This method create a contact
     * @param actorId actor's id
     * @param actorName actor's name
     * @param actorType actor's actorType (extra-intra-device)
     * @param receivedCryptoAddress received cryptoAddress
     * @param walletPublicKey wallet's id
     * @return WalletContactRecord
     */
    @Override
    public WalletContactRecord createWalletContact(UUID actorId, String actorName, Actors actorType, CryptoAddress receivedCryptoAddress, String walletPublicKey) throws CantCreateWalletContactException {

        WalletContactRecord walletContactRecord;
        try {
            UUID contactId = UUID.randomUUID();
            walletContactRecord = new WalletContactsMiddlewareRecord(actorId, actorName, actorType, contactId, receivedCryptoAddress, walletPublicKey);
            walletContactsMiddlewareDao.create(walletContactRecord);
            return walletContactRecord;
        } catch (CantCreateWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException {
        try {
            WalletContactRecord walletContactRecord = new WalletContactsMiddlewareRecord(contactId, receivedCryptoAddress, actorName);
            walletContactsMiddlewareDao.update(walletContactRecord);
        } catch (CantUpdateWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        try {
            walletContactsMiddlewareDao.delete(contactId);
        } catch (CantDeleteWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public WalletContactRecord getWalletContactByNameAndWalletPublicKey(String actorName, String walletPublicKey) throws CantGetWalletContactException, WalletContactNotFoundException {
        WalletContactRecord walletContactRecord;
        try {
            walletContactRecord = walletContactsMiddlewareDao.findByNameAndWalletPublicKey(actorName, walletPublicKey);
            return walletContactRecord;
        } catch (CantGetWalletContactException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (WalletContactNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new CantGetWalletContactException(CantGetWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<WalletContactRecord> getWalletContactByNameContainsAndWalletPublicKey(String actorName, String walletPublicKey) throws CantGetWalletContactException {
        List<WalletContactRecord> walletContactRecordList;
        try {
            walletContactRecordList = walletContactsMiddlewareDao.findByNameContainsAndWalletPublicKey(actorName, walletPublicKey);
            return walletContactRecordList;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletContactRecord getWalletContactByActorId(UUID actorId) throws CantGetWalletContactException, WalletContactNotFoundException {
        try {
            return walletContactsMiddlewareDao.findByActorId(actorId);
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletContactRecord getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException, WalletContactNotFoundException {
        WalletContactRecord walletContactRecord;
        try {
            walletContactRecord = walletContactsMiddlewareDao.findByContactId(contactId);
            return walletContactRecord;
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }


    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity Interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

}