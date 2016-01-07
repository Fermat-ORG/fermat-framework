package com.bitdubai.sub_app.crypto_broker_identity.common.holders;

import android.app.Activity;
import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker;

import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker.INVALID_ENTRY_DATA;
import static com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker.SUCCESS;

/**
 * Created by nelson on 01/09/15.
 */
public class CryptoBrokerIdentityInfoViewHolder extends FermatViewHolder implements FermatWorkerCallBack {

    private ErrorManager errorManager;

    private ExecutorService executor;

    private CryptoBrokerIdentityModuleManager moduleManager;

    private Activity activity;

    private String identityPublicKey;
    private ImageView identityImage;
    private FermatTextView identityName;
    private FermatCheckBox publishIdentityCheckBox;

    public CryptoBrokerIdentityInfoViewHolder(final View                              itemView     ,
                                              final ErrorManager                      errorManager ,
                                              final CryptoBrokerIdentityModuleManager moduleManager,
                                              final Activity                          activity     ) {
        super(itemView);

        this.errorManager  = errorManager ;
        this.moduleManager = moduleManager;
        this.activity      = activity     ;

        identityImage = (ImageView) itemView.findViewById(R.id.crypto_broker_identity_image);
        identityName = (FermatTextView) itemView.findViewById(R.id.crypto_broker_identity_alias);
        publishIdentityCheckBox = (FermatCheckBox) itemView.findViewById(R.id.crypto_broker_identity_publish_identity);
    }

    public void setImage(byte[] imageInBytes) {
        RoundedBitmapDrawable roundedBitmap;
        if (imageInBytes != null)
            roundedBitmap = ImagesUtils.getRoundedBitmap(identityImage.getResources(), imageInBytes);
        else
            roundedBitmap = ImagesUtils.getRoundedBitmap(identityImage.getResources(), R.drawable.person);

        identityImage.setImageDrawable(roundedBitmap);
    }

    public void setPublished(boolean isPublished) {

        publishIdentityCheckBox.setChecked(isPublished);

        publishIdentityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {

                changeExposureLevel(value);
            }
        });
    }

    public void changeExposureLevel(boolean value) {
        PublishIdentityWorker publishIdentityWorker = new PublishIdentityWorker(activity, moduleManager, identityPublicKey, value, this);
        executor = publishIdentityWorker.execute();
    }

    public void setText(String text) {
        identityName.setText(text);
    }

    public void setText(SpannableString text) {
        identityName.setText(text);
    }

    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        if (result.length > 0) {
            int resultCode = (int) result[0];

            if (resultCode == SUCCESS) {
                if (publishIdentityCheckBox.isChecked())
                    Toast.makeText(activity, "Published successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(activity, "Unpublished successfully", Toast.LENGTH_LONG).show();

            } else if (resultCode == INVALID_ENTRY_DATA) {
                Toast.makeText(activity, "There was a problem trying to get identity information.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        Toast.makeText(activity.getApplicationContext(),
                "Error trying to change the exposure level.",
                Toast.LENGTH_SHORT).
                show();

        errorManager.reportUnexpectedSubAppException(
                SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                ex);
    }
}
