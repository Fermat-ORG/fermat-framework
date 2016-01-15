package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.thoughtworks.xstream.XStream;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the metadata associated with a single instance of
 * a {@link DigitalAsset}, every asset can be distributed and redeemed
 * several amount of times, and this object keeps all the needing information
 * to keep track of it.
 * <p/>
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {
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

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    public DigitalAssetMetadata() {
        this.digitalAsset = null;
    }

    private String generateHash() {
        digitalAsset.setState(State.FINAL);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(digitalAsset);
        return CryptoHasher.performSha256(xml);
    }

    public String getDigitalAssetHash() {
        return generateHash();
    }

    public String getGenesisTransaction() {
        return getTransactionByDepth(1).getKey();
    }

    public String getGenesisBlock() {
        return getTransactionByDepth(1).getValue();
    }

    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }

    public DigitalAsset getDigitalAsset() {
        return this.digitalAsset;
    }

    public void setDigitalAsset(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
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

    public String getLastTransactionHash() {
        return getTransactionByDepth(transactionChain.size()).getKey();
    }

    public String getLastTransactionBlock() {
        return getTransactionByDepth(transactionChain.size()).getValue();
    }

    public LinkedHashMap<String, String> getTransactionChain() {
        return transactionChain;
    }

    public void addNewTransaction(String transactionHash, String blockHash) {
        transactionChain.put(transactionHash, blockHash);
    }

    public void removeTransaction(String transactionHash) {
        transactionChain.remove(transactionHash);
    }

}
