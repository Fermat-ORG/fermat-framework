package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.adapters.AppFriendsListAdapter;
import com.bitdubai.sub_app.crypto_broker_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app.crypto_broker_community.constants.Constants;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ListIdentitiesDialog extends FermatDialog<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager> implements FermatListItemListeners<CryptoBrokerCommunitySelectableIdentity> {

    /**
     * UI components
     */
    private CharSequence   title       ;

    private AppSelectableIdentitiesListAdapter adapter;

    public ListIdentitiesDialog(final Activity                                activity       ,
                                final CryptoBrokerCommunitySubAppSession      subAppSession  ,
                                final SubAppResourcesProviderManager          subAppResources) {

        super(
                activity,
                subAppSession,
                subAppResources
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<CryptoBrokerCommunitySelectableIdentity> cryptoBrokerCommunitySelectableIdentitiesList = new ArrayList<>();

        try {

            cryptoBrokerCommunitySelectableIdentitiesList = getSession().getModuleManager().listSelectableIdentities();

        } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {

            getSession().getErrorManager().reportUnexpectedUIException(
                    UISource.ADAPTER,
                    UnexpectedUIExceptionSeverity.UNSTABLE,
                    cantListIdentitiesToSelectException
            );
        }

        adapter = new AppSelectableIdentitiesListAdapter(getActivity(), cryptoBrokerCommunitySelectableIdentitiesList);
        adapter.setFermatListEventListener(this);

        adapter.changeDataSet(cryptoBrokerCommunitySelectableIdentitiesList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(CryptoBrokerCommunitySelectableIdentity data, int position) {

        System.out.println("****** Seleccione esta identidad: "+data);
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunitySelectableIdentity data, int position) {

        System.out.println("****** Seleccione largamente esta identidad: "+data);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_connections_list;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}
