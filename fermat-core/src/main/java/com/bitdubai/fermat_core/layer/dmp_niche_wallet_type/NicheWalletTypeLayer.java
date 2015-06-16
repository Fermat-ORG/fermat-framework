package com.bitdubai.fermat_core.layer.dmp_niche_wallet_type;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.NicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.bank_notes_wallet.BankNotesWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.crypto_loss_protected_wallet.CryptoLossProtectedWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.crypto_wallet.CryptoWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.discount_wallet.DiscountWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.fiat_over_crypto_loss_protected_wallet.FiatOverCryptoLossProtectedWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.fiat_over_crypto_wallet.FiatOverCryptoWalletNicheWalletTypeSubsystem;
import com.bitdubai.fermat_core.layer.dmp_niche_wallet_type.multi_account_wallet.MultiAccountWalletNicheWalletTypeSubsystem;

/**
 * Created by loui on 21/05/15.
 */
public class NicheWalletTypeLayer implements PlatformLayer {

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

        NicheWalletTypeSubsystem bankNotesWalletNicheWalletSubsystemType = new BankNotesWalletNicheWalletTypeSubsystem();

        try {
            bankNotesWalletNicheWalletSubsystemType.start();
            mBankNotesWallet = (bankNotesWalletNicheWalletSubsystemType).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem cryptoLossProtectedWalletNicheWalletTypeSubsystem  = new CryptoLossProtectedWalletNicheWalletTypeSubsystem();

        try {
            cryptoLossProtectedWalletNicheWalletTypeSubsystem.start();
            mCryptoLossProtectedWallet = (cryptoLossProtectedWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem cryptoWalletNicheWalletTypeSubsystem  = new CryptoWalletNicheWalletTypeSubsystem();

        try {
            cryptoWalletNicheWalletTypeSubsystem.start();
            mCryptoWallet = (cryptoWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem discountWalletNicheWalletTypeSubsystem  = new DiscountWalletNicheWalletTypeSubsystem();

        try {
            discountWalletNicheWalletTypeSubsystem.start();
            mDiscountWallet = (discountWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem fiatOverCryptoLossProtectedWalletNicheWalletTypeSubsystem  = new FiatOverCryptoLossProtectedWalletNicheWalletTypeSubsystem();

        try {
            fiatOverCryptoLossProtectedWalletNicheWalletTypeSubsystem.start();
            mFiatOverCryptoLossProtectedWallet = (fiatOverCryptoLossProtectedWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem fiatOverCryptoWalletNicheWalletTypeSubsystem  = new FiatOverCryptoWalletNicheWalletTypeSubsystem();

        try {
            fiatOverCryptoWalletNicheWalletTypeSubsystem.start();
            mFiatOverCryptoWallet = (fiatOverCryptoWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        NicheWalletTypeSubsystem multiAccountWalletNicheWalletTypeSubsystem  = new MultiAccountWalletNicheWalletTypeSubsystem();

        try {
            multiAccountWalletNicheWalletTypeSubsystem.start();
            mMultiAccountWallet = (multiAccountWalletNicheWalletTypeSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

    }
}
