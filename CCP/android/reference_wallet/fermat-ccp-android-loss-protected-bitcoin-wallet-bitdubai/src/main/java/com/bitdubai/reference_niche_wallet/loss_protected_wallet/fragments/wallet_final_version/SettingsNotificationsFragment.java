package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by josemanueldsds on 20/01/16.
 */
public class SettingsNotificationsFragment extends AbstractFermatFragment {

    private View rootView;
    private LossProtectedWalletSession referenceWalletSession;
    private LossProtectedWallet cryptoWallet;
    private FermatEditText port;
    private FermatEditText ipAdress;
    private Spinner spinner;
    private Switch notificationSwitch;


    public static SettingsNotificationsFragment newInstance() {
        return new SettingsNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = (LossProtectedWalletSession) appSession;
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (CantGetCryptoLossProtectedWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.settings_notifications, container, false);
            setUpUi();
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    public void setUpUi() {
        notificationSwitch = (Switch) rootView.findViewById(R.id.switch_notification);
    }

}
