package com.bitdubai.fermat_cry_api.layer.crypto_vault;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public interface CryptoVaultManager extends TransactionSender<CryptoTransaction> {
    public void connectToBitcoin() throws VaultNotConnectedToNetworkException;
    public void disconnectFromBitcoin();
    public CryptoAddress getAddress();
    public List<CryptoAddress> getAddresses(int amount);
    public void sendBitcoins (UUID walletId, UUID FermatTrId,  CryptoAddress addressTo, long satothis) throws InsufficientMoneyException, InvalidSendToAddressException;
    ;
}
