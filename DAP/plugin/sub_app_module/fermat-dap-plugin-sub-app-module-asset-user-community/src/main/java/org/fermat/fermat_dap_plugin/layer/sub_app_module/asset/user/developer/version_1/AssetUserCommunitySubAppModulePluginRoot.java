package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.exceptions.CantGetSupAppUserModuleException;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.version_1.structure.AssetUserCommunitySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Nerio on 13/10/15.
 * Modified by Luis Campo 27/11/2015
 */
@NeededIndirectPluginReferences(indirectReferences = {
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_EXTRA_USER),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_INTRA_USER)
})
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "nindriago",
        layer = Layers.SUB_APP_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_USER_COMMUNITY)
public class AssetUserCommunitySubAppModulePluginRoot extends AbstractModule<AssetUserSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_USER)
    IdentityAssetUserManager identityAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    ActorAssetIssuerManager actorAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_ISSUER)
    AssetIssuerWalletManager assetIssuerWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_USER)
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    AssetUserCommunitySubAppModuleManager assetUserCommunitySupAppModuleManager;

    BlockchainNetworkType blockchainNetworkType;
    String appPublicKey;

    public AssetUserCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetUserCommunitySubAppModulePluginRoot");
        returnedClasses.add("AssetUserCommunitySupAppModuleManager");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetUserCommunitySubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetUserCommunitySubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetUserCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetUserCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return AssetUserCommunitySubAppModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
//    @moduleManagerInterfacea(moduleManager = AssetUserCommunitySupAppModuleManager.class)
    public AssetUserCommunitySubAppModuleManager getModuleManager() throws CantGetModuleManagerException {
        try {
//            logManager.log(AssetUserCommunitySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset User Sup AppModule instantiation started...", null, null);

            if (assetUserCommunitySupAppModuleManager == null) {
                assetUserCommunitySupAppModuleManager = new AssetUserCommunitySupAppModuleManager(
                        pluginFileSystem,
                        logManager,
                        errorManager,
                        eventManager,
                        broadcaster,
                        pluginId,
                        identityAssetUserManager,
                        actorAssetIssuerManager,
                        actorAssetUserManager,
                        assetIssuerWalletManager,
                        assetUserWalletManager,
                        assetUserActorNetworkServiceManager,
                        this);
            }

//            logManager.log(AssetUserCommunitySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset User Sup AppModule instantiation finished successfully.", null, null);

            return assetUserCommunitySupAppModuleManager;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException(CantGetSupAppUserModuleException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }

//
//    @Override
//    public DAPConnectionState getActorRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetUserActorsException {
//
//        blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//        return actorAssetUserManager.getActorAssetUserRegisteredDAPConnectionState(actorAssetPublicKey, blockchainNetworkType);
//    }
//
//    @Override
//    public List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException {
//        List<ActorAssetUser> list = null;
//        List<AssetUserActorRecord> assetUserActorRecords = null;
//
//        try {
//            list = assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
//            if (list != null && list.size() > 0)
//                actorAssetUserManager.createActorAssetUserRegisterInNetworkService(list);
//        } catch (CantRequestListActorAssetUserRegisteredException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetAssetUserActorsException("Service not available, try in a few minutes.", null, "Problems with the Web Service.", null);
//        } catch (CantCreateAssetUserActorException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//        }
//
//        if (list != null) {
//            assetUserActorRecords = new ArrayList<>();
//            actorAssetUserManager.updateOfflineUserRegisterInNetworkService(list);
//
//            try {
////                BlockchainNetworkType blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();
////                for (ActorAssetUser actorAssetUser : actorAssetUserManager.getAllAssetUserActorInTableRegistered(blockchainNetworkType)) {
////                    if (Objects.equals(actorAssetUser.getType().getCode(), Actors.DAP_ASSET_USER.getCode())) {
////                        AssetUserActorRecord assetUserActorRecord = (AssetUserActorRecord) actorAssetUser;
////                        assetUserActorRecords.add(assetUserActorRecord);
////                    }
////                }
//                blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//                for (ActorAssetUser actorAssetUser : actorAssetUserManager.getAllAssetUserActorInTableRegistered(blockchainNetworkType)) {
//                    if (Objects.equals(actorAssetUser.getType().getCode(), Actors.DAP_ASSET_USER.getCode())) {
//                        AssetUserActorRecord assetUserActorRecord = (AssetUserActorRecord) actorAssetUser;
//                        assetUserActorRecords.add(assetUserActorRecord);
//                    }
//                }
//
//
//            } catch (CantGetAssetUserActorsException e) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                e.printStackTrace();
//            }
//        }
//        return assetUserActorRecords;
//    }
//
//    //@Override
//    public ActorAssetUser getActorUser(String actorAssetUserPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
//        try {
//            blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//            return actorAssetUserManager.getActorByPublicKey(actorAssetUserPublicKey, blockchainNetworkType);
//        } catch (CantAssetUserActorNotFoundException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<ActorAssetUser> getAllActorAssetUserConnected() throws CantGetAssetUserActorsException {
//        try {
//            blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//            return actorAssetUserManager.getAllAssetUserActorConnected(blockchainNetworkType);
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<AssetUserActorRecord> getAllActorAssetUserRegisteredWithCryptoAddressNotIntheGroup(String groupId) throws CantGetAssetUserActorsException {
//        List<AssetUserActorRecord> allUserRegistered = this.getAllActorAssetUserRegistered();
//        List<ActorAssetUser> allUserRegisteredInGroup = this.getListActorAssetUserByGroups(groupId);
//        List<AssetUserActorRecord> allUserRegisteredFiltered = new ArrayList<>();
//        for (AssetUserActorRecord record : allUserRegistered) {
//            // Obtain all user connected and not in the current group
//            if (record.getCryptoAddress() != null && (!userInGroup(record.getActorPublicKey(), allUserRegisteredInGroup))) {
//                allUserRegisteredFiltered.add(record);
//            }
//        }
//
//        return allUserRegisteredFiltered;
//    }
//
//    private boolean userInGroup(String actorPublicKey, List<ActorAssetUser> usersInGroup) {
//        for (ActorAssetUser record : usersInGroup) {
//            if (record.getActorPublicKey().equals(actorPublicKey)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetException {
////        try {
////            ActorAssetIssuer actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
////            if (actorAssetIssuer != null) {
////                blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();
////
////                actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers, blockchainNetworkType);
////            } else {
////                ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
////                if (actorAssetUser != null) {
////                    blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
////
////                    actorAssetUserManager.connectToActorAssetUser(actorAssetUser, actorAssetUsers, blockchainNetworkType);
////                } else {
////                    throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "There was an error connecting to users.", null);
////                }
////            }
////        } catch (CantGetAssetIssuerActorsException | CantGetAssetUserActorsException e) {
////            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
////            throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
////        }
//    }
//
//    @Override
//    public ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException {
//        try {
//            return actorAssetUserManager.createAssetUserGroup(groupName);
//        } catch (CantCreateAssetUserGroupException | ActorAssetUserGroupAlreadyExistException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public void renameGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException {
//        try {
//            actorAssetUserManager.updateAssetUserGroup(assetUserGroup);
//        } catch (CantUpdateAssetUserGroupException | RecordsNotFoundException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public void deleteGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
//        try {
//            actorAssetUserManager.deleteAssetUserGroup(assetUserGroupId);
//        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public void addActorAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException {
//        try {
//            actorAssetUserManager.addAssetUserToGroup(actorAssetUserGroupMember);
//        } catch (CantCreateAssetUserGroupException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public void removeActorAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
//        try {
//            actorAssetUserManager.removeAssetUserFromGroup(assetUserGroupMember);
//        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<ActorAssetUserGroup> getGroups() throws CantGetAssetUserGroupException {
//        try {
//            return actorAssetUserManager.getAssetUserGroupsList();
//        } catch (CantGetAssetUserGroupException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupId) throws CantGetAssetUserActorsException {
//        try {
//            blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//            return actorAssetUserManager.getListActorAssetUserByGroups(groupId, blockchainNetworkType);
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public List<ActorAssetUserGroup> getListGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException {
//        try {
//            return actorAssetUserManager.getListAssetUserGroupsByActorAssetUser(actorAssetUserPublicKey);
//        } catch (CantGetAssetUserGroupException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    @Override
//    public ActorAssetUserGroup getGroup(String groupId) throws CantGetAssetUserGroupException {
//        try {
//            return actorAssetUserManager.getAssetUserGroup(groupId);
//        } catch (CantGetAssetUserGroupException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw e;
//        }
//    }
//
//    public IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException {
//        try {
//            return identityAssetUserManager.getIdentityAssetUser();
//        } catch (CantGetAssetUserIdentitiesException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetIdentityAssetUserException(e);
//        }
//    }
//
//    @Override
//    public void askActorAssetUserForConnection(List<ActorAssetUser> actorAssetUsers) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {
//        try {
//            ActorAssetIssuer actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
//            if (actorAssetIssuer != null) {
//                blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();
//
//                for (ActorAssetUser actorAssetUser : actorAssetUsers) {
//                    this.actorAssetUserManager.askActorAssetUserForConnection(
//                            actorAssetIssuer.getActorPublicKey(),
//                            actorAssetUser.getName(),
//                            actorAssetUser.getActorPublicKey(),
//                            actorAssetUser.getProfileImage(),
//                            blockchainNetworkType);
//
//                    if (this.actorAssetUserManager.getActorAssetUserRegisteredDAPConnectionState(actorAssetUser.getActorPublicKey(), blockchainNetworkType) != DAPConnectionState.REGISTERED_REMOTELY) {
//                        System.out.println("The User you are trying to connect with is not connected" +
//                                "so we send the message to the assetUserActorNetworkService");
//                        this.assetUserActorNetworkServiceManager.askConnectionActorAsset(
//                                actorAssetIssuer.getActorPublicKey(),
//                                actorAssetIssuer.getName(),
//                                Actors.DAP_ASSET_ISSUER,
//                                actorAssetUser.getActorPublicKey(),
//                                actorAssetUser.getName(),
//                                Actors.DAP_ASSET_USER,
//                                actorAssetIssuer.getProfileImage(),
//                                blockchainNetworkType);
//                    } else {
//                        this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetIssuer.getActorPublicKey(), actorAssetUser.getActorPublicKey());
//                        System.out.println("The actor asset user is connected");
//                    }
//                }
//            } else {
//                ActorAssetUser actorAssetUserLogging = actorAssetUserManager.getActorAssetUser();
//                if (actorAssetUserLogging != null) {
//                    blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//
//                    for (ActorAssetUser actorAssetUser : actorAssetUsers) {
//                        this.actorAssetUserManager.askActorAssetUserForConnection(
//                                actorAssetUserLogging.getActorPublicKey(),
//                                actorAssetUser.getName(),
//                                actorAssetUser.getActorPublicKey(),
//                                actorAssetUser.getProfileImage(),
//                                blockchainNetworkType);
//
//                        if (this.actorAssetUserManager.getActorAssetUserRegisteredDAPConnectionState(actorAssetUser.getActorPublicKey(), blockchainNetworkType) != DAPConnectionState.REGISTERED_REMOTELY) {
//                            System.out.println("The User you are trying to connect with is not connected" +
//                                    "so we send the message to the assetUserActorNetworkService");
//                            this.assetUserActorNetworkServiceManager.askConnectionActorAsset(
//                                    actorAssetUserLogging.getActorPublicKey(),
//                                    actorAssetUserLogging.getName(),
//                                    Actors.DAP_ASSET_USER,
//                                    actorAssetUser.getActorPublicKey(),
//                                    actorAssetUser.getName(),
//                                    Actors.DAP_ASSET_USER,
//                                    actorAssetUserLogging.getProfileImage(),
//                                    blockchainNetworkType);
//                        } else {
//                            this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserLogging.getActorPublicKey(), actorAssetUser.getActorPublicKey());
//                            System.out.println("The actor asset user is connected");
//                        }
//                    }
//                } else {
//                    throw new CantConnectToActorAssetException(CantConnectToActorAssetException.DEFAULT_MESSAGE, null, "There was an error connecting to users.", null);
//                }
//            }
//        } catch (CantAskConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("", e, "", "");
//        } catch (CantRequestAlreadySendActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("", e, "", "actor asset user request already send");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAskConnectionActorAssetException("CAN'T ASK ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void acceptActorAssetUser(String actorAssetUserInPublicKey, ActorAssetUser actorAssetToAdd) throws CantAcceptActorAssetUserException {
//        try {
//
//            if (actorAssetToAdd.getType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
//                this.actorAssetIssuerManager.acceptActorAssetIssuer(actorAssetUserInPublicKey, actorAssetToAdd.getActorPublicKey());
//            } else {
//                if (actorAssetToAdd.getType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
//                    this.actorAssetUserManager.acceptActorAssetUser(actorAssetUserInPublicKey, actorAssetToAdd.getActorPublicKey());
//                }
//            }
//            this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserInPublicKey, actorAssetToAdd.getActorPublicKey());
//
//        } catch (CantAcceptActorAssetUserException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION - KEY " + actorAssetToAdd.getActorPublicKey(), e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION - KEY " + actorAssetToAdd.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, ActorAssetUser actorAssetToReject) throws CantDenyConnectionActorAssetException {
//        try {
//
//            if (actorAssetToReject.getType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
//                this.actorAssetIssuerManager.denyConnectionActorAssetIssuer(actorAssetUserLoggedInPublicKey, actorAssetToReject.getActorPublicKey());
//            } else {
//                if (actorAssetToReject.getType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
//                    this.actorAssetUserManager.denyConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, actorAssetToReject.getActorPublicKey());
//                }
//            }
//
//            this.assetUserActorNetworkServiceManager.denyConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetToReject.getActorPublicKey());
//
//        } catch (CantDenyConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION - KEY:" + actorAssetToReject.getActorPublicKey(), e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION - KEY:" + actorAssetToReject.getActorPublicKey(), FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public void disconnectToActorAssetUser(ActorAssetUser user) throws CantDisconnectAssetActorException {
//        try {
//            String actorLoggedPublicKey = null;
//
//            blockchainNetworkType = getNetworkTypeActiveByActorOrGeneric();
//
//            this.actorAssetUserManager.disconnectToActorAssetUser(user.getActorPublicKey(), blockchainNetworkType);
//
//            this.assetUserActorNetworkServiceManager.disconnectConnectionActorAsset(user.getActorPublicKey(), user.getActorPublicKey());
//
//        } catch (CantDisconnectAssetActorException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR ASSET USER CONNECTION - KEY:" + user.getActorPublicKey(), e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantDisconnectAssetActorException("CAN'T DISCONNECT ACTOR ASSET USER CONNECTION - KEY:" + user.getActorPublicKey(), e, "", "");
//        }
//    }
//
//    @Override
//    public void cancelActorAssetUser(String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException {
//        try {
//            String actorLoggedPublicKey = null;
//
//            this.actorAssetUserManager.cancelActorAssetUser(actorAssetUserToCancelPublicKey);
//
////            this.assetUserActorNetworkServiceManager.cancelConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToCancelPublicKey);
//
//        } catch (CantCancelConnectionActorAssetException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION - KEY:" + actorAssetUserToCancelPublicKey, e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION - KEY:" + actorAssetUserToCancelPublicKey, FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public List<ActorAssetUser> getWaitingYourConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
//        try {
//            List<DAPActor> dapActor;
//            List<ActorAssetUser> actorAssetUsers = new ArrayList<>();
//
//            dapActor = this.actorAssetIssuerManager.getWaitingYourConnectionActorAssetIssuer(actorAssetUserLoggedInPublicKey, max, offset);
//
//            if (dapActor.size() <= 0)
//                dapActor = this.actorAssetUserManager.getWaitingYourConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, max, offset);
//
//            //TODO Mejorar Implementacion para tener informacion mas completa
//            for (DAPActor record : dapActor) {
//                actorAssetUsers.add((new AssetUserActorRecord (
//                        record.getActorPublicKey(),
//                        record.getName(),
//                        null,
//                        null,
//                        null,
//                        (double) 0,
//                        (double) 0,
//                        null,
//                        (long)  0,
//                        (long)  0,
//                        null,
//                        record.getType(),
//                        record.getProfileImage())));
//            }
//
//            return  actorAssetUsers;
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING YOUR ACCEPTANCE", e, "", "");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING YOUR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public List<ActorAssetUser> getWaitingTheirConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
//        try {
//            List<DAPActor> dapActor;
//            List<ActorAssetUser> actorAssetUsers = new ArrayList<>();
//
//            dapActor = this.actorAssetIssuerManager.getWaitingTheirConnectionActorAssetIssuer(actorAssetUserLoggedInPublicKey, max, offset);
//
//            if (dapActor.size() <= 0)
//                dapActor =  this.actorAssetUserManager.getWaitingTheirConnectionActorAssetUser(actorAssetUserLoggedInPublicKey, max, offset);
//
//            //TODO Mejorar Implementacion para tener informacion mas completa
//            for (DAPActor record : dapActor) {
//                actorAssetUsers.add((new AssetUserActorRecord (
//                        record.getActorPublicKey(),
//                        record.getName(),
//                        null,
//                        null,
//                        null,
//                        (double) 0,
//                        (double) 0,
//                        null,
//                        (long)  0,
//                        (long)  0,
//                        null,
//                        record.getType(),
//                        record.getProfileImage())));
//            }
//
//            return  actorAssetUsers;
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING THEIR ACCEPTANCE", e, "", "Error on ACTOR ASSET USER MANAGER");
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetWaitingException("CAN'T GET ACTOR ASSET USER WAITING THEIR ACCEPTANCE", FermatException.wrapException(e), "", "unknown exception");
//        }
//    }
//
//    @Override
//    public ActorAssetUser getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException {
//        try {
//            return this.actorAssetUserManager.getLastNotificationActorAssetUser(actorAssetUserInPublicKey);
//        } catch (CantGetActorAssetNotificationException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", e, "", "Error on ACTOR ASSET Manager");
//        }
//    }
//
//    @Override
//    public int getWaitingYourConnectionActorAssetUserCount() {
//        //TODO: falta que este metodo que devuelva la cantidad de request de conexion que tenes
//        try {
//            int countActor;
//
//            if (getActiveAssetUserIdentity() != null) {
//                countActor = actorAssetIssuerManager.getWaitingYourConnectionActorAssetIssuer(getActiveAssetUserIdentity().getPublicKey(), 100, 0).size();
//
//                if (countActor <= 0) {
//                    countActor = getWaitingYourConnectionActorAssetUser(getActiveAssetUserIdentity().getPublicKey(), 100, 0).size();
//                }
//                return countActor;
//
//            }
//
//        } catch (CantGetActorAssetWaitingException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        } catch (CantGetIdentityAssetUserException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        } catch (Exception e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    @Override
//    public SettingsManager<AssetUserSettings> getSettingsManager() {
//        if (this.settingsManager != null)
//            return this.settingsManager;
//
//        this.settingsManager = new SettingsManager<>(
//                pluginFileSystem,
//                pluginId
//        );
//
//        return this.settingsManager;
//    }
//
//    @Override
//    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
//        return identityAssetUserManager.getSelectedActorIdentity();
//    }
//
//    @Override
//    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
//        identityAssetUserManager.createNewIdentityAssetUser(name, profile_img);
//    }
//
//    @Override
//    public void setAppPublicKey(String publicKey) {
//        this.appPublicKey = publicKey;
//    }
//
//    @Override
//    public int[] getMenuNotifications() {
//        int[] notifications = new int[5];
//        try {
//            if (getSelectedActorIdentity() != null) {
//                notifications[2] = actorAssetIssuerManager.getWaitingYourConnectionActorAssetIssuer(getSelectedActorIdentity().getPublicKey(), 100, 0).size();
//
//                if (notifications[2] <= 0) {
//                    notifications[2] = actorAssetUserManager.getWaitingYourConnectionActorAssetUser(getSelectedActorIdentity().getPublicKey(), 99, 0).size();
//                }
//            } else {
//                notifications[2] = 0;
//            }
//        } catch (CantGetActorAssetWaitingException | CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        }
//        return notifications;
//    }
//
//    private BlockchainNetworkType getNetworkTypeActiveByActorOrGeneric() {
//
//        try {
//            ActorAssetIssuer actorAssetIssuer;
//            actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
//
//            if (actorAssetIssuer != null) {
//                blockchainNetworkType = assetIssuerWalletSupAppModuleManager.getSelectedNetwork();
//            } else {
//                ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
//
//                if (actorAssetUser != null) {
//                    blockchainNetworkType = assetUserWalletSubAppModuleManager.getSelectedNetwork();
//                } else {
//                    blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
//                }
//            }
//        } catch (CantGetAssetIssuerActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        } catch (CantGetAssetUserActorsException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            e.printStackTrace();
//        }
//        return blockchainNetworkType;
//    }
}
