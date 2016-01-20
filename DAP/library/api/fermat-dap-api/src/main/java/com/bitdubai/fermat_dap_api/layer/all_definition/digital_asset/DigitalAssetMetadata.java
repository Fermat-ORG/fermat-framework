package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.thoughtworks.xstream.XStream;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {
    DigitalAsset digitalAsset;
    String genesisTransaction;
    String genesisBlock;
    String hash;

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    //I'm gonna add this constructor for now, TODO: I need to make a test to get this object with this constructor and without it
    public DigitalAssetMetadata(){
        this.digitalAsset = null;
    }

    private String  generateHash(){
        digitalAsset.setState(State.FINAL);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(digitalAsset);
        return CryptoHasher.performSha256(xml);
    }

    public String getDigitalAssetHash() {
        hash = generateHash();
        return hash;
    }

    public String getGenesisTransaction() {
        return genesisTransaction;
    }

    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    public String getGenesisBlock() {
        return genesisBlock;
    }

    public void setGenesisBlock(String genesisBlock) {
        this.genesisBlock = genesisBlock;
    }

    @Override
    public String toString(){
        return XMLParser.parseObject(this);
    }

    public DigitalAsset getDigitalAsset(){
        return this.digitalAsset;
    }
    public void setDigitalAsset(DigitalAsset digitalAsset){
        this.digitalAsset=digitalAsset;
    }

}
