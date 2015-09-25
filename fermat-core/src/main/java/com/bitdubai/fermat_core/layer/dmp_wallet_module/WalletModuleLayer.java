package com.bitdubai.fermat_core.layer.dmp_wallet_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.WalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.bank_notes_wallet.BankNotesWalletWalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.crypto_loss_protected_wallet.CryptoLossProtectedWalletWalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.crypto_wallet.CryptoWalletWalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.discount_wallet.DiscountWalletWalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.fiat_over_crypto_loss_protected_wallet.FiatOverCryptoLossProtectedWalletWalletModuleSubsystem;
import com.bitdubai.fermat_core.layer.dmp_wallet_module.multi_account_wallet.MultiAccountWalletWalletModuleSubsystem;

/**
 * Created by loui on 21/05/15.
 */
public class WalletModuleLayer implements PlatformLayer {

    private Plugin mBankNotesWallet;
    private Plugin mCryptoLossProtectedWallet;
    private Plugin mCryptoWallet;
    private Plugin mDiscountWallet;
    private Plugin mFiatOverCryptoLossProtectedWallet;
    private Plugin mMultiAccountWallet;


    public Plugin getmBankNotesWallet() {
        return mBankNotesWallet;
    }

    public Plugin getmCryptoLossProtectedWallet() {
        return mCryptoLossProtectedWallet;
    }

    public Plugin getmCryptoWallet() {
        return mCryptoWallet;
    }

    public Plugin getmDiscountWallet() {
        return mDiscountWallet;
    }

    public Plugin getmFiatOverCryptoLossProtectedWallet() {
        return mFiatOverCryptoLossProtectedWallet;
    }

    public Plugin getmMultiAccountWallet() {
        return mMultiAccountWallet;
    }


    @Override
    public void start() throws CantStartLayerException {

        WalletModuleSubsystem bankNotesWalletWalletModuleSubsystemType = new BankNotesWalletWalletModuleSubsystem();

        try {
            bankNotesWalletWalletModuleSubsystemType.start();
            mBankNotesWallet = (bankNotesWalletWalletModuleSubsystemType).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        WalletModuleSubsystem cryptoLossProtectedWalletWalletModuleSubsystem = new CryptoLossProtectedWalletWalletModuleSubsystem();

        try {
            cryptoLossProtectedWalletWalletModuleSubsystem.start();
            mCryptoLossProtectedWallet = (cryptoLossProtectedWalletWalletModuleSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        WalletModuleSubsystem cryptoWalletWalletModuleSubsystem = new CryptoWalletWalletModuleSubsystem();

        try {
            cryptoWalletWalletModuleSubsystem.start();
            mCryptoWallet = (cryptoWalletWalletModuleSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        WalletModuleSubsystem discountWalletWalletModuleSubsystem = new DiscountWalletWalletModuleSubsystem();

        try {
            discountWalletWalletModuleSubsystem.start();
            mDiscountWallet = (discountWalletWalletModuleSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        WalletModuleSubsystem fiatOverCryptoLossProtectedWalletWalletModuleSubsystem = new FiatOverCryptoLossProtectedWalletWalletModuleSubsystem();

        try {
            fiatOverCryptoLossProtectedWalletWalletModuleSubsystem.start();
            mFiatOverCryptoLossProtectedWallet = (fiatOverCryptoLossProtectedWalletWalletModuleSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        WalletModuleSubsystem multiAccountWalletWalletModuleSubsystem = new MultiAccountWalletWalletModuleSubsystem();

        try {
            multiAccountWalletWalletModuleSubsystem.start();
            mMultiAccountWallet = (multiAccountWalletWalletModuleSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

    }
}
