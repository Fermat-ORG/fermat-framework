package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.ChatIdentityPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database.ChatIdentityDatabaseDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/03/16.
 * Edited by Miguel Rincon on 19/04/2016
 * Updated by Miguel Rincon on 19/04/2016
 */
public class ChatIdentityManagerImpl implements ChatIdentityManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private PluginFileSystem pluginFileSystem;
    private ChatManager chatManager;
    private boolean isIdentityNew = true;
    private ChatIdentityPluginRoot chatIdentityPluginRoot;
    LocationManager locationManager;

    /**
     * Represents the DeviceUserManager
     */
    private DeviceUserManager deviceUserManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginId             of this module.
     */
    public ChatIdentityManagerImpl(final PluginDatabaseSystem pluginDatabaseSystem,
                                   final UUID pluginId,
                                   final ChatIdentityPluginRoot chatIdentityPluginRoot,
                                   final DeviceUserManager deviceUserManager,
                                   final PluginFileSystem pluginFileSystem,
                                   final ChatManager chatManager,
                                   final LocationManager locationManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.chatIdentityPluginRoot = chatIdentityPluginRoot;
        this.deviceUserManager = deviceUserManager;
        this.pluginFileSystem = pluginFileSystem;
        this.chatManager = chatManager;
        this.locationManager = locationManager;
    }

    private ChatIdentityDatabaseDao chatIdentityDao() {
        ChatIdentityDatabaseDao chatIdentityDatabaseDao = null;
        try {
            chatIdentityDatabaseDao = new ChatIdentityDatabaseDao(pluginDatabaseSystem, pluginId, pluginFileSystem);
        } catch (CantOpenDatabaseException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return chatIdentityDatabaseDao;
    }

    /**
     * The method <code>getIdentityAssetUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of Chat users associated to the current logged in Device User.
     * @throws CantListChatIdentityException if something goes wrong.
     */
    @Override
    public List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        DeviceUser loggedUser = null;
        try {
            loggedUser = deviceUserManager.getLoggedInDeviceUser();
            return chatIdentityDao().getChatIdentitiesFromCurrentDeviceUser(loggedUser);
        } catch (CantGetLoggedInDeviceUserException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantListIdentitiesException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return null;
    }

    /**
     * The method <code>getIdentityAssetIssuer</code> will give Identity Asset Issuer associated
     *
     * @return Identity Chat associated.
     * @throws CantGetChatIdentityException if something goes wrong.
     */
    @Override
    public ChatIdentity getIdentityChatUser() throws CantGetChatIdentityException {
        try {
            return chatIdentityDao().getChatIdentity();
        } catch (CantGetChatUserIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return null;
    }

    /**
     * The method <code>createNewIdentityChat</code> creates a new intra wallet user Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     * @return the intra user created
     * @throws CantCreateNewChatIdentityException if something goes wrong.
     */
    @Override
    public void createNewIdentityChat(String alias, byte[] profileImage, String country, String state, String city, String connectionState, long accuracy, GeoFrequency frecuency) throws CantCreateNewChatIdentityException {
        try {
            Location location = locationManager.getLocation();
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = AsymmetricCryptography.generateECCKeyPair();
            chatIdentityDao().createNewUser(alias, keyPair.getPublicKey(), keyPair.getPrivateKey(), loggedUser, profileImage, country, state, city, connectionState, accuracy, frecuency);
            registerIdentitiesANS(keyPair.getPublicKey(), true, location);
        } catch (CantCreateNewDeveloperException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantGetLoggedInDeviceUserException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (IdentityNotFoundException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantPublishIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantGetDeviceLocationException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    /**
     * The method <code>updateIdentityChat</code> change a identity information data
     *
     * @param identityPublicKey
     * @param identityAlias
     * @param profileImage
     * @throws CantUpdateChatIdentityException
     */
    @Override
    public void updateIdentityChat(String identityPublicKey, String identityAlias, byte[] profileImage, String country, String state, String city, String connectionState, long accuracy, GeoFrequency frecuency) throws CantUpdateChatIdentityException {
        try {
            chatIdentityDao().updateChatIdentity(identityPublicKey, identityAlias, profileImage, country, state, city, connectionState, accuracy, frecuency);
            Location location = locationManager.getLocation();
            registerIdentitiesANS(identityPublicKey, false, location);
        } catch (com.bitdubai.fermat_cht_api.all_definition.exceptions.CantUpdateChatIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (IdentityNotFoundException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantPublishIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (CantGetDeviceLocationException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    /**
     * The method <code>publishIdentity</code> is used to publish a Chat identity.
     *
     * @param publicKey
     * @throws CantPublishIdentityException
     * @throws IdentityNotFoundException
     */
    @Override
    public void publishIdentity(String publicKey, Location location) throws CantPublishIdentityException, IdentityNotFoundException {
        registerIdentitiesANS(publicKey, true, location);
    }

    private void registerIdentitiesANS(String publicKey, boolean isIdentityNew, Location location) throws CantPublishIdentityException, IdentityNotFoundException {
        try {
            ChatIdentity chatIdentity = chatIdentityDao().getChatIdentity();
            long refreshInterval = 0;
            refreshInterval = chatIdentity.getFrecuency().getRefreshInterval();
            final ChatExposingData chatExposingData = new ChatExposingData(chatIdentity.getPublicKey(), chatIdentity.getAlias(),
                    chatIdentity.getImage(), chatIdentity.getCountry(), chatIdentity.getState(), chatIdentity.getCity(),
                    chatIdentity.getConnectionState(), location, refreshInterval, chatIdentity.getAccuracy(),
                    ProfileStatus.UNKNOWN);
            chatIdentityDao().changeExposureLevel(chatIdentity.getPublicKey(), ExposureLevel.PUBLISH);

            if (isIdentityNew) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            chatManager.exposeIdentity(chatExposingData);
                        } catch (CantExposeIdentityException e) {
                            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
                        }
                    }
                }.start();
            } else {
                //TODO:Al actualizar la identidad falla la comunidad revisar
                try {
                    chatManager.updateIdentity(chatExposingData);
                } catch (CantExposeIdentityException e) {
                    chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
                }
            }
//            if(chatExposingData.getAlias().contains("*on"))
//            registerIdentitiesANSTest(chatExposingData);

        } catch (CantGetChatUserIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (com.bitdubai.fermat_cht_api.all_definition.exceptions.CantUpdateChatIdentityException e) {
            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    public void registerIdentitiesANSTest(ChatExposingData chatExposingDataSingle) throws CantPublishIdentityException, IdentityNotFoundException {
//        try {
        long refreshInterval = 0;
//            refreshInterval = chatIdentity.getFrecuency().getRefreshInterval();

        for (int f = 0; f < 100; f++) {

            final ChatExposingData chatExposingData = new ChatExposingData(UUID.randomUUID().toString(), chatExposingDataSingle.getAlias()+ " " + (f + 1), chatExposingDataSingle.getImage(), "", "", "",
                    "", null, refreshInterval, 0,
                    ProfileStatus.UNKNOWN);

//            chatIdentityDao().changeExposureLevel(chatIdentity.getPublicKey(), ExposureLevel.PUBLISH);

            new Thread() {
                @Override
                public void run() {
                    try {
                        chatManager.exposeIdentity(chatExposingData);
                    } catch (CantExposeIdentityException e) {
                        chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
                    }
                }
            }.start();


//        } catch (CantGetChatUserIdentityException e) {
//            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
//        } catch (com.bitdubai.fermat_cht_api.all_definition.exceptions.CantUpdateChatIdentityException e) {
//            chatIdentityPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
//        }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
