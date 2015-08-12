package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomListViewMati;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class ListModel {

    private  String titleTransaction="";
    private  String detailTransaction="";
    private  String imageUrl="";

    /*********** Set Methods ******************/

    public void setTitleTransaction(String titleTransaction) {
        this.titleTransaction = titleTransaction;
    }

    public void setDetailTransaction(String detailTransaction) {
        this.detailTransaction = detailTransaction;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /*********** Get Methods ****************/

    public String getTitleTransaction() {
        return titleTransaction;
    }

    public String getDetailTransaction() {
        return detailTransaction;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}