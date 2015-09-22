package com.bitdubai.fermat_cry_api.layer.crypto_vault;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CoultNotCreateCryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
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

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network beeing used
     * and we must have enought funds to send this money
     * @param walletPublicKey
     * @param FermatTrId internal transaction Id - used to validate that it was not send previously.
     * @param addressTo the valid address we are sending to
     * @param satoshis the amount in long of satoshis
     * @return the transaction Hash of the new created transaction in the vault.
     * @throws InsufficientMoneyException
     * @throws InvalidSendToAddressException
     * @throws CouldNotSendMoneyException
     */
    public String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, long satoshis) throws InsufficientMoneyException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException;


    /**
     * Validates if the passes CryptoAddress is valid in the current network or not.
     * @param addressTo
     * @return true if is valid and we can use it, or false if not.
     */
    public boolean isValidAddress(CryptoAddress addressTo);


    /**
     * returns the CryptoStatus of the passed transaction.
     * @param transactionId
     * @return
     * @throws CouldNotGetCryptoStatusException
     */
    public CryptoStatus getCryptoStatus(UUID transactionId) throws CouldNotGetCryptoStatusException;

    public CryptoTransaction generateDraftCryptoTransaction(CryptoAddress genesisAddress, long cryptoAmount) throws CoultNotCreateCryptoTransaction;
}
