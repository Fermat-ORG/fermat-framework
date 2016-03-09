package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

/**
 * Created by Gabriel Araujo on 08/03/16.
 */
public class ArtistActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    private long reprocessTimer = 300000; //five minutes
    private Timer timer = new Timer();
    /**
     * Executor
     */
    ExecutorService executorService;
    /**
     * Default constructor
     */
    public ArtistActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_ARTIST,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ARTIST_ACTOR,
                "Actor Network Service Artist",
                null);
    }

    @Override
    protected void onStart() throws CantStartPluginException {

        System.out.println("########################\nART ACTOR STARTED\n####################################");
        this.startTimer();
    }

    @Override
    protected void onNetworkServiceRegistered() {
        System.out.println("##########################\n Actor Artist Network Service Registred\nPublic Key: " + getIdentity().getPublicKey() + "\n#######################################3");
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }
    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
               // reprocessPendingMessage();
            }
        }, 0, reprocessTimer);
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }
}
