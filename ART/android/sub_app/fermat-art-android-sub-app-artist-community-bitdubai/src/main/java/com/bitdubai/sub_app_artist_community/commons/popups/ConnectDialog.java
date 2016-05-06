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
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSession;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ConnectDialog extends FermatDialog<ArtistSubAppSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    FermatButton positiveBtn;
    FermatButton negativeBtn;
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mSecondDescription;
    FermatTextView mTitle;
    CharSequence description;
    CharSequence secondDescription;
    CharSequence username;
    CharSequence title;

    private final ArtistCommunityInformation information;
    private final ArtistCommunitySelectableIdentity identity   ;


    public ConnectDialog(final Activity activity                          ,
                         final ArtistSubAppSession      artistSubAppSession,
                         final SubAppResourcesProviderManager          subAppResources                   ,
                         final ArtistCommunityInformation        information                       ,
                         final ArtistCommunitySelectableIdentity identity                          ) {

        super(activity, artistSubAppSession, subAppResources);

        this.information = information;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = (FermatTextView) findViewById(R.id.aac_description);
        mUsername = (FermatTextView) findViewById(R.id.aac_user_name);
        mSecondDescription = (FermatTextView) findViewById(R.id.aac_second_description);
        mTitle = (FermatTextView) findViewById(R.id.aac_title);
        positiveBtn = (FermatButton) findViewById(R.id.aac_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.aac_negative_button);
        mSecondDescription.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mSecondDescription.setText(secondDescription != null ? secondDescription : "");
        mDescription.setText(description != null ? description : "");
        mUsername.setText(username != null ? username : "");
        mTitle.setText(title != null ? title : "");

    }

    public void setSecondDescription(CharSequence secondDescription) {
        this.secondDescription = secondDescription;
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
                if (information != null && identity != null) {

                    //System.out.println("*********** i'm the selected identity: "+identity);
                    //System.out.println("*********** i'm the selected broker information: " + information);

                    getSession().getModuleManager().requestConnectionToArtist(identity, information);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 2);

                } else {
                    Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantRequestConnectionException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Can not request connection", Toast.LENGTH_SHORT).show();
            } catch (ActorConnectionAlreadyRequestedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "The connection has already been requested", Toast.LENGTH_SHORT).show();
            } catch (ActorTypeNotSupportedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "The connection has already been requested", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        } else if (i == R.id.aac_negative_button) {
            dismiss();
        }
    }

}
