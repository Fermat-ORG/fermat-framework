package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */
public class SettingsFragment2 extends FermatWalletFragment implements View.OnClickListener{


    /**
     * Plaform reference
     */
    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;
    private IntraUserModuleManager intraUserModuleManager;

    /**
     * UI
     */
    private View rootView;
    private Switch mSwitchNotifications;

    private ColorStateList mSwitchTrackStateList;


    public static SettingsFragment2 newInstance() {
        return new SettingsFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = (ReferenceWalletSession) walletSession;
        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();
        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.settings_fragment_base, container, false);
            setUpUI();
            setUpScreen(inflater);
            setUpActions();
            setUpUIData();
            return rootView;
        }catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpUI() throws CantGetActiveLoginIdentityException {
        //WalletUtils.setNavigatitDrawer(getPaintActivtyFeactures(), referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity());

        mSwitchNotifications = (Switch) rootView.findViewById(R.id.switch_notifications);
        mSwitchNotifications.setBackgroundTintList(getSwitchTrackColorStateList());
    }

    private void setUpActions(){

    }

    private void setUpUIData(){

    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
        /**
         * add navigation header
         */
        //addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater,getActivity(),referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity()));

        /**
         * Navigation view items
         */
        NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(getActivity(),null,referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity());
        setNavigationDrawer(navigationViewAdapter);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.scan_qr) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
            integrator.initiateScan();
        }
        else if (id == R.id.send_button){
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }

        }
        else if (id == R.id.imageView_contact){
            // if user press the profile image
        }


    }

    private ColorStateList getSwitchTrackColorStateList() {
        if (mSwitchTrackStateList == null) {
            final int[][] states = new int[3][];
            final int[] colors = new int[3];
            int i = 0;

            // Disabled state
            states[i] = new int[] { -android.R.attr.state_enabled };
            colors[i] = Color.RED;
            i++;

            states[i] = new int[] { android.R.attr.state_checked };
            colors[i] = Color.BLUE;
            i++;

            // Default enabled state
            states[i] = new int[0];
            colors[i] = Color.YELLOW;
            i++;

            mSwitchTrackStateList = new ColorStateList(states, colors);
        }
        return mSwitchTrackStateList;
    }


}
