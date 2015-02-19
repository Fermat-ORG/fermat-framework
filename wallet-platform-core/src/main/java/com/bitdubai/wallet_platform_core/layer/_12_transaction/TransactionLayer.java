package com.bitdubai.wallet_platform_core.layer._12_transaction;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._12_transaction.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._12_transaction.TransactionSubsystem;
import com.bitdubai.wallet_platform_core.layer._12_transaction.from_extrauser.FromExtraUserSubsystem;
import com.bitdubai.wallet_platform_core.layer._12_transaction.interuser.InterUserSubsystem;
import com.bitdubai.wallet_platform_core.layer._12_transaction.interwallet.InterWalletSubsystem;
import com.bitdubai.wallet_platform_core.layer._12_transaction.to_extrauser.ToExtrauserSubsystem;

/**
 * Created by loui on 16/02/15.
 */
public class TransactionLayer implements PlatformLayer {
    
    private Plugin mFromExtraUser;
    private Plugin mInterUser;
    private Plugin mInterWallet;
    private Plugin mToExtraUser;
    
    
    public  Plugin getFromExtraUserPlugin() {
        return mFromExtraUser;
    }

    public  Plugin getInterUserPlugin() {
        return mInterUser;
    }
  
    public  Plugin getInterWalletPlugin() {
        return mInterWallet;
    }

    public  Plugin getToExtraUserPlugin() {
        return mToExtraUser;

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


        /**
         * Let's try to start the To Extra User subsystem.
         */

        TransactionSubsystem toExtraUserSubsystem = new ToExtrauserSubsystem();

        try {
            toExtraUserSubsystem.start();
            mToExtraUser = toExtraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }



    }


}
