package com.bitdubai.fermat_core.layer.cry_cypto_vault;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.cry_cypto_vault.assets.AssetsSubsystem;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultSubsystem;
import com.bitdubai.fermat_core.layer.cry_cypto_vault.bitcoin.BitcoinSubsystem;

/**
 * Created by loui on 20/05/15.
 */
public class CryptoVaultLayer implements PlatformLayer {

    private Plugin mBitcoin;
    private Plugin mAssetsVault;



    public Plugin getmBitcoin() {
        return mBitcoin;
    }

    public Plugin getmAssetsVault() {
        return mAssetsVault;
    }





    @Override
    public void start() throws CantStartLayerException {


        CryptoVaultSubsystem bitcoinSubsystem = new BitcoinSubsystem();
        AssetsSubsystem assetsSubsystem = new AssetsSubsystem();

        try {
            bitcoinSubsystem.start();
            assetsSubsystem.start();
            mBitcoin = ((BitcoinSubsystem) bitcoinSubsystem).getPlugin();
            mAssetsVault  = ((AssetsSubsystem) assetsSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }


    }
}
