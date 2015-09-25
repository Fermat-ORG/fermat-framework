package com.bitdubai.fermat_core.layer.ccp_world;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.ccp_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_world.WorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.coinapult.CoinapultWorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.blockchain_info.BlockchainInfoWorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.coinbase.CoinbaseWorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.crypto_index.CryptoIndexWorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.location.LocationWorldSubsystem;
import com.bitdubai.fermat_core.layer.ccp_world.shape_shift.ShapeShiftWorldSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class WorldLayer implements PlatformLayer {
    
    Plugin mCryptoIndex;
    Plugin mBlockchainInfo;
    Plugin mCoinapult;
    Plugin mShapeShift;
    Plugin mCoinbase;
    Plugin mLocation;
    




    public Plugin getCryptoIndex(){
        return mCryptoIndex;
    }
    
    public Plugin getBlockchainInfo() {
        return mBlockchainInfo;
    }
    
    public Plugin getCoinapult() {
        return mCoinapult;        
    }
    
    public Plugin getShapeShift() {
        return mShapeShift;        
    }

    public Plugin getCoinbase(){
        return mCoinbase;
    }

    public Plugin getLocation() {
        return mLocation;
    }



    @Override
    public void start() {

        /**
         * Let's try to start the Crypto Index subsystem.
         */
        WorldSubsystem cryptoIndexSubsystem  = new CryptoIndexWorldSubsystem();
        
        try {
            cryptoIndexSubsystem.start();
            mCryptoIndex = ((WorldSubsystem) cryptoIndexSubsystem).getPlugin();
            
        } catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }


        /**
         * Let's try to start the Blockchain Info subsystem.
         */
        
        WorldSubsystem blockchainInfoSubsystem = new BlockchainInfoWorldSubsystem();
        
        try {
            blockchainInfoSubsystem.start();
            mBlockchainInfo = ((WorldSubsystem) blockchainInfoSubsystem).getPlugin();
            
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
        }

        /**
         * Let's try to start the Coinapult subsystem. 
         */
        
        WorldSubsystem coinapultSubsystem = new CoinapultWorldSubsystem();
        
        try {
            coinapultSubsystem.start();
            mCoinapult = ((WorldSubsystem) coinapultSubsystem).getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the Shape Shift subsystem. 
         */

        WorldSubsystem shapeShiftSubsystem = new ShapeShiftWorldSubsystem();

        try {
            shapeShiftSubsystem.start();
            mShapeShift = ((WorldSubsystem) shapeShiftSubsystem).getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the Coinbase subsystem.
         */

        WorldSubsystem coinbaseSubsystem = new CoinbaseWorldSubsystem();

        try {
            coinbaseSubsystem.start();
            mCoinbase = ((WorldSubsystem) coinbaseSubsystem).getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the Shape Shift subsystem.
         */

        WorldSubsystem locationSubsystem = new LocationWorldSubsystem();

        try {
            locationSubsystem.start();
            mLocation = ((WorldSubsystem) locationSubsystem).getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }
}
