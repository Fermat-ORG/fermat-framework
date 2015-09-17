package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceDatabaseConstants {

    /**
     * Contact Request database table definition.
     */
    static final String CONTACT_REQUEST_TABLE_NAME = "contact_request";

    static final String CONTACT_REQUEST_REQUEST_ID_COLUMN_NAME = "request_id";
    static final String CONTACT_REQUEST_WALLET_PUBLIC_KEY_TO_SEND_COLUMN_NAME = "wallet_public_key_to_send";
    static final String CONTACT_REQUEST_REFERENCE_WALLET_TO_SEND_COLUMN_NAME = "reference_wallet_to_send";
    static final String CONTACT_REQUEST_CRYPTO_ADDRESS_TO_SEND_COLUMN_NAME = "crypto_address_to_send";
    static final String CONTACT_REQUEST_REQUESTER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME = "requester_intra_user_public_key";
    static final String CONTACT_REQUEST_REQUESTER_INTRA_USER_NAME_COLUMN_NAME = "requester_intra_user_name";
    static final String CONTACT_REQUEST_REQUESTER_INTRA_USER_PROFILE_IMAGE_COLUMN_NAME = "requester_intra_user_profile_image";
    static final String CONTACT_REQUEST_WALLET_PUBLIC_KEY_ACCEPTING_REQUEST_COLUMN_NAME = "wallet_public_key_accepting_request";
    static final String CONTACT_REQUEST_REFERENCE_WALLET_ACCEPTING_REQUEST_COLUMN_NAME = "reference_wallet_accepting_request";
    static final String CONTACT_REQUEST_CRYPTO_ADDRESS_RECEIVED_COLUMN_NAME = "crypto_address_received";
    static final String CONTACT_REQUEST_INTRA_USER_PUBLIC_KEY_ACCEPTING_REQUEST_COLUMN_NAME = "intra_user_public_key_accepting_request";
    static final String CONTACT_REQUEST_STATE_COLUMN_NAME = "state";

    static final String CONTACT_REQUEST_FIRST_KEY_COLUMN = "request_id";

}
