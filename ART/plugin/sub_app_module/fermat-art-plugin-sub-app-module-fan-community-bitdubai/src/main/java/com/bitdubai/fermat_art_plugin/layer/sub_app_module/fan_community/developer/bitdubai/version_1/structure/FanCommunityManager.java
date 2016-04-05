package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanListException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListFansException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantLoginFanException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantStartRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.settings.FanCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class FanCommunityManager implements FanCommunityModuleManager {
    private final ErrorManager errorManager;
    private final FanActorConnectionManager fanActorConnectionManager;

    public FanCommunityManager(ErrorManager errorManager,
                               FanActorConnectionManager fanActorConnectionManager) {
        this.errorManager = errorManager;
        this.fanActorConnectionManager = fanActorConnectionManager;
    }

    @Override
    public List<FanCommunityInformation> listWorldFan(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListFansException {
        return null;
    }

    @Override
    public List<FanCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        return null;
    }

    @Override
    public void setSelectedActorIdentity(FanCommunitySelectableIdentity identity) {

    }

    @Override
    public List<LinkedFanIdentity> listFansPendingLocalAction(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public List<FanCommunityInformation> listAllConnectedFans(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public void acceptFan(UUID connectionId) throws CantAcceptRequestException {

    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException {

    }

    @Override
    public List<FanCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public FanCommunitySearch getCryptoCustomerSearch() {
        return null;
    }

    @Override
    public void askFanForAcceptance(String fanToAddName, String fanToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

    }

    @Override
    public void disconnectFan(UUID requestId) throws FanDisconnectingFailedException {

    }

    @Override
    public void cancelFan(String fanToCancelPublicKey) throws FanCancellingFailedException {

    }

    @Override
    public List<FanCommunityInformation> getAllCryptoCustomers(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public List<FanCommunityInformation> getFansWaitingYourAcceptance(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public List<FanCommunityInformation> getFansWaitingTheirAcceptance(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public void login(String fanPublicKey) throws CantLoginFanException {

    }

    @Override
    public SettingsManager<FanCommunitySettings> getSettingsManager() {
        return null;
    }

    @Override
    public FanCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    /*@Override
    public FanActorConnectionSearch getSearch(FanLinkedActorIdentity actorIdentitySearching) {
        return fanActorConnectionManager.getSearch(actorIdentitySearching);
    }

    @Override
    public void requestConnection(ActorIdentityInformation actorSending, ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {
        fanActorConnectionManager.requestConnection(actorSending,actorReceiving);
    }

    @Override
    public void disconnect(UUID connectionId) throws CantDisconnectFromActorException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        fanActorConnectionManager.disconnect(connectionId);
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        fanActorConnectionManager.denyConnection(connectionId);
    }

    @Override
    public void cancelConnection(UUID connectionId) throws CantCancelActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        fanActorConnectionManager.cancelConnection(connectionId);
    }

    @Override
    public void acceptConnection(UUID connectionId) throws CantAcceptActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        fanActorConnectionManager.acceptConnection(connectionId);
    }

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }*/
}
