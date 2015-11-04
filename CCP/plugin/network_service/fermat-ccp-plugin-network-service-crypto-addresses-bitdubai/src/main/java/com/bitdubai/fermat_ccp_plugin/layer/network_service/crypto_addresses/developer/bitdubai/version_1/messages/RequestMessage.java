package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.enums.MessageTypes;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.RequestMessage</code>
 * contains the structure of a request message for this plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class RequestMessage extends NetworkServiceMessage {

    private final UUID                  requestId                  ;
    private final CryptoCurrency        cryptoCurrency             ;
    private final Actors                identityTypeRequesting     ;
    private final Actors                identityTypeResponding     ;
    private final String                identityPublicKeyRequesting;
    private final String                identityPublicKeyResponding;
    private final CryptoAddressDealers  cryptoAddressDealer        ;
    private final BlockchainNetworkType blockchainNetworkType      ;

    public RequestMessage(final UUID                  requestId                  ,
                          final CryptoCurrency        cryptoCurrency             ,
                          final Actors                identityTypeRequesting     ,
                          final Actors                identityTypeResponding     ,
                          final String                identityPublicKeyRequesting,
                          final String                identityPublicKeyResponding,
                          final CryptoAddressDealers  cryptoAddressDealer        ,
                          final BlockchainNetworkType blockchainNetworkType      ) {

        super(MessageTypes.REQUEST);

        this.requestId                   = requestId                  ;
        this.cryptoCurrency              = cryptoCurrency             ;
        this.identityTypeRequesting      = identityTypeRequesting     ;
        this.identityTypeResponding      = identityTypeResponding     ;
        this.identityPublicKeyRequesting = identityPublicKeyRequesting;
        this.identityPublicKeyResponding = identityPublicKeyResponding;
        this.cryptoAddressDealer         = cryptoAddressDealer        ;
        this.blockchainNetworkType       = blockchainNetworkType      ;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
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

    public CryptoAddressDealers getCryptoAddressDealer() {
        return cryptoAddressDealer;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestId=" + requestId +
                ", cryptoCurrency=" + cryptoCurrency +
                ", identityTypeRequesting=" + identityTypeRequesting +
                ", identityTypeResponding=" + identityTypeResponding +
                ", identityPublicKeyRequesting='" + identityPublicKeyRequesting + '\'' +
                ", identityPublicKeyResponding='" + identityPublicKeyResponding + '\'' +
                ", cryptoAddressDealer=" + cryptoAddressDealer +
                ", blockchainNetworkType=" + blockchainNetworkType +
                '}';
    }
}
