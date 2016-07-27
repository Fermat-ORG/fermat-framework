package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.utils.LocationUtils;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.utils.RefreshParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServiceActorLocationUpdaterAgent</code> is
 * responsible for updating the geolocation of the actors.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceActorLocationUpdaterAgent extends FermatAgent {

    /**
     * Geo-location update high rate limit in seconds.
     */
    private static final int GEO_LOCATION_HIGH_RATE_LIMIT = 60;

    /**
     * Represent the networkServiceRoot
     */
    private AbstractActorNetworkService networkServiceRoot;

    /**
     * Represent the scheduledThreadPool
     */
    private ScheduledExecutorService scheduledThreadPool;

    /**
     * Represent the scheduledFuture
     */
    private ScheduledFuture scheduledFuture;

    /**
     * Constructor with parameter
     *
     * @param networkServiceRoot
     */
    public NetworkServiceActorLocationUpdaterAgent(final AbstractActorNetworkService networkServiceRoot){

        this.networkServiceRoot      = networkServiceRoot;
        this.status                  = AgentStatus.CREATED;
        this.scheduledThreadPool     = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Method that verify if an actor needs to update its location, and updates it.
     */
    private void processActorUpdate() {

        try {

            long currentTime = System.currentTimeMillis();

            for (Map.Entry<ActorProfile, RefreshParameters> actor : networkServiceRoot.getRegisteredActors().entrySet()) {

                if (actor.getValue().getRefreshInterval() > 0) {

                    try {
                        long nextExecution = actor.getValue().getLastExecution() + actor.getValue().getRefreshInterval();

                        if (nextExecution <= currentTime) {

                            actor.getValue().setLastExecution(currentTime);

                            Location location = networkServiceRoot.getLocationManager().getLocation();

                            location = LocationUtils.getRandomLocation(location, actor.getValue().getAccuracy());

                            actor.getKey().setLocation(location);

                            networkServiceRoot.updateRegisteredActor(
                                    actor.getKey(),
                                    UpdateTypes.GEOLOCATION
                            );
                        }
                    } catch (Exception e) {

                        System.out.println("NetworkServiceActorLocationUpdaterAgent (" + networkServiceRoot.getProfile().getNetworkServiceType() + ") - " + actor.getKey() + " processActorUpdate detect a error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("NetworkServiceActorLocationUpdaterAgent ("+networkServiceRoot.getProfile().getNetworkServiceType()+") - processActorUpdate detect a error: "+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * (non-javadoc)
     * @see FermatAgent#start()
     */
    @Override
    public void start() throws CantStartAgentException {

        try {
            this.scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

            scheduledFuture = scheduledThreadPool.scheduleAtFixedRate(new ActorUpdateTask(), GEO_LOCATION_HIGH_RATE_LIMIT, GEO_LOCATION_HIGH_RATE_LIMIT, TimeUnit.SECONDS);

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#resume()
     */
    public void resume() throws CantStartAgentException {

        try {

            scheduledFuture = scheduledThreadPool.scheduleAtFixedRate(new ActorUpdateTask(), GEO_LOCATION_HIGH_RATE_LIMIT, GEO_LOCATION_HIGH_RATE_LIMIT, TimeUnit.SECONDS);

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(exception, null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#pause()
     */
    public void pause() throws CantStopAgentException {

        try {

            scheduledFuture.cancel(Boolean.TRUE);

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(exception, null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#stop()
     */
    public void stop() throws CantStopAgentException {

        try {

            scheduledThreadPool.shutdown();
            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    private class ActorUpdateTask implements Runnable {

        /**
         * (non-javadoc)
         * @see Runnable#run()
         */
        @Override
        public void run() {
            processActorUpdate();
        }
    }

}
