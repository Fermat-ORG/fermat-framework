package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.fermat.FermatNetworkConfiguration;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.script.ScriptChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigo on 6/29/16.
 */
public class FermatTransactionConverter {

    /**
     * only static methods in this class.
     */
    private FermatTransactionConverter(){}

    /**
     * Static method that creates a CryptoTransaction from a Bitcoin Transaction
     * Some CryptoTransaction properties, depends whether this is an incoming or outgoing
     * transaction.
     * @param transaction
     * @return
     */
    public static CryptoTransaction getCryptoTransaction(BlockchainNetworkType blockchainNetworkType, Transaction transaction, CryptoCurrency cryptoCurrency){
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionHash(transaction.getHashAsString());
        cryptoTransaction.setCryptoCurrency(cryptoCurrency);
        cryptoTransaction.setOp_Return(getOpReturn(transaction));
        cryptoTransaction.setBlockHash(getBlockHash(transaction));
        cryptoTransaction.setCryptoStatus(getTransactionCryptoStatus(transaction));
        cryptoTransaction.setAddressTo(getAddressTo(transaction));
        cryptoTransaction.setAddressFrom(getAddressFrom(transaction));
        cryptoTransaction.setBlockchainNetworkType(blockchainNetworkType);


        return cryptoTransaction;
    }

    /**
     * gets the address From of this transaction
     * @param transaction
     * @return
     */
    private static CryptoAddress getAddressFrom(Transaction transaction) {
        CryptoAddress cryptoAddress= null;
        try{
            Address address = null;

            for (TransactionInput input : transaction.getInputs()){
                if (input.getFromAddress() != null)
                    address = input.getFromAddress();
            }

            cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.FERMAT);
        } catch (Exception e){
            /**
             * if there is an error, because this may not always be possible to get.
             */
            cryptoAddress = new CryptoAddress("Empty", CryptoCurrency.FERMAT);
        }
        return cryptoAddress;
    }

    /**
     * Gets the address to of this transaction
     * @param transaction
     * @return
     */
    private static CryptoAddress getAddressTo(Transaction transaction) {
        CryptoAddress cryptoAddress = null;
        try{
            Address address = null;
            /**
             * I will loop from the outputs that include keys that are in my wallet
             */
            for (TransactionOutput output : transaction.getOutputs()){
                /**
                 * get the address from the output
                 */
                address = output.getScriptPubKey().getToAddress(transaction.getParams());
            }

            cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.FERMAT);
            return cryptoAddress;
        } catch (Exception e){
            return cryptoAddress = new CryptoAddress("Empty", CryptoCurrency.FERMAT);
        }

    }


    /**
     * Gets the CryptoStatus of the transaction.
     * @param transaction
     * @return
     */
    public static CryptoStatus getTransactionCryptoStatus(Transaction transaction) {
        try{
            TransactionConfidence transactionConfidence = transaction.getConfidence();
            int depth = transactionConfidence.getDepthInBlocks();
            TransactionConfidence.ConfidenceType confidenceType = transactionConfidence.getConfidenceType();
            int broadcasters = transactionConfidence.getBroadcastBy().size();

            if (broadcasters == 0 && transactionConfidence.getSource() == TransactionConfidence.Source.SELF && depth == 0)
                return CryptoStatus.PENDING_SUBMIT;
            else if (depth == 0 && confidenceType == TransactionConfidence.ConfidenceType.PENDING)
                return CryptoStatus.ON_CRYPTO_NETWORK;
            else if(depth > 0 && depth < FermatNetworkConfiguration.IRREVERSIBLE_BLOCK_DEPTH)
                return CryptoStatus.ON_BLOCKCHAIN;
            else if (depth >= FermatNetworkConfiguration.IRREVERSIBLE_BLOCK_DEPTH)
                return CryptoStatus.IRREVERSIBLE;
            else
                return CryptoStatus.PENDING_SUBMIT;
        } catch (Exception e){
            System.out.println("***CryptoNetwork*** error calculating CryptoStatus on CryptoTransaction.");
            System.out.println(transaction.toString());
            e.printStackTrace();
            return CryptoStatus.ON_CRYPTO_NETWORK;
        }
    }

    /**
     * Will get the first block where this transaction was included, if any.
     * @param transaction
     * @return
     */
    private static String getBlockHash(Transaction transaction) {
        try{
            for (Map.Entry<Sha256Hash, Integer> entry : transaction.getAppearsInHashes().entrySet()){
                Sha256Hash hash = entry.getKey();
                return hash.toString();
            }
        } catch (Exception e){
            /**
             * return if error
             */
            return null;
        }

        /**
         * will return null if the transaction is not in a block
         */
        return null;


    }

    /**
     * Gets the OP_Return, if any, from the transaction output
     * @param transaction
     * @return
     */
    private static String getOpReturn(Transaction transaction) {
        String hash = "";
        try{
            for (TransactionOutput output : transaction.getOutputs()){
                /**
                 * if this is an OP_RETURN output, I will get the hash
                 */
                if (output.getScriptPubKey().isOpReturn()){
                    /**
                     * I get the chunks of the Script to get the op_Return value
                     */
                    for (ScriptChunk chunk : output.getScriptPubKey().getChunks()){
                        if (chunk.equalsOpCode(64))
                            hash = new String(chunk.data);
                    }
                }
            }
        } catch (Exception e){
            return "";
        }
        return hash;
    }

    /**
     * Will deserialize into CryptoTransaction the fermat transaction passed
     * @param wallet
     * @param transaction
     * @return
     */
    public static List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, CryptoCurrency cryptoCurrency, Wallet wallet, Transaction transaction){
        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();

        try{
            /**
             * for each output that sends us fermats, I will create INC transactions
             */
            for (TransactionOutput output : transaction.getWalletOutputs(wallet)){
                CryptoTransaction incomingCryptoTransaction = getBaseCryptoTransaction(transaction, cryptoCurrency, blockchainNetworkType);
                incomingCryptoTransaction.setCryptoTransactionType(CryptoTransactionType.INCOMING);
                incomingCryptoTransaction.setBtcAmount(output.getValue().getValue());
                incomingCryptoTransaction.setCryptoAmount(incomingCryptoTransaction.getBtcAmount());
                incomingCryptoTransaction.setAddressTo(new CryptoAddress(output.getAddressFromP2PKHScript(transaction.getParams()).toString(), cryptoCurrency));
                incomingCryptoTransaction.setAddressFrom(getAddressFrom(transaction));


                cryptoTransactions.add(incomingCryptoTransaction);
            }

            /**
             * I will get the connected outputs of the inputs that are mine, to form the OUT transactions
             */
            for (TransactionInput input : transaction.getInputs()){
                TransactionOutput output = input.getConnectedOutput();
                if (output == null)
                    continue;

                if (output.isMine(wallet)){
                    /**
                     * I will have to form transactions for each address we are sending money to.
                     */
                    for (TransactionOutput outputs : transaction.getOutputs()){

                        // if this aint a sent to address output, I don't need it.
                        if (!outputs.getScriptPubKey().isSentToAddress())
                            continue;

                        CryptoTransaction outgoingCryptoTransaction = getBaseCryptoTransaction(transaction,cryptoCurrency, blockchainNetworkType);
                        outgoingCryptoTransaction.setCryptoTransactionType(CryptoTransactionType.OUTGOING);
                        outgoingCryptoTransaction.setBtcAmount(outputs.getValue().getValue());
                        outgoingCryptoTransaction.setCryptoAmount(outgoingCryptoTransaction.getBtcAmount() + outgoingCryptoTransaction.getFee());
                        outgoingCryptoTransaction.setAddressFrom(new CryptoAddress(output.getAddressFromP2PKHScript(transaction.getParams()).toString(), cryptoCurrency));
                        outgoingCryptoTransaction.setAddressTo(new CryptoAddress(outputs.getAddressFromP2PKHScript(transaction.getParams()).toString(), cryptoCurrency));
                        cryptoTransactions.add(outgoingCryptoTransaction);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return cryptoTransactions;
    }

    /**
     * Returns the base CryptoTransaction with all non changing properties
     * @param transaction
     * @return
     */
    private static CryptoTransaction getBaseCryptoTransaction(Transaction transaction, CryptoCurrency cryptoCurrency,  BlockchainNetworkType blockchainNetworkType) {
        /**
         * will define all common properties of whatever cryptoTransaction we return
         */
        final CryptoCurrency CRYPTO_CURRENCY = cryptoCurrency;
        final String TRANSACTION_HASH = transaction.getHashAsString();
        final CryptoStatus CRYPTO_STATUS = getTransactionCryptoStatus(transaction);
        final String BLOCK_HASH = getBlockHash(transaction);
        final String OP_RETURN = getOpReturn(transaction);
        final long FEE = getCryptoTransactionFee(transaction);
        final int BLOCK_DEPTH = getBlockDepth(transaction);

        CryptoTransaction baseCryptoTransaction = new CryptoTransaction();
        baseCryptoTransaction.setBlockchainNetworkType(blockchainNetworkType);
        baseCryptoTransaction.setTransactionHash(TRANSACTION_HASH);
        baseCryptoTransaction.setCryptoCurrency(CRYPTO_CURRENCY);
        baseCryptoTransaction.setCryptoStatus(CRYPTO_STATUS);
        baseCryptoTransaction.setBlockHash(BLOCK_HASH);
        baseCryptoTransaction.setOp_Return(OP_RETURN);
        baseCryptoTransaction.setFee(FEE);
        baseCryptoTransaction.setBlockDepth(BLOCK_DEPTH);
        return baseCryptoTransaction;
    }

    private static int getBlockDepth(Transaction transaction){
        return transaction.getConfidence().getDepthInBlocks();
    }


    private static long getCryptoTransactionFee(Transaction transaction){
        long fee = (transaction.getFee() == null) ? 0 : transaction.getFee().getValue();

        /**
         * if fee is 0, Will try to recalculate
         */
        if (fee ==0)
            fee = FermatNetworkConfiguration.FIXED_FEE_VALUE;

        return fee;
    }

    /**
     * returns a copy of the passed crypto transaction.
     * @param previousCryptoTransaction
     * @return
     */
    public static CryptoTransaction copyCryptoTransaction(CryptoTransaction previousCryptoTransaction){
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionHash(previousCryptoTransaction.getTransactionHash());
        cryptoTransaction.setBlockHash(previousCryptoTransaction.getBlockHash());
        cryptoTransaction.setBlockchainNetworkType(previousCryptoTransaction.getBlockchainNetworkType());
        cryptoTransaction.setCryptoStatus(previousCryptoTransaction.getCryptoStatus());
        cryptoTransaction.setBlockDepth(previousCryptoTransaction.getBlockDepth());
        cryptoTransaction.setCryptoCurrency(previousCryptoTransaction.getCryptoCurrency());
        cryptoTransaction.setAddressTo(previousCryptoTransaction.getAddressTo());
        cryptoTransaction.setAddressFrom(previousCryptoTransaction.getAddressFrom());
        cryptoTransaction.setBtcAmount(previousCryptoTransaction.getBtcAmount());
        cryptoTransaction.setFee(previousCryptoTransaction.getFee());
        cryptoTransaction.setCryptoAmount(previousCryptoTransaction.getCryptoAmount());
        cryptoTransaction.setOp_Return(previousCryptoTransaction.getOp_Return());
        cryptoTransaction.setCryptoTransactionType(previousCryptoTransaction.getCryptoTransactionType());


        return cryptoTransaction;
    }
}
