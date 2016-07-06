package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_module.Crypto;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.enums.TransactionState;

import java.util.UUID;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraActorTransactionWrapper implements CryptoWalletTransactionRecord,BitcoinLossProtectedWalletTransactionRecord {

    /*
     * CryptoWalletTransactionRecord Interface member variables
     */
    private UUID          transactionId;
    private UUID          requestId;
    private String        actorFromPublicKey;
    private String        actorToPublicKey;
    private Actors        actorFromType;
    private Actors        actorToType;
    private String        transactionHash;
    private CryptoAddress addressFrom;
    private CryptoAddress addressTo;
    private long          amount;
    private String        op_Return;
    private long          timestamp;
    private String        memo;
    private boolean sameDevice;
    private BlockchainNetworkType blockchainNetworkType;
    private CryptoCurrency cryptoCurrency;

    private FeeOrigin feeOrigin;
    private long fee;
    private long total;


    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    @Override
    public UUID getTransactionId() {
        return this.transactionId;
    }

    @Override
    public UUID getRequestId() {
        return this.requestId;
    }

    public void setIdRequest(UUID id) {
        this.requestId = id;
    }

    public void setIdTransaction(UUID id) {
        this.transactionId = id;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    public void setActorFromPublicKey(String actorFromPublicKey) {
        this.actorFromPublicKey = actorFromPublicKey;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    public void setActorToPublicKey(String actorToPublicKey) {
        this.actorToPublicKey = actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {return this.actorToType; }

    public void setActorToType(Actors actorToType) {this.actorToType = actorToType;}

    @Override
    public Actors getActorFromType() { return this.actorFromType;}

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency){ this.cryptoCurrency = cryptoCurrency; }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    @Override
    public long getExchangRate() {
        return 0;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public void setActorFromType(Actors actorFromType){ this.actorFromType = actorFromType; }

    public void setTransactionHash(String tramsactionHash) {
        this.transactionHash = tramsactionHash;
    }

    public String getOp_Return() {
        return op_Return;
    }

    public void setOp_Return(String op_Return) {
        this.op_Return = op_Return;
    }

    private String           walletPublicKey;
    private TransactionState state;
    private CryptoStatus     cryptoStatus;
    private ReferenceWallet  referenceWallet;

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public TransactionState getState() {
        return this.state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public CryptoStatus getCryptoStatus() {
        return this.cryptoStatus;
    }

    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
    }

    public void setReferenceWallet(ReferenceWallet referenceWallet) {
        this.referenceWallet = referenceWallet;
    }

    public ReferenceWallet getReferenceWallet() {
        return this.referenceWallet;
    }

    public boolean isSameDevice() {
        return sameDevice;
    }

    public void setSameDevice(boolean sameDevice) {
        this.sameDevice = sameDevice;
    }

    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    public FeeOrigin getFeeOrigin() {
        return this.feeOrigin;
    }


    public void setFee(long fee ) {
        this.fee  = fee ;
    }

    public long getFee() {
        return this.fee ;
    }


}
