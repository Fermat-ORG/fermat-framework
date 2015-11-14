package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.AddConnectionsAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer
 */
public class AddConnectionFragment extends FermatWalletListFragment<CryptoWalletIntraUserActor>
        implements FermatListItemListeners<CryptoWalletIntraUserActor> {


    private static final Integer MAX_USER_SHOW = 20;
    private int offset = 0;
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoWalletIntraUserActor> intraUserInformationList;
    private ReferenceWalletSession referenceWalletSession;


    public static AddConnectionFragment newInstance() {
        return new AddConnectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            referenceWalletSession = (ReferenceWalletSession) walletSession;
            moduleManager = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();
            errorManager = referenceWalletSession.getErrorManager();
            intraUserInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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

        } catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    protected boolean hasMenu() {
        return false;
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
            adapter = new AddConnectionsAdapter(getActivity(), intraUserInformationList);
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
    public List<CryptoWalletIntraUserActor> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoWalletIntraUserActor> data = new ArrayList<>();

        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(),"Nodule manager null",Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.listAllIntraUserConnections(moduleManager.getActiveIdentities().get(0).getPublicKey(),
                        referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
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
    }

    @Override
    public void onLongItemClickListener(CryptoWalletIntraUserActor data, int position) {

    }
}
