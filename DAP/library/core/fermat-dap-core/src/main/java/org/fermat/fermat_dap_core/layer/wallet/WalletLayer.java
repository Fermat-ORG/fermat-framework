package org.fermat.fermat_dap_core.layer.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.wallet.asset_issuer.AssetIssuerPluginSubsystem;
import org.fermat.fermat_dap_core.layer.wallet.asset_user.AssetUserPluginSubsystem;
import org.fermat.fermat_dap_core.layer.wallet.redeem_point.RedeemPointPluginSubsystem;

/**
 * Created by lnacosta - (laion.cj91@gmail.com) on 11/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletLayer extends AbstractLayer {

    public WalletLayer() {
        super(Layers.WALLET);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new AssetIssuerPluginSubsystem());
            registerPlugin(new AssetUserPluginSubsystem());
            registerPlugin(new RedeemPointPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Wallet Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
