package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;

import java.util.UUID;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingDraftTransactionWrapper {

    private UUID requestId;
    private String walletPublicKey;
    private long valueToSend;
    private CryptoAddress addressTo;
    private ReferenceWallet referenceWallet;
    private BlockchainNetworkType blockchainNetworkType;
    private String actorFromPublicKey;
    private String actorToPublicKey;
    private Actors actorFromType;
    private Actors actorToType;
    private String memo;
    private long timestamp;
    private String txHash;
    private  CryptoCurrency cryptoCurrency;
    private long fee ;
    private FeeOrigin feeOrigin;

    public OutgoingDraftTransactionWrapper() {
    }

    public OutgoingDraftTransactionWrapper(UUID requestId, String walletPublicKey, long valueToSend, CryptoAddress addressTo, ReferenceWallet referenceWallet, BlockchainNetworkType blockchainNetworkType, String actorFromPublicKey, String actorToPublicKey, Actors actorFromType, Actors actorToType, String memo, long timestamp,
                                           String txHash,
                                           CryptoCurrency cryptoCurrency,
                                           long fee,
                                           FeeOrigin feeOrigin) {
        this.requestId = requestId;
        this.walletPublicKey = walletPublicKey;
        this.valueToSend = valueToSend;
        this.addressTo = addressTo;
        this.referenceWallet = referenceWallet;
        this.blockchainNetworkType = blockchainNetworkType;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey = actorToPublicKey;
        this.actorFromType = actorFromType;
        this.actorToType = actorToType;
        this.memo = memo;
        this.timestamp = timestamp;
        this.txHash = txHash;
        this.cryptoCurrency = cryptoCurrency;
        this.feeOrigin = feeOrigin;
        this.fee = fee;
    }

    public long getValueToSend() {
        return valueToSend;
    }

    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public ReferenceWallet getReferenceWallet() {
        return referenceWallet;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    public Actors getActorFromType() {
        return actorFromType;
    }

    public Actors getActorToType() {
        return actorToType;
    }

    public String getMemo() {
        return memo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTxHash() {
        return txHash;
    }

    public CryptoCurrency getCryptoCurrency(){
        return this.cryptoCurrency;
    }

    public FeeOrigin getFeeOrigin(){
        return this.feeOrigin;
    }

    public long getFee(){
        return this.fee;
    }
}
