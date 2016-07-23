package com.bitdubai.fermat_android_api.ui.Views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bitdubai.android_api.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2015.11.27..
 */
public class PresentationDialog<M extends ModuleManager> extends FermatDialog<ReferenceAppFermatSession<M>, SubAppResourcesProviderManager> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "PresentationDialog";
    private PresentationCallback callback;

    public enum TemplateType {
        TYPE_PRESENTATION, TYPE_PRESENTATION_WITHOUT_IDENTITIES, TYPE_PRESENTATION_WITH_ONE_IDENTITY
    }

    public static final String PRESENTATION_IDENTITY_CREATED = "presentation_identity_created";
    public static final String PRESENTATION_SCREEN_ENABLED = "presentation_screen_enabled";

    private final Activity activity;
    private final TemplateType type;
    private final boolean checkButton;

    /**
     * Fields
     */
    String title;
    int subTitle = -1;
    int body = -1;
    int textCheckboxNotShow = -1;
    int textFooter = -1;
    int textNameLeft = -1;
    int textNameRight = -1;
    private String textColor;
    //    private int titleTextColor = -1;
    private int viewColor = -1;
    private int resBannerImage = -1;
    private int iconRes = -1;
    private int resImageLeft = -1;
    private int resImageRight = -1;
    private int checkButtonAndTextVisible = -1;

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
    private FermatTextView checkbox_not_show_text;
    private ImageView image_view_left;
    private ImageView image_view_right;
    private Button btn_left;
    private Button btn_right;
    private FermatButton btn_dismiss;
    private ImageView img_icon;
    private View view_color;
    private View view_color1;
    private View view_color2;
    private View view_color3;
    private View view_color4;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param referenceAppFermatSession parent class of walletSession and SubAppSession
     * @param resources                 parent class of WalletResources and SubAppResources
     */
    private PresentationDialog(Activity activity, ReferenceAppFermatSession referenceAppFermatSession, SubAppResourcesProviderManager resources, TemplateType type, boolean checkButton) {
        super(activity, referenceAppFermatSession, resources);
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
            checkbox_not_show_text = (FermatTextView) findViewById(R.id.checkbox_not_show_text);
            checkbox_not_show.setChecked(!checkButton);
            img_icon = (ImageView) findViewById(R.id.img_icon);
            view_color = findViewById(R.id.view_color);
            view_color1 = findViewById(R.id.view_color1);
            view_color2 = findViewById(R.id.view_color2);
            view_color3 = findViewById(R.id.view_color3);
            view_color4 = findViewById(R.id.view_color4);
            setUpBasics();
            switch (type) {
                case TYPE_PRESENTATION:
                    image_view_left = (ImageView) findViewById(R.id.image_view_left);
                    image_view_right = (ImageView) findViewById(R.id.image_view_right);
                    container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
                    container_jane_doe = (FrameLayout) findViewById(R.id.container_jane_doe);
                    btn_left = (Button) findViewById(R.id.btn_left);
                    btn_right = (Button) findViewById(R.id.btn_right);
                    setUpBasics();
                    setUpListenersPresentation();
                    break;
                case TYPE_PRESENTATION_WITH_ONE_IDENTITY:
                    image_view_left = (ImageView) findViewById(R.id.image_view_left);
                    container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
                    btn_left = (Button) findViewById(R.id.btn_left);
                    setUpBasics();
                    setUpListenersPresentationOneIdenity();
                    break;
                case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                    btn_dismiss = (FermatButton) findViewById(R.id.btn_dismiss);
                    btn_dismiss.setOnClickListener(this);
                    setUpBasics();
                    checkbox_not_show.setOnCheckedChangeListener(this);
                    break;
            }
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }

    private void setUpBasics() {
        if (iconRes != -1 && img_icon != null) img_icon.setImageResource(iconRes);
        if (resBannerImage != -1 && image_banner != null)
            image_banner.setImageResource(resBannerImage);
        if (resImageLeft != -1 && image_view_left != null)
            image_view_left.setImageResource(resImageLeft);
        if (resImageRight != -1 && image_view_right != null)
            image_view_right.setImageResource(resImageRight);
        if (btn_left != null) {
            btn_left.setText(textNameLeft);
            if (viewColor != -1)
                btn_left.setBackgroundResource(viewColor);
        }
        if (btn_right != null) {
            btn_right.setText(textNameRight);
            if (viewColor != -1)
                btn_right.setBackgroundResource(viewColor);
        }
        if (txt_sub_title != null) txt_sub_title.setText(subTitle);
        if (txt_body != null) txt_body.setText(body);
        if (footer_title != null) footer_title.setText(textFooter);
        if (checkbox_not_show_text != null && textCheckboxNotShow != -1)
            checkbox_not_show_text.setText(textCheckboxNotShow);
        if (viewColor != -1) {
            view_color.setBackgroundResource(viewColor);
            view_color1.setBackgroundResource(viewColor);
            view_color2.setBackgroundResource(viewColor);
            view_color3.setBackgroundResource(viewColor);
            if (view_color4 != null)
                view_color4.setBackgroundResource(viewColor);
            if (btn_dismiss != null)
                btn_dismiss.setBackgroundResource(viewColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txt_title.setTextColor(getContext().getResources().getColorStateList(viewColor, getContext().getTheme()));
            } else {
                txt_title.setTextColor(getContext().getResources().getColorStateList(viewColor));
            }
        }
//        if (titleTextColor != -1) txt_title.setTextColor(titleTextColor);
        if (textColor != null) {
            int color = Color.parseColor(textColor);
            txt_sub_title.setTextColor(color);
            txt_body.setTextColor(color);
            footer_title.setTextColor(color);
        }
        if (checkButtonAndTextVisible == 0 && checkButton == false) {
            checkbox_not_show.setVisibility(View.GONE);
            checkbox_not_show_text.setVisibility(View.GONE);
        } else {
            checkbox_not_show.setVisibility(View.VISIBLE);
            checkbox_not_show_text.setVisibility(View.VISIBLE);
        }
    }

    private void setUpListenersPresentation() {
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        checkbox_not_show.setOnCheckedChangeListener(this);
    }

    private void setUpListenersPresentationOneIdenity() {
        btn_left.setOnClickListener(this);
        checkbox_not_show.setOnCheckedChangeListener(this);
    }

    @Override
    protected int setLayoutId() {
        switch (type) {
            case TYPE_PRESENTATION:
                return R.layout.presentation_dialog;
            case TYPE_PRESENTATION_WITH_ONE_IDENTITY:
                return R.layout.presentation_dialog_with_one_identity;
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
                getSession().getModuleManager().createIdentity(btn_left.getText().toString(), "Available", ImagesUtils.toByteArray(convertImage(resImageLeft)));
                getSession().setData(PRESENTATION_IDENTITY_CREATED, Boolean.TRUE);
            } catch (Exception e) {
                if (callback != null) callback.onError(e);
            }
            saveSettings();
            dismiss();
        } else if (id == R.id.btn_right) {
            try {
                getSession().getModuleManager().createIdentity(btn_right.getText().toString(), "Available", ImagesUtils.toByteArray(convertImage(resImageRight)));
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
        if (type != TemplateType.TYPE_PRESENTATION_WITH_ONE_IDENTITY) {
            try {
                FermatSettings bitcoinWalletSettings = ((ModuleSettingsImpl) getSession().getModuleManager()).loadAndGetSettings(getSession().getAppPublicKey());
                if (bitcoinWalletSettings != null) {
                    bitcoinWalletSettings.setIsPresentationHelpEnabled(!checkbox_not_show.isChecked());
                    ((ModuleSettingsImpl) getSession().getModuleManager()).persistSettings(getSession().getAppPublicKey(), bitcoinWalletSettings);
                } else {
                    Log.e(TAG, "Error: Save Settings null, verify if the module is running");
                }

            } catch (Exception e) {
                if (callback != null) callback.onError(e);
                else e.printStackTrace();
            }
        }
    }

    private Bitmap convertImage(int resImage) {
        return BitmapFactory.decodeResource(activity.getResources(), resImage);
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

    public void setSubTitle(int subTitle) {
        this.subTitle = subTitle;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public void setTextFooter(int textFooter) {
        this.textFooter = textFooter;
    }

    public void setTextCheckboxNoShow(int textCheckbox) {
        this.textCheckboxNotShow = textCheckbox;
    }

    public void setResBannerImage(int resBannerImage) {
        this.resBannerImage = resBannerImage;
    }

    public void setResImageLeft(int resImageLeft) {
        this.resImageLeft = resImageLeft;
    }

    public void setResImageRight(int resImageRight) {
        this.resImageRight = resImageRight;
    }

    public void setTextNameLeft(int textNameLeft) {
        this.textNameLeft = textNameLeft;
    }

    public void setTextNameRight(int textNameRight) {
        this.textNameRight = textNameRight;
    }

//    public void setTitleTextColor(int titleTextColor) {
//        this.titleTextColor = titleTextColor;
//    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setVIewColor(int viewColor) {
        this.viewColor = viewColor;
    }

    public void setCallback(PresentationCallback callback) {
        this.callback = callback;
    }

    public void setCheckButtonAndTextVisible(int checkButtonAndTextVisible) {
        this.checkButtonAndTextVisible = checkButtonAndTextVisible;
    }

    public static class Builder {

        /**
         * Members
         */
        private final WeakReference<Activity> activity;
        private final WeakReference<ReferenceAppFermatSession> fermatSession;
        private TemplateType templateType = TemplateType.TYPE_PRESENTATION;
        private boolean isCheckEnabled;
        private int checkButtonAndTextVisible = -1;
        private PresentationCallback callback;
        private String title;
        private int subTitle = -1;
        private int body = -1;
        private int textFooter = -1;
        private int textCheckbox = -1;
        private String textColor;
        private int textNameLeft = -1;
        private int textNameRight = -1;
        private int imageLeft = -1;
        private int imageRight = -1;
        private int bannerRes = -1;
        private int iconRes = -1;
        //        private int titleTextColor = -1;
        private int viewColor = -1;

        public PresentationDialog build() {
            PresentationDialog presentationDialog = new PresentationDialog(activity.get(), fermatSession.get(), null, templateType, isCheckEnabled);
            if (body != -1) {
                presentationDialog.setBody(body);
            }
            if (title != null) {
                presentationDialog.setTitle(title);
            }
            if (subTitle != -1) {
                presentationDialog.setSubTitle(subTitle);
            }
            if (textFooter != -1) {
                presentationDialog.setTextFooter(textFooter);
            }
            if (textCheckbox != -1) {
                presentationDialog.setTextCheckboxNoShow(textCheckbox);
            }
            if (bannerRes != -1) {
                presentationDialog.setResBannerImage(bannerRes);
            }
            if (imageLeft != -1) {
                presentationDialog.setResImageLeft(imageLeft);
            } else {
                presentationDialog.setResImageLeft(R.drawable.ic_profile_male);
            }
            if (imageRight != -1) {
                presentationDialog.setResImageRight(imageRight);
            } else {
                presentationDialog.setResImageRight(R.drawable.img_profile_female);
            }
            if (textNameLeft != -1) {
                presentationDialog.setTextNameLeft(textNameLeft);
            } else {
                presentationDialog.setTextNameLeft(R.string.name_left);
            }
            if (textNameRight != -1) {
                presentationDialog.setTextNameRight(textNameRight);
            } else {
                presentationDialog.setTextNameRight(R.string.name_right);
            }
            if (title != null) {
                presentationDialog.setTitle(title);
            }
            if (iconRes != -1) {
                presentationDialog.setIconRes(iconRes);
            }
//            if (titleTextColor != -1) {
//                presentationDialog.setTitleTextColor(titleTextColor);
//            }
            if (textColor != null) {
                presentationDialog.setTextColor(textColor);
            }
            if (viewColor != -1) {
                presentationDialog.setVIewColor(viewColor);
            }
            if (callback != null) {
                presentationDialog.setCallback(callback);
            }
            presentationDialog.setCheckButtonAndTextVisible(checkButtonAndTextVisible);

            return presentationDialog;
        }

        public Builder(Activity activity, ReferenceAppFermatSession referenceAppFermatSession) {
            this.activity = new WeakReference<Activity>(activity);
            this.fermatSession = new WeakReference<ReferenceAppFermatSession>(referenceAppFermatSession);
        }

        public Builder setBannerRes(int bannerRes) {
            this.bannerRes = bannerRes;
            return this;
        }

        public Builder setImageLeft(int imageLeft) {
            this.imageLeft = imageLeft;
            return this;
        }

        public Builder setImageRight(int imageRight) {
            this.imageRight = imageRight;
            return this;
        }

        public Builder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public Builder setTextNameLeft(int textNameLeft) {
            this.textNameLeft = textNameLeft;
            return this;
        }

        public Builder setTextNameRight(int textNameRight) {
            this.textNameRight = textNameRight;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitle(int subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setBody(int body) {
            this.body = body;
            return this;
        }

        public Builder setTextFooter(int textFooter) {
            this.textFooter = textFooter;
            return this;
        }

        public Builder setCheckboxText(int textCheckbox) {
            this.textCheckbox = textCheckbox;
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

//        public Builder setTitleTextColor(int TitleTextColorInHexa) {
//            this.titleTextColor = TitleTextColorInHexa;
//            return this;
//        }

        public Builder setTextColor(String textColorInHexa) {
            this.textColor = textColorInHexa;
            return this;
        }

        public Builder setVIewColor(int viewColorInHexa) {
            this.viewColor = viewColorInHexa;
            return this;
        }

        /*
        * setCheckButtonAndTextVisible
        * set checkButtonAndTextVisible = 0 if you do not want checkbox and text appear
        */

        public Builder setCheckButtonAndTextVisible(int checkButtonAndTextVisible) {
            this.checkButtonAndTextVisible = checkButtonAndTextVisible;
            return this;
        }
    }
}
