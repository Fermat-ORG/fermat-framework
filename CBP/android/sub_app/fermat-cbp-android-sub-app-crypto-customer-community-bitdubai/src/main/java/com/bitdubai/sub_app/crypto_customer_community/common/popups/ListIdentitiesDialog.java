package com.bitdubai.sub_app.crypto_customer_community.common.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 18/02/2016.
 */
public class ListIdentitiesDialog extends FermatDialog<CryptoCustomerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements FermatListItemListeners<CryptoCustomerCommunitySelectableIdentity> {

    /**
     * UI components
     */
    private CharSequence   title       ;
    private AppSelectableIdentitiesListAdapter adapter;


    /**
     * Managers
     */
    private CryptoCustomerCommunitySubAppModuleManager manager;

    public ListIdentitiesDialog(final Context activity,
                                final CryptoCustomerCommunitySubAppSession subAppSession,
                                final SubAppResourcesProviderManager subAppResources) {

        super(
                activity,
                subAppSession,
                subAppResources
        );

        manager = subAppSession.getModuleManager();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<CryptoCustomerCommunitySelectableIdentity> cryptoCustomerCommunitySelectableIdentitiesList = new ArrayList<>();

        try {

            cryptoCustomerCommunitySelectableIdentitiesList = manager.listSelectableIdentities();

        } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {

            getSession().getErrorManager().reportUnexpectedUIException(
                    UISource.ADAPTER,
                    UnexpectedUIExceptionSeverity.UNSTABLE,
                    cantListIdentitiesToSelectException
            );
        }

        adapter = new AppSelectableIdentitiesListAdapter(getActivity(), cryptoCustomerCommunitySelectableIdentitiesList);
        adapter.setFermatListEventListener(this);

        adapter.changeDataSet(cryptoCustomerCommunitySelectableIdentitiesList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(CryptoCustomerCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccc_fragment_list_identities;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}
