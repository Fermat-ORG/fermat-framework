package com.bitdubai.open_contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractHashGenerationTest {

    private ContractSaleRecord generateMockedContractRecord(){
        ContractSaleRecord contractRecord=new ContractSaleRecord();
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
        contractRecord.setStatus(ContractStatus.PENDING_PAYMENT);
        contractRecord.setNegotiationId("negotiationId");
        return contractRecord;
    }

    private ContractPurchaseRecord generateMockedContractPurchaseRecord(){
        ContractPurchaseRecord contractRecord=new ContractPurchaseRecord();
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
        contractRecord.setStatus(ContractStatus.PENDING_PAYMENT);
        contractRecord.setNegotiationId("negotiationId");
        return contractRecord;
    }

    @Test
    public void generationDifferentObjectTest(){
        ContractSaleRecord saleRecord=generateMockedContractRecord();
        saleRecord.generateContractHash();
        System.out.println(saleRecord);
        ContractPurchaseRecord purchaseRecord=generateMockedContractPurchaseRecord();
        purchaseRecord.generateContractHash();
        System.out.println(purchaseRecord);
        Assert.assertEquals(purchaseRecord.getContractId(), saleRecord.getContractId());
    }

    @Test
    public void generationHashTest() throws Exception{
        ContractSaleRecord contractRecord=generateMockedContractRecord();
        System.out.println("Contract XML\n" + contractRecord);
        String generatedHash=contractRecord.generateContractHash();
        System.out.println("Generated Hash:\n" + generatedHash);
        String innerHash=contractRecord.getContractId();
        System.out.println("Inner Hash:\n" + innerHash);
        System.out.println("Contract XML with contract hash\n" + contractRecord);
        Assert.assertEquals(innerHash, generatedHash);
    }

    @Test
    public void changeHashTest() throws Exception{
        ContractSaleRecord contractRecord=generateMockedContractRecord();
        System.out.println("Contract XML\n" + contractRecord);
        String generatedHash=contractRecord.generateContractHash();
        System.out.println("Generated Hash:\n" + generatedHash);
        contractRecord.setReferenceCurrency(ReferenceCurrency.EURO);
        String newContractHash=contractRecord.generateContractHash();
        System.out.println("New XML\n" + contractRecord);
        System.out.println("New Hash\n" + newContractHash);
        Assert.assertNotEquals(generatedHash, newContractHash);
    }

    @Test
    public void changeNegotiationIdTest() throws Exception{
        System.out.println("changeNegotiationIdTest");
        ContractSaleRecord contractRecord=generateMockedContractRecord();
        System.out.println("Contract XML\n" + contractRecord);
        String generatedHash=contractRecord.generateContractHash();
        System.out.println("Generated Hash:\n" + generatedHash);
        contractRecord.setNegotiationId("negotiationid");
        String newGeneratedHash=contractRecord.generateContractHash();
        System.out.println("Contract XML\n" + contractRecord);
        System.out.println("Generated Hash:\n" + newGeneratedHash);
        Assert.assertNotEquals(generatedHash, newGeneratedHash);
    }

}
