package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database.ChatIdentityDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/03/16.
 */
public class ChatIdentityManagerImpl implements ChatIdentityManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;
    private PluginFileSystem pluginFileSystem;

    /**
     * Represents the DeviceUserManager
     */
    private DeviceUserManager deviceUserManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public ChatIdentityManagerImpl(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId,
                                                   final ErrorManager errorManager,
                                                   final DeviceUserManager deviceUserManager,
                                                   final PluginFileSystem pluginFileSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.errorManager         = errorManager        ;
        this.deviceUserManager    = deviceUserManager   ;
        this.pluginFileSystem     = pluginFileSystem    ;
    }

    private ChatIdentityDatabaseDao chatIdentityDao(){
        ChatIdentityDatabaseDao chatIdentityDatabaseDao = null;
        try {
            chatIdentityDatabaseDao = new ChatIdentityDatabaseDao(pluginDatabaseSystem, pluginId, pluginFileSystem);
        } catch (CantOpenDatabaseException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }catch (CantListIdentitiesException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
    public ChatIdentity createNewIdentityChat(String alias, byte[] profileImage) throws CantCreateNewChatIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            KeyPair keyPair = AsymmetricCryptography.generateECCKeyPair();
            chatIdentityDao().createNewUser(alias, keyPair.getPublicKey(), keyPair.getPrivateKey(), loggedUser, profileImage);
        } catch (CantCreateNewDeveloperException e) {
            e.printStackTrace();
        } catch (CantGetLoggedInDeviceUserException e) {
            e.printStackTrace();
        }
        return null;
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
    public void updateIdentityChat(String identityPublicKey, String identityAlias, byte[] profileImage) throws CantUpdateChatIdentityException {
        try {
            chatIdentityDao().updateChatIdentity(identityPublicKey, identityAlias, profileImage);
        } catch (com.bitdubai.fermat_cht_api.all_definition.exceptions.CantUpdateChatIdentityException e) {
            e.printStackTrace();
        }
    }
}
