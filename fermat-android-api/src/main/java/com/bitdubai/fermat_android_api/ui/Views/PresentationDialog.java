package com.bitdubai.fermat_android_api.ui.Views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.11.27..
 */
public class PresentationDialog extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private PresentationCallback callback;

    public enum TemplateType {
        TYPE_PRESENTATION, TYPE_PRESENTATION_WITHOUT_IDENTITIES
    }

    public static final String PRESENTATION_IDENTITY_CREATED = "presentation_identity_created";
    public static final String PRESENTATION_SCREEN_ENABLED = "presentation_screen_enabled";

    private final Activity activity;
    private final TemplateType type;
    private final boolean checkButton;

    /**
     * Members
     */
    String title;
    String subTitle;
    String body;
    String textFooter;
    private String textColor;
    private int titleTextColor;
    private int viewColor = -1;
    int resBannerimage;
    private int iconRes = -1;

    /**
     * UI
     */
    private FrameLayout container_john_doe;
    private FrameLayout container_jane_doe;
    private FermatTextView txt_title;
    private ImageView image_banner;
    private FermatTextView txt_sub_title;
    private FermatTextView txt_body;
    private FermatTextView footer_title;
    private CheckBox checkbox_not_show;
    private ImageView image_view_left;
    private ImageView image_view_right;
    private Button btn_left;
    private Button btn_right;
    private FermatButton btn_dismiss;
    private ImageView img_icon;
    private View view_color;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    private PresentationDialog(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources, TemplateType type, boolean checkButton) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.type = type;
        this.checkButton = checkButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            txt_title = (FermatTextView) findViewById(R.id.txt_title);
            image_banner = (ImageView) findViewById(R.id.image_banner);
            txt_sub_title = (FermatTextView) findViewById(R.id.txt_sub_title);
            txt_body = (FermatTextView) findViewById(R.id.txt_body);
            footer_title = (FermatTextView) findViewById(R.id.footer_title);
            checkbox_not_show = (CheckBox) findViewById(R.id.checkbox_not_show);
            checkbox_not_show.setChecked(!checkButton);
            img_icon = (ImageView) findViewById(R.id.img_icon);
            view_color = findViewById(R.id.view_color);
            setUpBasics();
            switch (type) {
                case TYPE_PRESENTATION:
                    image_view_left = (ImageView) findViewById(R.id.image_view_left);
                    image_view_right = (ImageView) findViewById(R.id.image_view_right);
                    container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
                    container_jane_doe = (FrameLayout) findViewById(R.id.container_jane_doe);
                    btn_left = (Button) findViewById(R.id.btn_left);
                    btn_right = (Button) findViewById(R.id.btn_right);
                    setUpListenersPresentation();
                    break;
                case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                    btn_dismiss = (FermatButton) findViewById(R.id.btn_dismiss);
                    btn_dismiss.setOnClickListener(this);
                    break;
            }
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }

    private void setUpBasics() {
        if (iconRes != -1 && img_icon != null) img_icon.setImageResource(iconRes);
        if (resBannerimage != -1 && image_banner != null)
            image_banner.setImageResource(resBannerimage);
        if (txt_sub_title != null) txt_sub_title.setText(subTitle);
        if (txt_body != null) txt_body.setText(body);
        if (footer_title != null) footer_title.setText(textFooter);
        if (view_color != null) view_color.setBackgroundColor(viewColor);
        if (titleTextColor != -1) txt_title.setTextColor(titleTextColor);
        if (textColor != null) {
            int color = Color.parseColor(textColor);
            txt_sub_title.setTextColor(color);
            txt_body.setTextColor(color);
            footer_title.setTextColor(color);
        }
    }

    private void setUpListenersPresentation() {
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        checkbox_not_show.setOnCheckedChangeListener(this);
    }

    @Override
    protected int setLayoutId() {
        switch (type) {
            case TYPE_PRESENTATION:
                return R.layout.presentation_dialog;
            case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                return R.layout.presentation_dialog_without_identities;
        }
        return 0;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_left) {
            try {
                getSession().getModuleManager().createIdentity("John Doe", "Available", convertImage(R.drawable.ic_profile_male));
                getSession().setData(PRESENTATION_IDENTITY_CREATED, Boolean.TRUE);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
            saveSettings();
            dismiss();
        } else if (id == R.id.btn_right) {
            try {
                getSession().getModuleManager().createIdentity("Jane Doe", "Available", convertImage(R.drawable.img_profile_female));
                getSession().setData(PRESENTATION_IDENTITY_CREATED, Boolean.TRUE);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
            saveSettings();
            dismiss();
        } else if (id == R.id.btn_dismiss) {
            saveSettings();
            dismiss();
        }
    }

    private void saveSettings() {
        if (type != TemplateType.TYPE_PRESENTATION) {
            if (checkButton == checkbox_not_show.isChecked() || checkButton == !checkbox_not_show.isChecked())
                if (checkbox_not_show.isChecked()) {
                    SettingsManager settingsManager = getSession().getModuleManager().getSettingsManager();
                    try {
                        FermatSettings bitcoinWalletSettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
                        bitcoinWalletSettings.setIsPresentationHelpEnabled(false);
                        settingsManager.persistSettings(getSession().getAppPublicKey(), bitcoinWalletSettings);
                    } catch (CantGetSettingsException | SettingsNotFoundException | CantPersistSettingsException e) {
                        if (callback != null) callback.onError(e);
                    }
                }
        }
    }

    private byte[] convertImage(int resImage) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            getSession().setData(PRESENTATION_SCREEN_ENABLED, Boolean.TRUE);
        } else {
            getSession().setData(PRESENTATION_SCREEN_ENABLED, Boolean.FALSE);
        }

    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTextFooter(String textFooter) {
        this.textFooter = textFooter;
    }

    public void setResBannerimage(int resBannerimage) {
        this.resBannerimage = resBannerimage;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setVIewColor(int viewColor) {
        this.viewColor = viewColor;
    }

    public void setCallback(PresentationCallback callback) {
        this.callback = callback;
    }

    public static class Builder {

        /**
         * Members
         */
        private final WeakReference<Activity> activity;
        private final WeakReference<FermatSession> fermatSession;
        private TemplateType templateType = TemplateType.TYPE_PRESENTATION;
        private boolean isCheckEnabled = true;
        private PresentationCallback callback;
        private String title;
        private String subTitle;
        private String body;
        private String textFooter;
        private int bannerRes = -1;
        private int iconRes = -1;
        private int titleTextColor;
        private String textColor;
        private int viewColor;

        public PresentationDialog build() {
            PresentationDialog presentationDialog = new PresentationDialog(activity.get(), fermatSession.get(), null, templateType, isCheckEnabled);
            if (body != null) {
                presentationDialog.setBody(body);
            }
            if (title != null) {
                presentationDialog.setTitle(title);
            }
            if (subTitle != null) {
                presentationDialog.setSubTitle(subTitle);
            }
            if (textFooter != null) {
                presentationDialog.setTextFooter(textFooter);
            }
            if (bannerRes != -1) {
                presentationDialog.setResBannerimage(bannerRes);
            }
            if (iconRes != -1) {
                presentationDialog.setIconRes(iconRes);
            }
            if (titleTextColor != -1) {
                presentationDialog.setTitleTextColor(titleTextColor);
            }
            if (textColor != null) {
                presentationDialog.setTextColor(textColor);
            }
            if (viewColor != -1) {
                presentationDialog.setVIewColor(viewColor);
            }
            if (callback != null) {
                presentationDialog.setCallback(callback);
            }
            return presentationDialog;
        }

        public Builder(Activity activity, FermatSession fermatSession) {
            this.activity = new WeakReference<Activity>(activity);
            this.fermatSession = new WeakReference<FermatSession>(fermatSession);
        }

        public Builder setBannerRes(int bannerRes) {
            this.bannerRes = bannerRes;
            return this;
        }

        public Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setTextFooter(String textFooter) {
            this.textFooter = textFooter;
            return this;
        }

        public Builder setIsCheckEnabled(boolean isCheckEnabled) {
            this.isCheckEnabled = isCheckEnabled;
            return this;
        }

        public Builder setCallback(PresentationCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setTemplateType(TemplateType templateType) {
            this.templateType = templateType;
            return this;
        }

        public Builder setTitleTextColor(int TitletextColorInHexa) {
            this.titleTextColor = TitletextColorInHexa;
            return this;
        }

        public Builder setTextColor(String textColorInHexa) {
            this.textColor = textColorInHexa;
            return this;
        }

        public Builder setVIewColor(int viewColorInHexa) {
            this.viewColor = viewColorInHexa;
            return this;
        }
    }

}
