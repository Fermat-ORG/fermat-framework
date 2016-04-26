package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantCreateNetworkCallException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUnregisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.ProfileNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection</code>
 * contains all the method related with a network client connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public interface NetworkClientConnection {

    /**
     * Through the method <code>registerProfile</code> we can register a profile
     * in the server.
     *
     * @param profile  of the component that we're trying to register.
     *
     * @throws CantRegisterProfileException      if something goes wrong.
     * @throws ProfileAlreadyRegisteredException if the profile is already registered.
     */
    void registerProfile(Profile profile) throws CantRegisterProfileException, ProfileAlreadyRegisteredException;

    /**
     * Through the method <code>unregisterProfile</code> we can unregister a profile in the server.
     *
     * @param profile that we're trying to unregister.
     *
     * @throws CantUnregisterProfileException if something goes wrong.
     * @throws ProfileNotRegisteredException  if the profile is not registered.
     */
    void unregisterProfile(Profile profile) throws CantUnregisterProfileException, ProfileNotRegisteredException ;

    /**
     * Through the method <code>callNetworkService</code> we can request a network call between
     * two network services.
     *
     * @param fromNetworkService profile of the network service that is trying to communicate.
     * @param toNetworkService   profile of the network service which we're trying to reach.
     *
     * @throws CantCreateNetworkCallException if something goes wrong.
     */
    void callNetworkService(NetworkServiceProfile fromNetworkService,
                            NetworkServiceProfile toNetworkService  ) throws CantCreateNetworkCallException;

    /**
     * Through the method <code>callNetworkService</code> we can request a network call between
     * two actors.
     *
     * @param fromActor           profile of the actor that is trying to communicate.
     * @param toActor             profile of the actor which we're trying to reach.
     * @param fromNetworkService  profile of the network service that is trying to communicate.
     *
     * @throws CantCreateNetworkCallException
     */
    void callActor(ActorProfile          fromActor         ,
                   ActorProfile          toActor           ,
                   NetworkServiceProfile fromNetworkService) throws CantCreateNetworkCallException;

    /**
     * Through the method <code>registeredProfileDiscoveryQuery</code> we can make a discovery query
     * looking for registered profiles.
     *
     * @param discoveryQueryParameters helper class to make the query.
     *
     * @throws CantRequestProfileListException if something goes wrong.
     */
    void registeredProfileDiscoveryQuery(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestProfileListException;

    /**
     * Through the method <code>actorTraceDiscoveryQuery</code> we can make a discovery query
     * looking for actor profiles.
     *
     * @param discoveryQueryParameters helper class to make the query.
     *
     * @throws CantRequestProfileListException if something goes wrong.
     */
    void actorTraceDiscoveryQuery(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestProfileListException;

    /**
     * Through the method <code>isConnected</code> we can verify if the connection object is
     * connected with the server.
     *
     * @return boolean
     */
    boolean isConnected();

    /**
     * Through the method <code>isRegistered</code> we can verify if the client is
     * registered on a server.
     *
     * @return boolean
     */
    boolean isRegistered();

}
