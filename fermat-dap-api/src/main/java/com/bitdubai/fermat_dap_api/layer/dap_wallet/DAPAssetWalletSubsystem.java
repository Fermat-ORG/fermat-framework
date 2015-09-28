package com.bitdubai.fermat_dap_api.layer.dap_wallet;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by franklin on 04/09/15.
 */
public interface DAPAssetWalletSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
