package org.fermat.fermat_dap_core.layer.user_level_business_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_break.AssetBreakPluginSubsystem;
import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_distribution.AssetDistributionPluginSubsystem;
import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_fixed_bitcoin_direct_private_sell.AssetFixedBitcoinDirectPrivateSellPluginSubsystem;
import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_issuing.AssetIssuingPluginSubsystem;
import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_redemption.AssetRedemptionPluginSubsystem;
import org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_transfer.AssetTransferPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class UserLevelBusinessTransactionLayer extends AbstractLayer {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public UserLevelBusinessTransactionLayer() {
        super(Layers.USER_LEVEL_BUSINESS_TRANSACTION);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new AssetBreakPluginSubsystem());
            registerPlugin(new AssetDistributionPluginSubsystem());
            registerPlugin(new AssetFixedBitcoinDirectPrivateSellPluginSubsystem());
            registerPlugin(new AssetIssuingPluginSubsystem());
            registerPlugin(new AssetRedemptionPluginSubsystem());
            registerPlugin(new AssetTransferPluginSubsystem());

        } catch (Exception e) {

            throw new CantStartLayerException(
                    e,
                    "User Level Business Transaction Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
