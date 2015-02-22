package com.bitdubai.fermat_core.layer._12_transaction;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._12_transaction.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._12_transaction.TransactionSubsystem;
import com.bitdubai.fermat_core.layer._12_transaction.incoming_extra_user.IncomingExtraUserSubsystem;
import com.bitdubai.fermat_core.layer._12_transaction.incoming_intra_user.IncomingIntraUserSubsystem;
import com.bitdubai.fermat_core.layer._12_transaction.inter_wallet.InterWalletSubsystem;
import com.bitdubai.fermat_core.layer._12_transaction.outgoing_extra_user.OutgoingExtrauserSubsystem;
import com.bitdubai.fermat_core.layer._12_transaction.outgoing_intra_user.OutgoingIntraUserSubsytem;
import com.bitdubai.fermat_core.layer._12_transaction.outgoing_device_user.OutgoingDeviceUserSubsystem;

/**
 * Created by loui on 16/02/15.
 */
public class TransactionLayer implements PlatformLayer {
    
    private Plugin mFromExtraUser;
    
    private Plugin mIntraUser;
    
    private Plugin mInterWallet;
    
    private Plugin mToExtraUser;
    
    private Plugin mOutgoingIntraUser;
    
    private Plugin mOutgoingExtraUser;
    
    public  Plugin getFromExtraUserPlugin() {
        return mFromExtraUser;
    }

    public  Plugin getIntraUserPlugin() {
        return mIntraUser;
    }
  
    public  Plugin getInterWalletPlugin() {
        return mInterWallet;
    }

    public  Plugin getToExtraUserPlugin() {
        return mToExtraUser;

    }

    public Plugin getOutgoingIntraUser(){
        return mOutgoingIntraUser;   
    }
    
    public Plugin getOutgoingExtraUser(){
        return mOutgoingExtraUser;        
    }

    @Override
    public void start() throws CantStartLayerException {
        /**
         * Let's try to start the From Extra User subsystem.
         */

        TransactionSubsystem fromExtraUserSubsystem = new IncomingExtraUserSubsystem();

        try {
            fromExtraUserSubsystem.start();
            mFromExtraUser = fromExtraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
        
        /**
         * Let's try to start the Inter User subsystem.
         */

        TransactionSubsystem interUserSubsystem = new IncomingIntraUserSubsystem();

        try {
            interUserSubsystem.start();
            mIntraUser = interUserSubsystem.getPlugin();

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

        TransactionSubsystem toExtraUserSubsystem = new OutgoingDeviceUserSubsystem();

        try {
            toExtraUserSubsystem.start();
            mToExtraUser = toExtraUserSubsystem.getPlugin();

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
    }
}
