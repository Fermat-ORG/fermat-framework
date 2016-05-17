package org.fermat.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAsset implements Serializable {
    /**
     * properties defined by the Issuer
     */
    String publicKey;
    org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer identityAssetIssuer;
    String name;
    String description;
    List<Resource> resources;
    DigitalAssetContract contract;
    @XStreamOmitField
    long genesisAmount;

    /**
     * Properties defined by the Asset Issuer Transaction
     */
    CryptoAddress genesisAddress;

    State state;

    /**
     * default constructor
     */
    public DigitalAsset() {
        this.state = State.DRAFT;
    }

    public static DigitalAsset copyAsset(DigitalAsset previousAsset) {
        DigitalAsset newAsset = new DigitalAsset();
        newAsset.setGenesisAddress(previousAsset.getGenesisAddress());
        newAsset.setGenesisAmount(previousAsset.getGenesisAmount());
        newAsset.setIdentityAssetIssuer(previousAsset.getIdentityAssetIssuer());
        newAsset.setContract(previousAsset.getContract());
        newAsset.setResources(previousAsset.getResources());
        newAsset.setDescription(previousAsset.getDescription());
        newAsset.setName(previousAsset.getName());
        newAsset.setState(previousAsset.getState());
        newAsset.setPublicKey(previousAsset.getPublicKey());
        return newAsset;
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

    public IdentityAssetIssuer getIdentityAssetIssuer() {
        return identityAssetIssuer;
    }

    public void setIdentityAssetIssuer(IdentityAssetIssuer identityAssetIssuer) {
        this.identityAssetIssuer = identityAssetIssuer;
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
        if (resources == null) return Collections.EMPTY_LIST;
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

    /**
     * The string of the Digital Asset will be used to generate a unique Hash
     * I generate an XML with the class structure
     *
     * @return
     */
    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }
}
