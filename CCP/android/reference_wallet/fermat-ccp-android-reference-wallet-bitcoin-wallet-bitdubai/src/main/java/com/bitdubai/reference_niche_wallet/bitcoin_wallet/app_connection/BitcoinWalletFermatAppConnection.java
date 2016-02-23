package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.header.BitcoinWalletHeaderPainter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.BitcoinWalletNavigationViewPainter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class BitcoinWalletFermatAppConnection extends AppConnections<ReferenceWalletSession>{

    private CryptoWallet moduleManager = null;
    private ReferenceWalletSession referenceWalletSession;

    public BitcoinWalletFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ReferenceWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_WALLET,
                Developers.BITDUBAI,
                new Version()
            );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new ReferenceWalletSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new BitcoinWalletNavigationViewPainter(getActivity(),getActiveIdentity());
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new BitcoinWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code){

        NotificationPainter notification = null;

        try
        {
            this.referenceWalletSession = (ReferenceWalletSession)this.getSession();
            if(referenceWalletSession!=  null)
                if(referenceWalletSession.getModuleManager()!=  null)
                    moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();
            CryptoWalletTransaction transaction;
            PaymentRequest  paymentRequest;

            String[] params = code.split("_");
            String notificationType = params[0];
            String transactionId = params[1];
            //find last transaction
            switch (notificationType){
                case "TRANSACTIONARRIVE":
                    if(moduleManager != null){
                        transaction= moduleManager.getTransaction(UUID.fromString(transactionId), referenceWalletSession.getAppPublicKey(),referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                        notification = new BitcoinWalletNotificationPainter("Received money", transaction.getInvolvedActor().getName() + " send "+ WalletUtils.formatBalanceString(transaction.getAmount()) + " BTC","","");

                    }else{
                        notification = new BitcoinWalletNotificationPainter("Received money", "","","");
                    }


                    break;
                case "PAYMENTREQUEST":
                    if(moduleManager != null){
                        paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                        notification = new BitcoinWalletNotificationPainter("","You have received a Payment Request, for" + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC","","");
                    }
                    else
                    {
                        notification = new BitcoinWalletNotificationPainter("","You have received a new Payment Request.","","");
                    }
                    break;

                case "PAYMENTDENIED":
                    if(moduleManager != null){
                        paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                        notification = new BitcoinWalletNotificationPainter("","Your Payment Request, for " + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC was deny.","","");
                    }
                    else
                    {
                        notification = new BitcoinWalletNotificationPainter("","Your Payment Request was deny.","","");
                    }
                    break;

                case "TRANSACTION_REVERSE":
                    if(moduleManager != null) {
                        transaction = moduleManager.getTransaction(UUID.fromString(transactionId), referenceWalletSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                        notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed", "Sending " + WalletUtils.formatBalanceString(transaction.getAmount()) + " BTC could not be completed.", "", "");
                    }else
                        {
                            notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed","Your last Sending could not be completed.","","");
                         }
                    break;

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return notification;
    }
}
