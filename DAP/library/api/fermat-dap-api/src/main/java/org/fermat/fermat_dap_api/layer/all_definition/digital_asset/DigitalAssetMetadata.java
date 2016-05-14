package org.fermat.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.thoughtworks.xstream.XStream;

import org.fermat.fermat_dap_api.layer.all_definition.enums.State;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents the metadata associated with a single instance of
 * a {@link DigitalAsset}, every asset can be distributed and redeemed
 * several amount of times, and this object keeps all the needing information
 * to keep track of it.
 * <p/>
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {

    //VARIABLE DECLARATION
    /**
     * This ID is used for identified the metadata,
     * since this metadata have lots of transactions and there can be
     * the same asset in the same devices at different steps of the process
     * we need to identify every single of them so it can get represented on
     * the asset statistics.
     */

    private UUID metadataId;

    {
        //If its a previous metadata then we will need to override this.
        metadataId = UUID.randomUUID();
    }

    /**
     * The {@link DigitalAsset} to which this object
     * corresponds too.
     */
    private DigitalAsset digitalAsset;

    /**
     * This map was created as {@link LinkedHashMap} on purpose.
     * It will store all the transactions and their blocks associated with this
     * DigitalAsset so we can do further validations.
     */
    private LinkedHashMap<String, String> transactionChain;

    {
        transactionChain = new LinkedHashMap<>();
    }

    /**
     * The network type where this asset belongs.
     */
    private BlockchainNetworkType networkType;

    {
        networkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
    }

    private org.fermat.fermat_dap_api.layer.dap_actor.DAPActor lastOwner;

    //CONSTRUCTORS

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    public DigitalAssetMetadata() {
        this.digitalAsset = null;
    }

    public DigitalAssetMetadata(UUID metadataId, DigitalAsset digitalAsset, LinkedHashMap<String, String> transactionChain, BlockchainNetworkType networkType, org.fermat.fermat_dap_api.layer.dap_actor.DAPActor lastOwner) {
        this.metadataId = metadataId;
        this.digitalAsset = digitalAsset;
        this.transactionChain = transactionChain;
        this.networkType = networkType;
        this.lastOwner = lastOwner;
    }

    //PRIVATE METHODS

    private String generateHash() {
        digitalAsset.setState(State.FINAL);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(digitalAsset);
        return CryptoHasher.performSha256(xml);
    }

    private Map.Entry<String, String> getTransactionByDepth(int transactionDepth) {
        int actualDepth = 0;
        for (Map.Entry<String, String> entry : transactionChain.entrySet()) {
            actualDepth++;
            if (actualDepth == transactionDepth) {
                return entry;
            }

            if (actualDepth > transactionDepth) break; //Did what I could :(
        }
        throw new IllegalStateException("This depth is not found on this transaction chain.");
    }


    //PUBLIC METHODS
    public void addNewTransaction(CryptoTransaction cryptoTransaction) {
        addNewTransaction(cryptoTransaction.getTransactionHash(), cryptoTransaction.getBlockHash());
    }

    public void addNewTransaction(String transactionHash, String blockHash) {
        transactionChain.put(transactionHash, blockHash);
    }

    public void removeLastTransaction() {
        transactionChain.remove(getLastTransactionHash());
    }

    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof DigitalAssetMetadata)) return false;

        DigitalAssetMetadata that = (DigitalAssetMetadata) o;

        if (!getMetadataId().equals(that.getMetadataId())) return false;
        if (!getDigitalAsset().equals(that.getDigitalAsset())) return false;
        return getTransactionChain().equals(that.getTransactionChain());

    }

    @Override
    public int hashCode() {
        int result = getMetadataId().hashCode();
        result = 31 * result + getDigitalAsset().hashCode();
        result = 31 * result + getTransactionChain().hashCode();
        return result;
    }

    //GETTERS AND SETTERS
    public String getDigitalAssetHash() {
        return generateHash();
    }

    public String getGenesisTransaction() {
        return getTransactionByDepth(1).getKey();
    }

    public String getGenesisBlock() {
        return getTransactionByDepth(1).getValue();
    }

    public DigitalAsset getDigitalAsset() {
        return this.digitalAsset;
    }

    public void setDigitalAsset(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    public UUID getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(UUID metadataId) {
        this.metadataId = metadataId;
    }

    public String getLastTransactionHash() {
        return getTransactionByDepth(transactionChain.size()).getKey();
    }

    public String getLastTransactionBlock() {
        return getTransactionByDepth(transactionChain.size()).getValue();
    }

    public LinkedHashMap<String, String> getTransactionChain() {
        return transactionChain;
    }

    public org.fermat.fermat_dap_api.layer.dap_actor.DAPActor getLastOwner() {
        return lastOwner;
    }

    public void setLastOwner(org.fermat.fermat_dap_api.layer.dap_actor.DAPActor lastOwner) {
        this.lastOwner = lastOwner;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }
}
