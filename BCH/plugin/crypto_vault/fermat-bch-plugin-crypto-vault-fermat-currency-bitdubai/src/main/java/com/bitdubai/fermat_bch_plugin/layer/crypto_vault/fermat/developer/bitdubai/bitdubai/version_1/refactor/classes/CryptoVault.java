package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.FermatNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;



import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed.exceptions.InvalidSeedException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.util.FermatBlockchainNetworkSelector;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 2/26/16.
 */
public abstract class CryptoVault {
    /**
     * Platform variables
     */
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    BlockchainManager<ECKey, Transaction> blockchainCryptoManager;

    /**
     * Constants variables
     */
    final String CRYPTO_VAULT_SEED_FILEPATH;
    final String CRYPTO_VAULT_SEED_FILENAME;

    /**
     * Constructor
     * @param pluginFileSystem
     * @param pluginId
     * @param blockchainCryptoManager
     * @param CRYPTO_VAULT_SEED_FILEPATH
     * @param CRYPTO_VAULT_SEED_FILENAME
     */
    public CryptoVault(PluginFileSystem pluginFileSystem,
                       UUID pluginId,
                       BlockchainManager<ECKey, Transaction> blockchainCryptoManager,
                       String CRYPTO_VAULT_SEED_FILEPATH,
                       String CRYPTO_VAULT_SEED_FILENAME) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.blockchainCryptoManager = blockchainCryptoManager;
        this.CRYPTO_VAULT_SEED_FILEPATH = CRYPTO_VAULT_SEED_FILEPATH;
        this.CRYPTO_VAULT_SEED_FILENAME = CRYPTO_VAULT_SEED_FILENAME;
    }


    public Transaction signTransaction(List<ECKey> walletKeys, Transaction transactionToSign) throws CantSignTransactionException{
        //validate parameters
        if ((walletKeys == null || walletKeys.size() == 0) || transactionToSign == null)
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, null, "SignTransaction parameters can't be null", "null parameters.");

        final NetworkParameters NETWORK_PARAMETERS = transactionToSign.getParams();

        /**
         * Create the bitcoinj wallet from our seed and by importing the keys of the passed account.
         */
        final Wallet wallet;
        try {
            wallet = Wallet.fromSeed(NETWORK_PARAMETERS, getVaultSeed());
            wallet.importKeys(walletKeys);
        } catch (InvalidSeedException e) {
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, e, "Unable to create wallet from seed.", "seed issue");
        }

        /**
         * Once I get the wallet, I will get the outputs that are referenced by the transaction inputs that are mine with the original transaction inputs.
         */
        for (Map.Entry<TransactionInput, TransactionOutput> entry : getOwnOutputsForSigning(wallet, transactionToSign).entrySet()){
            Script scriptToSign = entry.getValue().getScriptPubKey();

            /**
             * I need to get the index of the input I'm going to sign.
             * Since the input class doesn't have an index selector, I need to check each one of them
             */
            int inputIndex = 0;
            for (TransactionInput intputToSign : transactionToSign.getInputs()){
                if (intputToSign.equals(entry.getKey()))
                    break;
                else
                    inputIndex++;
            }


            /**
             * I get the signature hash for my output.
             */
            Sha256Hash sigHash = transactionToSign.hashForSignature(inputIndex, scriptToSign, Transaction.SigHash.ALL, false);

            /**
             * I get the private key that I will use to sign the hash
             */
            ECKey privateKey = getPrivateKey(scriptToSign.getToAddress(NETWORK_PARAMETERS));

            /**
             * If I didn't find a matching private key, then I will continue with another script.
             */
            if (privateKey == null)
                continue;


            /**
             * I create the signature
             */
            ECKey.ECDSASignature signature = privateKey.sign(sigHash).toCanonicalised();

            TransactionSignature transactionSignature = new TransactionSignature(signature, Transaction.SigHash.ALL, false);

            Script inputScript = ScriptBuilder.createInputScript(transactionSignature, privateKey);

            /**
             * I will add the signature to the input script of the transaction
             */
            transactionToSign.getInput(inputIndex).setScriptSig(inputScript);

            /**
             * Verify everything is ok
             */
            try{
                transactionToSign.getInput(inputIndex).verify(entry.getValue());
            } catch (VerificationException e){
                throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, e, "Error during signing of transaction.", "incorrect signature");
            }
        }

        /**
         * I have created all the script for the original outputs that holds the funds of the transaction
         * I have sign them with a vault key and attach the signature to each input. I'm ready to return the transaction
         */
        return transactionToSign;
    }

    /**
     * Based on the public Key, I get the corresponding private key
     * @param address
     * @return
     */
    public abstract ECKey getPrivateKey(Address address);



    /**
     * Gets the transaction outputs referenced in the passed transaction Input outpoints that are mine.
     * @param wallet my wallet
     * @param transaction the transaction to analize
     * @return the outputs used in the passed transaction as inputs that are mine.
     */
    private HashMap<TransactionInput, TransactionOutput> getOwnOutputsForSigning(Wallet wallet, Transaction transaction){
        HashMap<TransactionInput, TransactionOutput> inputOutputMap = new HashMap<>();

        final BlockchainNetworkType blockchainNetworkType = FermatBlockchainNetworkSelector.getBlockchainNetworkType(transaction.getParams());

        for (TransactionInput transactionInput : transaction.getInputs()){
            TransactionOutPoint outPoint = transactionInput.getOutpoint();
            Sha256Hash outputtHash = outPoint.getHash();
            long outputIndex = outPoint.getIndex();

            /**
             * I will get the transaction that is referenced in the input
             */
            Transaction outputTransaction = blockchainCryptoManager.getBlockchainProviderTransaction(blockchainNetworkType, outputtHash.toString());
            if (outputTransaction != null){
                /**
                 * I will get the referenced output of the transaction and check if it is mine
                 */
                TransactionOutput output = outputTransaction.getOutput(outputIndex);

                if (output != null){
                    /**
                     * If they are mine, I will add them to the list with the input that we are evaluating
                     */
                    if (output.isMine(wallet)){
                        inputOutputMap.put(transactionInput, output);
                    }

                }
            }
        }
        return inputOutputMap;
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    public DeterministicSeed getVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, CRYPTO_VAULT_SEED_FILEPATH, CRYPTO_VAULT_SEED_FILENAME);
            if (!vaultSeedGenerator.seedExists()){
                vaultSeedGenerator.create();
                /**
                 * I reload it to make sure I'm using the seed I will start using from now on. Issue #3330
                 */
                vaultSeedGenerator.load();
            } else
                vaultSeedGenerator.load();
            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
            seed.check();
            return seed;
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
        } catch (MnemonicException e) {
            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
        }
    }
//
//    public DeterministicSeed getVaultSeed(String seedName) throws InvalidSeedException,CantFounSeedException{
//        try{
//            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, CRYPTO_VAULT_SEED_FILEPATH, seedName);
//            if (!vaultSeedGenerator.seedExists()){
//                throw new CantFounSeedException(new Exception("Seed not found"));
//            } else
//                vaultSeedGenerator.load();
//            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
//            seed.check();
//            return seed;
//        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
//            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
//        } catch (MnemonicException e) {
//            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
//        }
//    }


//    public void importMnemonicSeed(List<String> mnemonicCode,long creationTime,@Nullable String userPhrase) throws InvalidSeedException {
//        try{
//            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, CRYPTO_VAULT_SEED_FILEPATH, CRYPTO_VAULT_SEED_FILENAME);
//            if (!vaultSeedGenerator.seedExists()) {
//                vaultSeedGenerator.createFrom(mnemonicCode, creationTime, userPhrase);
//            }
//        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
//            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
//        }
//    }

    private Wallet tempWallet;
    AtomicBoolean booleanProperty;
    SPVBlockStore chainStore;
    BlockChain chain;



    public void importCryptoFromSeed(final NetworkParameters networkParameters,List<String> mnemonicCode,long creationTimeSeconds,@Nullable String userPhrase){



    }



    /**
     * It validates if the amount to be send it less than what the network is allowing.
     * @param satoshisToSend
     * @return true if this is a dusty send and we shouldn't allow to send it.
     */
    public static boolean isDustySend(long satoshisToSend){
        if (satoshisToSend < FermatNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND)
            return true;
        else
            return false;
    }
}
