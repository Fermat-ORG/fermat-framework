package com.bitdubai.sub_app.crypto_customer_identity.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;

/**
 * Created by penny on 26/07/16.
 */
public class DialogSelectCamPic extends FermatDialog implements View.OnClickListener {

    private Button camBtn;
    private Button galleryBtn;
    int BUTTON_TOUCH = 0;
    public int TOUCH_GALLERY = 1;
    public int TOUCH_CAM = 2;

    public DialogSelectCamPic(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryBtn = (Button) findViewById(R.id.cbp_galery_picture_option);
        camBtn = (Button) findViewById(R.id.cbp_cam_picture_option);
        galleryBtn.setOnClickListener(this);
        camBtn.setOnClickListener(this);
        getWindow().setTitle("");
    }

    private void setButtonTouch(int touch) {
        BUTTON_TOUCH = touch;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.dialog_cbp_select_cam_or_gallery;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public int getButtonTouch() {
        return BUTTON_TOUCH;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cbp_galery_picture_option) {
            setButtonTouch(TOUCH_GALLERY);
            dismiss();
        }
        if (i == R.id.cbp_cam_picture_option) {
            setButtonTouch(TOUCH_CAM);
            dismiss();
        }

    }
}
