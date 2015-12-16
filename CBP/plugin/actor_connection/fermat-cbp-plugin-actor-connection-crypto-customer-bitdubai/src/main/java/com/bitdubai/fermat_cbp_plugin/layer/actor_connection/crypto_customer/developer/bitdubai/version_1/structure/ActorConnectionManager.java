package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionManager</code>
 * bla bla bla.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/11/2015.
 */
public class ActorConnectionManager implements CryptoCustomerActorConnectionManager {

    @Override
    public CryptoCustomerActorConnectionSearch getSearch(CryptoCustomerLinkedActorIdentity actorIdentitySearching) {
        return null;
    }

    @Override
    public void requestConnection(final ActorIdentityInformation actorSending  ,
                                  final ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {

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
