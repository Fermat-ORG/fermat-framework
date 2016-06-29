package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatActorWaitingException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatIdentitiesToSelectException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 15/06/16.
 */
public interface ChatActorCommunitySubAppModuleManager extends ModuleManager, Serializable, ModuleSettingsImpl<ChatActorCommunitySettings> {

    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND)
    List<ChatActorCommunityInformation> listWorldChatActor(String PublicKey, Actors actorType, DeviceLocation deviceLocation, double distance, String alias, int max, int offset) throws CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException;

    List<ChatActorCommunitySelectableIdentity> listSelectableIdentities() throws CantListChatIdentitiesToSelectException, CantListChatIdentityException;

    void setSelectedActorIdentity(ChatActorCommunitySelectableIdentity identity) throws CantPersistSettingsException, CantGetSettingsException, SettingsNotFoundException;

    ChatActorCommunitySearch getChatActorSearch();

    ChatActorCommunitySearch searchConnectedChatActor(ChatActorCommunitySelectableIdentity selectedIdentity);

    void requestConnectionToChatActor(ChatActorCommunitySelectableIdentity selectedIdentity     ,
                                         ChatActorCommunityInformation chatActorLocalToContact) throws CantRequestActorConnectionException,
            ActorChatTypeNotSupportedException,
            ActorChatConnectionAlreadyRequestesException, ConnectionAlreadyRequestedException;

    void acceptChatActor(UUID requestId) throws CantAcceptChatRequestException, ActorConnectionRequestNotFoundException;

    void denyChatConnection(UUID requestId) throws ChatActorConnectionDenialFailedException, ActorConnectionRequestNotFoundException;

    void disconnectChatActor(UUID requestId) throws ChatActorDisconnectingFailedException, ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException, CantDisconnectFromActorException, UnexpectedConnectionStateException, ActorConnectionNotFoundException;

    void cancelChatActor(UUID requestId) throws ChatActorCancellingFailedException, ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException;

    List<ChatActorCommunityInformation> listAllConnectedChatActor(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                         final int                                     max             ,
                                                                         final int                                     offset          ) throws CantListChatActorException;

    List<ChatActorCommunityInformation> listChatActorPendingLocalAction    (final String publicKey, final Actors actorType,                                                                              final int max,
                                                                               final int offset) throws CantListChatActorException;




    List<ChatActorCommunityInformation> listChatActorPendingRemoteAction(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                                final int max,
                                                                                final int offset) throws CantListChatActorException;

    List<ChatActorCommunityInformation> getChatActorWaitingYourAcceptanceCount(String publicKey, int max, int offset) throws CantGetChatActorWaitingException;


    ConnectionState getActorConnectionState(String publicKey) throws CantValidateActorConnectionStateException;

    HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException;

    List<CountryDependency> getCountryDependencies(String countryCode)
            throws CantGetCountryDependenciesListException,
            CantConnectWithExternalAPIException,
            CantCreateBackupFileException;

    List<City> getCitiesByCountryCode(String countryCode)
            throws CantGetCitiesListException;

    List<City> getCitiesByCountryCodeAndDependencyName(
            String countryName,
            String dependencyName)
            throws CantGetCitiesListException,
            CantCreateCountriesListException;

    GeoRectangle getGeoRectangleByLocation(String location)
            throws CantCreateGeoRectangleException;

    Address getAddressByCoordinate(double latitude, double longitude)
            throws CantCreateAddressException;

    GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException;

    Location getLocation() throws CantGetDeviceLocationException;

    List<ExtendedCity> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException;

    @Override
    ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;

    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();


}
