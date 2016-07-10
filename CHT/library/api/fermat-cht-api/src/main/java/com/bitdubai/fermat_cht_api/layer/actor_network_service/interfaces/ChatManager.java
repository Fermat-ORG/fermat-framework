package com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public interface ChatManager extends FermatManager {

    void exposeIdentity(final ChatExposingData chatExposingData) throws CantExposeIdentityException;

    void updateIdentity(final ChatExposingData chatExposingData) throws CantExposeIdentityException;

    void exposeIdentities(final Collection<ChatExposingData> chatExposingDataList) throws CantExposeIdentitiesException;

    ChatSearch getSearch();

    void requestConnection(final ChatConnectionInformation chatConnectionInformation) throws CantRequestConnectionException;

    void disconnect(final UUID requestId) throws CantDisconnectException, ConnectionRequestNotFoundException;

    void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException;

    void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException;

    void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException;

    List<ChatConnectionRequest> listPendingConnectionNews(Actors actorType) throws CantListPendingConnectionRequestsException;

    List<ChatConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException;

    void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException;

    boolean isActorOnline(String publicKey);

}
