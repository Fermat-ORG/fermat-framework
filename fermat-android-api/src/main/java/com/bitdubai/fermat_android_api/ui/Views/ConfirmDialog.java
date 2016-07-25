package com.bitdubai.fermat_android_api.ui.Views;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.lang.ref.WeakReference;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class ConfirmDialog extends FermatDialog<ReferenceAppFermatSession, ResourceProviderManager> implements
        View.OnClickListener {

    private FermatTextView titleText;
    private FermatTextView descriptionText;
    private FermatButton yesBtn;
    private FermatButton noBtn;

    private OnClickAcceptListener btnListener;

    private String title;
    private String message;
    private int colorStyle;

    public interface OnClickAcceptListener {
        void onClick();
    }

    public ConfirmDialog(Activity activity, ReferenceAppFermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        titleText = (FermatTextView) findViewById(R.id.confirmDialogTitle);
        descriptionText = (FermatTextView) findViewById(R.id.confirmDialogContentText);
        yesBtn = (FermatButton) findViewById(R.id.confirmDialogYesButton);
        noBtn = (FermatButton) findViewById(R.id.confirmDialogNoButton);

        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);

        if (title != null) titleText.setText(title);
        if (message != null) descriptionText.setText(message);
        if (colorStyle != 0) {
            titleText.setBackgroundColor(colorStyle);
            yesBtn.setTextColor(colorStyle);
            noBtn.setTextColor(colorStyle);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.confirm_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirmDialogYesButton) {
            if (btnListener != null) {
                btnListener.onClick();
            }
            dismiss();
        } else if (view.getId() == R.id.confirmDialogNoButton) {
            dismiss();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setColorStyle(int colorStyle) {
        this.colorStyle = colorStyle;
    }

    public void setYesBtnListener(OnClickAcceptListener btnListener) {
        this.btnListener = btnListener;
    }

    public static class Builder {
        private final WeakReference<Activity> activity;
        private final WeakReference<ReferenceAppFermatSession> fermatSession;

        private String title;
        private String message;
        private int colorStyle;
        private OnClickAcceptListener btnListener;

        public Builder(Activity activity, ReferenceAppFermatSession referenceAppFermatSession) {
            this.activity = new WeakReference<>(activity);
            this.fermatSession = new WeakReference<>(referenceAppFermatSession);
        }

        public ConfirmDialog build() {
            ConfirmDialog confirmDialog = new ConfirmDialog(activity.get(), fermatSession.get(), null);
            if (title != null) {
                confirmDialog.setTitle(title);
            }
            if (message != null) {
                confirmDialog.setMessage(message);
            }
            if (colorStyle != 0) {
                confirmDialog.setColorStyle(colorStyle);
            }
            if (btnListener != null) {
                confirmDialog.setYesBtnListener(btnListener);
            }
            return confirmDialog;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setColorStyle(String colorStyle) {
            this.colorStyle = Color.parseColor(colorStyle);
            return this;
        }

        public Builder setColorStyle(int colorStyle) {
            this.colorStyle = colorStyle;
            return this;
        }

        public Builder setYesBtnListener(OnClickAcceptListener btnListener) {
            this.btnListener = btnListener;
            return this;
        }
    }
}
