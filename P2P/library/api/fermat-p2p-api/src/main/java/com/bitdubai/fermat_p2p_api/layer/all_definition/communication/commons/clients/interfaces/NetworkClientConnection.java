package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUnregisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

import java.util.List;

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
     */
    void registerProfile(Profile profile) throws CantRegisterProfileException;

    /**
     * Through the method <code>unregisterProfile</code> we can unregister a profile in the server.
     *
     * @param profile that we're trying to unregister.
     *
     * @throws CantUnregisterProfileException if something goes wrong.
     */
    void unregisterProfile(Profile profile) throws CantUnregisterProfileException;

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
     * Through the method <code>listRegisteredActorProfiles</code> we can get a list of registered actors
     * filtering them with an instance of discovery query parameters.
     *
     * @param discoveryQueryParameters parameters to discover the actors.
     *
     * @return a list of actors profile that meets the parameters.
     *
     * @throws CantRequestProfileListException
     */
    List<ActorProfile> listRegisteredActorProfiles(DiscoveryQueryParameters discoveryQueryParameters) throws CantRequestProfileListException;

    /**
     * Through the method <code>getCommunicationChannelType</code> we can get the communication channel type
     * of the network client connection object.
     *
     * @return a CommunicationChannels enum element.
     */
    CommunicationChannels getCommunicationChannelType();

    void callActor(NetworkServiceProfile networkServiceProfile, ActorProfile actorProfile);

    /**
     * Through the method <code>isActorOnline</code> we can know if an actor is connected to the fermat network.
     *
     * @param publicKey of the actor
     *
     * @return a boolean value indicating if the actor is online.
     */
    Boolean isActorOnline(final String publicKey);

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
