package com.bitdubai.sub_app.crypto_customer_community.common.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.adapters.AppSelectableIdentitiesListAdapter;

import java.util.List;


/**
 * Created by Alejandro Bicelis on 18/02/2016.
 */
public class ListIdentitiesDialog extends FermatDialog<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements FermatListItemListeners<CryptoCustomerCommunitySelectableIdentity> {


    /**
     * Managers
     */
    private CryptoCustomerCommunitySubAppModuleManager manager;

    public ListIdentitiesDialog(final Context activity,
                                final ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> subAppSession,
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

        getSelectableIdentitiesInOtherThread();

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
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccc_fragment_list_identities;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    private void getSelectableIdentitiesInOtherThread() {

        FermatWorker fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {
                return manager.listSelectableIdentities();
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            @SuppressWarnings("unchecked")
            public void onPostExecute(Object... result) {
                if (result != null && result[0] != null) {
                    List<CryptoCustomerCommunitySelectableIdentity> selectableIdentities = (List<CryptoCustomerCommunitySelectableIdentity>) result[0];

                    AppSelectableIdentitiesListAdapter adapter = new AppSelectableIdentitiesListAdapter(getActivity(), selectableIdentities);
                    adapter.setFermatListEventListener(ListIdentitiesDialog.this);

                    adapter.changeDataSet(selectableIdentities);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                Toast.makeText(getActivity(), "Error trying to get list of selectable identities", Toast.LENGTH_SHORT).show();

                getSession().getErrorManager().reportUnexpectedUIException(UISource.ADAPTER,
                        UnexpectedUIExceptionSeverity.UNSTABLE, ex);
            }
        });

        fermatWorker.execute();
    }
}
