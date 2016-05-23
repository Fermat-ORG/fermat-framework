package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.IdentitiesAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetIdentityFragment extends FermatWalletListFragment<CryptoBrokerIdentity>
        implements FermatListItemListeners<CryptoBrokerIdentity>, DialogInterface.OnDismissListener {

    private List<CryptoBrokerIdentity> identities;
    private CryptoBrokerIdentity selectedIdentity;
    private CryptoBrokerWalletPreferenceSettings walletSettings;

    private LinearLayout container;

    private ErrorManager errorManager;
    private CryptoBrokerWalletModuleManager moduleManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
        errorManager = appSession.getErrorManager();

        //Obtain walletSettings or create new wallet settings if first time opening wallet
        walletSettings = null;
        try {
            walletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            walletSettings = null;
        }

        if (walletSettings == null) {
            walletSettings = new CryptoBrokerWalletPreferenceSettings();
            walletSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), walletSettings);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        identities = getMoreDataAsync(FermatRefreshTypes.NEW, 0);

    }

    public static AbstractFermatFragment newInstance() {
        return new WizardPageSetIdentityFragment();
    }

    @Override
    protected void initViews(final View layout) {
        super.initViews(layout);

        container = (LinearLayout) layout.findViewById(R.id.cbw_wizard_set_identity_container);

        View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIdentity != null) {
                    try {
                        moduleManager.associateIdentity(selectedIdentity, appSession.getAppPublicKey());
                        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_MERCHANDISES, appSession.getAppPublicKey());

                    } catch (CantCreateNewBrokerIdentityWalletRelationshipException e) {
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

                        Toast.makeText(WizardPageSetIdentityFragment.this.getActivity(), "Sorry, Can't associate the identity whit this wallet", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(WizardPageSetIdentityFragment.this.getActivity(), R.string.cbw_select_identity_warning_msg, Toast.LENGTH_LONG).show();

                }
            }
        });

        verifyIfWalletConfigured();
    }

    private void verifyIfWalletConfigured() {

        new Handler().postDelayed(new Runnable() {
            boolean walletConfigured;

            @Override
            public void run() {
                try {
                    walletConfigured = moduleManager.isWalletConfigured(appSession.getAppPublicKey());

                } catch (Exception ex) {
                    Object data = appSession.getData(CryptoBrokerWalletSession.CONFIGURED_DATA);
                    walletConfigured = (data != null);

                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

                }

                if (walletConfigured) {
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

                } else {
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                    container.setVisibility(View.VISIBLE);
                    container.startAnimation(fadeInAnimation);
                    PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.banner_crypto_broker)
                            .setIconRes(R.drawable.crypto_broker)
                            .setSubTitle(R.string.cbw_crypto_broker_wallet_merchandises_subTitle)
                            .setBody(R.string.cbw_crypto_broker_wallet_identity_body)
                            .setTextFooter(R.string.cbw_crypto_broker_wallet_identity_footer)
                            .build();

                    boolean showDialog;
                    try {
                        CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
                        showDialog = moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
                        if (showDialog) {
                            presentationDialog.show();
                        }
                    } catch (FermatException e) {
                        makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
                    }
                    //presentationDialog.show();
                }

            }
        }, 500);
    }

    @Override
    public FermatAdapter getAdapter() {
        IdentitiesAdapter adapter = new IdentitiesAdapter(getActivity(), identities);
        adapter.setFermatListEventListener(this);
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        return layoutManager;
    }

    @Override
    public void onItemClickListener(CryptoBrokerIdentity data, int position) {
        selectedIdentity = data;
        IdentitiesAdapter identitiesAdapter = (IdentitiesAdapter) this.adapter;
        identitiesAdapter.selectItem(position);
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerIdentity data, int position) {

    }

    @Override
    public List<CryptoBrokerIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerIdentity> data = new ArrayList<>();

        try {
            data.addAll(moduleManager.getListOfIdentities());
            if (moduleManager.getListOfIdentities().isEmpty()) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.banner_crypto_broker)
                        .setIconRes(R.drawable.crypto_broker)
                        .setSubTitle(R.string.cbw_crypto_broker_wallet_identity_2_subTitle)// + identities)
                        .setBody(R.string.cbw_crypto_broker_wallet_identity_2_body)
                        .setTextFooter(R.string.cbw_crypto_broker_wallet_identity_2_footer)
                        .build();
                presentationDialog.setOnDismissListener(this);

                boolean showDialog;
                try {
                    CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
                    showDialog = moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
                    if (showDialog) {
                        presentationDialog.show();
                    }
                } catch (FermatException e) {
                    makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
                }
                //presentationDialog.show();
            }


        } catch (FermatException ex) {

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                        ex);
            }
        }

        return data;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                identities = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(identities);


            } else {

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbw_wizard_step_set_identity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.cbw_identities_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onDetach() {
        layoutManager = null;
        super.onDetach();
    }

    /**
     * This method will be invoked when the dialog is dismissed.
     *
     * @param dialog The dialog that was dismissed will be passed into the
     *               method.
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        try {
            adapter.changeDataSet(moduleManager.getListOfIdentities());
        } catch (CantGetCryptoBrokerIdentityListException e) {
            errorManager.reportUnexpectedWalletException(
                    Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                    e);
        } catch (CantListCryptoBrokerIdentitiesException e) {
            errorManager.reportUnexpectedWalletException(
                    Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                    e);
        }
    }
}
