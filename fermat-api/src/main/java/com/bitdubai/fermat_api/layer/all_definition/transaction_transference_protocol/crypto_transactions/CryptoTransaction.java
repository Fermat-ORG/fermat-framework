package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

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
 * Created by eze on 11/06/15.
 */

public class CryptoTransaction{
    private String transactionHash;
    private String blockHash;
    private BlockchainNetworkType blockchainNetworkType;
    private CryptoAddress addressFrom;
    private CryptoAddress addressTo;
    private CryptoCurrency cryptoCurrency;
    private long btcAmount;
    private long fee;
    private long cryptoAmount; // btcAmount + fee
    private CryptoStatus cryptoStatus;
    private String op_Return;
    private CryptoTransactionType cryptoTransactionType;
    private int blockDepth;





    /**
     * Overloaded constructor
     * @param transactionHash
     * @param blockchainNetworkType
     * @param addressFrom
     * @param addressTo
     * @param cryptoCurrency
     * @param cryptoAmount
     * @param cryptoStatus
     */
    public CryptoTransaction(String transactionHash,
                             BlockchainNetworkType blockchainNetworkType,
                             CryptoAddress addressFrom,
                             CryptoAddress addressTo,
                             CryptoCurrency cryptoCurrency,
                             long cryptoAmount,
                             CryptoStatus cryptoStatus) {
        this.transactionHash = transactionHash;
        this.blockchainNetworkType = blockchainNetworkType;
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
    public static CryptoTransaction getCryptoTransaction(BlockchainNetworkType blockchainNetworkType, Transaction transaction){
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setTransactionHash(transaction.getHashAsString());
        cryptoTransaction.setCryptoCurrency(CryptoCurrency.BITCOIN);
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

            cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
        } catch (Exception e){
            /**
             * if there is an error, because this may not always be possible to get.
             */
            cryptoAddress = new CryptoAddress("Empty", CryptoCurrency.BITCOIN);
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

            cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
            return cryptoAddress;
        } catch (Exception e){
            return cryptoAddress = new CryptoAddress("Empty", CryptoCurrency.BITCOIN);
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
            else if(depth > 0 && depth < 3)
                return CryptoStatus.ON_BLOCKCHAIN;
            else if (depth >= 3)
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
     * Will deserialize into CryptoTransaction the bitcoin transaction passed
     * @param wallet
     * @param transaction
     * @return
     */
    public static List<CryptoTransaction> getCryptoTransactions(BlockchainNetworkType blockchainNetworkType, Wallet wallet, Transaction transaction){
        List<CryptoTransaction> cryptoTransactions = new ArrayList<>();

        try{
            /**
             * for each output that sends us bitcoins, I will create INC transactions
             */
            for (TransactionOutput output : transaction.getWalletOutputs(wallet)){
                CryptoTransaction incomingCryptoTransaction = getBaseCryptoTransaction(transaction, blockchainNetworkType);
                incomingCryptoTransaction.setCryptoTransactionType(CryptoTransactionType.INCOMING);
                incomingCryptoTransaction.setBtcAmount(output.getValue().getValue());
                incomingCryptoTransaction.setCryptoAmount(incomingCryptoTransaction.getBtcAmount());
                incomingCryptoTransaction.setAddressTo(new CryptoAddress(output.getAddressFromP2PKHScript(transaction.getParams()).toString(), CryptoCurrency.BITCOIN));
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

                        CryptoTransaction outgoingCryptoTransaction = getBaseCryptoTransaction(transaction, blockchainNetworkType);
                        outgoingCryptoTransaction.setCryptoTransactionType(CryptoTransactionType.OUTGOING);
                        outgoingCryptoTransaction.setBtcAmount(outputs.getValue().getValue());
                        outgoingCryptoTransaction.setCryptoAmount(outgoingCryptoTransaction.getBtcAmount() + outgoingCryptoTransaction.getFee());
                        outgoingCryptoTransaction.setAddressFrom(new CryptoAddress(output.getAddressFromP2PKHScript(transaction.getParams()).toString(), CryptoCurrency.BITCOIN));
                        outgoingCryptoTransaction.setAddressTo(new CryptoAddress(outputs.getAddressFromP2PKHScript(transaction.getParams()).toString(), CryptoCurrency.BITCOIN));
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
    private static CryptoTransaction getBaseCryptoTransaction(Transaction transaction, BlockchainNetworkType blockchainNetworkType) {
        /**
         * will define all common properties of whatever cryptoTransaction we return
         */
        final CryptoCurrency CRYPTO_CURRENCY = CryptoCurrency.BITCOIN;
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
        //todo improve.
        if (fee ==0)
            fee = 30000;

        return fee;
    }

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

    /**
     * Getters
     * */

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
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

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
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

    public CryptoTransactionType getCryptoTransactionType() {
        return cryptoTransactionType;
    }

    public void setCryptoTransactionType(CryptoTransactionType cryptoTransactionType) {
        this.cryptoTransactionType = cryptoTransactionType;
    }

    public int getBlockDepth() {
        return blockDepth;
    }

    public void setBlockDepth(int blockDepth) {
        this.blockDepth = blockDepth;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(long btcAmount) {
        this.btcAmount = btcAmount;
    }
}
