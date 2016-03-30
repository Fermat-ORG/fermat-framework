package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/03/16.
 */
public class ChatIdentityManagerImpl implements ChatIdentityManager {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private ErrorManager errorManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public ChatIdentityManagerImpl(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId,
                                                   final ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.errorManager         = errorManager        ;
    }
    /**
     * The method <code>getIdentityAssetUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of Chat users associated to the current logged in Device User.
     * @throws CantListChatIdentityException if something goes wrong.
     */
    @Override
    public List<ChatIdentity> getIdentityAssetUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        return null;
    }

    /**
     * The method <code>getIdentityAssetIssuer</code> will give Identity Asset Issuer associated
     *
     * @return Identity Chat associated.
     * @throws CantGetChatIdentityException if something goes wrong.
     */
    @Override
    public ChatIdentity getIdentityAssetUser() throws CantGetChatIdentityException {
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

    }
}
