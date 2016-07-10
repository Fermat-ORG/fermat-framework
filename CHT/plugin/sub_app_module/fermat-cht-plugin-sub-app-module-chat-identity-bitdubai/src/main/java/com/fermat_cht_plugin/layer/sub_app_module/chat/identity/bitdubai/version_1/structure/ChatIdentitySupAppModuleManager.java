package com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantCreateNewChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 03/04/16.
 */
public class ChatIdentitySupAppModuleManager extends ModuleManagerImpl<ChatIdentityPreferenceSettings> implements ChatIdentityModuleManager, Serializable {

    private ChatIdentityManager chatIdentityManager;
    private final LocationManager locationManager                       ;

    public ChatIdentitySupAppModuleManager(ChatIdentityManager chatIdentityManager,
                                           PluginFileSystem pluginFileSystem,
                                           UUID pluginId,
                                           LocationManager locationManager){

        super(pluginFileSystem, pluginId);
        this.chatIdentityManager = chatIdentityManager;
        this.locationManager = locationManager;

    }
    /**
     * The method <code>getIdentityAssetUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of Chat users associated to the current logged in Device User.
     * @throws CantListChatIdentityException if something goes wrong.
     */
    @Override
    public List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        return chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
    }

    @Override
    public ChatIdentity getIdentityChatUser() throws CantGetChatIdentityException {
        return chatIdentityManager.getIdentityChatUser();
    }

    @Override
    public void createNewIdentityChat(String alias, byte[] profileImage, String country, String state, String city, String connectionState, long accurancy, GeoFrequency frecuency) throws CantCreateNewChatIdentityException {
        chatIdentityManager.createNewIdentityChat(alias, profileImage, country, state, city, connectionState, accurancy, frecuency);
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
    public void updateIdentityChat(String identityPublicKey, String identityAlias, byte[] profileImage, String country, String state, String city, String connectionState, long accurancy, GeoFrequency frecuency) throws CantUpdateChatIdentityException {
        chatIdentityManager.updateIdentityChat(identityPublicKey, identityAlias, profileImage, country, state, city, connectionState, accurancy, frecuency);
    }

    /**
     * Through the method <code>getLocation</code> we can get the location coordinates of user.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }

    /**
     * Through the method <code>getSelectedActorIdentity</code> we can get the selected actor identity.
     *
     * @return an instance of the selected actor identity.
     * @throws CantGetSelectedActorIdentityException if something goes wrong.
     * @throws ActorIdentityNotSelectedException     if there's no actor identity selected.
     */
    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
//        try {
//            List<ChatIdentity> identities = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
//            return (identities == null || identities.isEmpty()) ? null : chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser().get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * Create identity
     *
     * @param name
     * @param phrase
     * @param profile_img
     */
    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        chatIdentityManager.createNewIdentityChat(name, profile_img, null, null, null, "available", 0, GeoFrequency.NONE);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


}
