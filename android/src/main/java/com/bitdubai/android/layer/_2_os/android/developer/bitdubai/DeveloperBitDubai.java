package com.bitdubai.android.layer._2_os.android.developer.bitdubai;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;

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
