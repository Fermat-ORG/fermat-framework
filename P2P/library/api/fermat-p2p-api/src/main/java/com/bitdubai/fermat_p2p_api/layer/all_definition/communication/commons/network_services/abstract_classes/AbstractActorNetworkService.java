package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.ImageUtil;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.utils.LocationUtils;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUnregisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServiceActorLocationUpdaterAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorProfileRegisteredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorProfileUpdatedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUnregisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUpdateRegisteredActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.interfaces.ActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.utils.RefreshParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService</code>
 * implements the basic functionality of an actor network service component and define its behavior.<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public abstract class AbstractActorNetworkService extends AbstractNetworkService implements ActorNetworkService {

    /**
     * Represent the MAX_IMAGE_SIZE
     */
    public static final long MAX_IMAGE_SIZE = 10240;

    private ConcurrentHashMap<ActorProfile, RefreshParameters> registeredActors;

    private NetworkServiceActorLocationUpdaterAgent actorLocationUpdaterAgent;

    /**
     * Constructor with parameters
     *
     * @param pluginVersionReference
     * @param eventSource
     * @param networkServiceType
     */
    public AbstractActorNetworkService(final PluginVersionReference pluginVersionReference,
                                       final EventSource            eventSource           ,
                                       final NetworkServiceType     networkServiceType    ) {

        super(
                pluginVersionReference,
                eventSource,
                networkServiceType
        );

        registeredActors = new ConcurrentHashMap<>();
    }

    /**
     * Initializes all event listener and configure
     */
    private void initializeActorNetworkServiceListeners() {

        /*
         * 1. Listen and handle Network Client Actor Profile Registered Event
         */
        FermatEventListener actorRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED);
        actorRegistered.setEventHandler(new NetworkClientActorProfileRegisteredEventHandler(this));
        eventManager.addListener(actorRegistered);
        listenersAdded.add(actorRegistered);

        /*
         * 2. Listen and handle Network Client Actor Profile Updated Event
         */
        FermatEventListener actorUpdated = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_UPDATED);
        actorUpdated.setEventHandler(new NetworkClientActorProfileUpdatedEventHandler(this));
        eventManager.addListener(actorUpdated);
        listenersAdded.add(actorUpdated);

    }

    @Override
    public void registerActor(final String   publicKey      ,
                              final String   name           ,
                              final String   alias          ,
                              final String   extraData      ,
                              final Location location       ,
                              final Actors   type           ,
                              final byte[]   image          ,
                              final long     refreshInterval,
                              final long     accuracy       ) throws ActorAlreadyRegisteredException, CantRegisterActorException {

        System.out.println("******************* REGISTERING ACTOR: "+name+ " - type: "+type + "  ENTER METHOD");

        //validateImageSize(image.length); TODO COMMENTED UNTIL BETTER MANAGEMENT BE IMPLEMENTED

        ActorProfile actorToRegister = new ActorProfile();
        actorToRegister.setIdentityPublicKey(publicKey);

        if (registeredActors.get(actorToRegister) != null)
            throw new ActorAlreadyRegisteredException("publicKey: " + publicKey + " - name: " + name, "An actor is already registered with the given public key.");

        actorToRegister.setActorType(type.getCode());
        actorToRegister.setName(name);
        actorToRegister.setAlias(alias);
        actorToRegister.setPhoto(image);

        System.out.println("******************* REGISTERING ACTOR: " + name + " - type: " + type + "  ASK ABOUT LOCATION");

        if (location != null)
            actorToRegister.setLocation(location);
        else {
            try {

                Location location1 = locationManager.getLastKnownLocation();
                location1 = LocationUtils.getRandomLocation(location1, accuracy);
                actorToRegister.setLocation(location1);

            } catch (CantGetDeviceLocationException exception) {

                exception.printStackTrace();
            }
        }
        actorToRegister.setNsIdentityPublicKey(this.getPublicKey());
        actorToRegister.setExtraData(extraData);

        System.out.println("******************* REGISTERING ACTOR: " + name + " - type: " + type + "  LOCATION AND DATA SET");

        registeredActors.put(
                actorToRegister,
                new RefreshParameters(
                        1,
                        refreshInterval,
                        accuracy
                )
        );

        if (getConnection() != null ) {
            if(getConnection().isRegistered()) {
                try {
                    this.getConnection().registerProfile(actorToRegister);
                    registeredActors.get(actorToRegister).setLastExecution(System.currentTimeMillis());
                } catch (CantRegisterProfileException exception) {
                    throw new CantRegisterActorException(exception, "publicKey: " + actorToRegister.getIdentityPublicKey() + " - name: " + actorToRegister.getName(), "There was an error trying to register the actor through the network service.");
                }
            } else{
                System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  getConnection().isRegistered(): " +getConnection().isRegistered());
            }
        } else {
            System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  getConnection(): null");
        }

        System.out.println("******************* REGISTERING ACTOR: " + name + " - type: " + type + "  GO OUT METHOD");
    }

    public void registerActor(final ActorProfile actorToRegister,
                              final long         refreshInterval,
                              final long         accuracy       ) throws ActorAlreadyRegisteredException, CantRegisterActorException {

        System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  ENTER METHOD");

        //validateImageSize(image.length); TODO COMMENTED UNTIL BETTER MANAGEMENT BE IMPLEMENTED

        if (registeredActors.get(actorToRegister) != null)
            throw new ActorAlreadyRegisteredException("publicKey: " + actorToRegister.getIdentityPublicKey() + " - name: " + actorToRegister.getName(), "An actor is already registered with the given public key.");

        registeredActors.put(
                actorToRegister,
                new RefreshParameters(
                        1,
                        refreshInterval,
                        accuracy
                )
        );

        if (this.getConnection() != null && this.getConnection().isRegistered()) {

            try {

                this.getConnection().registerProfile(actorToRegister);
                registeredActors.get(actorToRegister).setLastExecution(System.currentTimeMillis());

            } catch (CantRegisterProfileException exception) {

                throw new CantRegisterActorException(exception, "publicKey: "+actorToRegister.getIdentityPublicKey()+" - name: "+actorToRegister.getName(), "There was an error trying to register the actor through the network service.");
            }
        }

        System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  GO OUT METHOD");
    }

    private ActorProfile getRegisteredActorByPublicKey(final String publicKey) {

        if (registeredActors != null)
            for (Map.Entry<ActorProfile, RefreshParameters> entry : registeredActors.entrySet())
                if (entry.getKey().getIdentityPublicKey().equals(publicKey))
                    return entry.getKey();

        return null;
    }

    @Override
    public void updateRegisteredActor(final String   publicKey,
                                      final String   name     ,
                                      final String   alias    ,
                                      final Location location ,
                                      final String   extraData,
                                      final byte[]   image    ) throws ActorNotRegisteredException, CantUpdateRegisteredActorException {

        //validateImageSize(image.length); TODO COMMENTED UNTIL BETTER MANAGEMENT BE IMPLEMENTED

        ActorProfile actorToUpdate = getRegisteredActorByPublicKey(publicKey);

        if (registeredActors == null || actorToUpdate == null)
            throw new ActorNotRegisteredException("publicKey: " + publicKey + " - name: " + name, "The actor we're trying to update is not registered.");

        if (location != null)
            actorToUpdate.setLocation(location);

        if (name != null)
            actorToUpdate.setName(name);

        if (alias != null)
            actorToUpdate.setAlias(alias);

        if (image.length > 0)
            actorToUpdate.setPhoto(image);

        if (extraData != null)
            actorToUpdate.setExtraData(extraData);

        if (this.getConnection() != null && this.getConnection().isRegistered()) {

            try {

                this.getConnection().updateRegisteredProfile(actorToUpdate, UpdateTypes.FULL);
                registeredActors.get(actorToUpdate).setLastExecution(System.currentTimeMillis());

            } catch (CantUpdateRegisteredProfileException exception) {

                throw new CantUpdateRegisteredActorException(exception, "publicKey: "+publicKey+" - name: "+name, "There was an error trying to update a registered actor through the network service.");
            }
        }
    }

    public void updateRegisteredActor(final ActorProfile actorToUpdate,
                                      final UpdateTypes  type         ) throws CantUpdateRegisteredActorException {

        if (this.getConnection() != null && this.getConnection().isRegistered()) {

            try {

                this.getConnection().updateRegisteredProfile(actorToUpdate, type);
                registeredActors.get(actorToUpdate).setLastExecution(System.currentTimeMillis());

            } catch (CantUpdateRegisteredProfileException exception) {

                throw new CantUpdateRegisteredActorException(exception, "publicKey: "+actorToUpdate.getIdentityPublicKey()+" - name: "+actorToUpdate.getName(), "There was an error trying to update a registered actor through the network service.");
            }
        }
    }

    @Override
    public void unregisterActor(String publicKey) throws ActorNotRegisteredException, CantUnregisterActorException {

        ActorProfile actorToUnregister = getRegisteredActorByPublicKey(publicKey);
        if (actorToUnregister == null)
            throw new ActorNotRegisteredException("publicKey: "+publicKey, "The actor we're trying to update is not registered.");

        registeredActors.remove(actorToUnregister);

        if (this.getConnection() != null && this.getConnection().isRegistered()) {

            try {

                this.getConnection().unregisterProfile(actorToUnregister);

            } catch (CantUnregisterProfileException  exception) {

                throw new CantUnregisterActorException(exception, "publicKey: "+publicKey, "There was an error trying to update a registered actor through the network service.");
            }
        }
    }

    @Override
    public boolean isActorOnline(final String publicKey) {

        if (this.getConnection() != null)
            return this.getConnection().isActorOnline(publicKey);
        else
            return false;
    }

    protected final void onNetworkServiceRegistered() {

        if (registeredActors != null) {

            if (this.getConnection() != null && this.getConnection().isRegistered()) {

                for (Map.Entry<ActorProfile, RefreshParameters> actorToRegister : registeredActors.entrySet()) {

                    try {

                        Location location = LocationUtils.getRandomLocation(actorToRegister.getKey().getLocation(), actorToRegister.getValue().getAccuracy());

                        actorToRegister.getKey().setLocation(location);

                        this.getConnection().registerProfile(actorToRegister.getKey());

                    } catch (CantRegisterProfileException exception) {

                        CantRegisterActorException cantRegisterActorException = new CantRegisterActorException(exception, "publicKey: "+actorToRegister.getKey().getIdentityPublicKey()+" - name: "+actorToRegister.getKey().getName(), "There was an error trying to register the actor through the network service.");

                        this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRegisterActorException);
                    }
                }
            }
        }

        try {
            actorLocationUpdaterAgent.start();
        } catch (Exception exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception,"Plugin: "+pluginVersionReference.toString3());
        }

        onActorNetworkServiceRegistered();
    }

    @Override
    public void startConnection() throws CantRegisterProfileException {
        getConnection().registerProfile(getProfile());

    }

    public final void onActorRegistered(String publicKey) {

        ActorProfile registeredActor = (registeredActors != null ? getRegisteredActorByPublicKey(publicKey) : null);

        if (registeredActor != null)
            onActorRegistered(registeredActor);
    }

    public final void onActorUpdated(String publicKey) {

        ActorProfile registeredActor = (registeredActors != null ? getRegisteredActorByPublicKey(publicKey) : null);

        if (registeredActor != null)
            onActorUpdated(registeredActor);
    }

    protected void onActorUpdated(ActorProfile actorProfile) {

        System.out.println("im updated : "+ actorProfile);
    }

    protected void onActorRegistered(ActorProfile actorProfile) {

        System.out.println("im registered : "+ actorProfile);
    }

    protected final void onNetworkServiceStart() throws CantStartPluginException {

        try {

            /*
             * Initialize listeners
             */
            initializeActorNetworkServiceListeners();

            actorLocationUpdaterAgent = new NetworkServiceActorLocationUpdaterAgent(this);

            onActorNetworkServiceStart();

        } catch (Exception exception) {

            System.out.println(exception.toString());

            String context = "Plugin ID: " + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "NS Name: " + this.getProfile().getNetworkServiceType();

            String possibleCause = "The Template triggered an unexpected problem that wasn't able to solve by itself - ";
            possibleCause += exception.getMessage();
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }
    }

    /**
     * Validate the image size of the profile
     *
     * @param byteArraySize
     */
    private void validateImageSize(long byteArraySize){
        if (byteArraySize > MAX_IMAGE_SIZE){
            throw new IllegalArgumentException("The current image size ("+ ImageUtil.bytesIntoHumanReadable(byteArraySize)+ ") exceeds the allowable limit size ("+ ImageUtil.bytesIntoHumanReadable(MAX_IMAGE_SIZE)+ ")");
        }
    }

    protected final void onNetworkClientConnectionLost() {

        if (registeredActors != null)
            for (Map.Entry<ActorProfile, RefreshParameters> actorToRegister : registeredActors.entrySet())
                actorToRegister.getValue().setLastExecution(1);

        try {
            actorLocationUpdaterAgent.stop();
        } catch (Exception exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        onActorNetworkServiceConnectionLost();
    }

    protected final void onNetworkClientConnectionClosed() {

        if (registeredActors != null)
            for (Map.Entry<ActorProfile, RefreshParameters> actorToRegister : registeredActors.entrySet())
                actorToRegister.getValue().setLastExecution(1);

        try {
            actorLocationUpdaterAgent.stop();
        } catch (Exception exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        onActorNetworkServiceConnectionClosed();
    }

    protected void onActorNetworkServiceConnectionLost() {

    }

    protected void onActorNetworkServiceConnectionClosed() {

    }

    protected void onActorNetworkServiceStart() throws CantStartPluginException {

    }

    protected void onActorNetworkServiceRegistered() {

    }

    public ConcurrentHashMap<ActorProfile, RefreshParameters> getRegisteredActors() {

        return registeredActors;
    }
}