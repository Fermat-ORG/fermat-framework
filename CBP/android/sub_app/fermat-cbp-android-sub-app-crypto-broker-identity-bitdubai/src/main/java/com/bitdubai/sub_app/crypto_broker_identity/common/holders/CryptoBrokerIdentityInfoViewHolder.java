package com.bitdubai.sub_app.crypto_broker_identity.common.holders;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.sub_app.crypto_broker_identity.R;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;


/**
 * Created by nelson on 01/09/15.
 */
public class CryptoBrokerIdentityInfoViewHolder extends FermatViewHolder implements FermatWorkerCallBack {
    public static final int INVALID_ENTRY_DATA = 4;

    private ErrorManager errorManager;
    private ExecutorService executor;
    private Activity activity;

    private ImageView identityImage;
    private FermatTextView identityName;
    private FermatTextView identityStatus;

    public CryptoBrokerIdentityInfoViewHolder(final View itemView, final ErrorManager errorManager, final Activity activity) {
        super(itemView);

        this.errorManager = errorManager;
        this.activity = activity;

        identityImage = (ImageView) itemView.findViewById(R.id.crypto_broker_identity_image);
        identityName = (FermatTextView) itemView.findViewById(R.id.crypto_broker_identity_alias);
        identityStatus = (FermatTextView) itemView.findViewById(R.id.crypto_broker_identity_status);
    }

    public void setImage(byte[] imageInBytes) {
        ByteArrayInputStream bytes = new ByteArrayInputStream(imageInBytes);
        BitmapDrawable bmd = new BitmapDrawable(bytes);
        identityImage.setImageBitmap(bmd.getBitmap());
    }

    public void setStatus(String status) {
        identityStatus.setText(status);
    }

    public void setText(String text) {
        identityName.setText(text);
    }

    public void setText(SpannableString text) {
        identityName.setText(text);
    }

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        if (result.length > 0) {
            int resultCode = (int) result[0];

            if (resultCode == INVALID_ENTRY_DATA) {
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

        Toast.makeText(activity.getApplicationContext(), "Error trying to change the exposure level.", Toast.LENGTH_SHORT).show();

        errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }
}
