package com.bitdubai.sub_app.intra_user_identity.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantListIntraUsersIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserModuleIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.common.adapters.IntraUserIdentityInfoAdapter;

import com.bitdubai.sub_app.intra_user_identity.common.views.DividerItemDecoration;

import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSession;
import com.bitdubai.sub_app.intra_user_identity.util.CommonLogger;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntraUserIdentityListFragment extends FermatListFragment<IntraUserModuleIdentity>
        implements FermatListItemListeners<IntraUserModuleIdentity> {


    private IntraUserIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<IntraUserModuleIdentity> identityInformationList;

    IntraUserIdentitySubAppSession intraUserIdentitySubAppSession ;


    public static IntraUserIdentityListFragment newInstance() {
        return new IntraUserIdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            intraUserIdentitySubAppSession = (IntraUserIdentitySubAppSession) appSession;
            moduleManager = intraUserIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

         try {

             FloatingActionButton newIdentityButton = (FloatingActionButton) layout.findViewById(R.id.new_crypto_broker_identity_float_action_button);
             newIdentityButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode(), appSession.getAppPublicKey());
                 }
             });

             if (getActivity().getActionBar() != null) {
                 getActivity().getActionBar().setDisplayShowHomeEnabled(false);
             }

             RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
             recyclerView.addItemDecoration(itemDecoration);

             //TODO: para la primer version limitamos la cantidad de identidades a una sola
             //TODO: Robert sacá esto si queres testear lo tuyo XXOO
             if(moduleManager.getAllIntraWalletUsersFromCurrentDeviceUser().size() > 0)
                 newIdentityButton.setVisibility(View.INVISIBLE);
             else
                 newIdentityButton.setVisibility(View.VISIBLE);
         }
        catch (CantListIntraUsersIdentityException e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Can't Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        catch (Exception e)
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
        return R.layout.fragment_intra_user_identity_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.crypto_broker_identity_recycler_view;
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
                identityInformationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(identityInformationList);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new IntraUserIdentityInfoAdapter(getActivity(), identityInformationList);
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
    public List<IntraUserModuleIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<IntraUserModuleIdentity> data = new ArrayList<>();

        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(),"Nodule manager null",Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.getAllIntraWalletUsersFromCurrentDeviceUser();
            }
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        return data;
    }


    @Override
    public void onItemClickListener(IntraUserModuleIdentity data, int position) {
        //intraUserIdentitySubAppSession.setData(SessionConstants.IDENTITY_SELECTED,data);
        //changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
    }

    @Override
    public void onLongItemClickListener(IntraUserModuleIdentity data, int position) {

    }
}
