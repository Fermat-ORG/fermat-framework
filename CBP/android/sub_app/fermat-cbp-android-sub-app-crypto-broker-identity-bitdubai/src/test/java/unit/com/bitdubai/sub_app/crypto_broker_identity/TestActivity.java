package unit.com.bitdubai.sub_app.crypto_broker_identity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;

/**
 * Created by nelson on 21/09/15.
 */
public class TestActivity extends Activity implements WizardConfiguration, FermatScreenSwapper {
    public static final int LAYOUT_ID = android.R.id.content;
    private Fragment lastFragment = null;

    public void setFragment(Fragment fragment) {
        lastFragment = fragment;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(TestActivity.LAYOUT_ID, lastFragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayout view = new LinearLayout(this);
        view.setId(LAYOUT_ID);
        setContentView(view);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showWizard(WizardTypes key, Object... args) {
        // DO NOTHING...
    }

    @Override
    public void changeScreen(String screen, int idContainer, Object[] objects) {

    }

    @Override
    public void selectWallet(InstalledWallet installedWallet) {

    }

    @Override
    public void changeActivity(String activity, Object... objects) {
        boolean areEquals = Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode().equals(activity);

        if (areEquals) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(lastFragment);
            ft.commit();

            lastFragment = null;
        }
    }

    @Override
    public void selectSubApp(InstalledSubApp installedSubApp) {

    }

    @Override
    public void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType) {

    }

    @Override
    public void onCallbackViewObserver(FermatCallback fermatCallback) {

    }
}
