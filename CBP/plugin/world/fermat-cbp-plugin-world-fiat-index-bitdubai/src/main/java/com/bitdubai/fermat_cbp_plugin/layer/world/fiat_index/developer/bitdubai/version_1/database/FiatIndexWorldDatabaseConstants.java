package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.database.FiatIndexWorldDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 21/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FiatIndexWorldDatabaseConstants {

    /**
     * Fiat Index database table definition.
     */
    static final String FIAT_INDEX_TABLE_NAME = "fiat_index";

    static final String FIAT_INDEX_ID_COLUMN_NAME = "id";
    static final String FIAT_INDEX_CURRENCY_COLUMN_NAME = "currency";
    static final String FIAT_INDEX_SALE_PRICE_COLUMN_NAME = "sale_price";
    static final String FIAT_INDEX_PURCHASE_PRICE_COLUMN_NAME = "purchase_price";
    static final String FIAT_INDEX_PROVIDER_DESCRIPTION_COLUMN_NAME = "provider description";
    static final String FIAT_INDEX_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String FIAT_INDEX_FIRST_KEY_COLUMN = "transaction_id";

}