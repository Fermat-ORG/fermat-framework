package com.bitdubai.wallet_platform_core.layer._11_transaction;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._11_transaction.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._11_transaction.TransactionSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_transaction.from_extrauser.FromExtraUserSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_transaction.interuser.InterUserSubsystem;
import com.bitdubai.wallet_platform_core.layer._11_transaction.interwallet.InterWalletSubsystem;

/**
 * Created by loui on 16/02/15.
 */
public class TransactionLayer implements PlatformLayer {
    
    private Plugin mFromExtraUser;
    private Plugin mInterUser;
    private Plugin mInterWallet;
 
    public  Plugin getFromExtraUserPlugin() {
        return mFromExtraUser;
    }


    public  Plugin getInterUserPlugin() {
        return mInterUser;
    }
  
    

    public  Plugin getInterWalletPlugin() {
        return mInterWallet;
    }
  
 
    @Override
    public void start() throws CantStartLayerException {
        /**
         * Let's try to start the From Extra User subsystem.
         */

        TransactionSubsystem fromExtraUserSubsystem = new FromExtraUserSubsystem();

        try {
            fromExtraUserSubsystem.start();
            mFromExtraUser = fromExtraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        
        /**
         * Let's try to start the Inter User subsystem.
         */

        TransactionSubsystem interUserSubsystem = new InterUserSubsystem();

        try {
            interUserSubsystem.start();
            mInterUser = interUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


        /**
         * Let's try to start the Inter User subsystem.
         */

        TransactionSubsystem interWalletSubsystem = new InterWalletSubsystem();

        try {
            interWalletSubsystem.start();
            mInterWallet = interWalletSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


    }


}
