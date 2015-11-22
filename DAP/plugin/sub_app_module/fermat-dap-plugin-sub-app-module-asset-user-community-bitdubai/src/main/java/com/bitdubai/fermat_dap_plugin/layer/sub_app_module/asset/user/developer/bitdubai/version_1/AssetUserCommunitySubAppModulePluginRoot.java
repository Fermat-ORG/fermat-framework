package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO explain here the main functionality of the plug-in.
 *
 * Created by Nerio on 13/10/15.
 */
public class AssetUserCommunitySubAppModulePluginRoot extends AbstractPlugin implements
        AssetUserCommunitySubAppModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    // TODO ADDED ERROR MANAGER REFERENCE, PLEASE MAKE USE OF THE ERROR MANAGER.

    public AssetUserCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException {

        List<AssetUserActorRecord> assetUserActorRecords = new ArrayList<>();

        try {
            for (ActorAssetUser actorAssetUser : actorAssetUserManager.getAllAssetUserActorInTableRegistered()) {
                AssetUserActorRecord assetUserActorRecord = (AssetUserActorRecord) actorAssetUser;
                assetUserActorRecords.add(assetUserActorRecord);
            }

        } catch (CantGetAssetUserActorsException e) {
            e.printStackTrace(); // TODO MAKE USER OF ERROR MANAGER
        }
        return assetUserActorRecords;
    }

    @Override
    public void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToAssetUserException {

        ActorAssetIssuer actorAssetIssuer;
        //TODO Actor Asset Issuer de BD Local
        try {
            actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();

            actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers);

        } catch (CantGetAssetIssuerActorsException e) {
            // TODO MAKE USER OF ERROR MANAGER
            throw new CantConnectToAssetUserException(CantConnectToAssetUserException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        }
    }
}
