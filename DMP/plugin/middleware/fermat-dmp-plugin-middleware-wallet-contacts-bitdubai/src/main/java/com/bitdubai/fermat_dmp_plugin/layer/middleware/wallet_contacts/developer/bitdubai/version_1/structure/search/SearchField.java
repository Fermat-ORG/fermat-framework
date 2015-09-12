package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactSearch;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lnacosta on 2015.09.04..
 */
public class SearchField {

    private String field;

    private DatabaseFilterType filterType;

    private String value;

    public SearchField(String field, DatabaseFilterType filterType, String value) {
        this.field = field;
        this.filterType = filterType;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public DatabaseFilterType getFilterType() {
        return filterType;
    }

    public String getValue() {
        return value;
    }
}
