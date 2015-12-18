package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by mati on 2015.11.27..
 */
public class PresentationIntraUserCommunityDialog extends FermatDialog<SubAppsSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    private final Activity activity;
    private FermatButton startCommunity;
    private CheckBox dontShowAgainCheckBox;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationIntraUserCommunityDialog(Activity activity, SubAppsSession fermatSession, SubAppResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startCommunity = (FermatButton) findViewById(R.id.start_community);
        dontShowAgainCheckBox = (CheckBox) findViewById(R.id.checkbox_not_show);
        startCommunity.setOnClickListener(this);


    }

    @Override
    protected int setLayoutId() {
        return R.layout.tutorial_intra_user_community;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.start_community) {
            SharedPreferences pref = getContext().getSharedPreferences("dont show dialog more", Context.MODE_PRIVATE);
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
