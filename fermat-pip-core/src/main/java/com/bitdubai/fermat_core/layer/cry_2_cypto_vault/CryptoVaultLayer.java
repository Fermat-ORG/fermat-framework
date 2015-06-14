package com.bitdubai.fermat_core.layer.cry_2_cypto_vault;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.cry_2_crypto_vault.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.cry_2_crypto_vault.CryptoVaultSubsystem;
import com.bitdubai.fermat_core.layer.cry_2_cypto_vault.bitcoin.BitcoinSubsystem;

/**
 * Created by loui on 20/05/15.
 */
public class CryptoVaultLayer implements PlatformLayer {

    private Plugin mBitcoin;




    public Plugin getmBitcoin() {
        return mBitcoin;
    }




    @Override
    public void start() throws CantStartLayerException {


        CryptoVaultSubsystem bitcoinSubsystem = new BitcoinSubsystem();

        try {
            bitcoinSubsystem.start();
            mBitcoin = ((BitcoinSubsystem) bitcoinSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }


    }
}
