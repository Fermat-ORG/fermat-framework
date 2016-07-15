package com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.CryptoVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExportCryptoVaultSeedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantGetDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantImportSeedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotGenerateTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.definition.util.CryptoAmount;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 11/06/15.
 */
public interface CryptoVaultManager extends FermatManager, PlatformCryptoVault {
    /**
     * gets a new fresh crypto address
     * @param blockchainNetworkType the network type we want to generate this address for.
     * @return
     */
    CryptoAddress getAddress(BlockchainNetworkType blockchainNetworkType);

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network beeing used
     * and we must have enought funds to send this money
     * @param walletPublicKey
     * @param FermatTrId internal transaction Id - used to validate that it was not send previously.
     * @param addressTo the valid address we are sending to
     * @param cryptoAmount the amount to send in the transaction, including fee information
     * @param blockchainNetworkType the network type where we are going to create this transaction
     * @return the transaction Hash of the new created transaction in the vault.
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CouldNotSendMoneyException
     */
    String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, CryptoAmount cryptoAmount, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException;
    String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, CryptoAmount cryptoAmount, String op_Return, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException;
    /**
     * Generates a new Bitcoin Transaction with needed inputs and outputs.
     * Also signs and pass to the Crypto Network the transaction generated.
     * @param walletPublicKey
     * @param fermatTrId
     * @param addressTo
     * @param cryptoAmount the amount to send in the transaction, including fee information
     * @param blockchainNetworkType the network type where we are going to create this transaction
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CryptoTransactionAlreadySentException
     */
    String generateTransaction (String walletPublicKey, UUID fermatTrId,  CryptoAddress addressTo, CryptoAmount cryptoAmount, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException,  CryptoTransactionAlreadySentException, CouldNotGenerateTransactionException;
    String generateTransaction (String walletPublicKey, UUID fermatTrId,  CryptoAddress addressTo, CryptoAmount cryptoAmount, String op_Return, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException,  CryptoTransactionAlreadySentException, CouldNotGenerateTransactionException;

    /**
     * Validates if the passes CryptoAddress is valid in the current network or not.
     * @param addressTo the address we want to validate
     * @param blockchainNetworkType the network type we are validating the address in.
     * @return true if is valid and we can use it, or false if not.
     */
    boolean isValidAddress(CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType);

    /**
     * Gets the Mnemonic code generated for this vault.
     * It can be used to export and import it somewhere else.
     * @return
     * @throws CantLoadExistingVaultSeed
     */
    CryptoVaultSeed exportCryptoVaultSeed();

    /**
     * * Imports the passed seed into the vault.
     * @param destinationAddress
     * @param blockchainNetworkType
     * @param mnemonicCode
     * @param date
     * @throws CantImportSeedException
     */
    void importSeedFromMnemonicCode(CryptoAddress destinationAddress, BlockchainNetworkType blockchainNetworkType, List<String> mnemonicCode,long date) throws CantImportSeedException;


    /**
     * Signs the owned inputs of the passed Draft transaction
     * @param draftTransaction the transaction to sign
     * @return the signed Transaction
     * @throws CantSignTransactionException
     */
    DraftTransaction signTransaction(DraftTransaction draftTransaction) throws CantSignTransactionException;

    /**
     * Adds more inputs and outputs to a draft transaction
     * @param draftTransaction the incomplete draft transaction
     * @param valueToSend the amount of bitcoins in satoshis to add to the transaction
     * @param addressTo the address to that will receive the bitcoins.
     * @return the draft transaction with the added values.
     * @throws CantCreateDraftTransactionException
     */
    DraftTransaction addInputsToDraftTransaction (DraftTransaction draftTransaction, long valueToSend, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantCreateDraftTransactionException;

    /**
     * Returns a stored draft transaction
     * @param blockchainNetworkType the network type this transaction was created
     * @param txHash the txHash of the draft transaction
     * @return a previously stored draft transaction
     * @throws CantGetDraftTransactionException
     */
    DraftTransaction getDraftTransaction(BlockchainNetworkType blockchainNetworkType, String txHash) throws CantGetDraftTransactionException;

    /**
     * Persists a draft transaction in the vault.
     * @param draftTransaction the draft Transaction to store
     * @throws CantStoreBitcoinTransactionException
     */
    void saveTransaction(DraftTransaction draftTransaction) throws CantStoreBitcoinTransactionException;

}
