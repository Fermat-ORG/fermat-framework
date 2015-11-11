package com.bitdubai.fermat_dap_core.layer.digital_asset_transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.appropriation_stats.AppropriationStatsPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.asset_appropriation.AssetAppropriationPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.asset_distribution.AssetDistributionPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.asset_issuing.AssetIssuingPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.asset_reception.AssetReceptionPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.issuer_redemption.IssuerRedemptionPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.redeem_point_redemption.RedeemPointRedemptionPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.digital_asset_transaction.user_redemption.UserRedemptionPluginSubsystem;

/**
 * Created by lnacosta - (laion.cj91@gmail.com) on 11/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DigitalAssetTransactionLayer extends AbstractLayer {

    public DigitalAssetTransactionLayer() {
        super(Layers.DIGITAL_ASSET_TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new AppropriationStatsPluginSubsystem());
            registerPlugin(new AssetAppropriationPluginSubsystem());
            registerPlugin(new AssetDistributionPluginSubsystem());
            registerPlugin(new AssetIssuingPluginSubsystem());
            registerPlugin(new AssetReceptionPluginSubsystem());
            registerPlugin(new IssuerRedemptionPluginSubsystem());
            registerPlugin(new RedeemPointRedemptionPluginSubsystem());
            registerPlugin(new UserRedemptionPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Wallet Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
