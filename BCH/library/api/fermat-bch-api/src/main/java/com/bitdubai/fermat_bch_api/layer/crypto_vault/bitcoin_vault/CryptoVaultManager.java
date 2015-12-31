package com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public interface CryptoVaultManager extends FermatManager, PlatformCryptoVault {
    /**
     * gets a new fresh crypto address
     * @return
     */
    CryptoAddress getAddress();

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network beeing used
     * and we must have enought funds to send this money
     * @param walletPublicKey
     * @param FermatTrId internal transaction Id - used to validate that it was not send previously.
     * @param addressTo the valid address we are sending to
     * @param satoshis the amount in long of satoshis
     * @return the transaction Hash of the new created transaction in the vault.
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CouldNotSendMoneyException
     */
    String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, long satoshis) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException;

    /**
     * Generates a new Bitcoin Transaction with needed inputs and outputs.
     * Also signs and pass to the Crypto Network the transaction generated.
     * @param walletPublicKey
     * @param fermatTrId
     * @param addressTo
     * @param satoshis
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CryptoTransactionAlreadySentException
     */
    String generateTransaction (String walletPublicKey, UUID fermatTrId,  CryptoAddress addressTo, long satoshis) throws InsufficientCryptoFundsException, InvalidSendToAddressException,  CryptoTransactionAlreadySentException;
    String generateTransaction (String walletPublicKey, UUID fermatTrId,  CryptoAddress addressTo, long satoshis, String op_Return) throws InsufficientCryptoFundsException, InvalidSendToAddressException,  CryptoTransactionAlreadySentException;

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network beeing used
     * and we must have enought funds to send this money. It allows including an Op_return output value.
     * @param walletPublicKey
     * @param FermatTrId
     * @param addressTo
     * @param satoshis
     * @param op_Return
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CouldNotSendMoneyException
     * @throws CryptoTransactionAlreadySentException
     */
    String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, long satoshis, String op_Return) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException;




    /**
     * Validates if the passes CryptoAddress is valid in the current network or not.
     * @param addressTo
     * @return true if is valid and we can use it, or false if not.
     */
    boolean isValidAddress(CryptoAddress addressTo);
}
