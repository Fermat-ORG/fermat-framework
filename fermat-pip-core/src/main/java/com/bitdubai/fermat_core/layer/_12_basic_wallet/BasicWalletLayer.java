package com.bitdubai.fermat_core.layer._12_basic_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._12_basic_wallet.BasicWalletSubsystem;
import com.bitdubai.fermat_api.layer._12_basic_wallet.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer._12_basic_wallet.discount_wallet.DiscountWalletSubsystem;

/**
 * Created by loui on 21/04/15.
 */
public class BasicWalletLayer implements PlatformLayer {

    private Plugin mDiscountWallet;


    public Plugin getDiscountWallet(){
        return mDiscountWallet;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the App Runtime subsystem.
         */

        BasicWalletSubsystem discountWalletSubsystem = new DiscountWalletSubsystem();

        try {
            discountWalletSubsystem .start();
            mDiscountWallet = discountWalletSubsystem .getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


    }
}
