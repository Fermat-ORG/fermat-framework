package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_draft;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;

import java.util.UUID;

/**
 * Created by mati on 2016.02.13..
 */
public interface OutgoingDraftManager {


    void addInputsToDraftTransaction (
            UUID requestId,
            DraftTransaction draftTransaction,
            long valueToSend,
            CryptoAddress addressTo,
            String walletPublicKey,
            ReferenceWallet referenceWallet,
            String memo,
            String actorToPublicKey,
            Actors actorToType,
            String actorFromPublicKey,
            Actors ActorFromType,
            BlockchainNetworkType blockchainNetworkType
            );

    DraftTransaction getPending(UUID requestId);

    void markRead(UUID requestId);
}
