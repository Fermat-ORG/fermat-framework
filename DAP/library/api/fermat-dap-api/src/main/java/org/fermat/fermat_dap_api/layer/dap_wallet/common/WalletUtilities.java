package org.fermat.fermat_dap_api.layer.dap_wallet.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 2/02/16.
 */
public final class WalletUtilities implements Serializable{

    //VARIABLE DECLARATION
    public static String WALLET_PUBLIC_KEY = "walletPublicKeyTest";
    public static String DEFAULT_MEMO_REDEMPTION = "Asset Redeemed";
    public static String DEFAULT_MEMO_ISSUING = "Asset Created";
    public static String DEFAULT_MEMO_DISTRIBUTION = "Asset Delivered";
    public static String DEFAULT_MEMO_APPROPRIATION = "Asset Appropriated";
    public static String DEFAULT_MEMO_SELL = "Asset Sold";
    public static String DEFAULT_MEMO_BUY = "Asset Bought";
    public static String DEFAULT_MEMO_ROLLBACK = "Transaction rollback";
    //CONSTRUCTORS

    private WalletUtilities() {
        throw new AssertionError("NO INSTANCES!!!");
    }

    //PUBLIC METHODS

    /**
     * This method constructs the wallet UUID from the network type and its public key. This
     * is manly used inside the wallets, when we use this value to create a database and accessing it.
     *
     * @param walletPublicKey {@link String} that represents the wallet public key
     * @param networkType     {@link BlockchainNetworkType} that represents the network where this wallet is running.
     * @return {@link UUID} instance constructed by these parameters.
     */
    public static UUID constructWalletId(String walletPublicKey, BlockchainNetworkType networkType) {
        if (networkType == null) {
            networkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        }
        String finalString = networkType.getCode() + "-" + walletPublicKey;
        return UUID.nameUUIDFromBytes(finalString.getBytes());
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
