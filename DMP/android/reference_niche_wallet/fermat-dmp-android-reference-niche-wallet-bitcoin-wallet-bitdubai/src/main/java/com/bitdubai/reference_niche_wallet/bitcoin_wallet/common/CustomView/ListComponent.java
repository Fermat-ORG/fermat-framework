package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CustomView;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class ListComponent implements CustomComponentsObjects{

    private  String titleTransaction="";
    private  String detailTransaction="";
    private CryptoWalletTransaction cryptoWalletTransaction;
    private WalletContactRecord walletContactRecord;
    private CryptoWallet cryptoWallet;

    public ListComponent(CryptoWalletTransaction cryptoWalletTransaction,WalletContactRecord walletContactRecord, CryptoWallet cryptoWallet) {
        //this.titleTransaction = cryptoWalletTransaction.getBitcoinWalletTransaction().;
        generateTitle();
        generateDetailTransaction();
        this.walletContactRecord = walletContactRecord;
        this.cryptoWallet = cryptoWallet;
    }

    private void generateTitle(){
        if(cryptoWalletTransaction.getBitcoinWalletTransaction().getTransactionType().equals(TransactionType.CREDIT)){
            titleTransaction+= "Receive from "+getActorNameProvisorio(cryptoWalletTransaction);
        }else if (cryptoWalletTransaction.getBitcoinWalletTransaction().getTransactionType().equals(TransactionType.DEBIT)){
            titleTransaction+= "Send from "+getActorNameProvisorio(cryptoWalletTransaction);
        }
    }

    private String getActorNameProvisorio(CryptoWalletTransaction cryptoWalletTransaction) {
        if (cryptoWalletTransaction.getContactId() != null) {
            try {
                WalletContactRecord walletContactRecord = cryptoWallet.findWalletContactById(cryptoWalletTransaction.getContactId());
                return walletContactRecord.getActorName();
            } catch (Exception e) {
                System.out.println("esta es para vos mati.");
            }
        } else if (cryptoWalletTransaction.getInvolvedActor() != null) {
            return cryptoWalletTransaction.getInvolvedActor().getName();
        }
        return "Unknow";
    }

    private void generateDetailTransaction(){
        String textBody = cryptoWalletTransaction.getBitcoinWalletTransaction().getMemo();
        if(textBody.length() != 0){
            detailTransaction+= textBody;
        }else{
            detailTransaction+= "Add memo to this transaction";
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
        return walletContactRecord.getContactProfileImage();
    }
}