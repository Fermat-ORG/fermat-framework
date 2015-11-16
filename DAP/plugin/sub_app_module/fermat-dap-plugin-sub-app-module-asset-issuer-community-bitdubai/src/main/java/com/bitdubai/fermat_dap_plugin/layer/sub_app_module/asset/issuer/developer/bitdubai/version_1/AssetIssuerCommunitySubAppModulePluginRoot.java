package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantConnectToAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO explain here the main functionality of the plug-in.
 *
 * Created by Nerio on 13/10/15.
 */
public class AssetIssuerCommunitySubAppModulePluginRoot extends AbstractPlugin implements
        AssetIssuerCommunitySubAppModuleManager {


    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    // TODO ADDED ERROR MANAGER REFERENCE, PLEASE MAKE USE OF THE ERROR MANAGER.

    public AssetIssuerCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<ActorAssetIssuer> getAllActorAssetIssuerRegistered() throws CantGetAssetIssuerActorsException {

        try {
            return actorAssetIssuerManager.getAllAssetIssuerActorInTableRegistered();
        } catch (CantGetAssetIssuerActorsException e) {
            e.printStackTrace(); // TODO PLEASE MAKE USE OF THE ERROR MANAGER.
        }
        return new ArrayList<>();
    }

    @Override
    public void connectToActorAssetIssuer(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToAssetIssuerException {

        ActorAssetRedeemPoint actorAssetRedeemPoint;
        //TODO Actor Asset Redeem Point de BD Local
        try {
            actorAssetRedeemPoint = actorAssetRedeemPointManager.getActorAssetRedeemPoint();

            actorAssetIssuerManager.connectToActorAssetIssuer(actorAssetRedeemPoint, actorAssetIssuers);

        } catch (CantGetAssetRedeemPointActorsException e) {
            // TODO PLEASE MAKE USE OF THE ERROR MANAGER.
            throw new CantConnectToAssetIssuerException(CantConnectToAssetIssuerException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        }
    }
}
