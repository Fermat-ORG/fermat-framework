package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;
import com.edmodo.cropper.CropImageView;
/**
 * FERMAT-ORG
 * Developed by Lozadaa on 13/05/16.
 */
public class DialogCropImage extends FermatDialog implements View.OnClickListener{

    /**
     * UI components
     */
    private ImageView camBtn;
    private ImageView galleryBtn;
    private ImageView rotateBtn;
    int BUTTON_TOUCH = 0;
    public int TOUCH_GALLERY = 1;
    public int TOUCH_CAM = 2;
    public int TOUCH_ROTATE = 3;
    CropImageView cropImageView;
    Bitmap image;
    Bitmap croppedImage;
    public DialogCropImage(Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources, Bitmap image) {
        super(activity, referenceAppFermatSession, resources);
        this.image = image;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            cropImageView = (CropImageView) findViewById(R.id.CropImageView);
            cropImageView.setImageBitmap(image);
            cropImageView.setGuidelines(2);
            Button btnCrop = (Button) findViewById(R.id.btnCrop);
              Button btnRotate = (Button) findViewById(R.id.btnRotateCropper);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);
            btnCrop.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnRotate.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_crop_image;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public Bitmap getCroppedImage(){
        return croppedImage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnCrop) {
            if(cropImageView.getCroppedImage().getHeight() >= 200 && cropImageView.getCroppedImage().getWidth() >= 200) {
                croppedImage = cropImageView.getCroppedImage();
                dismiss();
            }else{
                Toast.makeText(getActivity(), "Image crop is too small", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == R.id.btnCancel) {
            dismiss();
        }
        if( i == R.id.btnRotateCropper){
            cropImageView.rotateImage(90);
        }
    }
}