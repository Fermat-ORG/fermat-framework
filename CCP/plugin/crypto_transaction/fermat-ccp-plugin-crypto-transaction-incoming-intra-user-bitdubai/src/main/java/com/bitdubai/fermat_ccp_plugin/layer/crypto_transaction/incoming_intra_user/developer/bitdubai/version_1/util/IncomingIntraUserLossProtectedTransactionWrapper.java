package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by natalia on 18/03/16.
 */
public class IncomingIntraUserLossProtectedTransactionWrapper implements BitcoinLossProtectedWalletTransactionRecord {

    private final UUID          transactionId     ;
    private final UUID          requestId     ;
    private final String        actorFromPublicKey;
    private final String        actorToPublicKey  ;
    private final Actors        actorFromType     ;
    private final Actors        actorToType       ;
    private final String        transactionHash   ;
    private final CryptoAddress addressFrom       ;
    private final CryptoAddress addressTo         ;
    private final long          amount            ;
    private final long          timestamp         ;
    private final String        memo              ;
    private final BlockchainNetworkType blockchainNetworkType;
    private long exchangeRate;
    private CryptoCurrency cryptoCurrency;


    public IncomingIntraUserLossProtectedTransactionWrapper(final UUID        transactionId     ,
                                               final UUID          requestId     ,
                                               final String        actorFromPublicKey,
                                               final String        actorToPublicKey  ,
                                               final Actors        actorFromType     ,
                                               final Actors        actorToType       ,
                                               final String        transactionHash   ,
                                               final CryptoAddress addressFrom       ,
                                               final CryptoAddress addressTo         ,
                                               final long          amount            ,
                                               final long          timestamp         ,
                                               final String        memo              ,
                                               final BlockchainNetworkType blockchainNetworkType,
                                                            final CryptoCurrency cryptoCurrency) {

        this.transactionId      = transactionId     ;
        this.requestId          = requestId         ;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey   = actorToPublicKey  ;
        this.actorFromType      = actorFromType     ;
        this.actorToType        = actorToType       ;
        this.transactionHash    = transactionHash   ;
        this.addressFrom        = addressFrom       ;
        this.addressTo          = addressTo         ;
        this.amount             = amount            ;
        this.timestamp          = timestamp         ;
        this.memo               = memo              ;
        this.blockchainNetworkType = blockchainNetworkType;
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public long getExchangRate() {
        return 0;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }


}
