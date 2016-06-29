package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.R;

/**
 * Created by penny on 29/06/16.
 */
public class DialogSelectCamPic extends FermatDialog implements View.OnClickListener{

    /**
     * UI components
     */
    private ImageView camBtn;
    private ImageView galleryBtn;
    int BUTTON_TOUCH = 0;
    public int TOUCH_GALLERY = 1;
    public int TOUCH_CAM = 2;
    public DialogSelectCamPic(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryBtn = (ImageView) findViewById(R.id.img_gallery);
        camBtn = (ImageView) findViewById(R.id.img_cam);
        galleryBtn.setOnClickListener(this);
        camBtn.setOnClickListener(this);
        getWindow().setTitle("");
    }

    private void setButtonTouch(int touch) {
        BUTTON_TOUCH = touch;
    }



    @Override
    protected int setLayoutId() {
        return R.layout.dap_dialog_redeem_point_v2_select_cam_or_gallery;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public int getButtonTouch(){
        return BUTTON_TOUCH;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_gallery) {
            setButtonTouch(TOUCH_GALLERY);
            dismiss();
        }
        if(i == R.id.img_cam){
            setButtonTouch(TOUCH_CAM);
            dismiss();
        }

    }
}
