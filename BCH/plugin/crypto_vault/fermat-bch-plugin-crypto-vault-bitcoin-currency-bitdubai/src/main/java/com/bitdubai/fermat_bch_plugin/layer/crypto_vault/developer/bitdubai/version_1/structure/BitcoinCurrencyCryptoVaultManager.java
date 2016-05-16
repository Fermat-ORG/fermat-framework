package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.InvalidSeedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantGetDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDao;

import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinCurrencyCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantValidateCryptoNetworkIsActiveException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;
import org.bitcoinj.wallet.WalletTransaction;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 12/17/15.
 */
public class BitcoinCurrencyCryptoVaultManager  extends CryptoVault{
    /**
     * BitcoinCurrencyCryptoVaultManager variables
     */
    UUID pluginId;
    VaultKeyHierarchyGenerator vaultKeyHierarchyGenerator;
    BitcoinCurrencyCryptoVaultDao dao;


    /**
     * File name information where the seed will be stored
     */
    private final String BITCOIN_VAULT_SEED_FILEPATH = "BitcoinVaultSeed";
    private final String BITCOIN_VAULT_SEED_FILENAME;
    private final String DRAFT_TRANSACTION_PATH = "draftTransactions";


    /**
     * platform interfaces definition
     */
    BitcoinNetworkManager bitcoinNetworkManager;
    PluginFileSystem pluginFileSystem;
    PluginDatabaseSystem pluginDatabaseSystem;
    ErrorManager errorManager;


    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public BitcoinCurrencyCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager,
                                   ErrorManager errorManager) throws InvalidSeedException {
        super(pluginFileSystem, pluginId, bitcoinNetworkManager, "BitcoinVaultSeed", seedFileName);

        this.pluginId = pluginId;
        BITCOIN_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.errorManager = errorManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(this.getVaultSeed(), pluginDatabaseSystem, this.bitcoinNetworkManager, this.pluginId);
        new Thread(vaultKeyHierarchyGenerator).start();
    }

    /**
     * Will get a new crypto address from the Bitcoin vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewBitcoinVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        /**
         * I create the account manually instead of getting it from the database because this method always returns addresses
         * from the Bitcoin vault account with Id 0.
         */
        com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Bitcoin Vault account", HierarchyAccountType.MASTER_ACCOUNT);
        return vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getBitcoinAddress(blockchainNetworkType, vaultAccount);
    }


    /**
     * Transform a CryptoAddress into a BitcoinJ Address
     * * @param networkParameters the network parameters where we are using theis address.
     * @param cryptoAddress the Crypto Address
     * @return a bitcoinJ address.
     */
    private Address getBitcoinAddress(NetworkParameters networkParameters, CryptoAddress cryptoAddress) throws AddressFormatException {
        Address address = new Address(networkParameters, cryptoAddress.getAddress());
        return address;
    }

    /**
     * Will make sure that the passed network type is active.
     * @param blockchainNetworkType the networktype to validate
     */
    private void validateNetorkIsActive(BlockchainNetworkType blockchainNetworkType) throws CantValidateCryptoNetworkIsActiveException {
        /**
         * I need to make sure that we have generated a key on the network type to which the address belongs
         * to, so we can be sure that the Crypto Network is listening on this network.
         */
        try {
            List<BlockchainNetworkType> networkTypes = getDao().getActiveNetworkTypes();

            /**
             * If the passed networktype is not included, then is not valid.
             */
            if (!networkTypes.contains(blockchainNetworkType)){
                StringBuilder output = new StringBuilder("The Network type" + blockchainNetworkType.getCode() + " is not active.");
                output.append(System.lineSeparator());
                output.append("Active Networks are: " + networkTypes.toString());
                throw new CantValidateCryptoNetworkIsActiveException(CantValidateCryptoNetworkIsActiveException.DEFAULT_MESSAGE, null, output.toString(), null);
            }
        } catch (CantExecuteDatabaseOperationException e) {
            /**
             * If I can't validate this. I will continue because I may be listening to this network already.
             */
            e.printStackTrace();
        }
    }

    /**
     * Calculates the network parameters from the specified address.
     * @param addressTo
     * @return
     * @throws AddressFormatException in case the address is not in the correct format.
     */
    private NetworkParameters getNetworkParametersFromAddress(String addressTo) throws AddressFormatException {
        NetworkParameters networkParameters = Address.getParametersFromAddress(addressTo);

        /**
         * if the network parameters calculated is different that the Default network I will double check
         */
        if (BitcoinNetworkSelector.getBlockchainNetworkType(networkParameters) != BlockchainNetworkType.getDefaultBlockchainNetworkType()){
            try {
                // If only one network is enabled, then I will return the default
                if (getDao().getActiveNetworkTypes().size() == 1)
                    return BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                else {
                    // If I have TestNet and RegTest registered, I may return any of them since they share the same prefix.
                    return networkParameters;
                }
            } catch (CantExecuteDatabaseOperationException e) {
                return BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }
        } else
            return networkParameters;
    }

    /**
     * instantiates and creates the dao object to access the database
     * @return
     */
    private BitcoinCurrencyCryptoVaultDao getDao(){
        if (dao == null){
            try {
                dao = new BitcoinCurrencyCryptoVaultDao(this.pluginDatabaseSystem, this.pluginId);
            } catch (CantInitializeBitcoinCurrencyCryptoVaultDatabaseException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }

    /**
     * Determines if the passsed CryptoAddress is valid.
     * @param addressTo the address to validate
     * @return true if valid, false if it is not.
     */
    public boolean isValidAddress(CryptoAddress addressTo) {
        /**
         * I extract the network Parameter from the address
         */
        NetworkParameters networkParameters;
        try {
            networkParameters = getNetworkParametersFromAddress(addressTo.getAddress());
        } catch (AddressFormatException e) {
            /**
             * If there is an error, I will use the default parameters.
             */
            networkParameters = BitcoinNetworkSelector.getNetworkParameter(BlockchainNetworkType.getDefaultBlockchainNetworkType());
        }

        /**
         * If the address is correct, then no exception raised.
         */
        try {
            Address address = new Address(networkParameters, addressTo.getAddress());
            return true;
        } catch (AddressFormatException e) {
            return false;
        }
    }

    /**
     * gets a fresh un used crypto Address from the vault
     */
    public CryptoAddress getAddress(BlockchainNetworkType blockchainNetworkType) {
        try {
            return this.getNewBitcoinVaultCryptoAddress(blockchainNetworkType);
        } catch (GetNewCryptoAddressException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send bitcoins to the specified address. The Address must be a valid address in the network being used
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
    public synchronized String sendBitcoins (String walletPublicKey, UUID FermatTrId,  CryptoAddress addressTo, long satoshis, String op_Return, boolean broadcast, BlockchainNetworkType blockchainNetworkType)
            throws InsufficientCryptoFundsException,
            InvalidSendToAddressException,
            CouldNotSendMoneyException,
            CryptoTransactionAlreadySentException {

        /**
         * validates we are not sending less than permited.
         */
        if (isDustySend(satoshis))
            throw new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, null, "Dusty send request: " + satoshis, "send more bitcoins!");


        /**
         * I get the network for this address and validate that is active
         */
        try {
            validateNetorkIsActive(blockchainNetworkType);
        } catch (CantValidateCryptoNetworkIsActiveException e) {
            throw new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, e, "The network is not active!", null);
        }

        /**
         * I get the networkParameter
         */
        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);

        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            InvalidSendToAddressException exception = new InvalidSendToAddressException(e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * I get the Bitcoin Transactions stored in the CryptoNetwork for this vault.
         */
        List<Transaction> transactions = bitcoinNetworkManager.getBitcoinTransactions(blockchainNetworkType);

        /**
         * Create the bitcoinj wallet from the keys of this account
         */
        com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Bitcoin Vault account", HierarchyAccountType.MASTER_ACCOUNT);
        //final Wallet wallet = getWalletForAccount(vaultAccount, networkParameters);
        Wallet wallet = null;
        Context walletContext = new Context(networkParameters);

        try {
             wallet = Wallet.fromSeed(walletContext.getParams(), getVaultSeed());
        } catch (InvalidSeedException e) {
            e.printStackTrace();
        }

        wallet.importKeys(vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(vaultAccount));

        /**
         * Add transactions to the wallet that we can use to spend.
         */
        for (Transaction transaction : transactions){
            if (!transaction.isEveryOwnedOutputSpent(wallet)){
                WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, transaction);
                wallet.addWalletTransaction(walletTransaction);
            }
        }

        /**
         * sets the fee and value to send
         */
        Coin fee = Coin.valueOf(BitcoinNetworkConfiguration.FIXED_FEE_VALUE);
        final Coin coinToSend = Coin.valueOf(satoshis);

        if (coinToSend.isNegative() || coinToSend.isZero()){
            StringBuilder output = new StringBuilder("The resulting value to be send is insufficient.");
            output.append(System.lineSeparator());
            output.append("We are trying to send " + coinToSend.getValue() + " satoshis, which is ValueToSend - fee (" + satoshis + " - " + fee.getValue() + ")");


            CouldNotSendMoneyException exception = new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, null, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * creates the send request and broadcast it on the network.
         */
        Wallet.SendRequest sendRequest = Wallet.SendRequest.to(address, coinToSend);

        /**
         * I set SendRequest properties
         */
        sendRequest.fee = fee;
        sendRequest.feePerKb = Coin.ZERO;
        sendRequest.shuffleOutputs = false;

        /**
         * I will add the OP_Return output if any
         */
        if (op_Return != null){
            sendRequest.tx.addOutput(Coin.ZERO, new ScriptBuilder().op(ScriptOpCodes.OP_RETURN).data(op_Return.getBytes()).build());
        }

        //trying to solve an issue
        try {
            sendRequest.changeAddress =  getBitcoinAddress(networkParameters, getAddress(blockchainNetworkType));
        } catch (AddressFormatException e) {
            e.printStackTrace();
        }

        try {
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {
            StringBuilder output = new StringBuilder("Not enought money to send bitcoins.");
            output.append(System.lineSeparator());
            output.append("Current balance available for this vault: " + wallet.getBalance().getValue());
            output.append(System.lineSeparator());
            output.append("Current value to send: " + coinToSend.getValue() + " (+fee: " + fee.getValue() + ")");
            InsufficientCryptoFundsException exception = new InsufficientCryptoFundsException(InsufficientCryptoFundsException.DEFAULT_MESSAGE, e, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception){
            StringBuilder output = new StringBuilder("Error sending bitcoins.");
            output.append(System.lineSeparator());
            output.append("Bitcoin Vault status: ");
            output.append(wallet.toString());
            output.append(System.lineSeparator());
            output.append("Transaction Status: ");
            output.append(sendRequest.tx.toString());
            CouldNotSendMoneyException e = new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, exception, output.toString(), "Bitcoin vault");

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }

        /**
         * I will store the transaction in the crypto network
         */
        try {
            bitcoinNetworkManager.storeBitcoinTransaction(blockchainNetworkType, sendRequest.tx, FermatTrId, true);
        } catch (CantStoreBitcoinTransactionException e) {
            throw new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, e, "There was an error storing the transaction in the Crypto Network-", "Crypto Network error or database error.");
        }

        /**
         * If I'm requested to broadcast it now, then I will
         */
        if (broadcast){
            try {
                bitcoinNetworkManager.broadcastTransaction(sendRequest.tx.getHashAsString());
            } catch (CantBroadcastTransactionException e) {
                throw new CouldNotSendMoneyException(CouldNotSendMoneyException.DEFAULT_MESSAGE, e, "There was an error broadcasting the transaction.", "Crypto Network error");
            }
        }

        return sendRequest.tx.getHashAsString();
    }

    /**
     * Gets the Mnemonic code generated for this vault.
     * It can be used to export and import it somewhere else.
     * @return
     * @throws CantLoadExistingVaultSeed
     */
    public List<String> getMnemonicCode() throws CantLoadExistingVaultSeed {
        try {
            return this.getVaultSeed().getMnemonicCode();
        } catch (InvalidSeedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadExistingVaultSeed(CantLoadExistingVaultSeed.DEFAULT_MESSAGE, e, "error loading Seed", "seed generator");
        }
    }

    /**
     * Signs the owned inputs of the passed Draft transaction
     * @param draftTransaction the transaction to sign
     * @return the signed Transaction
     * @throws CantSignTransactionException
     */
    public DraftTransaction signTransaction(DraftTransaction draftTransaction) throws CantSignTransactionException {
        if (draftTransaction == null)
            throw new CantSignTransactionException (CantSignTransactionException.DEFAULT_MESSAGE, null, "DraftTransaction can't be null", "null parameter");

        Transaction transaction = draftTransaction.getBitcoinTransaction();

        HierarchyAccount masterHierarchyAccount;
        try {
            masterHierarchyAccount = this.getDao().getHierarchyAccounts().get(0);
        } catch (CantExecuteDatabaseOperationException e) {
            //If there was an error, I will create a master account manually
            masterHierarchyAccount = new HierarchyAccount(0, "Bitcoin Vault", HierarchyAccountType.MASTER_ACCOUNT);
        }

        /**
         * I get a private key and the list of public keys we are using to monitor the network.
         */
        ECKey privateKey = null;
        List<ECKey> walletKeys = null;
        try {
            privateKey = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getNextAvailableKey(masterHierarchyAccount);
            walletKeys = this.vaultKeyHierarchyGenerator.getAllAccountsKeyList();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, e, "Error getting a private key from the key hierarchy. Can't sign a transaction.", "No private key to sign");
        }

        /**
         * I get a signed transaction from the abstract class CryptoVault.
         */
        transaction = this.signTransaction(walletKeys, transaction);
        System.out.println("***CryptoVault*** Transaction signed.");
        System.out.println(transaction.toString());

        /**
         * add it to the draft transaction and return it.
         */
        draftTransaction.setBitcoinTransaction(transaction);
        return draftTransaction;
    }

    /**
     * Adds more inputs and outputs to a draft transaction
     * @param draftTransaction the incomplete draft transaction
     * @param valueToSend the amount of bitcoins in satoshis to add to the transaction
     * @param addressTo the address to that will receive the bitcoins.
     * @return the draft transaction with the added values.
     * @throws CantCreateDraftTransactionException
     */
    public DraftTransaction addInputsToDraftTransaction(DraftTransaction draftTransaction, long valueToSend, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantCreateDraftTransactionException {
        if (draftTransaction == null || addressTo == null || valueToSend == 0 || blockchainNetworkType == null){
            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, "Parameters can't be null", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        /**
         * validates we are not sending less than permited.
         */
        if (isDustySend(valueToSend))
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, "Dusty send request: " + valueToSend, "send more bitcoins!");

        /**
         * I get the network for this address and validate that is active
         */
        try {
           validateNetorkIsActive(blockchainNetworkType);
        } catch (CantValidateCryptoNetworkIsActiveException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, "The network to which this address belongs to, is not active!", null);
        }

        /**
         * I get the networkParameter
         */
        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);

        /**
         * I get the bitcoin address
         */
        Address address = null;
        try {
            address = getBitcoinAddress(networkParameters,addressTo);
        } catch (AddressFormatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE,e, "The specified address " + addressTo.getAddress() + " is not valid.", null);
        }

        /**
         * I get the Bitcoin Transactions stored in the CryptoNetwork for this vault.
         */
        List<Transaction> transactions = bitcoinNetworkManager.getBitcoinTransactions(blockchainNetworkType);

        /**
         * Create the bitcoinj wallet from the keys of this account
         */
        com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount vaultAccount = new com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount(0, "Bitcoin Vault account", HierarchyAccountType.MASTER_ACCOUNT);
        //final Wallet wallet = getWalletForAccount(vaultAccount, networkParameters);
        Wallet wallet = null;

        //will create a new context to use
        Context walletContext = new Context(networkParameters);

        try {
            wallet = Wallet.fromSeed(walletContext.getParams(), getVaultSeed());
        } catch (InvalidSeedException e) {
            e.printStackTrace();
        }

        wallet.importKeys(vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getDerivedKeys(vaultAccount));

        /**
         * Add transactions to the wallet that we can use to spend.
         */
        for (Transaction transaction : transactions){
            if (!transaction.isEveryOwnedOutputSpent(wallet)){
                WalletTransaction walletTransaction = new WalletTransaction(WalletTransaction.Pool.UNSPENT, transaction);
                wallet.addWalletTransaction(walletTransaction);
            }
        }


        /**
         * sets the fee and value to send
         */
        Coin fee = Coin.valueOf(BitcoinNetworkConfiguration.FIXED_FEE_VALUE);
        final Coin coinToSend = Coin.valueOf(valueToSend);

        if (coinToSend.isNegative() || coinToSend.isZero()){
            StringBuilder output = new StringBuilder("The resulting value to be send is insufficient.");
            output.append(System.lineSeparator());
            output.append("We are trying to send " + coinToSend.getValue() + " satoshis, which is ValueToSend - fee (" + valueToSend + " - " + fee.getValue() + ")");



            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, null, output.toString(), null);
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;

        }

        /**
         * I will add the outputs to the Draft transaction
         */
        Transaction transaction = draftTransaction.getBitcoinTransaction();
        transaction.addOutput(coinToSend, address);
        Wallet.SendRequest sendRequest = Wallet.SendRequest.forTx(transaction);

        /**
         * I set SendRequest properties
         */
        sendRequest.fee = fee;
        sendRequest.feePerKb = Coin.ZERO;
        sendRequest.shuffleOutputs = false;

        try {
            // this will add the inputs to fill the transaction.
            wallet.completeTx(sendRequest);
        } catch (InsufficientMoneyException e) {
            StringBuilder output = new StringBuilder("Not enought money to send bitcoins.");
            output.append(System.lineSeparator());
            output.append("Current balance available for this vault: " + wallet.getBalance().getValue());
            output.append(System.lineSeparator());
            output.append("Current value to send: " + coinToSend.getValue() + " (+fee: " + fee.getValue() + ")");
            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, output.toString(), null);

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception){
            StringBuilder output = new StringBuilder("Error sending bitcoins.");
            output.append(System.lineSeparator());
            output.append("Bitcoin Vault status: ");
            output.append(wallet.toString());
            output.append(System.lineSeparator());
            output.append("Transaction Status: ");
            output.append(sendRequest.tx.toString());
            CantCreateDraftTransactionException e = new CantCreateDraftTransactionException(CouldNotSendMoneyException.DEFAULT_MESSAGE, exception, output.toString(), "Bitcoin vault");

            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }

        /**
         * I will store the transaction in the crypto network
         */
        try {
            bitcoinNetworkManager.storeBitcoinTransaction(blockchainNetworkType, sendRequest.tx, UUID.randomUUID(), true);
        } catch (CantStoreBitcoinTransactionException e) {
            throw new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, "There was an error storing the transaction in the Crypto Network-", "Crypto Network error or database error.");
        }

        /**
         * updates the bitcoin transaction
         */
        draftTransaction.setBitcoinTransaction(sendRequest.tx);

        // I add the seller crypto address
        draftTransaction.setSellerCryptoAddress(addressTo);

        /**
         * I will store this transaction to be able to return it later
         */
        try {
            storeDraftTransaction(draftTransaction);
        } catch (CantCreateFileException | CantPersistFileException e) {
            CantCreateDraftTransactionException exception = new CantCreateDraftTransactionException(CantCreateDraftTransactionException.DEFAULT_MESSAGE, e, "Draft Transaction could not be stored on disk", "IO error");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        System.out.println("***BitcoinVault*** Draft Transaction completed.");
        System.out.println(draftTransaction.toString());

        return draftTransaction;
    }

    /**
     * Will store on disk this draft transaction
     * @param draftTransaction
     */
    public void storeDraftTransaction(DraftTransaction draftTransaction) throws CantCreateFileException, CantPersistFileException {
        byte [] serializedDraftTransaction = draftTransaction.serialize();

        PluginBinaryFile pluginBinayFile = pluginFileSystem.createBinaryFile(this.pluginId, DRAFT_TRANSACTION_PATH, draftTransaction.getTxHash(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

        pluginBinayFile.setContent(serializedDraftTransaction);
        pluginBinayFile.persistToMedia();
    }


    /**
     * Returns a stored draft transaction
     * @param blockchainNetworkType
     * @param txHash
     * @return
     * @throws CantGetDraftTransactionException
     */
    public DraftTransaction getDraftTransaction(BlockchainNetworkType blockchainNetworkType, String txHash) throws CantGetDraftTransactionException{
        try {
            PluginBinaryFile pluginBinaryFile = pluginFileSystem.getBinaryFile(pluginId, DRAFT_TRANSACTION_PATH, txHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginBinaryFile.loadFromMedia();
            byte[] serializedDraftTransaction = pluginBinaryFile.getContent();

            DraftTransaction draftTransaction = DraftTransaction.deserialize(blockchainNetworkType, serializedDraftTransaction);
            return draftTransaction;
        } catch (Exception e) {
            CantGetDraftTransactionException exception = new CantGetDraftTransactionException(CantGetDraftTransactionException.DEFAULT_MESSAGE, e, "IO error while getting the draft transaction from disk. txHash: " + txHash, "PluginFileSystem");
            errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public ECKey getPrivateKey(Address address) {
        ECKey privateKey = this.vaultKeyHierarchyGenerator.getVaultKeyHierarchy().getPrivateKey(address);
        return privateKey;
    }
}
