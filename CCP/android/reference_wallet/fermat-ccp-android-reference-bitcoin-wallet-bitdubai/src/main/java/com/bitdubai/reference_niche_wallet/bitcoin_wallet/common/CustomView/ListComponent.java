package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class ListComponent implements CustomComponentsObjects{

    private  String titleTransaction="";
    private  String detailTransaction="";
    private CryptoWalletTransaction cryptoWalletTransaction;

    public ListComponent(CryptoWalletTransaction cryptoWalletTransaction) {
        //this.titleTransaction = cryptoWalletTransaction.;
        this.cryptoWalletTransaction = cryptoWalletTransaction;
        generateTitle();
        generateDetailTransaction();

    }

    private void generateTitle(){
        try {
            if (cryptoWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                titleTransaction += "Receive from " + cryptoWalletTransaction.getInvolvedActor().getName();
            } else if (cryptoWalletTransaction.getTransactionType().equals(TransactionType.DEBIT)) {
                titleTransaction += "Send from " + cryptoWalletTransaction.getInvolvedActor().getName();
            }
            titleTransaction+= " "+formatBalanceString(cryptoWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void generateDetailTransaction(){
        try {
        String textBody = cryptoWalletTransaction.getMemo();
        if(textBody.length() != 0){
            detailTransaction+= textBody;
        }else{
            detailTransaction+= "Add memo to this transaction";
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*********** Set Methods ******************/

    public void setTitleTransaction(String titleTransaction) {
        this.titleTransaction = titleTransaction;
    }

    public void setDetailTransaction(String detailTransaction) {
        this.detailTransaction = detailTransaction;
    }


    /*********** Get Methods ****************/

    public String getTitleTransaction() {
        return titleTransaction;
    }

    public String getDetailTransaction() {
        return detailTransaction;
    }

    @Override
    public String getTitle() {
        return titleTransaction;
    }

    @Override
    public String getDetail() {
        return detailTransaction;
    }

    public byte[] getImage() {
        return cryptoWalletTransaction.getInvolvedActor().getPhoto();
    }
}