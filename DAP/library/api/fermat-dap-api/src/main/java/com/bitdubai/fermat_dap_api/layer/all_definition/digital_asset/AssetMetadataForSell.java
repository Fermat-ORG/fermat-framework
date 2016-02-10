package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public class AssetMetadataForSell extends DigitalAssetMetadata {

    //VARIABLE DECLARATION
    private long unityPrice;
    private int availableQuantity;
    //CONSTRUCTORS

    public AssetMetadataForSell(DigitalAsset digitalAsset) {
        super(digitalAsset);
    }

    public AssetMetadataForSell() {
        super();
    }

    public AssetMetadataForSell(UUID metadataId, DigitalAsset digitalAsset, LinkedHashMap<String, String> transactionChain, DAPActor lastOwner) {
        super(metadataId, digitalAsset, transactionChain, lastOwner);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public long getUnityPrice() {
        return unityPrice;
    }

    public void setUnityPrice(long unityPrice) {
        this.unityPrice = unityPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    //INNER CLASSES
}
