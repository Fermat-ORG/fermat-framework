package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.license.CantStartSubsystemException;

/**
 * Created by franklin on 04/09/15.
 */
public interface AssetIssuerWalletSubsystem {
    void star() throws CantStartSubsystemException;
    Plugin getPlugin();
}
