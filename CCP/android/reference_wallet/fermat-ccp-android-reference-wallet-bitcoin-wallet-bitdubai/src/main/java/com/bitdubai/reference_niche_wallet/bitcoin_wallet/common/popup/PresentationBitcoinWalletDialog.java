package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by mati on 2015.11.27..
 */
public class PresentationBitcoinWalletDialog extends FermatDialog<ReferenceWalletSession,SubAppResourcesProviderManager> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final Activity activity;

    /**
     * Members
     */
    String title;
    String subTitle;
    String body;
    String textFooter;

    int resBannerimage;

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

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationBitcoinWalletDialog(Activity activity, ReferenceWalletSession fermatSession, SubAppResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txt_title = (FermatTextView) findViewById(R.id.txt_title);
        image_banner = (ImageView) findViewById(R.id.image_banner);
        txt_sub_title = (FermatTextView) findViewById(R.id.txt_sub_title);
        txt_body = (FermatTextView) findViewById(R.id.txt_body);
        footer_title = (FermatTextView) findViewById(R.id.footer_title);
        checkbox_not_show = (CheckBox) findViewById(R.id.checkbox_not_show);
        image_view_left = (ImageView) findViewById(R.id.image_view_left);
        image_view_right = (ImageView) findViewById(R.id.image_view_right);
        container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
        container_jane_doe = (FrameLayout) findViewById(R.id.container_jane_doe);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
        setUpListeners();
    }

    private void setUpListeners(){
//        container_john_doe.setOnClickListener(this);
//        container_jane_doe.setOnClickListener(this);
//        btn_left.setOnClickListener(this);
//        btn_right.setOnClickListener(this);
//        checkbox_not_show.setOnCheckedChangeListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.presentation_wallet;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.add_fermat_user){
            try {
                Object[] object = new Object[2];
                changeApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY, getSession().getCommunityConnection(), object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(id == R.id.add_extra_user){
            dismiss();
        }
        else if(id == R.id.container_john_doe){
            container_john_doe.setBackgroundColor(Color.parseColor("#7dff7d"));
            container_jane_doe.setBackgroundColor(Color.TRANSPARENT);
        }
        else if(id == R.id.container_jane_doe){
            container_jane_doe.setBackgroundColor(Color.parseColor("#7dff7d"));
            container_john_doe.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(activity,"Checked",Toast.LENGTH_SHORT).show();
    }


}
