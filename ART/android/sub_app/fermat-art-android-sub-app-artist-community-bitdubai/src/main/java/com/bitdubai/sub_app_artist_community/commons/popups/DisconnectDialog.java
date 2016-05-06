package com.bitdubai.sub_app_artist_community.commons.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSession;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class DisconnectDialog extends FermatDialog<ArtistSubAppSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    FermatButton positiveBtn;
    FermatButton negativeBtn;
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mTitle;
    CharSequence description;
    CharSequence username;
    CharSequence title;

    ArtistCommunityInformation artistCommunityInformation;

    ArtistCommunitySelectableIdentity identity;


    public DisconnectDialog(Activity a,
                            ArtistSubAppSession artistSubAppSession,
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

        mDescription = (FermatTextView) findViewById(R.id.aac_description);
        mUsername = (FermatTextView) findViewById(R.id.aac_user_name);
        mTitle = (FermatTextView)findViewById(R.id.aac_title);
        positiveBtn = (FermatButton) findViewById(R.id.aac_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.aac_negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mDescription.setText(description!= null ? description : "");
        mUsername.setText(username!= null ? username: "");
        mTitle.setText(title != null ? title: "");

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

                    getSession().getModuleManager().disconnectArtist(artistCommunityInformation.getConnectionId());
                    Toast.makeText(getContext(), "Disconnected successfully", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 1);

                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }   catch (ArtistDisconnectingFailedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Could not disconnect, please try again", Toast.LENGTH_SHORT).show();
            } catch (ConnectionRequestNotFoundException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error. Could not disconnect.", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        }else if( i == R.id.aac_negative_button){
            dismiss();
        }
    }


}
