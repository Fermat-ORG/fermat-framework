package com.bitdubai.sub_app_artist_community.commons.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSession;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AcceptDialog extends FermatDialog<ArtistSubAppSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */

    ArtistCommunityInformation artistCommunityInformation;

    ArtistCommunitySelectableIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;

    public AcceptDialog(Activity a                                 ,
                        ArtistSubAppSession      artistSubAppSession,
                        SubAppResourcesProviderManager          subAppResources                   ,
                        ArtistCommunityInformation        artistCommunityInformation           ,
                        ArtistCommunitySelectableIdentity identity                          ) {

        super(a, artistSubAppSession, subAppResources);

        this.artistCommunityInformation = artistCommunityInformation;
        this.identity             = identity               ;
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
        description.setText("Do you want to accept");
        userName.setText(artistCommunityInformation.getAlias());

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
            if (artistCommunityInformation != null && identity != null) {
                try {
                    getSession().getModuleManager().acceptArtist(artistCommunityInformation.getConnectionId());
                } catch (CantAcceptRequestException e) {
                    Toast.makeText(getContext(), artistCommunityInformation.getAlias() + " Can not accept the request.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (ConnectionRequestNotFoundException e) {
                    Toast.makeText(getContext(), artistCommunityInformation.getAlias() + "Request ID not found.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), artistCommunityInformation.getAlias() + " Accepted connection request", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
            dismiss();
            /*} catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }*/
            dismiss();
        } else if (i == R.id.aac_negative_button) {
            //try {
            if (artistCommunityInformation != null && identity != null) {
                Toast.makeText(getContext(), "TODO DENY ->", Toast.LENGTH_SHORT).show();
                // getSession().getModuleManager().denyConnection(identity.getPublicKey(), information.getPublicKey());
            } else {
                Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
            dismiss();
            /*} catch (IntraUserConnectionDenialFailedException e) {
                e.printStackTrace();
            }*/
            dismiss();
        }
    }


}
