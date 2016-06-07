package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletDatabaseConstant {
    /**
     * Asset Wallet User database table definition.
     */
    public static final String ASSET_WALLET_USER_TABLE_NAME = "AssetUserWallet";
    public static final String ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME = "Id";
    // This second Id is used to verify that that the same transaction is not applied twice.
    // We can't use the transaction hash because some credit/debit operations do not involve a hash
    public static final String ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME = "VerificationId";
    public static final String ASSET_WALLET_USER_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_USER_ASSET_CRYPTO_ADDRESS_COLUMN_NAME = "cryptoAddress";
    public static final String ASSET_WALLET_USER_ADDRESS_FROM_COLUMN_NAME = "addressFrom";
    public static final String ASSET_WALLET_USER_ADDRESS_TO_COLUMN_NAME = "addressTo";
    public static final String ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME = "actorFrom";
    public static final String ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME = "actorTo";
    public static final String ASSET_WALLET_USER_ACTOR_FROM_TYPE_COLUMN_NAME = "actorFromType";
    public static final String ASSET_WALLET_USER_ACTOR_TO_TYPE_COLUMN_NAME = "actorToType";
    public static final String ASSET_WALLET_USER_AMOUNT_COLUMN_NAME = "amount";
    public static final String ASSET_WALLET_USER_TYPE_COLUMN_NAME = "type";
    public static final String ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME = "balanceType";
    public static final String ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String ASSET_WALLET_USER_MEMO_COLUMN_NAME = "memo";
    public static final String ASSET_WALLET_USER_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";
    public static final String ASSET_WALLET_USER_RUNNING_BOOK_BALANCE_COLUMN_NAME = "runningBookBalance";
    public static final String ASSET_WALLET_USER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "runningAvailableBalance";


    // tabla nueva movimientos del resumen de los Assets - balance y book balance, id
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_NAME = "AssetUserWalletTotalBalances";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_NAME_COLUMN_NAME = "name";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME = "description";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME = "availableBalance";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME = "bookBalance";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME = "quantityAvailableBalance";
    public static final String ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME = "QuantityBookBalance";


    public static final String ASSET_WALLET_USER_METADATA_LOCK_TABLE_NAME = "MetadataLock";
    public static final String ASSET_WALLET_USER_METADATA_LOCK_METADATA_ID_COLUMN_NAME = "metadataId";
    public static final String ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME = "genesisTx";
    public static final String ASSET_WALLET_USER_METADATA_LOCK_STATUS_COLUMN_NAME = "status";


    public static final String ASSET_WALLET_USER_ADDRESSES_TABLE_NAME = "AssetAddresses";
    public static final String ASSET_WALLET_USER_ADDRESSES_ID_COLUMN_NAME = "id";
    public static final String ASSET_WALLET_USER_ADDRESSES_ASSET_PUBLICKEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME = "cryptoAddress";
    public static final String ASSET_WALLET_USER_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME = "cryptoCurrency";
    public static final String ASSET_WALLET_USER_ADDRESSES_AVAILABLE_COLUMN_NAME = "available";


}
