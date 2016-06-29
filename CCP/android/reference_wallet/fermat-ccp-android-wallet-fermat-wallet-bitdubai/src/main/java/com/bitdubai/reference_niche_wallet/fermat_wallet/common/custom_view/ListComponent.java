package com.bitdubai.reference_niche_wallet.fermat_wallet.common.custom_view;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.enums.ShowMoneyType;

import static com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Modified by Joaquin Carrasquero on 2016.05.26
 */

public class ListComponent implements CustomComponentsObjects{

    private  String titleTransaction="";
    private  String detailTransaction="";
    private FermatWalletModuleTransaction fermatWalletTransaction;

    public ListComponent(FermatWalletModuleTransaction fermatWalletTransaction) {
        //this.titleTransaction = cryptoWalletTransaction.;
        this.fermatWalletTransaction = fermatWalletTransaction;
        generateTitle();
        generateDetailTransaction();

    }

    private void generateTitle(){
        try {
            if (fermatWalletTransaction.getTransactionType().equals(TransactionType.CREDIT)) {
                titleTransaction += "Receive from " + fermatWalletTransaction.getInvolvedActor().getName();
            } else if (fermatWalletTransaction.getTransactionType().equals(TransactionType.DEBIT)) {
                titleTransaction += "Send from " + fermatWalletTransaction.getInvolvedActor().getName();
            }
            titleTransaction+= " "+formatBalanceString(fermatWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void generateDetailTransaction(){
        try {
        String textBody = fermatWalletTransaction.getMemo();
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
        return fermatWalletTransaction.getInvolvedActor().getPhoto();
    }
}