package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database;

/**
 * Created by franklin on 14/10/15.
 */
public class AssetWalletRedeemPointDatabaseConstant {
    /**
     * Asset Wallet Redeem Point database table definition.
     */
    public static final String ASSET_WALLET_REDEEM_POINT_TABLE_NAME = "AssetWalletRedeemPoint";
    public static final String ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME = "Id";
    // This second Id is used to verify that that the same transaction is not applied twice.
    // We can't use the transaction hash because some credit/debit operations do not involve a hash
    public static final String ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME = "VerificationId";
    public static final String ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME = "addressFrom";
    public static final String ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME = "addressTo";
    public static final String ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME = "actorFrom";
    public static final String ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME = "actorTo";
    public static final String ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME = "actorFromType";
    public static final String ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME = "actorToType";
    public static final String ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME = "amount";
    public static final String ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME = "type";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME = "balanceType";
    public static final String ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME = "memo";
    public static final String ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";
    public static final String ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME = "runningBookBalance";
    public static final String ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "runningAvailableBalance";


    // tabla nueva movimientos del resumen de los Assets - balance y book balance, id

    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME = "AssetWalletRedeemPontTotalBalances";
    public static final String ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME_COLUMN_NAME = "name";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME = "description";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME = "availableBalance";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME = "bookBalance";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME = "quantityAvailableBalance";
    public static final String ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME = "QuantityBookBalance";

    // REDEEM POINT STATISTICS TABLE.
    public static final String ASSET_WALLET_REDEEM_POINT_STATISTIC_TABLE_NAME = "redemption_statistic";
    public static final String ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ID_COLUMN_NAME = "statistic_id";
    public static final String ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_GENESIS_TRANSACTION_KEY_COLUMN_NAME = "genesisTransaction";
    public static final String ASSET_WALLET_REDEEM_POINT_STATISTIC_USER_PUBLICKEY_COLUMN_NAME = "userPublicKey";
    public static final String ASSET_WALLET_REDEEM_POINT_STATISTIC_REDEMPTION_TIMESTAMP_COLUMN_NAME = "timestamp";


}
