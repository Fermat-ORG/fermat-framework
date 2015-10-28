package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.AddressExchangeRequest;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesNetworkServiceAddressExchangeRequest</code>
 * haves all the consumable methods to get the data of a Address Exchange Request.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceAddressExchangeRequest implements AddressExchangeRequest {

    private final UUID                        requestId                  ;
    private final String                      walletPublicKey            ;
    private final Actors                      identityTypeRequesting     ;
    private final Actors                      identityTypeResponding     ;
    private final String                      identityPublicKeyRequesting;
    private final String                      identityPublicKeyResponding;
    private final CryptoCurrency              cryptoCurrency             ;
    private final CryptoAddress               cryptoAddress              ;
    private final ProtocolState               state                      ;
    private final RequestType                 type                       ;
    private final RequestAction               action                     ;
    private final BlockchainNetworkType       blockchainNetworkType      ;

    public CryptoAddressesNetworkServiceAddressExchangeRequest(final UUID                        requestId                  ,
                                                               final String                      walletPublicKey            ,
                                                               final Actors                      identityTypeRequesting     ,
                                                               final Actors                      identityTypeResponding     ,
                                                               final String                      identityPublicKeyRequesting,
                                                               final String                      identityPublicKeyResponding,
                                                               final CryptoCurrency              cryptoCurrency             ,
                                                               final CryptoAddress               cryptoAddress              ,
                                                               final ProtocolState               state                      ,
                                                               final RequestType                 type                       ,
                                                               final RequestAction               action                     ,
                                                               final BlockchainNetworkType       blockchainNetworkType      ) {

        this.requestId                   = requestId                  ;
        this.walletPublicKey             = walletPublicKey            ;
        this.identityTypeRequesting      = identityTypeRequesting     ;
        this.identityTypeResponding      = identityTypeResponding     ;
        this.identityPublicKeyRequesting = identityPublicKeyRequesting;
        this.identityPublicKeyResponding = identityPublicKeyResponding;
        this.cryptoCurrency              = cryptoCurrency             ;
        this.cryptoAddress               = cryptoAddress              ;
        this.state                       = state                      ;
        this.type                        = type                       ;
        this.action                      = action                     ;
        this.blockchainNetworkType       = blockchainNetworkType      ;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public Actors getIdentityTypeRequesting() {
        return identityTypeRequesting;
    }

    public Actors getIdentityTypeResponding() {
        return identityTypeResponding;
    }

    public String getIdentityPublicKeyRequesting() {
        return identityPublicKeyRequesting;
    }

    public String getIdentityPublicKeyResponding() {
        return identityPublicKeyResponding;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public ProtocolState getState() {
        return state;
    }

    public RequestType getType() {
        return type;
    }

    public RequestAction getAction() {
        return action;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public String toString() {
        return "CryptoAddressesNetworkServiceAddressExchangeRequest{" +
                "requestId=" + requestId +
                ", walletPublicKey='" + walletPublicKey + '\'' +
                ", identityTypeRequesting=" + identityTypeRequesting +
                ", identityTypeResponding=" + identityTypeResponding +
                ", identityPublicKeyRequesting='" + identityPublicKeyRequesting + '\'' +
                ", identityPublicKeyResponding='" + identityPublicKeyResponding + '\'' +
                ", cryptoCurrency=" + cryptoCurrency +
                ", cryptoAddress=" + cryptoAddress +
                ", state=" + state +
                ", type=" + type +
                ", action=" + action +
                ", blockchainNetworkType=" + blockchainNetworkType +
                '}';
    }
}