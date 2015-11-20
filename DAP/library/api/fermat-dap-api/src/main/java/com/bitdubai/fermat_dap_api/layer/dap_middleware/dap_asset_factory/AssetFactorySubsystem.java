package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.CantStartSubsystemException;

/**
 * Created by franklin on 10/09/15.
 */
public interface AssetFactorySubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
