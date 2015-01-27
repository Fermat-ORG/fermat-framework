package com.bitdubai.platform.layer._10_middleware;

import com.bitdubai.platform.layer.CantStartLayerException;
import com.bitdubai.platform.layer.PlatformLayer;
import com.bitdubai.platform.layer._10_middleware.shell.ShellSubsystem;
import com.bitdubai.platform.layer._10_middleware.wallet.WalletSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class MiddlewareLayer implements PlatformLayer {

    private MiddlewareEngine mShellEngine;
    private MiddlewareEngine mWalletEngine;

    public MiddlewareEngine getShellEngine() {
        return mShellEngine;
    }

    public MiddlewareEngine getWalletEngine() {
        return mWalletEngine;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the shell subsystem.
         */
        MiddlewareSubsystem shellSubsystem = new ShellSubsystem();

        try {
            shellSubsystem.start();
            mShellEngine = ((MiddlewareSubsystem) shellSubsystem).getMiddlewareEngine();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

        /**
         * Let's try to start the wallet subsystem.
         */
        MiddlewareSubsystem walletSubsystem = new WalletSubsystem();

        try {
            walletSubsystem.start();
            mWalletEngine = ((MiddlewareSubsystem) walletSubsystem).getMiddlewareEngine();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }

    }
}
