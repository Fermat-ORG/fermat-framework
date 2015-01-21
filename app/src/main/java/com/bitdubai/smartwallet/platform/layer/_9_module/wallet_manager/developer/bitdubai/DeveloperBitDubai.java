package com.bitdubai.smartwallet.platform.layer._9_module.wallet_manager.developer.bitdubai;

import com.bitdubai.smartwallet.platform.layer._9_module.Module;
import com.bitdubai.smartwallet.platform.layer._9_module.ModuleDeveloper;
import com.bitdubai.smartwallet.platform.layer._9_module.wallet_manager.developer.bitdubai.version_1.WalletManagerModule;


/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai  implements ModuleDeveloper {

    Module mModule;

    @Override
    public Module getModule() {
        return mModule;
    }

    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mModule = new WalletManagerModule();

    }
}
