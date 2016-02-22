package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantGetAssetUserIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Nerio on 13/10/15.
 * Modified by Luis Campo 27/11/2015
 */
@NeededIndirectPluginReferences(indirectReferences = {
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_EXTRA_USER),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_INTRA_USER)
//        @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.MIDDLEWARE, plugin = Plugins.CRYPTO_ADDRESSES),
})
public class AssetUserCommunitySubAppModulePluginRoot extends AbstractPlugin implements
        AssetUserCommunitySubAppModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.ASSET_ISSUER)
    AssetIssuerWalletSupAppModuleManager assetIssuerWalletSupAppModuleManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.ASSET_USER)
    AssetUserWalletSubAppModuleManager assetUserWalletSubAppModuleManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_USER)
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_USER)
    IdentityAssetUserManager identityAssetUserManager;

    private SettingsManager<AssetUserSettings> settingsManager;

    BlockchainNetworkType blockchainNetworkType;

    private String appPublicKey;

    public AssetUserCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException {
        List<ActorAssetUser> list = null;
        List<AssetUserActorRecord> assetUserActorRecords = null;

        try {
            list = assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
            actorAssetUserManager.createActorAssetUserRegisterInNetworkService(list);
        } catch (CantRequestListActorAssetUserRegisteredException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateAssetUserActorException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        if (list != null) {
            assetUserActorRecords = new ArrayList<>();

            try {
                BlockchainNetworkType blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();
                for (ActorAssetUser actorAssetUser : actorAssetUserManager.getAllAssetUserActorInTableRegistered(blockchainNetworkType)) {
                    if(Objects.equals(actorAssetUser.getType().getCode(), Actors.DAP_ASSET_USER.getCode())) {
                        AssetUserActorRecord assetUserActorRecord = (AssetUserActorRecord) actorAssetUser;
                        assetUserActorRecords.add(assetUserActorRecord);
                    }
                }

            } catch (CantGetAssetUserActorsException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }
        return assetUserActorRecords;
    }

    //@Override
    public ActorAssetUser getActorUser(String actorAssetUserPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        try {
            return actorAssetUserManager.getActorByPublicKey(actorAssetUserPublicKey, assetIssuerWalletSupAppModuleManager.getSelectedNetwork());
        } catch (CantAssetUserActorNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<ActorAssetUser> getAllActorAssetUserConnected() throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getAllAssetUserActorConnected(assetIssuerWalletSupAppModuleManager.getSelectedNetwork());
        } catch (CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<AssetUserActorRecord> getAllActorAssetUserRegisteredWithCryptoAddressNotIntheGroup(String groupId) throws CantGetAssetUserActorsException {
        List<AssetUserActorRecord> allUserRegistered = this.getAllActorAssetUserRegistered();
        List<ActorAssetUser> allUserRegisteredInGroup = this.getListActorAssetUserByGroups(groupId);
        List<AssetUserActorRecord> allUserRegisteredFiltered = new ArrayList<>();
        for (AssetUserActorRecord record : allUserRegistered)
        {
            // Obtain all user connected and not in the current group
            if (record.getCryptoAddress() != null && (!userInGroup(record.getActorPublicKey(), allUserRegisteredInGroup)))
            {
                allUserRegisteredFiltered.add(record);
            }
        }

        return allUserRegisteredFiltered;
    }

    private boolean userInGroup(String actorPublicKey, List<ActorAssetUser> usersInGroup) {
        for (ActorAssetUser record : usersInGroup) {
            if (record.getActorPublicKey().equals(actorPublicKey))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetUserException {
        try {
            ActorAssetIssuer actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
            if (actorAssetIssuer != null) {
                blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();

                actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers, blockchainNetworkType);
            } else {
                ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
                if (actorAssetUser != null) {
                    blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();

                    actorAssetUserManager.connectToActorAssetUser(actorAssetUser, actorAssetUsers, blockchainNetworkType);
                } else {
                    throw new CantConnectToActorAssetUserException(CantConnectToActorAssetUserException.DEFAULT_MESSAGE, null, "There was an error connecting to users.", null);
                }
            }
        } catch (CantGetAssetIssuerActorsException | CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConnectToActorAssetUserException(CantConnectToActorAssetUserException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        }
    }

    @Override
    public void disconnectToActorAssetUser(ActorAssetUser user) throws CantDisconnectAssetUserActorException {
        try {
            actorAssetUserManager.disconnectToActorAssetUser(user,assetIssuerWalletSupAppModuleManager.getSelectedNetwork());
        } catch (CantDisconnectAssetUserActorException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (CantDeleteRecordException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException {
        try {
            return actorAssetUserManager.createAssetUserGroup(groupName);
        } catch (CantCreateAssetUserGroupException | ActorAssetUserGroupAlreadyExistException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void renameGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException {
        try {
            actorAssetUserManager.updateAssetUserGroup(assetUserGroup);
        } catch (CantUpdateAssetUserGroupException | RecordsNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void deleteGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            actorAssetUserManager.deleteAssetUserGroup(assetUserGroupId);
        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
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
    public void removeActorAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            actorAssetUserManager.removeAssetUserFromGroup(assetUserGroupMember);
        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<ActorAssetUserGroup> getGroups() throws CantGetAssetUserGroupException {
        try {
            return actorAssetUserManager.getAssetUserGroupsList();
        } catch (CantGetAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupId) throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getListActorAssetUserByGroups(groupId, assetIssuerWalletSupAppModuleManager.getSelectedNetwork());
        } catch (CantGetAssetUserActorsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<ActorAssetUserGroup> getListGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException {
        try {
            return actorAssetUserManager.getListAssetUserGroupsByActorAssetUser(actorAssetUserPublicKey);
        } catch (CantGetAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public ActorAssetUserGroup getGroup(String groupId) throws CantGetAssetUserGroupException {
        try {
            return actorAssetUserManager.getAssetUserGroup(groupId);
        } catch (CantGetAssetUserGroupException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    public IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException {
        try {
            return identityAssetUserManager.getIdentityAssetUser();
        } catch (CantGetAssetUserIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityAssetUserException(e);
        }
    }

    @Override
    public void askActorAssetUserForConnection(List<ActorAssetUser> actorAssetUsers) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {
        try {
        ActorAssetIssuer actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
        if (actorAssetIssuer != null) {
            blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();

            /**
             *Call Actor Intra User to add request connection
             */
            for (ActorAssetUser actorAssetUser : actorAssetUsers) {
                this.actorAssetUserManager.askActorAssetUserForConnection(
                        actorAssetIssuer.getActorPublicKey(),
                        actorAssetUser.getName(),
                        actorAssetUser.getActorPublicKey(),
                        actorAssetUser.getProfileImage(),
                        blockchainNetworkType);

                /**
                 *Call Network Service Intra User to add request connection
                 */
                if (this.actorAssetUserManager.getActorAssetUserRegisteredDAPConnectionState(actorAssetUser.getActorPublicKey()) != DAPConnectionState.REGISTERED_REMOTELY) {
                    System.out.println("The User you are trying to connect with is not connected" +
                            "so we send the message to the assetUserActorNetworkService");
                    this.assetUserActorNetworkServiceManager.askConnectionActorAsset(
                            actorAssetIssuer.getActorPublicKey(),
                            actorAssetIssuer.getName(),
                            Actors.DAP_ASSET_ISSUER,
                            actorAssetUser.getActorPublicKey(),
                            actorAssetUser.getName(),
                            Actors.DAP_ASSET_USER,
                            actorAssetIssuer.getProfileImage(),
                            blockchainNetworkType);
                } else {
                    this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetIssuer.getActorPublicKey(), actorAssetUser.getActorPublicKey());
                    System.out.println("The actor asset user is connected");
                }
            }
        } else {
            throw new CantConnectToActorAssetUserException(CantConnectToActorAssetUserException.DEFAULT_MESSAGE, null, "There was an error connecting to users.", null);
        }
        } catch (CantAskConnectionActorAssetException e) {
            throw new CantAskConnectionActorAssetException("", e, "", "");
        } catch (CantRequestAlreadySendActorAssetException e) {
            throw new CantAskConnectionActorAssetException("", e, "", "Intra user request already send");
        } catch (Exception e) {
            throw new CantAskConnectionActorAssetException("CAN'T ASK INTRA USER CONNECTION", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void acceptActorAssetUser(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException {
        try {
            /**
             *Call Actor Intra User to accept request connection
             */
            this.actorAssetUserManager.acceptActorAssetUser(actorAssetUserInPublicKey, actorAssetUserToAddPublicKey);

            /**
             *Call Network Service User to accept request connection
             */
            this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserInPublicKey, actorAssetUserToAddPublicKey);

        } catch (CantAcceptActorAssetUserException e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION - KEY " + actorAssetUserToAddPublicKey, e, "", "");
        } catch (Exception e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION - KEY " + actorAssetUserToAddPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException {
        try {
            /**
             *Call Actor Intra User to denied request connection
             */
            this.actorAssetUserManager.denyConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, actorAssetUserToRejectPublicKey);

            /**
             *Call Network Service User to denied request connection
             */
            this.assetUserActorNetworkServiceManager.denyConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToRejectPublicKey);

        } catch (CantDenyConnectionActorAssetException e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION - KEY:" + actorAssetUserToRejectPublicKey, e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION - KEY:" + actorAssetUserToRejectPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void disconnectActorAssetUser(String intraUserLoggedInPublicKey, String actorAssetUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException {
        try
        {
            /**
             *Call Actor User to disconnect request connection
             */
            this.actorAssetUserManager.disconnectActorAssetUser(intraUserLoggedInPublicKey, actorAssetUserToDisconnectPublicKey);

            /**
             *Call Network Service User to disconnect request connection
             */
            this.assetUserActorNetworkServiceManager.disconnectConnectionActorAsset(intraUserLoggedInPublicKey, actorAssetUserToDisconnectPublicKey);

        } catch (CantDisconnectAssetUserActorException e) {
            throw new CantDisconnectAssetUserActorException("CAN'T DISCONNECT ACTOR ASSET USER CONNECTION- KEY:" + actorAssetUserToDisconnectPublicKey, e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectAssetUserActorException("CAN'T DISCONNECT ACTOR ASSET USER CONNECTION- KEY:" + actorAssetUserToDisconnectPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public void cancelActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException {
        try {
            /**
             *Call Actor Intra User to cancel request connection
             */
            this.actorAssetUserManager.cancelActorAssetUser(actorAssetUserLoggedInPublicKey, actorAssetUserToCancelPublicKey);

            /**
             *Call Network Service Intra User to cancel request connection
             */
            this.assetUserActorNetworkServiceManager.cancelConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToCancelPublicKey);
        } catch (CantCancelConnectionActorAssetException e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION- KEY:" + actorAssetUserToCancelPublicKey, e, "", "");
        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION- KEY:" + actorAssetUserToCancelPublicKey, FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public List<ActorAssetUser> getWaitingYourConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
//        List<ActorAssetUser> intraUserList = new ArrayList<ActorAssetUser>();

        try {

//            for (ActorAssetUser intraUserActor : actorsList) {
//                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState()));
//            }

            return this.actorAssetUserManager.getWaitingYourConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, max, offset);
        } catch (CantGetActorAssetWaitingException e) {
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING YOUR ACCEPTANCE", e, "", "");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING YOUR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public List<ActorAssetUser> getWaitingTheirConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
//            List<ActorAssetUser> intraUserList = new ArrayList<ActorAssetUser>();

            //            for (ActorAssetUser intraUserActor : actorsList) {
//                intraUserList.add(new IntraUserModuleInformation(intraUserActor.getName(),"",intraUserActor.getPublicKey(),intraUserActor.getProfileImage(),intraUserActor.getContactState()));
//            }
            return this.actorAssetUserManager.getWaitingTheirConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, max, offset);
        } catch (CantGetActorAssetWaitingException e) {
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING THEIR ACCEPTANCE", e, "", "Error on IntraUserActor Manager");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING THEIR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
        }
    }

    @Override
    public ActorAssetUser getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException {
        try {
            return this.actorAssetUserManager.getLastNotificationActorAssetUser(actorAssetUserInPublicKey);
        } catch (CantGetActorAssetNotificationException e) {
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION",e,"","Error on ACTOR ASSET Manager");

        }
    }

    @Override
    public int getWaitingYourConnectionActorAssetUserCount() {
        //TODO: falta que este metodo que devuelva la cantidad de request de conexion que tenes
        try {

            if (getActiveAssetUserIdentity() != null){
                return getWaitingYourConnectionActorAssetUser(getActiveAssetUserIdentity().getPublicKey(), 100, 0).size();
            }

        } catch (CantGetActorAssetWaitingException e) {
            e.printStackTrace();
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SettingsManager<AssetUserSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return identityAssetUserManager.getSelectedActorIdentity();
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetUserManager.createNewIdentityAssetUser(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.appPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        int[] notifications = new int[4];
        try {
            if(getSelectedActorIdentity() != null)
                notifications[2] = actorAssetUserManager.getWaitingYourConnectionActorAssetUser(getSelectedActorIdentity().getPublicKey(),99,0).size();
            else
                notifications[2] = 0;
        } catch (CantGetActorAssetWaitingException | CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
