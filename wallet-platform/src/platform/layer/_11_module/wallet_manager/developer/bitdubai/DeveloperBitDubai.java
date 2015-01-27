package platform.layer._11_module.wallet_manager.developer.bitdubai;

import platform.layer._11_module.ModuleService;
import platform.layer._11_module.ModuleDeveloper;
import platform.layer._11_module.wallet_manager.developer.bitdubai.version_1.WalletManagerModule;


/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai  implements ModuleDeveloper {

    ModuleService mModule;

    @Override
    public ModuleService getModule() {
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
