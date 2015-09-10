package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactSearch;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchField;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchOrder;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactMiddlewareSearch</code>
 * haves all the methods that interact with the search of wallet contacts.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/09/2015.
 *
 * @version 1.0
 */
public class WalletContactMiddlewareSearch implements WalletContactSearch {

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    private ErrorManager errorManager;

    private List<SearchField> searchFields = new ArrayList<>();

    private List<SearchOrder> searchOrders = new ArrayList<>();

    public WalletContactMiddlewareSearch(ErrorManager errorManager, WalletContactsMiddlewareDao walletContactsMiddlewareDao) {
        this.errorManager = errorManager;
        this.walletContactsMiddlewareDao = walletContactsMiddlewareDao;
    }

    @Override
    public void setActorAlias(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME;
        searchFields.add(new SearchField(field, DatabaseFilterType.LIKE, value));
        if (order)
            searchOrders.add(new SearchOrder(field, DatabaseFilterOrder.ASCENDING));
    }

    @Override
    public void setActorFirstName(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME;
        searchFields.add(new SearchField(field, DatabaseFilterType.LIKE, value));
        if (order)
            searchOrders.add(new SearchOrder(field, DatabaseFilterOrder.ASCENDING));
    }

    @Override
    public void setActorLastName(String value, boolean order) {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME;
        searchFields.add(new SearchField(field, DatabaseFilterType.LIKE, value));
        if (order)
            searchOrders.add(new SearchOrder(field, DatabaseFilterOrder.ASCENDING));
    }

    @Override
    public List<WalletContactRecord> getResult(String walletPublicKey) throws CantGetAllWalletContactsException {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME;
        searchFields.add(new SearchField(field, DatabaseFilterType.EQUAL, walletPublicKey));

        try {
            return walletContactsMiddlewareDao.listWalletContactRecords(searchFields, searchOrders);
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<WalletContactRecord> getResult(String walletPublicKey, int max, int offset) throws CantGetAllWalletContactsException {
        String field = WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME;
        searchFields.add(new SearchField(field, DatabaseFilterType.EQUAL, walletPublicKey));

        try {
            return walletContactsMiddlewareDao.listWalletContactRecords(searchFields, searchOrders, max, offset);
        } catch (CantGetAllWalletContactsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }
}
