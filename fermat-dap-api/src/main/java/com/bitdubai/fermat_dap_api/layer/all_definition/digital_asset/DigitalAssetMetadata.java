package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetMetadata {
    DigitalAsset digitalAsset;
    String genesisTransaction;
    String hash;

    public DigitalAssetMetadata(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    private String  generateHash(){
        digitalAsset.setState(State.FINAL);
        return CryptoHasher.performSha256(digitalAsset.toString());
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

    @Override
    public String toString(){
        /*String digitalAssetMetadataString="\nDigital Asset Metadata:\n" +
                "Digital Asset XML:\n"+digitalAsset+"\n" +
                "Genesis Transaction: "+genesisTransaction+"\n" +
                "Hash:"+hash;
        return digitalAssetMetadataString;*/
        //For testing and future use, I need the XML from this object
        return XMLParser.parseObject(this);
    }

    public DigitalAsset getDigitalAsset(){
        return this.digitalAsset;
    }

}
