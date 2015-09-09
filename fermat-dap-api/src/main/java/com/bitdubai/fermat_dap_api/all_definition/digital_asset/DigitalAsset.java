package com.bitdubai.fermat_dap_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAsset  implements Serializable{
    /**
     * properties defined by the Issuer
     */
    String publicKey;
    String name;
    String description;
    List<Resource> resources;
    DigitalAssetContract contract;
    int quantity;
    long unitValue;
    long genesisAmount;
    long transactionFee;



    /**
     * Properties defined by the Asset Issuer Transaction
     */
    String genesisTransaction;
    CryptoAddress genesisAddress;

    State state;

    /**
     * default constructor
     */
    public DigitalAsset() {
        this.state = State.DRAFT;
    }

    /**
     * Getters and setters
     */
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public DigitalAssetContract getContract() {
        return contract;
    }

    public void setContract(DigitalAssetContract contract) {
        this.contract = contract;
    }

    public String getGenesisTransaction() {
        return genesisTransaction;
    }

    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    public CryptoAddress getGenesisAddress() {
        return genesisAddress;
    }

    public void setGenesisAddress(CryptoAddress genesisAddress) {
        this.genesisAddress = genesisAddress;
    }

    public long getGenesisAmount() {
        return genesisAmount;
    }

    public void setGenesisAmount(long genesisAmount) {
        this.genesisAmount = genesisAmount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(long transactionFee) {
        this.transactionFee = transactionFee;
    }

    public long getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(long unitValue) {
        this.unitValue = unitValue;
    }

    /**
     * The string of the Digital Asset will be used to generate a unique Hash
     * I generate an XML with the class structure
     * @return
     */
    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }
}
