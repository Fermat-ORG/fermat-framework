package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;

/**
 * Created by lnacosta on 2015.09.04..
 */
public class SearchOrder {

    private String field;

    private DatabaseFilterOrder filterOrder;

    public SearchOrder(String field, DatabaseFilterOrder filterOrder) {
        this.field = field;
        this.filterOrder = filterOrder;
    }

    public String getField() {
        return field;
    }

    public DatabaseFilterOrder getFilterOrder() {
        return filterOrder;
    }
}
