package com.bitdubai.fermat_core.layer.dmp_transaction;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.incoming_device_user.IncomingdeviceUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.incoming_extra_user.IncomingExtraUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.incoming_intra_user.IncomingIntraUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.inter_wallet.InterWalletSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.outgoing_extra_user.OutgoingExtrauserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_transaction.outgoing_intra_user.OutgoingIntraUserSubsytem;
import com.bitdubai.fermat_core.layer.dmp_transaction.outgoing_device_user.OutgoingDeviceUserSubsystem;

/**
 * Created by loui on 16/02/15.
 */
public class TransactionLayer implements PlatformLayer {
    
    private Plugin mIncomingExtraUser;

    private Plugin mOutgoingExtraUser;

    private Plugin mIncomingDeviceUser;
    
    private Plugin mOutgoingDeviceUser;

    private Plugin mIncomingIntraUser;

    private Plugin mOutgoingIntraUser;
    
    private Plugin mInterWallet;

    public  Plugin getIncomingExtraUserPlugin() {
        return mIncomingExtraUser;
    }

    public  Plugin getIncomingIntraUserPlugin() {
        return mIncomingIntraUser;
    }

    public  Plugin getIncomingDeviceUserPlugin() {
        return mIncomingDeviceUser;
    }

    public  Plugin getInterWalletPlugin() {
        return mInterWallet;
    }

    public  Plugin getOutgoingExtraUserPlugin() {
        return mOutgoingExtraUser;
    }

    public  Plugin getOutgoingIntraUserPlugin() {
        return mOutgoingIntraUser;
    }

    public  Plugin getOutgoingDeviceUserPlugin() {
        return mOutgoingDeviceUser;
    }



    @Override
    public void start() throws CantStartLayerException {
        /**
         * Let's try to start the From Extra User subsystem.
         */

        TransactionSubsystem incomingExtraUserSubsystem = new IncomingExtraUserSubsystem();

        try {
            incomingExtraUserSubsystem.start();
            mIncomingExtraUser = incomingExtraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        
        /**
         * Let's try to start the Inter User subsystem.
         */

        TransactionSubsystem incomingIntraUserSubsystem = new IncomingIntraUserSubsystem();

        try {
            incomingIntraUserSubsystem.start();
            mIncomingIntraUser = incomingIntraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


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


        /**
         * Let's try to start the Outgoing Intra User Subsytem. 
         */
        
        TransactionSubsystem outgoingIntraUserSubsystem = new OutgoingIntraUserSubsytem();
        
        try {
            outgoingIntraUserSubsystem.start();
            mOutgoingIntraUser = outgoingIntraUserSubsystem.getPlugin();

        }catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
        }

        /**
         * Let's try to start the Outgoing Extra User Subsytem. 
         */

        TransactionSubsystem outgoingExtraUserSubsystem = new OutgoingExtrauserSubsystem();
        
        try {
            outgoingExtraUserSubsystem.start();
            mOutgoingExtraUser = outgoingExtraUserSubsystem.getPlugin();
        }catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());

        }

        /**
         * TODO: DELETE AFTER CORRECT REUBICATION
         * Let's try to start the Incoming Crypto Subsystem.


        TransactionSubsystem incomingCryptoSubsystem = new IncomingCryptoSubsysten();

        try {
            incomingCryptoSubsystem.start();
            mIncomingCrypto = incomingCryptoSubsystem.getPlugin();
        } catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessageContent());
        }
         */
    }
}
