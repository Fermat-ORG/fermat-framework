package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.script.ScriptChunk;

import java.util.Map;

/**
 * Created by eze on 11/06/15.
 */

public class CryptoTransaction{
    private String transactionHash;
    private String blockHash;
    private CryptoAddress addressFrom;
    private CryptoAddress addressTo;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private CryptoStatus cryptoStatus;
    private String op_Return;




    /**
     * Overloaded constructor
     * @param transactionHash
     * @param addressFrom
     * @param addressTo
     * @param cryptoCurrency
     * @param cryptoAmount
     * @param cryptoStatus
     */
    public CryptoTransaction(String transactionHash,
                             CryptoAddress addressFrom,
                             CryptoAddress addressTo,
                             CryptoCurrency cryptoCurrency,
                             long cryptoAmount,
                             CryptoStatus cryptoStatus) {

        this.transactionHash = transactionHash;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.cryptoStatus = cryptoStatus;
    }


    /**
     * Default constructor.
     */
    public CryptoTransaction(){}

    /**
     * Static method that creates a CryptoTransaction from a Bitcoin Transaction
     * Some CryptoTransaction properties, depends whether this is an incoming or outgoing
     * transaction.
     * @param transaction
     * @return
     */
    public static CryptoTransaction getCryptoTransaction(Transaction transaction){
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionHash(transaction.getHashAsString());
        cryptoTransaction.setCryptoCurrency(CryptoCurrency.BITCOIN);
        cryptoTransaction.setOp_Return(getOpReturn(transaction));
        cryptoTransaction.setBlockHash(getBlockHash(transaction));
        cryptoTransaction.setCryptoStatus(getTransactionCryptoStatus(transaction));

        return cryptoTransaction;
    }


    /**
     * Gets the CryptoStatus of the trasaction.
     * @param transaction
     * @return
     */
    private static CryptoStatus getTransactionCryptoStatus(Transaction transaction) {
        try{
            int depth = transaction.getConfidence().getDepthInBlocks();

            if (depth == 0)
                return CryptoStatus.ON_CRYPTO_NETWORK;
            else if(depth == 1)
                return CryptoStatus.ON_BLOCKCHAIN;
            else if (depth >= 2)
                return CryptoStatus.IRREVERSIBLE;
            else
                return CryptoStatus.PENDING_SUBMIT;
        } catch (Exception e){
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
     * Getters
     * */

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public CryptoCurrency getCryptoCurrency() {return cryptoCurrency;}

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    public String getOp_Return() {
        return op_Return;
    }

    /**
     * setters
     */

    public void setOp_Return(String op_Return) {
        this.op_Return = op_Return;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public void setAddressFrom(CryptoAddress addressFrom) {
        this.addressFrom = addressFrom;
    }

    public void setAddressTo(CryptoAddress addressTo) {
        this.addressTo = addressTo;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public void setCryptoStatus(CryptoStatus cryptoStatus) {
        this.cryptoStatus = cryptoStatus;
    }
}
