package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 6/01/16.
 */
public class AssetRedeemedContentMessage implements DAPContentMessage {

    //VARIABLE DECLARATION
    private String redeemPointPublicKey;
    private String assetRedeemedPublicKey;
    private String userThatRedeemed;

    //CONSTRUCTORS


    public AssetRedeemedContentMessage() {
    }

    public AssetRedeemedContentMessage(String redeemPointPublicKey, String assetRedeemedPublicKey, String userThatRedeemed) {
        this.redeemPointPublicKey = redeemPointPublicKey;
        this.assetRedeemedPublicKey = assetRedeemedPublicKey;
        this.userThatRedeemed = userThatRedeemed;
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public String getRedeemPointPublicKey() {
        return redeemPointPublicKey;
    }

    public void setRedeemPointPublicKey(String redeemPointPublicKey) {
        this.redeemPointPublicKey = redeemPointPublicKey;
    }

    public String getAssetRedeemedPublicKey() {
        return assetRedeemedPublicKey;
    }

    public void setAssetRedeemedPublicKey(String assetRedeemedPublicKey) {
        this.assetRedeemedPublicKey = assetRedeemedPublicKey;
    }

    public String getUserThatRedeemed() {
        return userThatRedeemed;
    }

    public void setUserThatRedeemed(String userThatRedeemed) {
        this.userThatRedeemed = userThatRedeemed;
    }

    //INNER CLASSES
}
