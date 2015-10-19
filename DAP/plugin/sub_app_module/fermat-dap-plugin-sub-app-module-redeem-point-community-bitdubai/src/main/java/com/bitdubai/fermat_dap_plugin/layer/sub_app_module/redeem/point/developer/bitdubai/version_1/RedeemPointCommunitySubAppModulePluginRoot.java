package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.DealsWithActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.version_1.structure.RedeemPointCommunitySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nerio on 13/10/15.
 */
public class RedeemPointCommunitySubAppModulePluginRoot implements RedeemPointCommunitySubAppModuleManager, DealsWithActorAssetRedeemPoint, DealsWithLogger, LogManagerForDevelopers, Plugin, Service {

    UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    RedeemPointCommunitySupAppModuleManager redeemPointCommunitySupAppModuleManager;

    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {
        redeemPointCommunitySupAppModuleManager = new RedeemPointCommunitySupAppModuleManager(actorAssetRedeemPointManager);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) throws CantSetObjectException {
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
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
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.version_1.RedeemPointCommunitySubAppModulePluginRoot");
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
            if (RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                RedeemPointCommunitySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> actorAssetList = new ArrayList<>();

//        Location location = new DeviceLocation(00.00, 00.00, 12345678910L, 00.00, LocationProvider.NETWORK);

//        actorAssetList.add(new RedeemPointActorRecord("Rodrigo Acosta",UUID.randomUUID().toString(),new byte[0],location));
//        actorAssetList.add(new RedeemPointActorRecord("Franklin Marcano",UUID.randomUUID().toString(),new byte[0],location));
//        actorAssetList.add(new RedeemPointActorRecord("Manuel Colmenares",UUID.randomUUID().toString(),new byte[0],location));
//        actorAssetList.add(new RedeemPointActorRecord("Nerio Indriago",UUID.randomUUID().toString(),new byte[0],location));

        return actorAssetList;
    }
}
