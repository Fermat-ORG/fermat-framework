package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database;

/**
 * The Class <code>WalletContactsMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactsMiddlewareDatabaseConstants {

    /**
     * Wallet Contacts database table definition.
     */
    public static final String WALLET_CONTACTS_TABLE_NAME                    = "wallet_contacts"  ;

    public static final String WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME        = "contact_id"       ;
    public static final String WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME  = "actor_public_key" ;
    public static final String WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME        = "actor_type"       ;
    public static final String WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME       = "actor_alias"      ;
    public static final String WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME  = "actor_first_name" ;
    public static final String WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME   = "actor_last_name"  ;
    public static final String WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME     = "compatibility"    ;

    public static final String WALLET_CONTACTS_FIRST_KEY_COLUMN              = "contact_id"       ;

    /**
     * Wallet Contact Addresses database table definition.
     */
    public static final String WALLET_CONTACT_ADDRESSES_TABLE_NAME                  = "wallet_contact_addresses";

    public static final String WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME      = "contact_id"              ;
    public static final String WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME  = "crypto_address"          ;
    public static final String WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency"         ;
    public static final String WALLET_CONTACT_ADDRESSES_TIME_STAMP_COLUMN_NAME      = "time_stamp"              ;

    public static final String WALLET_CONTACT_ADDRESSES_FIRST_KEY_COLUMN            = "crypto_address"          ;

}