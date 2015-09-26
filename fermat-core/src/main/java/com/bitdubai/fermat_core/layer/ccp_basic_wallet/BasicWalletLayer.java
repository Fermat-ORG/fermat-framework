package com.bitdubai.fermat_core.layer.ccp_basic_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.BasicWalletSubsystem;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.ccp_basic_wallet.bitcoin_wallet.BitcoinWalletSubsystem;
import com.bitdubai.fermat_core.layer.ccp_basic_wallet.discount_wallet.DiscountWalletSubsystem;

/**
 * Created by loui on 21/04/15.
 */
public class BasicWalletLayer implements PlatformLayer {

    private Plugin mDiscountWallet;
    private Plugin mBitcoinWallet;


    public Plugin getDiscountWallet(){
        return mDiscountWallet;
    }

    public Plugin getBitcoinWallet() {
        return mBitcoinWallet;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the Discount Wallet subsystem.
         */

        BasicWalletSubsystem discountWalletSubsystem = new DiscountWalletSubsystem();

        try {
            discountWalletSubsystem .start();
            mDiscountWallet = discountWalletSubsystem .getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


        /**
         * Let's try to start the Bitcoin Wallet subsystem.
         */

        BasicWalletSubsystem bitcoinWalletSubsystem = new BitcoinWalletSubsystem();

        try {
            bitcoinWalletSubsystem.start();
            mBitcoinWallet = bitcoinWalletSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


    }
}
