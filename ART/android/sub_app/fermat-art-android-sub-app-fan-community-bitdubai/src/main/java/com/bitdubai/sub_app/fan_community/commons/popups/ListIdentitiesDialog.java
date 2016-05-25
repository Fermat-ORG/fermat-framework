package com.bitdubai.sub_app.fan_community.commons.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class ListIdentitiesDialog extends
        FermatDialog<
                FanCommunitySubAppSession,
                SubAppResourcesProviderManager>
        implements
        FermatListItemListeners<FanCommunitySelectableIdentity> {

    /**
     * UI components
     */
    private CharSequence title;
    private AppSelectableIdentitiesListAdapter adapter;

    /**
     * Managers
     */
    private FanCommunityModuleManager manager;

    public ListIdentitiesDialog(final Context activity,
                                final FanCommunitySubAppSession subAppSession,
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

        List<FanCommunitySelectableIdentity> fanCommunitySelectableIdentities = new ArrayList<>();
        try {
            fanCommunitySelectableIdentities = manager.listSelectableIdentities();
        } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {
            getSession().getErrorManager().reportUnexpectedUIException(
                    UISource.ADAPTER,
                    UnexpectedUIExceptionSeverity.UNSTABLE,
                    cantListIdentitiesToSelectException
            );
        }

        adapter = new AppSelectableIdentitiesListAdapter(getActivity(), fanCommunitySelectableIdentities);
        adapter.setFermatListEventListener(this);

        adapter.changeDataSet(fanCommunitySelectableIdentities);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.afcrecycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(FanCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void onLongItemClickListener(FanCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.afc_fragment_list_identities;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}
