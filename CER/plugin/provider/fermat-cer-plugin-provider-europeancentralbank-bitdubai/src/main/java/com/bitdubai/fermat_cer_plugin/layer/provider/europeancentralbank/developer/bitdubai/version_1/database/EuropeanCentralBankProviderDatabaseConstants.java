package com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.database.EuropeanCentralBankProviderDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class EuropeanCentralBankProviderDatabaseConstants {

    /**
     * Provider Info database table definition.
     */
    static final String PROVIDER_INFO_TABLE_NAME = "provider_info";

    static final String PROVIDER_INFO_ID_COLUMN_NAME = "id";
    static final String PROVIDER_INFO_NAME_COLUMN_NAME = "name";

    static final String PROVIDER_INFO_FIRST_KEY_COLUMN = "id";

    /**
     * Current Exchange Rates database table definition.
     */
    static final String CURRENT_EXCHANGE_RATES_TABLE_NAME = "current_exchange_rates";

    static final String CURRENT_EXCHANGE_RATES_ID_COLUMN_NAME = "id";
    static final String CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME = "from_currency";
    static final String CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME = "to_currency";
    static final String CURRENT_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME = "sale_price";
    static final String CURRENT_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME = "purchase_price";
    static final String CURRENT_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String CURRENT_EXCHANGE_RATES_FIRST_KEY_COLUMN = "id";

    /**
     * Daily Exchange Rates database table definition.
     */
    static final String DAILY_EXCHANGE_RATES_TABLE_NAME = "daily_exchange_rates";

    static final String DAILY_EXCHANGE_RATES_ID_COLUMN_NAME = "id";
    static final String DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME = "from_currency";
    static final String DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME = "to_currency";
    static final String DAILY_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME = "sale_price";
    static final String DAILY_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME = "purchase_price";
    static final String DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String DAILY_EXCHANGE_RATES_FIRST_KEY_COLUMN = "id";

}