package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.AddonDeveloper;

/**
 * Created by ciencias on 03.01.15.
 */

/**
 * Each developer might have different versions of their own implementation. This object chooses which version to start.
 */

public class DeveloperBitDubai implements AddonDeveloper {

    Addon addon;

    @Override
    public Addon getAddon() {
        return addon;
    }

    public DeveloperBitDubai(){
       // addon = new AndroidOs();
    }

}
