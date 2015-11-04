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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.DealsWithActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.DealsWithActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
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
public class RedeemPointCommunitySubAppModulePluginRoot implements RedeemPointCommunitySubAppModuleManager, DealsWithActorAssetUser, DealsWithActorAssetRedeemPoint, DealsWithLogger, LogManagerForDevelopers, Plugin, Service {

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

    ActorAssetUserManager actorAssetUserManager;

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
    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.actorAssetUserManager = actorAssetUserManager;
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

    List<ActorAssetRedeemPoint> actorAssetList;

    @Override
    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException {
        actorAssetList = new ArrayList<>();

        try {
            actorAssetRedeemPointManager.registerActorInActorNetowrkSerice();

            actorAssetList = actorAssetRedeemPointManager.getAllAssetRedeemPointActorInTableRegistered();
        } catch (CantGetAssetRedeemPointActorsException e) {
            e.printStackTrace();
        } catch (CantCreateActorRedeemPointException e) {
            e.printStackTrace();
        }

        return actorAssetList;
    }

    @Override
    public void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantConnectToActorAssetRedeemPointException {
        //todo SE DEBE CONOCER QUIEN ES EL REQUESTER SOLICITANTE Y QUIEN EL SOLICITADO

        ActorAssetUser actorAssetUser;
        //TODO Para Realizacion de TEST se tomara el ISSUER de la BD LOCAL
        //TODO Se necesita PASAR el Actor seleccionado en la Community
        try {
            actorAssetUser = actorAssetUserManager.getActorAssetUser();
            actorAssetRedeemPointManager.connectToActorAssetRedeemPoint(actorAssetUser, actorAssetRedeemPoints);
        } catch (CantGetAssetUserActorsException e) {
            throw new CantConnectToActorAssetRedeemPointException(CantConnectToActorAssetRedeemPointException.DEFAULT_MESSAGE, e, "There was an error connecting to users.", null);
        } catch (CantAssetUserActorNotFoundException e) {
            e.printStackTrace();
        }
    }
}
