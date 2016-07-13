package com.bitdubai.fermat_bch_api.layer.crypto_network.faucet;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by rodrigo on 7/11/16.
 */
public class BitcoinFaucetManager  {

    /**
     *
     * @param blockchainNetworkType
     * @param cryptoAddress
     * @param amount
     * @throws CantGetCoinsFromFaucetException
     */
    public static void giveMeCoins(BlockchainNetworkType blockchainNetworkType, CryptoAddress cryptoAddress, long amount) throws CantGetCoinsFromFaucetException {
        if (cryptoAddress.getCryptoCurrency() != CryptoCurrency.BITCOIN)
            throw new CantGetCoinsFromFaucetException(null, "Coins requested is not Bitcoin. This faucet only allows BTC request.", "Wrong faucet manager selected.");



            if (blockchainNetworkType == BlockchainNetworkType.TEST_NET){
                BitcoinTestNetFaucetManager testNetFaucetManager = new BitcoinTestNetFaucetManager();
                System.out.println("***BitcoinFaucet***requesting coins to faucet...");
                testNetFaucetManager.giveMeCoins(cryptoAddress, amount);
            }

            //add same behaviour for RegTestNetwork.

    }
}
