package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.ImageUtil;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.utils.LocationUtils;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestActorFullPhotoException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantUpdateRegisteredProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUnregisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantUpdateRegisteredActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.interfaces.ActorNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.utils.RefreshParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    private HashMap<UUID, ActorProfile> registeredActorsByPackageId;

    /**
     * Constructor with parameters
     *
     * @param pluginVersionReference
     * @param eventSource
     * @param networkServiceType
     */
    public AbstractActorNetworkService(final PluginVersionReference pluginVersionReference,
                                       final EventSource eventSource,
                                       final NetworkServiceType networkServiceType) {

        super(
                pluginVersionReference,
                eventSource,
                networkServiceType
        );

        registeredActors = new ConcurrentHashMap<>();
        registeredActorsByPackageId = new HashMap<>();
    }

    /**
     * Initializes all event listener and configure
     */
   // private void initializeActorNetworkServiceListeners() {

        /*
         * 1. Listen and handle Network Client Actor Profile Registered Event
         */
     /*   FermatEventListener actorRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_REGISTERED);
        actorRegistered.setEventHandler(new NetworkClientActorProfileRegisteredEventHandler(this));
        eventManager.addListener(actorRegistered);
        listenersAdded.add(actorRegistered);
*/
        /*
         * 2. Listen and handle Network Client Actor Profile Updated Event
         */
  /*      FermatEventListener actorUpdated = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_PROFILE_UPDATED);
        actorUpdated.setEventHandler(new NetworkClientActorProfileUpdatedEventHandler(this));
        eventManager.addListener(actorUpdated);
        listenersAdded.add(actorUpdated);

    }*/

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

            try {
                UUID packageId = this.getConnection().register(actorToRegister, getProfile());
                if (packageId != null)
                    registeredActorsByPackageId.put(packageId, actorToRegister);
            } catch (CantRegisterProfileException | CantSendMessageException e) {
                throw new CantRegisterActorException(e.getCause(), e.getContext(), e.getPossibleReason());
            }
            registeredActors.get(actorToRegister).setLastExecution(System.currentTimeMillis());

        } else {
            System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  getConnection(): null");
        }

        System.out.println("******************* REGISTERING ACTOR: " + name + " - type: " + type + "  GO OUT METHOD");
    }

    public void registerActor(final ActorProfile actorToRegister,
                              final long         refreshInterval,
                              final long         accuracy       ) throws ActorAlreadyRegisteredException, CantRegisterActorException {


        if (actorToRegister != null){

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

            try {
                UUID packageId = this.getConnection().register(actorToRegister, getProfile());
                if (packageId != null)
                    registeredActorsByPackageId.put(packageId, actorToRegister);
            } catch (CantRegisterProfileException | CantSendMessageException e) {
                throw new CantRegisterActorException(e.getCause(), e.getContext(), e.getPossibleReason());
            }
            registeredActors.get(actorToRegister).setLastExecution(System.currentTimeMillis());

            System.out.println("******************* REGISTERING ACTOR: " + actorToRegister.getName() + " - type: " + actorToRegister.getActorType() + "  GO OUT METHOD");

        }else {

            System.out.println("******************* REGISTERING ACTOR IS NULL ");

        }

       }

    private ActorProfile getRegisteredActorByPublicKey(final String publicKey) {

        if (registeredActors != null)
            for (Map.Entry<ActorProfile, RefreshParameters> entry : registeredActors.entrySet())
                if (entry.getKey().getIdentityPublicKey().equals(publicKey))
                    return entry.getKey();

        return null;
    }

    private ActorProfile getRegisteredActorByPackageId(final UUID packageId) {

        if (registeredActorsByPackageId != null)
            return registeredActorsByPackageId.get(packageId);

        return null;
    }

    @Override
    public void updateRegisteredActor(final String   publicKey,
                                      final String   name     ,
                                      final String   alias    ,
                                      final Location location ,
                                      final String   extraData,
                                      final byte[]   image    ) throws ActorNotRegisteredException, CantUpdateRegisteredActorException, CantUpdateRegisteredProfileException {

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

        this.getConnection().update(actorToUpdate, UpdateTypes.FULL, getNetworkServiceType());
        registeredActors.get(actorToUpdate).setLastExecution(System.currentTimeMillis());
    }

    public void updateRegisteredActor(final ActorProfile actorToUpdate,
                                      final UpdateTypes  type         ) throws CantUpdateRegisteredActorException, CantUpdateRegisteredProfileException {

        this.getConnection().update(actorToUpdate, type,getNetworkServiceType());
        registeredActors.get(actorToUpdate).setLastExecution(System.currentTimeMillis());
    }

    @Override
    public void unregisterActor(String publicKey) throws ActorNotRegisteredException, CantUnregisterActorException {

        ActorProfile actorToUnregister = getRegisteredActorByPublicKey(publicKey);
        if (actorToUnregister == null)
            throw new ActorNotRegisteredException("publicKey: "+publicKey, "The actor we're trying to update is not registered.");

        registeredActors.remove(actorToUnregister);

    }

    @Override
    public String getActorFullPhoto(final String publicKey) throws CantRequestActorFullPhotoException {

        // TODO add full photo management
        /*if (this.getConnection() != null)
            return this.getConnection().getActorFullPhoto(publicKey);
        else*/
            return null;
    }

    protected final void onNetworkServiceRegistered() {

        System.out.println(" ESTOY PASANDO POR ACA!");

        if (registeredActors != null) {

            for (Map.Entry<ActorProfile, RefreshParameters> actorToRegister : registeredActors.entrySet()) {

                Location location = LocationUtils.getRandomLocation(actorToRegister.getKey().getLocation(), actorToRegister.getValue().getAccuracy());

                actorToRegister.getKey().setLocation(location);

                try {

                    UUID packageId = this.getConnection().register(actorToRegister.getKey(), getProfile());
                    if (packageId != null)
                        registeredActorsByPackageId.put(packageId, actorToRegister.getKey());

                } catch (CantRegisterProfileException | CantSendMessageException e) {
                    e.printStackTrace();
                }
            }
        }

        onActorNetworkServiceRegistered();
    }

    public final void onActorRegistered(UUID packageId, String publicKey) {

        ActorProfile registeredActor = (registeredActors != null ? getRegisteredActorByPackageId(packageId) : null);

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

        onActorNetworkServiceConnectionLost();
    }

    protected final void onNetworkClientConnectionClosed() {

        if (registeredActors != null)
            for (Map.Entry<ActorProfile, RefreshParameters> actorToRegister : registeredActors.entrySet())
                actorToRegister.getValue().setLastExecution(1);

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

    @Override
    public void handleProfileRegisteredSuccessfully(ProfileTypes types, UUID packageId, String profilePublicKey) {
        if (types.equals(ProfileTypes.ACTOR)){
            onActorRegistered(packageId,profilePublicKey);
        }else {
            super.handleProfileRegisteredSuccessfully(types, packageId, profilePublicKey);
        }
    }

    public ConcurrentHashMap<ActorProfile, RefreshParameters> getRegisteredActors() {

        return registeredActors;
    }


}