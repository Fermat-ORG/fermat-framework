package com.bitdubai.fermat_core.layer.dmp_transaction;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.incoming_device_user.IncomingdeviceUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.inter_wallet.InterWalletSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.outgoing_device_user.OutgoingDeviceUserSubsystem;

/**
 * Created by loui on 16/02/15.
 */
public class TransactionLayer implements PlatformLayer {

    private Plugin mIncomingDeviceUser;
    
    private Plugin mOutgoingDeviceUser;

    private Plugin mInterWallet;

    public  Plugin getIncomingDeviceUserPlugin() {
        return mIncomingDeviceUser;
    }

    public  Plugin getInterWalletPlugin() {
        return mInterWallet;
    }

    public  Plugin getOutgoingDeviceUserPlugin() {
        return mOutgoingDeviceUser;
    }



    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the Inter User subsystem.
         */

        TransactionSubsystem incomingDeviceUserSubsystem = new IncomingdeviceUserSubsystem();

        try {
            incomingDeviceUserSubsystem.start();
            mIncomingDeviceUser = incomingDeviceUserSubsystem.getPlugin();

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

        TransactionSubsystem outgoingDeviceUserSubsystem = new OutgoingDeviceUserSubsystem();

        try {
            outgoingDeviceUserSubsystem.start();
            mOutgoingDeviceUser = outgoingDeviceUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }
}
