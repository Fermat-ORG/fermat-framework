package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SpacesItemDecoration;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import java.util.List;

/**
 * Created by mati on 2015.11.27..
 */
public class ContactsTutorialPart1V2 extends FermatDialog<ReferenceWalletSession,SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    private FermatButton search_contact_btn;
    private FermatButton cancel_btn;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public ContactsTutorialPart1V2(Activity activity, ReferenceWalletSession fermatSession, SubAppResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        search_contact_btn =(FermatButton) findViewById(R.id.search_contact_btn);
        cancel_btn = (FermatButton) findViewById(R.id.cancel_btn);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.contacts_tutorial_part1_v2;
    }

    @Override
    protected int setWindowFeacture() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.search_contact_btn){
            try {
                Object[] object = new Object[2];
                changeApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY, getSession().getCommunityConnection(), object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(id == R.id.cancel_btn){
            dismiss();
        }
    }
}
