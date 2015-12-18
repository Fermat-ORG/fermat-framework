package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by mati on 2015.11.27..
 */
public class PresentationBitcoinWalletDialog extends FermatDialog<ReferenceWalletSession,SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    private FermatButton add_fermat_user;
    private FermatButton add_extra_user;
    private FrameLayout container_john_doe;
    private FrameLayout container_jane_doe;

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

        add_fermat_user =(FermatButton) findViewById(R.id.add_fermat_user);
        add_extra_user = (FermatButton) findViewById(R.id.add_extra_user);

        container_john_doe = (FrameLayout) findViewById(R.id.container_john_doe);
        container_jane_doe = (FrameLayout) findViewById(R.id.container_jane_doe);
        container_john_doe.setOnClickListener(this);
        container_jane_doe.setOnClickListener(this);

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
}
