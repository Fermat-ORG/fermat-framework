package org.fermat.fermat_dap_core.layer.sub_app_module;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.sub_app_module.asset_factory.AssetFactoryPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.asset_issuer_community.AssetIssuerCommunityPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.asset_issuer_identity.AssetIssuerIdentityPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.asset_user_community.AssetUserCommunityPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.asset_user_identity.AssetUserIdentityPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.redeem_point_community.RedeemPointCommunityPluginSubsystem;
import org.fermat.fermat_dap_core.layer.sub_app_module.redeem_point_identity.RedeemPointIdentityPluginSubsystem;

/**
 * Created by lnacosta - (laion.cj91@gmail.com) on 12/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SubAppModuleLayer extends AbstractLayer {

    public SubAppModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new AssetFactoryPluginSubsystem());
            registerPlugin(new AssetIssuerCommunityPluginSubsystem());
            registerPlugin(new AssetIssuerIdentityPluginSubsystem());
            registerPlugin(new AssetUserCommunityPluginSubsystem());
            registerPlugin(new AssetUserIdentityPluginSubsystem());
            registerPlugin(new RedeemPointCommunityPluginSubsystem());
            registerPlugin(new RedeemPointIdentityPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "SubApp Module Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
}
