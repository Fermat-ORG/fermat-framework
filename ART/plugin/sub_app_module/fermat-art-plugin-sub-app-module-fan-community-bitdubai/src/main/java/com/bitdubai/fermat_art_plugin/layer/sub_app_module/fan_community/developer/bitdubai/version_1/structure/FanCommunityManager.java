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
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.FanCommunityModuleManager;

import java.util.UUID;
import java.util.logging.ErrorManager;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class FanCommunityManager implements FanCommunityModuleManager {
    private final ErrorManager errorManager;

    public FanCommunityManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public FanActorConnectionSearch getSearch(FanLinkedActorIdentity actorIdentitySearching) {
        return null;
    }

    @Override
    public void requestConnection(ActorIdentityInformation actorSending, ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {

    }

    @Override
    public void disconnect(UUID connectionId) throws CantDisconnectFromActorException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

    }

    @Override
    public void cancelConnection(UUID connectionId) throws CantCancelActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

    }

    @Override
    public void acceptConnection(UUID connectionId) throws CantAcceptActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

    }
}
