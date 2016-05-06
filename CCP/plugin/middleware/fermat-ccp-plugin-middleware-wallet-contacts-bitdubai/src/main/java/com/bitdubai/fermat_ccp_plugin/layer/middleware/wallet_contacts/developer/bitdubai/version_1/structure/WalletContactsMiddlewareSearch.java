package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseConstants;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * The interface <code>WalletContactsMiddlewareSearch</code>
 * haves all the methods that interact with the search of wallet contacts.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/09/2015.
 *
 * @version 1.0
 */
public class WalletContactsMiddlewareSearch implements WalletContactsSearch {

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    private ErrorManager errorManager;

    private DatabaseTable walletContactsTable;

    private String walletPublicKey;

    public WalletContactsMiddlewareSearch(ErrorManager errorManager, WalletContactsMiddlewareDao walletContactsMiddlewareDao, String walletPublicKey) {
        this.errorManager = errorManager;
        this.walletContactsMiddlewareDao = walletContactsMiddlewareDao;
        this.walletContactsTable = walletContactsMiddlewareDao.getWalletContactsTable();
        this.walletPublicKey = walletPublicKey;

        this.resetFilters();
    }

    @Override
    public void resetFilters() {
        this.walletContactsTable = walletContactsMiddlewareDao.getWalletContactsTable();
        walletContactsTable.addStringFilter(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
    }

    @Override
    public void setActorAlias(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME;
        walletContactsTable.addStringFilter(field, value, DatabaseFilterType.LIKE);
        if (order)
            walletContactsTable.addFilterOrder(field, DatabaseFilterOrder.ASCENDING);
    }

    @Override
    public void setActorFirstName(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME;
        walletContactsTable.addStringFilter(field, value, DatabaseFilterType.LIKE);
        if (order)
            walletContactsTable.addFilterOrder(field, DatabaseFilterOrder.ASCENDING);
    }

    @Override
    public void setActorLastName(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME;
        walletContactsTable.addStringFilter(field, value, DatabaseFilterType.LIKE);
        if (order)
            walletContactsTable.addFilterOrder(field, DatabaseFilterOrder.ASCENDING);
    }

    @Override
    public List<WalletContactRecord> getResult() throws CantGetAllWalletContactsException {
        try {
            return walletContactsMiddlewareDao.listWalletContactRecords(walletContactsTable);
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<WalletContactRecord> getResult(int max, int offset) throws CantGetAllWalletContactsException {
        try {
            return walletContactsMiddlewareDao.listWalletContactRecords(walletContactsTable, max, offset);
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }
}
