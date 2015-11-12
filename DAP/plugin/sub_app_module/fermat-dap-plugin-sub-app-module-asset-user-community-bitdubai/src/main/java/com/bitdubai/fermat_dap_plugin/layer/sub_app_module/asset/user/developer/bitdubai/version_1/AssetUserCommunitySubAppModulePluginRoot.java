package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.DealsWithActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.DealsWithActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1.structure.AssetUserCommunitySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nerio on 13/10/15.
 */
public class AssetUserCommunitySubAppModulePluginRoot implements AssetUserCommunitySubAppModuleManager, DealsWithActorAssetIssuer, DealsWithActorAssetUser, DealsWithLogger, LogManagerForDevelopers, Plugin, Service {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    UUID pluginId;
    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    AssetUserCommunitySupAppModuleManager assetUserCommunitySupAppModuleManager;

    ActorAssetUserManager actorAssetUserManager;

    ActorAssetIssuerManager actorAssetIssuerManager;
    List<ActorAssetUser> actorAssetList;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        assetUserCommunitySupAppModuleManager = new AssetUserCommunitySupAppModuleManager(actorAssetUserManager);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    @Override
    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.user.developer.bitdubai.version_1.AssetUserCommunitySubAppModulePluginRoot");
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

    @Override
    public List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException {

        actorAssetList = new ArrayList<>();
        List<AssetUserActorRecord> assetUserActorRecords = null;

        try {
            actorAssetUserManager.registerActorInActorNetowrkSerice();
            actorAssetList = actorAssetUserManager.getAllAssetUserActorInTableRegistered();

            ActorAssetIssuer actorAssetIssuer;
            actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();

            assetUserActorRecords = new ArrayList<>();

            for (ActorAssetUser actorAssetUser : actorAssetUserManager.getAllAssetUserActorInTableRegistered()) {
                AssetUserActorRecord assetUserActorRecord = new AssetUserActorRecord();
                assetUserActorRecord = (AssetUserActorRecord) actorAssetUser;
                assetUserActorRecords.add(assetUserActorRecord);
            }

            List<ActorAssetUser> actorAssetUser = actorAssetUserManager.getAllAssetUserActorInTableRegistered();

            actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUser);

        } catch (CantCreateAssetUserActorException e) {
            e.printStackTrace();
        } catch (CantGetAssetIssuerActorsException e) {
            e.printStackTrace();
        } catch (CantGetAssetUserActorsException e) {
            e.printStackTrace();
        } catch (CantConnectToAssetUserException e) {
            e.printStackTrace();
        }
        return assetUserActorRecords;
    }

    @Override
    public void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToAssetUserException {
        //todo SE DEBE CONOCER QUIEN ES EL REQUESTER SOLICITANTE Y QUIEN EL SOLICITADO

        ActorAssetIssuer actorAssetIssuer;
        //TODO Para Realizacion de TEST se tomara el ISSUER de la BD LOCAL
        //TODO Se necesita PASAR el Actor seleccionado en la Community
        try {
            actorAssetIssuer = actorAssetIssuerManager.getActorAssetIssuer();
//            actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers);

            actorAssetUserManager.connectToActorAssetUser(actorAssetIssuer, actorAssetUsers);
        } catch (CantGetAssetIssuerActorsException e) {
            throw new CantConnectToAssetUserException(CantConnectToAssetUserException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        }
    }
}
