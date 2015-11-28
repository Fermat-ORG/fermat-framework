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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO explain here the main functionality of the plug-in.
 *
 * Created by Nerio on 13/10/15.
 * Modified by Luis Campo 27/11/2015
 */
public class AssetUserCommunitySubAppModulePluginRoot extends AbstractPlugin implements
        AssetUserCommunitySubAppModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        return assetUserActorRecords;
    }

    @Override
    public void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetUserException {

        ActorAssetIssuer actorAssetIssuer;
        //TODO Actor Asset Issuer de BD Local
        try {
            actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();

            actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers);

        } catch (CantGetAssetIssuerActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConnectToActorAssetUserException(CantConnectToActorAssetUserException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        }
    }

    @Override
    public void createGroup(ActorAssetUserGroup assetUserGroup) throws CantCreateAssetUserGroupException {
        try {
            actorAssetUserManager.createAssetUserGroup(assetUserGroup);
        } catch (CantCreateAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void renameGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException {
        try {
            actorAssetUserManager.updateAssetUserGroup(assetUserGroup);
        } catch (CantUpdateAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void deleteGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException {
        try {
            actorAssetUserManager.deleteAssetUserGroup(assetUserGroupId);
        } catch (CantDeleteAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void addActorAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException {
        try {
            actorAssetUserManager.addAssetUserToGroup(actorAssetUserGroupMember);
        } catch (CantCreateAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void removeActorAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantCreateAssetUserGroupException {
        try {
            actorAssetUserManager.removeAssetUserFromGroup(assetUserGroupMember);
        } catch (CantCreateAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<ActorAssetUserGroup> getGroups() throws CantGetAssetUserGroupExcepcion {
        List<ActorAssetUserGroup> groupList = new ArrayList<ActorAssetUserGroup>();
        try {
            groupList = actorAssetUserManager.getAssetUserGroupsList();
        } catch (CantGetAssetUserGroupExcepcion e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } finally {
            return groupList;
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUserActorsException {
        List<ActorAssetUser> assetUserList = new ArrayList<ActorAssetUser>();
        try {
            assetUserList = actorAssetUserManager.getListActorAssetUserByGroups(groupName);
        } catch (CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } finally {
            return assetUserList;
        }
    }

    @Override
    public List<ActorAssetUserGroup> getListGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupExcepcion {
        List<ActorAssetUserGroup> actorAssetUserGroups = new ArrayList<ActorAssetUserGroup>();
        try {
            actorAssetUserGroups = actorAssetUserManager.getListAssetUserGroupsByActorAssetUser(actorAssetUserPublicKey);
        } catch (CantGetAssetUserGroupExcepcion e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } finally {
            return actorAssetUserGroups;
        }
    }

    @Override
    public ActorAssetUserGroup getGroup(String groupId) throws CantGetAssetUserGroupExcepcion {
        ActorAssetUserGroup actorAssetUserGroup = null;
        try {
            actorAssetUserGroup = actorAssetUserManager.getAssetUserGroup(groupId);
        } catch (CantGetAssetUserGroupExcepcion e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } finally {
            return actorAssetUserGroup;
        }
    }
}
