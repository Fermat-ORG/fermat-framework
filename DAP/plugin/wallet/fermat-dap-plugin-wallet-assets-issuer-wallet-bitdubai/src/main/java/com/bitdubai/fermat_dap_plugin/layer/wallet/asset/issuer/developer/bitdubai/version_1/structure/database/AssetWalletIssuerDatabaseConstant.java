package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database;

/**
 * Created by franklin on 25/09/15.
 */
public class AssetWalletIssuerDatabaseConstant {
    /**
     * Asset Wallet Issuer database table definition.
     */
    public static final String ASSET_WALLET_ISSUER_TABLE_NAME = "AssetWalletIssuer";
    public static final String ASSET_WALLET_ISSUER_TABLE_ID_COLUMN_NAME = "Id";
    // This second Id is used to verify that that the same transaction is not applied twice.
    // We can't use the transaction hash because some credit/debit operations do not involve a hash
    public static final String ASSET_WALLET_ISSUER_VERIFICATION_ID_COLUMN_NAME = "VerificationId";
    public static final String ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_ISSUER_ADDRESS_FROM_COLUMN_NAME = "addressFrom";
    public static final String ASSET_WALLET_ISSUER_ADDRESS_TO_COLUMN_NAME = "addressTo";
    public static final String ASSET_WALLET_ISSUER_ACTOR_FROM_COLUMN_NAME= "actorFrom";
    public static final String ASSET_WALLET_ISSUER_ACTOR_TO_COLUMN_NAME= "actorTo";
    public static final String ASSET_WALLET_ISSUER_ACTOR_FROM_TYPE_COLUMN_NAME= "actorFromType";
    public static final String ASSET_WALLET_ISSUER_ACTOR_TO_TYPE_COLUMN_NAME= "actorToType";
    public static final String ASSET_WALLET_ISSUER_AMOUNT_COLUMN_NAME = "amount";
    public static final String ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME = "type";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME = "balanceType";
    public static final String ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String ASSET_WALLET_ISSUER_MEMO_COLUMN_NAME = "memo";
    public static final String ASSET_WALLET_ISSUER_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";
    public static final String ASSET_WALLET_ISSUER_RUNNING_BOOK_BALANCE_COLUMN_NAME = "runningBookBalance";
    public static final String ASSET_WALLET_ISSUER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "runningAvailableBalance";


    // tabla nueva movimientos del resumen de los Assets - balance y book balance, id

    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME = "AssetWalletIssuerTotalBalances";
    //public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_ID_COLUMN_NAME = "Id";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME = "name";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME = "description";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME = "availableBalance";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME = "bookBalance";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME = "quantityAvailableBalance";
    public static final String ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME = "QuantityBookBalance";

}
