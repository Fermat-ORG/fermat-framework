package com.bitdubai.sub_app_artist_community.commons.popups;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ListIdentitiesDialog extends
        FermatDialog<
                ReferenceAppFermatSession<ArtistCommunitySubAppModuleManager>,
                SubAppResourcesProviderManager>
        implements
        FermatListItemListeners<ArtistCommunitySelectableIdentity> {

    /**
     * UI components
     */
    private CharSequence title;
    private AppSelectableIdentitiesListAdapter adapter;

    /**
     * Managers
     */
    private ArtistCommunitySubAppModuleManager manager;

    public ListIdentitiesDialog(final Context activity,
                                final ReferenceAppFermatSession subAppSession,
                                final SubAppResourcesProviderManager subAppResources) {
        super(
                activity,
                subAppSession,
                subAppResources
        );
        manager = (ArtistCommunitySubAppModuleManager) subAppSession.getModuleManager();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<ArtistCommunitySelectableIdentity> artistCommunitySelectableIdentities = new ArrayList<>();
        try {
            artistCommunitySelectableIdentities = manager.listSelectableIdentities();
        } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {
            getSession().getErrorManager().reportUnexpectedUIException(
                    UISource.ADAPTER,
                    UnexpectedUIExceptionSeverity.UNSTABLE,
                    cantListIdentitiesToSelectException
            );
        }

        adapter = new AppSelectableIdentitiesListAdapter(getActivity(), artistCommunitySelectableIdentities);
        adapter.setFermatListEventListener(this);

        adapter.changeDataSet(artistCommunitySelectableIdentities);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.accrecycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClickListener(ArtistCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void onLongItemClickListener(ArtistCommunitySelectableIdentity data, int position) {
        manager.setSelectedActorIdentity(data);
        dismiss();
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.aac_fragment_list_identities;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}