package com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.SessionConstant;



/**
 * Created by natalia on 02/05/16
 */

public class BlockchainDownloadInfoDialog extends FermatDialog<ReferenceAppFermatSession<FermatWallet>,SubAppResourcesProviderManager>

        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int TYPE_PRESENTATION =1;
    public static final int TYPE_PRESENTATION_WITHOUT_IDENTITIES =2;
    private static final String TAG = "BlockchainDownload";

    private final Activity activity;
    private final boolean checkButton;
    private final int type;

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
    private CheckBox checkbox_not_show;
    private FermatButton btn_dismiss;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */

    public BlockchainDownloadInfoDialog(Activity activity, ReferenceAppFermatSession<FermatWallet> fermatSession, SubAppResourcesProviderManager resources,int type,boolean checkButton) {

        super(activity, fermatSession, resources);
        this.activity = activity;
        this.checkButton = checkButton;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkbox_not_show = (CheckBox) findViewById(R.id.checkbox_not_show);
        checkbox_not_show.setChecked(!checkButton);
        btn_dismiss = (FermatButton) findViewById(R.id.btn_dismiss);
        btn_dismiss.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fermat_wallet_dialog_blockchain_download;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        saveSettings();
        dismiss();
    }

    private void saveSettings(){
        if(type!=TYPE_PRESENTATION)
            if(checkButton == checkbox_not_show.isChecked()  || checkButton == !checkbox_not_show.isChecked())
                if(checkbox_not_show.isChecked()){
                    try {
                        FermatWalletSettings bitcoinWalletSettings = getSession().getModuleManager().loadAndGetSettings(getSession().getAppPublicKey());
                        bitcoinWalletSettings.setIsBlockchainDownloadEnabled(false);
                        getSession().getModuleManager().persistSettings(getSession().getAppPublicKey(), bitcoinWalletSettings);
                    } catch (CantGetSettingsException e) {
                        e.printStackTrace();
                    } catch (SettingsNotFoundException e) {
                        e.printStackTrace();
                    } catch (CantPersistSettingsException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.TRUE);
        else
            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.FALSE);
    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }
}
