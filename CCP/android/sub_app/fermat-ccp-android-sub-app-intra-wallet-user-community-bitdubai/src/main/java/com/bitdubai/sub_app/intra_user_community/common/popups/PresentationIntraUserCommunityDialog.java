package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * @author Jose manuel de Sousa
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class PresentationIntraUserCommunityDialog extends FermatDialog<SubAppsSession, SubAppResourcesProviderManager> implements View.OnClickListener {
    public static final int TYPE_PRESENTATION = 1;
    public static final int TYPE_PRESENTATION_WITHOUT_IDENTITIES = 2;
    private final Activity activity;
    private final int type;
    private FermatButton startCommunity;
    private CheckBox dontShowAgainCheckBox;
    private Button btn_left;
    private Button btn_right;
    private ImageView image_view_left;
    private FrameLayout container_john_doe;
    private ImageView image_view_right;
    private FrameLayout container_jane_doe;


    /**
     * Constructor using Session and Resources
     *
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationIntraUserCommunityDialog(Activity activity, SubAppsSession fermatSession, SubAppResourcesProviderManager resources, int type) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (type) {
            case TYPE_PRESENTATION:
                dontShowAgainCheckBox = (CheckBox) findViewById(R.id.checkbox_not_show);
                image_view_left = (ImageView) findViewById(R.id.image_view_left);
                image_view_right = (ImageView) findViewById(R.id.image_view_right);
                container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
                container_jane_doe = (FrameLayout) findViewById(R.id.container_jane_doe);
                btn_left = (Button) findViewById(R.id.btn_left);
                btn_right = (Button) findViewById(R.id.btn_right);
                btn_left.setOnClickListener(this);
                btn_right.setOnClickListener(this);
                break;
            case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                dontShowAgainCheckBox = (CheckBox) findViewById(R.id.checkbox_not_show);
                startCommunity = (FermatButton) findViewById(R.id.start_community);
                startCommunity.setOnClickListener(this);
                break;
        }


    }

    @Override
    protected int setLayoutId() {
        switch (type) {
            case TYPE_PRESENTATION:
                return R.layout.tutorial_intra_user_community;
            case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                return R.layout.tutorial_intra_user_community_whitout_identity;
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
            dismiss();
            Toast.makeText(getActivity(), "Create identity first", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_right) {
            dismiss();
            Toast.makeText(getActivity(), "Create identity first", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.start_community) {
            SharedPreferences pref = getContext().getSharedPreferences("don't show dialog more", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            if (dontShowAgainCheckBox.isChecked()) {
                edit.putBoolean("isChecked", true);
                edit.apply();
                dismiss();
            } else {
                edit.putBoolean("isChecked", false);
                edit.apply();
                dismiss();
            }
        }
    }
}
