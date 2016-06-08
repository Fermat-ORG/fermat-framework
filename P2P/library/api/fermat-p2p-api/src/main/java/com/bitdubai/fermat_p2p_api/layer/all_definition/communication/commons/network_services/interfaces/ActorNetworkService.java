package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUnregisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUpdateRegisteredActorException;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.interfaces.ActorNetworkService</code>
 * define the public methods that provides an actor network service component.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface ActorNetworkService {

    /**
     * Through the method <code>registerActor</code> we can register an actor
     * profile in the fermat network.
     *
     * @param publicKey  of the actor.
     * @param name       of the actor.
     * @param alias      of the actor.
     * @param extraData  of the actor, this field can be null.
     * @param location   of the actor, if not specified it will get one.
     * @param type       of the actor.
     * @param image      of the actor.
     *
     * @throws ActorAlreadyRegisteredException if the actor is already registered.
     * @throws CantRegisterActorException      if something goes wrong.
     */
    void registerActor(String   publicKey      ,
                       String   name           ,
                       String   alias          ,
                       String   extraData      ,
                       Location location       ,
                       Actors   type           ,
                       byte[]   image          ,
                       long     refreshInterval,
                       long     accuracy       ) throws ActorAlreadyRegisteredException, CantRegisterActorException;

    /**
     * Through the method <code>updateRegisteredActor</code> we can update
     * the profile of a registered actor.
     *
     * @param publicKey of the actor, needed parameter to find the actor to update.
     * @param name      of the actor, this field can be null.
     * @param alias     of the actor, this field can be null.
     * @param extraData of the actor, this field can be null.
     * @param image     of the actor, this field can be null.
     * @param location  of the actor, this field can be null.
     *
     * @throws ActorNotRegisteredException        if the actor is not registered through this network service.
     * @throws CantUpdateRegisteredActorException if something goes wrong.
     */
    void updateRegisteredActor(String   publicKey,
                               String   name     ,
                               String   alias    ,
                               Location location ,
                               String   extraData,
                               byte[]   image    ) throws ActorNotRegisteredException, CantUpdateRegisteredActorException;

    /**
     * Through the method <code>unregisterActor</code> we can unregister
     * the profile of a registered actor.
     *
     * @param publicKey of the actor.
     *
     * @throws ActorNotRegisteredException  if the actor is not registered through this network service.
     * @throws CantUnregisterActorException if something goes wrong.
     */
    void unregisterActor(String publicKey) throws ActorNotRegisteredException, CantUnregisterActorException;

    /**
     * Through the method <code>isActorOnline</code> we can check if an actor is online or not.
     *
     * @param publicKey of the actor.
     */
    boolean isActorOnline(String publicKey);

}
