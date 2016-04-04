package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtistCommunityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class ArtistCommunityManager implements ArtistCommunityModuleManager {
    private final ErrorManager errorManager;
    private final ArtistActorConnectionManager artistActorConnectionManager;
    public ArtistCommunityManager(ErrorManager errorManager
                                  ,ArtistActorConnectionManager artistActorConnectionManager
    ) {
        this.errorManager = errorManager;
        this.artistActorConnectionManager = artistActorConnectionManager;
    }


    @Override
    public ActorConnectionSearch getSearch(ArtistLinkedActorIdentity actorIdentitySearching) {
        return artistActorConnectionManager.getSearch(actorIdentitySearching);
    }

    @Override
    public void requestConnection(ActorIdentityInformation actorSending, ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {
        artistActorConnectionManager.requestConnection(actorSending,actorReceiving);
    }

    @Override
    public void disconnect(UUID connectionId) throws CantDisconnectFromActorException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        artistActorConnectionManager.disconnect(connectionId);
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        artistActorConnectionManager.denyConnection(connectionId);
    }

    @Override
    public void cancelConnection(UUID connectionId) throws CantCancelActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        artistActorConnectionManager.cancelConnection(connectionId);
    }

    @Override
    public void acceptConnection(UUID connectionId) throws CantAcceptActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {
        artistActorConnectionManager.acceptConnection(connectionId);
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
    }
}
