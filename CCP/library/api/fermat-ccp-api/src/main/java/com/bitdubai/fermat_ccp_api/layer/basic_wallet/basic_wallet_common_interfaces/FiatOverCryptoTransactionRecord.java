package com.bitdubai.fermat_ccp_api.layer.basic_wallet.basic_wallet_common_interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The interface <code>FiatOverCryptoTransactionRecord</code>
 * provides the methods to inspect the information that comes from a fiat transaction made over a crypto network channel of value
 */
public interface FiatOverCryptoTransactionRecord {

    public UUID getTransactionId();

    public boolean comesFromARequest();

    public UUID getRequestId();

    public FiatCurrency getFiatCurrency();

    public long getFiatAmount();

    public String getTransactionHash();

    public CryptoAddress getAddressFrom();

    public CryptoAddress getAddressTo();

    public long getCryptoAmount();

    public long getTimestamp();

    public String getMemo();

    public String getActorToPublicKey();

    public String getActorFromPublicKey();

    public Actors getActorToType();

    public Actors getActorFromType();
}
