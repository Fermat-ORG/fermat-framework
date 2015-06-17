package com.bitdubai.fermat_p2p_api.layer.cry_crypto_vault;

import com.bitdubai.fermat_p2p_api.layer.all_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public interface CryptoVaultManager {
    public void setUserId (UUID UserId);
    public void connectToBitcoin();
    public void disconnectFromBitcoin();
    public CryptoAddress getAddress();
    public List<CryptoAddress> getAddresses(int amount);
}
