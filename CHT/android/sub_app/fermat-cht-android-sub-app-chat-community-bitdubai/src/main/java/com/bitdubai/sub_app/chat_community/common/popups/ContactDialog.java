package com.bitdubai.sub_app.chat_community.common.popups;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.chat_community.R;

/**
 * Created by roy on 10/06/16.
 */
public class ContactDialog extends FermatDialog implements View.OnClickListener {

    private ImageView profilePhoto;
    private TextView profileName;
    private TextView countryName;

    String bodyName;
    String country;
    Bitmap image;

    public ContactDialog (Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);

    }


    public void setProfilePhoto (Bitmap photo){
        image = photo;
    }

    public void setProfileName (String name){
        bodyName = name;
    }

    public void setCountryText (String txt){
        country = txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileName = (TextView) this.findViewById(R.id.profile_name);
        profilePhoto = (ImageView) this.findViewById(R.id.profile_image);
        countryName = (TextView) this.findViewById(R.id.country_place);

        profileName.setText(bodyName);
        profilePhoto.setImageBitmap(image);
        countryName.setText(country);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.cht_comm_dialog_contact_info;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
