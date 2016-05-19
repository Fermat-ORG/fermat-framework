package com.bitdubai.fermat_android_api.engine;

import com.bitdubai.fermat_core.FermatSystem;

/**
 * Created by Matias Furszyfer on 2015.12.15..
 */
public interface FermatApplicationSession {

    FermatSystem getFermatSystem();

    ApplicationManager getApplicationManager();

}
