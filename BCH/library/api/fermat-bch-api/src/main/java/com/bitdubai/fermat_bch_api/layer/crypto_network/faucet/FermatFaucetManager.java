package com.bitdubai.fermat_bch_api.layer.crypto_network.faucet;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by rodrigo on 7/26/16.
 */
public class FermatFaucetManager {
    /**
     *
     * @param blockchainNetworkType
     * @param cryptoAddress
     * @param amount
     * @throws CantGetCoinsFromFaucetException
     */
    public static void giveMeCoins(BlockchainNetworkType blockchainNetworkType, CryptoAddress cryptoAddress, long amount) throws CantGetCoinsFromFaucetException {
        if (cryptoAddress.getCryptoCurrency() != CryptoCurrency.FERMAT)
            throw new CantGetCoinsFromFaucetException(null, "Coins requested is not Fermat. This faucet only allows FER request.", "Wrong faucet manager selected.");



        if (blockchainNetworkType == BlockchainNetworkType.PRODUCTION){
            FermatMainNetFaucetManager mainNetFaucetManager = new FermatMainNetFaucetManager();
            System.out.println("***FermatFaucet***requesting coins to faucet...");
            mainNetFaucetManager.giveMeCoins(cryptoAddress, amount);
        }

        //add same behaviour for RegTestNetwork.

    }
}
