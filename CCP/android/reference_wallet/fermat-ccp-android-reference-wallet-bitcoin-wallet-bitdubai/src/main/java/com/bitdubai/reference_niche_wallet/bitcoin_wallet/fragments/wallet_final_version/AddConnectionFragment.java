package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.AddConnectionsAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.AddConnectionCallback;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer
 */
public class AddConnectionFragment extends FermatWalletListFragment<CryptoWalletIntraUserActor>
        implements FermatListItemListeners<CryptoWalletIntraUserActor>,AddConnectionCallback {


    private static final Integer MAX_USER_SHOW = 20;
    private int offset = 0;
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoWalletIntraUserActor> intraUserInformationList;
    private ReferenceWalletSession referenceWalletSession;
    private Menu menu;
    private boolean isMenuVisible;
    private int connectionPickCounter;
    private LinearLayout empty_view;
    private boolean connectionDialogIsShow=false;


    public static AddConnectionFragment newInstance() {
        return new AddConnectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            referenceWalletSession = (ReferenceWalletSession) appSession;
            moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();
            errorManager = referenceWalletSession.getErrorManager();
            intraUserInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
            isMenuVisible=false;
            connectionPickCounter = 0;
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
            onRefresh();
        } catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    private void setUpScreen(View layout) {
        empty_view = (LinearLayout) layout.findViewById(R.id.empty_view);
        empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(
                        getActivity(),
                        referenceWalletSession,
                        null);

                connectionWithCommunityDialog.show();
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
        return R.layout.fragment_add_connections_list;
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
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                intraUserInformationList = (ArrayList) result[0];
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

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,ex);
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
            menu.add(0, BitcoinWalletConstants.IC_ACTION_ADD_CONNECTION, 0, "ADD")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == BitcoinWalletConstants.IC_ACTION_ADD_CONNECTION){
                for(CryptoWalletIntraUserActor cryptoWalletIntraUserActor : intraUserInformationList){
                    try {
                        if (cryptoWalletIntraUserActor.isSelected()) {
                            moduleManager.convertConnectionToContact(
                                    cryptoWalletIntraUserActor.getAlias(),
                                    Actors.INTRA_USER,
                                    cryptoWalletIntraUserActor.getPublicKey(),
                                    cryptoWalletIntraUserActor.getProfileImage(),
                                    Actors.INTRA_USER,
                                    referenceWalletSession.getIntraUserModuleManager().getPublicKey()
                                    ,appSession.getAppPublicKey(),
                                    CryptoCurrency.BITCOIN,
                                    BlockchainNetworkType.DEFAULT);
                            Toast.makeText(getActivity(),"Contact Created",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Please try again later",Toast.LENGTH_SHORT).show();
                        errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                    }
                }
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
    public List<CryptoWalletIntraUserActor> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoWalletIntraUserActor> data = new ArrayList<>();

        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(),"Nodule manager null",Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.listAllIntraUserConnections(moduleManager.getActiveIdentities().get(0).getPublicKey(),
                        referenceWalletSession.getAppPublicKey(),
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


    @Override
    public void onItemClickListener(CryptoWalletIntraUserActor data, int position) {
        //intraUserIdentitySubAppSession.setData(SessionConstants.IDENTITY_SELECTED,data);
        //changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
        //data.setSelected(!data.isSelected());
        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onLongItemClickListener(CryptoWalletIntraUserActor data, int position) {

    }

    @Override
    public void addMenuEnabled() {
        if(!isMenuVisible){
            menu.add(0, BitcoinWalletConstants.IC_ACTION_ADD_CONNECTION, 0, "ADD")
                    .setIcon(R.drawable.button_add_connection)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            connectionPickCounter++;
        }
    }

    @Override
    public void addMenuDisabled() {
        connectionPickCounter--;
        if(connectionPickCounter==0){
            menu.clear();
        }
    }
}
