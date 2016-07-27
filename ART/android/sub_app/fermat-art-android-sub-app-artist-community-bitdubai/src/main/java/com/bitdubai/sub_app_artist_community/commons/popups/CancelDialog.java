package com.bitdubai.sub_app_artist_community.commons.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;


/**
 * Created by Gabriel Araujo 29/04/2016.
 *
 */
public class CancelDialog extends FermatDialog<ReferenceAppFermatSession<ArtistCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mTitle;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    CharSequence description;
    CharSequence username;
    CharSequence title;

    ArtistCommunityInformation artistCommunityInformation;

    ArtistCommunitySelectableIdentity identity;


    public CancelDialog(Activity a,
                        ReferenceAppFermatSession artistSubAppSession,
                        SubAppResourcesProviderManager subAppResources,
                        ArtistCommunityInformation artistCommunityInformation,
                        ArtistCommunitySelectableIdentity identity) {

        super(a, artistSubAppSession, subAppResources);

        this.artistCommunityInformation = artistCommunityInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = (FermatTextView) findViewById(R.id.aac_title);
        mDescription = (FermatTextView) findViewById(R.id.aac_description);
        mUsername = (FermatTextView) findViewById(R.id.aac_user_name);
        positiveBtn = (FermatButton) findViewById(R.id.aac_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.aac_negative_button);
        mTitle.setText("Cancel");
        mDescription.setText("Do you want to cancel connection with");
        mUsername.setText(artistCommunityInformation.getAlias()+"?");
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);


    }
    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public void setUsername(CharSequence username) {
        this.username = username;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
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
            try {
                if (artistCommunityInformation != null && identity != null) {

                    getSession().getModuleManager().cancelArtist(artistCommunityInformation.getConnectionId());
                    Toast.makeText(getActivity(), "Cancelled successfully", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 1);

                } else {
                    Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (ArtistCancellingFailedException e) {
                Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            } catch (ConnectionRequestNotFoundException e) {
                Toast.makeText(getActivity(), "The conenction request was not found, please try again later.", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        }else if( i == R.id.aac_negative_button){
            dismiss();
        }
    }


}
