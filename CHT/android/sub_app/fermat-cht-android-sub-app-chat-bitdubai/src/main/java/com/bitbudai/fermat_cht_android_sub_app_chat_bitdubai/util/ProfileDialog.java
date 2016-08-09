package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

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
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * Created by roy on 7/06/16.
 */
public class ProfileDialog extends FermatDialog implements View.OnClickListener {

    private ImageView profilePhoto;
    private ImageView chatButton;
    private TextView profileName;
    String bodyName;
    Bitmap image;
    int BUTTON_TOUCH = 0;
    public int TOUCH_CHAT = 1;


    public ProfileDialog(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);

    }

    public void setProfilePhoto(Bitmap photo) {
        image = photo;
    }

    public void setProfileName(String name) {
        bodyName = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileName = (TextView) this.findViewById(R.id.contact_name_dialog);
        profilePhoto = (ImageView) this.findViewById(R.id.profile_image_dialog);
        chatButton = (ImageView) this.findViewById(R.id.chatContactButton);
        chatButton.setOnClickListener(this);

        profileName.setText(bodyName);
        profilePhoto.setImageBitmap(image);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chatContactButton) {
            setButtonTouch(TOUCH_CHAT);
            dismiss();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.cht_dialog_contact_info;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    private void setButtonTouch(int touch) {
        BUTTON_TOUCH = touch;
    }

    public int getButtonTouch() {
        return BUTTON_TOUCH;
    }
}
