package com.bitdubai.sub_app_artist_community.commons.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;

import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AcceptDialog extends FermatDialog<ReferenceAppFermatSession<ArtistCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */


    ArtistCommunitySelectableIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    //Identity data
    private UUID connectionId;
    private String alias;

    public AcceptDialog(
            Activity a,
            ReferenceAppFermatSession artistSubAppSession,
            SubAppResourcesProviderManager subAppResources,
            ArtistCommunityInformation artistCommunityInformation,
            ArtistCommunitySelectableIdentity identity) {

        super(a, artistSubAppSession, subAppResources);
        this.connectionId = artistCommunityInformation.getConnectionId();
        this.alias = artistCommunityInformation.getAlias();
        this.identity = identity;
    }

    public AcceptDialog(
            Activity a,
            ReferenceAppFermatSession artistSubAppSession,
            SubAppResourcesProviderManager subAppResources,
            UUID connectionId,
            String alias,
            ArtistCommunitySelectableIdentity identity) {
        super(a, artistSubAppSession, subAppResources);
        this.connectionId = connectionId;
        this.alias = alias;
        this.identity = identity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (FermatTextView) findViewById(R.id.aac_title);
        description = (FermatTextView) findViewById(R.id.aac_description);
        userName = (FermatTextView) findViewById(R.id.aac_user_name);
        positiveBtn = (FermatButton) findViewById(R.id.aac_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.aac_negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connect");
        description.setText("Do you want to accept the connection request from");
        userName.setText(alias+"?");

    }

    @Override
    protected int setLayoutId() {
        return R.layout.aac_dialog_builder;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.aac_positive_button) {
            // try {
            if (connectionId != null && alias !=null && identity != null) {
                try {
                    getSession().getModuleManager().acceptArtist(connectionId);
                } catch (CantAcceptRequestException e) {
                    Toast.makeText(getContext(), "Can not accept the request from "+alias+".", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (ConnectionRequestNotFoundException e) {
                    Toast.makeText(getContext(), "Request ID not found.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Accepted connection with "+alias, Toast.LENGTH_SHORT).show();
                getSession().setData("connectionresult", 3);
            } else {
                Toast.makeText(getActivity(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
            dismiss();
            /*} catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }*/
            dismiss();
        } else if (i == R.id.aac_negative_button) {
            try {
                if (connectionId != null && alias !=null && identity != null) {
                    Toast.makeText(getContext(), "TODO DENY ->", Toast.LENGTH_SHORT).show();
                    getSession().getModuleManager().denyConnection(connectionId);
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }


}
