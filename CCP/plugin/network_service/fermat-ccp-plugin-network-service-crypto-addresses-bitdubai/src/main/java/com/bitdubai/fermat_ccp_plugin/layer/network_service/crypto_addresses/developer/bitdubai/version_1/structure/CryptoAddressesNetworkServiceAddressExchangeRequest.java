package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.AddressExchangeRequestState;
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
    private final Actors                      identityTypeAccepting      ;
    private final String                      identityPublicKeyRequesting;
    private final String                      identityPublicKeyAccepting ;
    private final CryptoAddress               cryptoAddressFromRequest   ;
    private final CryptoAddress               cryptoAddressFromResponse  ;
    private final BlockchainNetworkType       blockchainNetworkType      ;
    private final AddressExchangeRequestState state                      ;

    public CryptoAddressesNetworkServiceAddressExchangeRequest(UUID                        requestId                  ,
                                                               String                      walletPublicKey            ,
                                                               Actors                      identityTypeRequesting     ,
                                                               Actors                      identityTypeAccepting      ,
                                                               String                      identityPublicKeyRequesting,
                                                               String                      identityPublicKeyAccepting ,
                                                               CryptoAddress               cryptoAddressFromRequest   ,
                                                               CryptoAddress               cryptoAddressFromResponse  ,
                                                               BlockchainNetworkType       blockchainNetworkType      ,
                                                               AddressExchangeRequestState state                      ) {

        this.requestId                   = requestId                  ;
        this.walletPublicKey             = walletPublicKey            ;
        this.identityTypeRequesting      = identityTypeRequesting     ;
        this.identityTypeAccepting       = identityTypeAccepting      ;
        this.identityPublicKeyRequesting = identityPublicKeyRequesting;
        this.identityPublicKeyAccepting  = identityPublicKeyAccepting ;
        this.cryptoAddressFromRequest    = cryptoAddressFromRequest   ;
        this.cryptoAddressFromResponse   = cryptoAddressFromResponse  ;
        this.blockchainNetworkType       = blockchainNetworkType      ;
        this.state                       = state                      ;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public Actors getIdentityTypeRequesting() {
        return identityTypeRequesting;
    }

    @Override
    public Actors getIdentityTypeAccepting() {
        return identityTypeAccepting;
    }

    @Override
    public String getIdentityPublicKeyRequesting() {
        return identityPublicKeyRequesting;
    }

    @Override
    public String getIdentityPublicKeyAccepting() {
        return identityPublicKeyAccepting;
    }

    @Override
    public CryptoAddress getCryptoAddressFromRequest() {
        return cryptoAddressFromRequest;
    }

    @Override
    public CryptoAddress getCryptoAddressFromResponse() {
        return cryptoAddressFromResponse;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public AddressExchangeRequestState getState() {
        return state;
    }
}