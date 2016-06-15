package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.adapters.AppSelectableIdentitiesListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity.UNSTABLE;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ListIdentitiesDialog extends FermatDialog<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements FermatListItemListeners<CryptoBrokerCommunitySelectableIdentity> {


    /**
     * Managers
     */
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;

    public ListIdentitiesDialog(final Context activity,
                                final ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> subAppSession,
                                final SubAppResourcesProviderManager subAppResources) {

        super(activity, subAppSession, subAppResources);

        moduleManager = subAppSession.getModuleManager();
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<CryptoBrokerCommunitySelectableIdentity> identityList = new ArrayList<>();

        try {
            identityList = moduleManager.listSelectableIdentities();

        } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {
            final ErrorManager errorManager = getSession().getErrorManager();
            errorManager.reportUnexpectedUIException(UISource.ADAPTER, UNSTABLE, cantListIdentitiesToSelectException);
        }

        AppSelectableIdentitiesListAdapter adapter = new AppSelectableIdentitiesListAdapter(getActivity(), identityList);
        adapter.setFermatListEventListener(this);

        adapter.changeDataSet(identityList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(CryptoBrokerCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.cbc_fragment_list_identities;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}
