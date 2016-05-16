package com.bitdubai.reference_wallet.crypto_customer_wallet.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.header.CryptoCustomerWalletHeaderPainter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer.CustomerNavigationViewPainter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.notifications.CryptoCustomerNotificationPainter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CANCEL_NEGOTIATION_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_BROKER_ACK_PAYMENT_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_BROKER_SUBMITED_MERCHANDISE;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_COMPLETED_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_EXPIRATION_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_NEW_CONTRACT_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_WAITING_FOR_CUSTOMER_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_CANCELLED_NOTIFICATION;


/**
 * Created by Nelson Ramirez
 *
 * @since 2015.12.17
 */
public class CryptoCustomerWalletFermatAppConnection extends AppConnections<CryptoCustomerWalletSession> {

    public CryptoCustomerWalletFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_CUSTOMER,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    protected AbstractFermatSession getSession() {
        return new CryptoCustomerWalletSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CustomerNavigationViewPainter(getContext(), getFullyLoadedSession());
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new CryptoCustomerWalletHeaderPainter(getContext(), getFullyLoadedSession());
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code) {
        switch (code){
            case CCW_WAITING_FOR_CUSTOMER_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Negotiation Update","You have received a negotiation update, check your wallet.","");
            case CCW_CANCEL_NEGOTIATION_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Negotiation Canceled","Check the Contract Story, a broker has canceled a negotiation.","");
            case CCW_NEW_CONTRACT_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("New contract.","A new Contract has been created, check your wallet","");
            case CCW_CONTRACT_BROKER_ACK_PAYMENT_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Contract Update","A broker has acknowledged your payment.","");
            case CCW_CONTRACT_BROKER_SUBMITED_MERCHANDISE:
                return new CryptoCustomerNotificationPainter("Contract Update","A Merchandise has been submitted.","");
            case CCW_CONTRACT_COMPLETED_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Contract Update","The contract has been completed.","");
            case CCW_CONTRACT_CANCELLED_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Contract Update","The contract has been cancelled.","");
            case CCW_CONTRACT_EXPIRATION_NOTIFICATION:
                return new CryptoCustomerNotificationPainter("Expiring contract.","A contract is about to expire, check your wallet.","");
            default:
                return super.getNotificationPainter(code);
        }
    }
}
