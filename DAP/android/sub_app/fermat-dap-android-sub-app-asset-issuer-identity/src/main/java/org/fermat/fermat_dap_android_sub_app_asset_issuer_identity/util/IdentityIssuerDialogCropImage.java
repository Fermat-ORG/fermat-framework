package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.util;

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
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;
import com.edmodo.cropper.CropImageView;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 13/05/16.
 */
public class IdentityIssuerDialogCropImage extends FermatDialog implements View.OnClickListener {

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

    public IdentityIssuerDialogCropImage(Context activity, FermatSession fermatSession, ResourceProviderManager resources, Bitmap image) {
        super(activity, fermatSession, resources);
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
        return R.layout.dap_issuer_identity_dialog_crop_image;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public Bitmap getCroppedImage() {
        return croppedImage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnCrop) {
            if (cropImageView.getCroppedImage().getHeight() >= 200 && cropImageView.getCroppedImage().getWidth() >= 200) {
                croppedImage = cropImageView.getCroppedImage();
                dismiss();
            } else {
                Toast.makeText(getActivity(), "Image crop is too small", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == R.id.btnCancel) {
            dismiss();
        }
        if (i == R.id.btnRotateCropper) {
            cropImageView.rotateImage(90);
        }
    }
}