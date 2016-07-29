package com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database.DolarTodayProviderDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DolarTodayProviderDatabaseConstants {

    /**
     * Provider Info database table definition.
     */
    static final String PROVIDER_INFO_TABLE_NAME = "provider_info";

    static final String PROVIDER_INFO_ID_COLUMN_NAME = "id";
    static final String PROVIDER_INFO_NAME_COLUMN_NAME = "name";

    static final String PROVIDER_INFO_FIRST_KEY_COLUMN = "id";

    /**
     * Query History database table definition.
     */
    static final String QUERY_HISTORY_TABLE_NAME = "query_history";

    static final String QUERY_HISTORY_ID_COLUMN_NAME = "id";
    static final String QUERY_HISTORY_FROM_CURRENCY_COLUMN_NAME = "from_currency";
    static final String QUERY_HISTORY_TO_CURRENCY_COLUMN_NAME = "to_currency";
    static final String QUERY_HISTORY_SALE_PRICE_COLUMN_NAME = "sale_price";
    static final String QUERY_HISTORY_PURCHASE_PRICE_COLUMN_NAME = "purchase_price";
    static final String QUERY_HISTORY_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String QUERY_HISTORY_FIRST_KEY_COLUMN = "id";

}