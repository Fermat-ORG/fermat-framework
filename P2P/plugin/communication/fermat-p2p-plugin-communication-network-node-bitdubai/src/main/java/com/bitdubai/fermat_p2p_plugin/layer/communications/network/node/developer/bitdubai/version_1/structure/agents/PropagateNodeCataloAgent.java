/*
 * @#PropagateNodeCataloAgent.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.agents.PropagateNodeCataloAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PropagateNodeCataloAgent  extends FermatAgent {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(PropagateNodeCataloAgent.class.getName());

    /**
     * Represent the scheduledThreadPool
     */
    private ScheduledExecutorService scheduledThreadPool;

    /**
     * Represent the scheduledFutures
     */
    private List<ScheduledFuture> scheduledFutures;

    /**
     * Constructor
     */
    public  PropagateNodeCataloAgent(){
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
    }

    /**
     * Propagation logic implementation
     */
    private void propagateCatalog(){

    }

    /**
     * Internal thread
     */
    private class PropagationTask implements Runnable {

        /**
         * (non-javadoc)
         * @see Runnable#run()
         */
        @Override
        public void run() {
            propagateCatalog();
        }
    }


    /**
     * (non-javadoc)
     * @see FermatAgent#start()
     */
    @Override
    public void start() throws CantStartAgentException {

        try {

            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PropagationTask(), 1,  1, TimeUnit.MINUTES));
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
            try {

                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PropagationTask(), 1,  1, TimeUnit.MINUTES));
                this.status = AgentStatus.STARTED;

            } catch (Exception exception) {
                throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
            }

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#pause()
     */
    public void pause() throws CantStopAgentException {
        try {

            for (ScheduledFuture future: scheduledFutures) {
                future.cancel(Boolean.TRUE);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
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
}
