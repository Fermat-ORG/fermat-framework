package com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * Created by mati on 2015.11.24..
 */
public class CustomerNavigationViewPainter extends NavigationViewPainter {

    private static final String TAG = "CustomerNavigationView";

    private CryptoCustomerIdentity actorIdentity;
    private WeakReference<FermatApplicationCaller> applicationsHelper;
    private CryptoCustomerWalletModuleManager moduleManager;
    private NumberFormat numberFormat = DecimalFormat.getInstance();

    public CustomerNavigationViewPainter(Context activity, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> session,
                                         FermatApplicationCaller applicationsHelper) {
        super(activity);

        ErrorManager errorManager = session.getErrorManager();

        try {
            moduleManager = session.getModuleManager();
            actorIdentity = moduleManager.getAssociatedIdentity(session.getAppPublicKey());
            this.applicationsHelper = new WeakReference<>(applicationsHelper);

        } catch (FermatException ex) {
            if (errorManager == null)
                Log.e(TAG, ex.getMessage(), ex);
            else
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View addNavigationViewHeader() {
        try {
            return FragmentsCommons.setUpHeaderScreen((LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE), getContext(), actorIdentity, applicationsHelper.get());
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            return new CryptoCustomerWalletNavigationViewAdapter(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base) {
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.ccw_navigation_view_bottom, base, true);
        FermatTextView bitcoinBalance = (FermatTextView) layout.findViewById(R.id.ccw_navigation_view_bitcoin_balance);
        FermatTextView fermatBalance = (FermatTextView) layout.findViewById(R.id.ccw_navigation_view_fermat_balance);

        long satoshisBTC = moduleManager.getBalanceBitcoinWallet(WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode());
        double bitcoins = BitcoinConverter.convert(satoshisBTC, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

        long satoshisFER = moduleManager.getBalanceBitcoinWallet(WalletsPublicKeys.CCP_FERMAT_WALLET.getCode());
        double fermats = BitcoinConverter.convert(satoshisFER, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.FERMAT);

        bitcoinBalance.setText(String.format("%1$s %2$s", fixFormat(bitcoins), CryptoCurrency.BITCOIN.getCode()));
        fermatBalance.setText(String.format("%1$s %2$s", fixFormat(fermats), CryptoCurrency.FERMAT.getCode()));

        return layout;
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    getContext().getResources(), R.drawable.ccw_navigation_drawer_background, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return drawable;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return false;
    }


    private String fixFormat(Double value) {


        if (compareLessThan1(value)) {
            numberFormat.setMaximumFractionDigits(8);
        } else {
            numberFormat.setMaximumFractionDigits(2);
        }

        return String.valueOf(numberFormat.format(new BigDecimal(value)));

    }

    private Boolean compareLessThan1(Double value) {
        Boolean lessThan1 = true;

        lessThan1 = BigDecimal.valueOf(value).compareTo(BigDecimal.ONE) == -1;


        return lessThan1;
    }


}
