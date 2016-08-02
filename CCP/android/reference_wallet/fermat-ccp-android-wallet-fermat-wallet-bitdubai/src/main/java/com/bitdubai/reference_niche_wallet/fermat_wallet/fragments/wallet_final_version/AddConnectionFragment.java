package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletIntraUserActor;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.FermatWalletConstants;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.AddConnectionsAdapter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.AddConnectionCallback;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer
 */
public class AddConnectionFragment extends FermatWalletListFragment<FermatWalletIntraUserActor,ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager>
        implements FermatListItemListeners<FermatWalletIntraUserActor>,AddConnectionCallback {


    private static final Integer MAX_USER_SHOW = 20;
    private int offset = 0;
    private FermatWallet moduleManager;
    private ErrorManager errorManager;
    private ArrayList<FermatWalletIntraUserActor> intraUserInformationList = new ArrayList<>();

    private ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;


    private Menu menu;
    private boolean isMenuVisible;
    private boolean isContactAddPopUp = false;
    private int connectionPickCounter;
    private LinearLayout empty_view;
    private boolean connectionDialogIsShow=false;
    Handler hnadler;
    BlockchainNetworkType blockchainNetworkType;

    public static AddConnectionFragment newInstance() {
        return new AddConnectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module

            fermatWalletSessionReferenceApp = appSession;
            moduleManager = fermatWalletSessionReferenceApp.getModuleManager();
            errorManager = fermatWalletSessionReferenceApp.getErrorManager();


            isMenuVisible=false;
            connectionPickCounter = 0;
            hnadler = new Handler();
            FermatWalletSettings fermatWalletSettings = null;
            fermatWalletSettings = moduleManager.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());

            if(fermatWalletSettings != null) {

                if (fermatWalletSettings.getBlockchainNetworkType() == null) {
                    fermatWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                moduleManager.persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), fermatWalletSettings);

            }

            blockchainNetworkType = moduleManager.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey()).getBlockchainNetworkType();

            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        try {
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);
            setUpScreen(layout);
            setUpFAV();
            onRefresh();
            if(intraUserInformationList.isEmpty()){
                FermatAnimationsUtils.showEmpty(getActivity(), true, empty_view);
                isContactAddPopUp = false;
            }else {
                FermatAnimationsUtils.showEmpty(getActivity(),false,empty_view);
                isContactAddPopUp = true;
            }

            hideSoftKeyboard(getActivity());
        } catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    private void setUpFAV() {
        FrameLayout frameLayout = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lbs = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        frameLayout.setLayoutParams(lbs);

        //ImageView icon = new ImageView(getActivity());  Create an icon
        //icon.setImageResource(R.drawable.btn_request_selector);
        //icon.setImageResource(R.drawable.ic_contact_newcontact);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Object[] object = new Object[2];
                    changeApp(SubAppsPublicKeys.CCP_COMMUNITY.getCode(), object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        View view = new View(getActivity());
        view.setLayoutParams(lbs);

        frameLayout.addView(view);
        frameLayout.setOnClickListener(onClickListener);
        view.setOnClickListener(onClickListener);
        final com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                .setContentView(frameLayout).setBackgroundDrawable(R.drawable.fermat_add_connection_selector)
                .build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .attachTo(actionButton)
                .build();
    }

    private void setUpScreen(View layout) {
        empty_view = (LinearLayout) layout.findViewById(R.id.empty_view);
        empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(
                        getActivity(),
                        fermatWalletSessionReferenceApp,
                        null);
                if (isContactAddPopUp){
                    connectionWithCommunityDialog.show();
                }
                connectionWithCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        connectionDialogIsShow = false;
                        onRefresh();
                    }
                });
                connectionDialogIsShow = true;
            }
        });
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fermat_wallet_fragment_add_connections_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.connections_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if(menu!=null) menu.clear();
        connectionPickCounter = 0;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                intraUserInformationList = (ArrayList) result[0];
                clean(intraUserInformationList);
                if (adapter != null)
                    adapter.changeDataSet(intraUserInformationList);
                if(intraUserInformationList.isEmpty()){
                    FermatAnimationsUtils.showEmpty(getActivity(), true, empty_view);
                }else {
                    FermatAnimationsUtils.showEmpty(getActivity(),false,empty_view);
                }
            }
        }
    }

    private void clean(List<FermatWalletIntraUserActor> intraUserInformationList){
        for(FermatWalletIntraUserActor cryptoWalletIntraUserActor : intraUserInformationList){
             cryptoWalletIntraUserActor.setSelected(false);
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_FERMAT_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new AddConnectionsAdapter(getActivity(), intraUserInformationList,this);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        if(isMenuVisible){
            menu.add(0, FermatWalletConstants.IC_ACTION_ADD_CONNECTION, 0, "ADD")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == FermatWalletConstants.IC_ACTION_ADD_CONNECTION){
                for(FermatWalletIntraUserActor fermatWalletIntraUserActor : intraUserInformationList){
                    try {
                        if (fermatWalletIntraUserActor.isSelected()) {
                            moduleManager.convertConnectionToContact(
                                    fermatWalletIntraUserActor.getAlias(),
                                    Actors.INTRA_USER,
                                    fermatWalletIntraUserActor.getPublicKey(),
                                    fermatWalletIntraUserActor.getProfileImage(),
                                    Actors.INTRA_USER,
                                    moduleManager.getSelectedActorIdentity().getPublicKey()
                                    , fermatWalletSessionReferenceApp.getAppPublicKey(),
                                    CryptoCurrency.BITCOIN,
                                    blockchainNetworkType);
                            Toast.makeText(getActivity(),"Contact Created",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Please try again later",Toast.LENGTH_SHORT).show();
                        errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                    }
                }
                onRefresh();
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<FermatWalletIntraUserActor> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<FermatWalletIntraUserActor> data = new ArrayList<>();
        runThread();
        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(),"Nodule manager null",Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.listAllIntraUserConnections(moduleManager.getActiveIdentities().get(0).getPublicKey(),
                        fermatWalletSessionReferenceApp.getAppPublicKey(),
                        MAX_USER_SHOW,
                        offset);
            }
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return data;
    }

    private void runThread(){
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(320);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (menu != null) menu.clear();
                        }
                    });
                }
            }
        };
        timer.start();
    }


    @Override
    public void onItemClickListener(FermatWalletIntraUserActor data, int position) {
        //intraUserIdentitySubAppSession.setData(SessionConstants.IDENTITY_SELECTED,data);
        //changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
        //data.setSelected(!data.isSelected());
        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onLongItemClickListener(FermatWalletIntraUserActor data, int position) {

    }

    @Override
    public void addMenuEnabled() {
        connectionPickCounter++;
        if(!isMenuVisible){
            isMenuVisible = true;
            menu.add(0, FermatWalletConstants.IC_ACTION_ADD_CONNECTION, 0, "ADD")
                    .setIcon(R.drawable.fermat_button_add_connection)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    @Override
    public void addMenuDisabled() {
        connectionPickCounter--;
        if(connectionPickCounter==0){
            menu.clear();
            isMenuVisible = false;
        }
    }

    @Override
    public void setSelected(FermatWalletIntraUserActor cryptoWalletIntraUserActor,boolean selected) {
        intraUserInformationList.remove(cryptoWalletIntraUserActor);
        cryptoWalletIntraUserActor.setSelected(selected);
        intraUserInformationList.add(cryptoWalletIntraUserActor);

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
