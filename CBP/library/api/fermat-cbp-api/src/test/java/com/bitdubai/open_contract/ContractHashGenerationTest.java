package com.bitdubai.open_contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractHashGenerationTest {

    private ContractRecord generateMockedContractRecord(){
        ContractRecord contractRecord=new ContractRecord();
        contractRecord.setMerchandiseAmount(10);
        contractRecord.setMerchandiseCurrency(CurrencyType.CRYPTO_MONEY);
        contractRecord.setMerchandiseDeliveryExpirationDate(616);
        contractRecord.setPaymentAmount(10);
        contractRecord.setPaymentCurrency(CurrencyType.BANK_MONEY);
        contractRecord.setPaymentExpirationDate(161);
        contractRecord.setPublicKeyBroker("brokerPublicKey");
        contractRecord.setPublicKeyCustomer("customerPublicKey");
        contractRecord.setReferenceCurrency(ReferenceCurrency.DOLLAR);
        contractRecord.setReferencePrice(245);
        contractRecord.setStatus(ContractStatus.CREATING_CONTRACT);
        return contractRecord;
    }

    @Test
    public void generationHashTest() throws Exception{
        ContractRecord contractRecord=generateMockedContractRecord();
        System.out.println("Contract XML\n" + contractRecord);
        String generatedHash=contractRecord.generateContractHash();
        System.out.println("Generated Hash:\n" + generatedHash);
        String innerHash=contractRecord.getContractHash();
        System.out.println("Inner Hash:\n" + innerHash);
        System.out.println("Contract XML with contract hash\n" + contractRecord);
        Assert.assertEquals(innerHash, generatedHash);
    }

    @Test
    public void changeHashTest() throws Exception{
        ContractRecord contractRecord=generateMockedContractRecord();
        System.out.println("Contract XML\n" + contractRecord);
        String generatedHash=contractRecord.generateContractHash();
        System.out.println("Generated Hash:\n" + generatedHash);
        contractRecord.setReferenceCurrency(ReferenceCurrency.EURO);
        String newContractHash=contractRecord.generateContractHash();
        System.out.println("New XML\n" + contractRecord);
        System.out.println("New Hash\n" + newContractHash);
        Assert.assertNotEquals(generatedHash,newContractHash);
    }

}
