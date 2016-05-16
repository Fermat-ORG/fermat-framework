package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanListException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListFansException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantLoginFanException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.settings.FanCommunitySettings;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public interface FanCommunityModuleManager extends
        ModuleManager<
                FanCommunitySettings,
                FanCommunitySelectableIdentity>, ModuleSettingsImpl<FanCommunitySettings> {

    /**
     * The method <code>listWorldFanatics</code> returns the list of all fans in the world,
     * setting their status (CONNECTED, for example) with respect to the selectedIdentity parameter
     * logged in artist
     * @return a list of all fanatics in the world
     * @throws CantListFansException if something goes wrong.
     */
    List<FanCommunityInformation> listWorldFan(
            FanCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantListFansException;

    /**
     * The method <code>listSelectableIdentities</code> returns the list of all local artist
     * identities on the device
     * @return a list of all local artist identities on device
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<FanCommunitySelectableIdentity> listSelectableIdentities() throws
            CantListIdentitiesToSelectException;

    /**
     * The method <code>setSelectedActorIdentity</code> saves an identity as default
     */
    void setSelectedActorIdentity(FanCommunitySelectableIdentity identity);

    /**
     * The method <code>listFanPendingLocalAction</code> returns the list of fans waiting
     * to be accepted or rejected by the logged user
     * @return the list of fans waiting to be accepted or rejected by the logged in user.
     * @throws CantGetFanListException if something goes wrong.
     */
    List<LinkedFanIdentity> listFansPendingLocalAction(
            final FanCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantGetFanListException;

    /**
     * The method <code>listFanPendingLocalAction</code> returns the list of fans waiting
     * to be accepted or rejected by the logged user
     * @return the list of fans waiting to be accepted or rejected by the logged in user.
     * @throws CantGetFanListException if something goes wrong.
     */
    List<LinkedFanIdentity> listFansPendingRemoteAction(
            final FanCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantGetFanListException;
    /**
     * The method <code>listAllConnectedFans</code> returns the list of all fan
     * registered by the logged in user
     * @return the list of fans connected to the logged in user
     * @throws CantGetFanListException if something goes wrong.
     */
    List<ArtCommunityInformation> listAllConnectedFans(
            final FanCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws
            CantGetFanListException;

    /**
     * The method <code>acceptFan</code> takes the information of a connection request, accepts
     * the request and adds the fan to the list managed by this plugin with ContactState CONTACT.
     * @param connectionId      The id of the connection
     * @throws CantAcceptRequestException
     */
    void acceptFan(UUID connectionId) throws CantAcceptRequestException;

    /**
     * The method <code>denyConnection</code> denies a connection request from other fanatic
     *
     * @param connectionId the connection id of the user to deny its connection request
     * @throws CantDenyActorConnectionRequestException
     */
    void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException;

    /**
     * The method <code>listFanAvailablesToContact</code> searches for fan that the logged in
     * fan could be interested to add.     *
     * @return a list with information of fanatic
     * @throws CantGetFanListException
     */
    List<FanCommunityInformation> getSuggestionsToContact(
            int max,
            int offset) throws CantGetFanListException;

    /**
     * The method <code>searchFanatic</code> gives us an interface to manage a search for a particular
     * fanatic
     *
     * @return a searching interface
     */
    FanCommunitySearch getFanaticSearch();

    /**
     * The method <code>requestConnectionToArtist</code> initialises a contact request between
     * two Artists.
     *
     * @param selectedIdentity       The selected local artist identity.
     * @param artistToContact  The information of the remote artist to connect to.
     *
     * @throws CantRequestConnectionException           if something goes wrong.
     * @throws ActorConnectionAlreadyRequestedException if the connection already exists.
     * @throws ActorTypeNotSupportedException           if the actor type is not supported.
     */
    void requestConnectionToFan(
            FanCommunitySelectableIdentity selectedIdentity,
            FanCommunityInformation artistToContact) throws
            CantRequestConnectionException,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException;

    /**
     * The method <code>disconnectFan</code> disconnect a fan from the list managed by this
     * plugin
     *
     * @throws FanDisconnectingFailedException
     */
    void disconnectFan(final UUID requestId) throws FanDisconnectingFailedException;

    /**
     * The method <code>cancelArtist</code> cancels an artist from the list managed by this
     * @param fanToCancelPublicKey
     * @throws FanCancellingFailedException
     */
    void cancelFan(String fanToCancelPublicKey) throws FanCancellingFailedException;

    /**
     * The method <code>getAllFans</code> returns the list of all fan registered by the
     * logged in fan
     *
     * @return the list of fan connected to the logged in artist
     * @throws FanCancellingFailedException
     */
    List<FanCommunityInformation> getAllFanatics(
            int max,
            int offset) throws CantGetFanListException;

    /**
     * The method <code>getFanWaitingYourAcceptance</code> returns the list of fanatic
     * waiting to be accepted
     * or rejected by the logged in Fan
     * @return the list of fan waiting to be accepted or rejected by the  logged in Fan
     * @throws CantGetFanListException
     */
    List<FanCommunityInformation> getFansWaitingYourAcceptance(
            int max,
            int offset) throws CantGetFanListException;

    /**
     * The method <code>getFansWaitingTheirAcceptance</code> list the fanatic that haven't
     * answered to a sent connection request by the current logged in fan.
     * @return the list of fan that haven't answered to a sent connection request by the current
     * logged in fan.
     * @throws CantGetFanListException
     */
    List<FanCommunityInformation> getFansWaitingTheirAcceptance(
            int max,
            int offset) throws CantGetFanListException;


    /**
     * The method <code>login</code> let an fan log in     *
     * @param fanPublicKey the public key of the fanatic to log in
     */
    void login(String fanPublicKey) throws CantLoginFanException;

    /**
     * This method checks if an actor connection exists.
     * @param linkedIdentityPublicKey
     * @param linkedIdentityActorType
     * @param actorPublicKey
     * @return
     * @throws CantGetActorConnectionException
     */
    List<FanActorConnection> getRequestActorConnections(
            String linkedIdentityPublicKey,
            Actors linkedIdentityActorType,
            String actorPublicKey) throws CantGetActorConnectionException;

    @Override
    SettingsManager<FanCommunitySettings> getSettingsManager();

    @Override
    FanCommunitySelectableIdentity getSelectedActorIdentity() throws
            CantGetSelectedActorIdentityException,
            ActorIdentityNotSelectedException;


    @Override
    void createIdentity(String name, String phrase, byte[] profile_img) throws Exception;


    @Override
    void setAppPublicKey(String publicKey);

    @Override
    int[] getMenuNotifications();
}