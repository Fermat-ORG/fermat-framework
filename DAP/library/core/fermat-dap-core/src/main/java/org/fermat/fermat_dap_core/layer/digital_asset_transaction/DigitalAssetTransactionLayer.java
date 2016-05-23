package org.fermat.fermat_dap_core.layer.digital_asset_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_appropriation.AssetAppropriationPluginSubsystem;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_distribution.AssetDistributionPluginSubsystem;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_issuing.AssetIssuingPluginSubsystem;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_transfer.AssetTransferPluginSubsystem;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.issuer_appropriation.IssuerAppropriationPluginSubsystem;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.redeem_point_redemption.RedeemPointRedemptionPluginSubsystem;

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

            registerPlugin(new AssetAppropriationPluginSubsystem());
            registerPlugin(new AssetDistributionPluginSubsystem());
            registerPlugin(new AssetIssuingPluginSubsystem());
            registerPlugin(new org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_reception.AssetReceptionPluginSubsystem());
            registerPlugin(new AssetTransferPluginSubsystem());
            registerPlugin(new IssuerAppropriationPluginSubsystem());
            registerPlugin(new org.fermat.fermat_dap_core.layer.digital_asset_transaction.issuer_redemption.IssuerRedemptionPluginSubsystem());
            registerPlugin(new RedeemPointRedemptionPluginSubsystem());
            registerPlugin(new org.fermat.fermat_dap_core.layer.digital_asset_transaction.user_redemption.UserRedemptionPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Wallet Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
