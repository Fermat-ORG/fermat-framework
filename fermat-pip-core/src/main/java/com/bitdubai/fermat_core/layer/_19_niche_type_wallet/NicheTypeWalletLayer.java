package com.bitdubai.fermat_core.layer._19_niche_type_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._19_niche_type_wallet.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._19_niche_type_wallet.NicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.bank_notes_wallet.BankNotesWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.crypto_loss_protected_wallet.CryptoLossProtectedWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.crypto_wallet.CryptoWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.discount_wallet.DiscountWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.fiat_over_crypto_loss_protected_wallet.FiatOverCryptoLossProtectedWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.fiat_over_crypto_wallet.FiatOverCryptoWalletNicheTypeWalletSubsystem;
import com.bitdubai.fermat_core.layer._19_niche_type_wallet.multi_account_wallet.MultiAccountWalletNicheTypeWalletSubsystem;

/**
 * Created by loui on 21/05/15.
 */
public class NicheTypeWalletLayer implements PlatformLayer {

    private Plugin mBankNotesWallet;
    private Plugin mCryptoLossProtectedWallet;
    private Plugin mCryptoWallet;
    private Plugin mDiscountWallet;
    private Plugin mFiatOverCryptoLossProtectedWallet;
    private Plugin mFiatOverCryptoWallet;
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
    public Plugin getmFiatOverCryptoWallet() {
        return mFiatOverCryptoWallet;
    }
    public Plugin getmMultiAccountWallet() {
        return mMultiAccountWallet;
    }




    @Override
    public void start() throws CantStartLayerException {

        NicheTypeWalletSubsystem bankNotesWalletNicheTypeWalletSubsystem = new BankNotesWalletNicheTypeWalletSubsystem();

        try {
            bankNotesWalletNicheTypeWalletSubsystem.start();
            mBankNotesWallet = (bankNotesWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem cryptoLossProtectedWalletNicheTypeWalletSubsystem  = new CryptoLossProtectedWalletNicheTypeWalletSubsystem();

        try {
            cryptoLossProtectedWalletNicheTypeWalletSubsystem.start();
            mCryptoLossProtectedWallet = (cryptoLossProtectedWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem cryptoWalletNicheTypeWalletSubsystem  = new CryptoWalletNicheTypeWalletSubsystem();

        try {
            cryptoWalletNicheTypeWalletSubsystem.start();
            mCryptoWallet = (cryptoWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem discountWalletNicheTypeWalletSubsystem  = new DiscountWalletNicheTypeWalletSubsystem();

        try {
            discountWalletNicheTypeWalletSubsystem.start();
            mDiscountWallet = (discountWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem fiatOverCryptoLossProtectedWalletNicheTypeWalletSubsystem  = new FiatOverCryptoLossProtectedWalletNicheTypeWalletSubsystem();

        try {
            fiatOverCryptoLossProtectedWalletNicheTypeWalletSubsystem.start();
            mFiatOverCryptoLossProtectedWallet = (fiatOverCryptoLossProtectedWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem fiatOverCryptoWalletNicheTypeWalletSubsystem  = new FiatOverCryptoWalletNicheTypeWalletSubsystem();

        try {
            fiatOverCryptoWalletNicheTypeWalletSubsystem.start();
            mFiatOverCryptoWallet = (fiatOverCryptoWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheTypeWalletSubsystem multiAccountWalletNicheTypeWalletSubsystem  = new MultiAccountWalletNicheTypeWalletSubsystem();

        try {
            multiAccountWalletNicheTypeWalletSubsystem.start();
            mMultiAccountWallet = (multiAccountWalletNicheTypeWalletSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

    }
}
