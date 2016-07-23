package com.bitdubai.open_contract;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractHashGenerationTest {

    private ContractSaleRecord generateMockedContractRecord() {
        ContractSaleRecord contractRecord = new ContractSaleRecord();
        contractRecord.setMerchandiseAmount(10);
        contractRecord.setMerchandiseCurrency(CryptoCurrency.BITCOIN);
        contractRecord.setMerchandiseDeliveryExpirationDate(616);
        contractRecord.setPaymentAmount(10);
        contractRecord.setPaymentCurrency(FiatCurrency.US_DOLLAR);
        contractRecord.setPaymentExpirationDate(161);
        contractRecord.setPublicKeyBroker("brokerPublicKey");
        contractRecord.setPublicKeyCustomer("customerPublicKey");
        contractRecord.setReferenceCurrency(ReferenceCurrency.DOLLAR);
        contractRecord.setReferencePrice(245);
        contractRecord.setStatus(ContractStatus.PENDING_PAYMENT);
        contractRecord.setNegotiationId("negotiationId");
        return contractRecord;
    }

    private ContractPurchaseRecord generateMockedContractPurchaseRecord() {
        ContractPurchaseRecord contractRecord = new ContractPurchaseRecord();
        contractRecord.setMerchandiseAmount(10);
        contractRecord.setMerchandiseCurrency(CryptoCurrency.BITCOIN);
        contractRecord.setMerchandiseDeliveryExpirationDate(616);
        contractRecord.setPaymentAmount(10);
        contractRecord.setPaymentCurrency(FiatCurrency.US_DOLLAR);
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
    public void generationDifferentObjectTest() {
        ContractSaleRecord saleRecord = generateMockedContractRecord();
        saleRecord.generateContractHash();
        System.out.println(saleRecord);
        ContractPurchaseRecord purchaseRecord = generateMockedContractPurchaseRecord();
        purchaseRecord.generateContractHash();
        System.out.println(purchaseRecord);
        Assert.assertEquals(purchaseRecord.getContractId(), saleRecord.getContractId());
    }

    @Test
    public void generationHashTest() throws Exception {
        ContractSaleRecord contractRecord = generateMockedContractRecord();
        System.out.println(new StringBuilder().append("Contract XML\n").append(contractRecord).toString());
        String generatedHash = contractRecord.generateContractHash();
        System.out.println(new StringBuilder().append("Generated Hash:\n").append(generatedHash).toString());
        String innerHash = contractRecord.getContractId();
        System.out.println(new StringBuilder().append("Inner Hash:\n").append(innerHash).toString());
        System.out.println(new StringBuilder().append("Contract XML with contract hash\n").append(contractRecord).toString());
        Assert.assertEquals(innerHash, generatedHash);
    }

    @Test
    public void changeHashTest() throws Exception {
        ContractSaleRecord contractRecord = generateMockedContractRecord();
        System.out.println(new StringBuilder().append("Contract XML\n").append(contractRecord).toString());
        String generatedHash = contractRecord.generateContractHash();
        System.out.println(new StringBuilder().append("Generated Hash:\n").append(generatedHash).toString());
        contractRecord.setReferenceCurrency(ReferenceCurrency.EURO);
        String newContractHash = contractRecord.generateContractHash();
        System.out.println(new StringBuilder().append("New XML\n").append(contractRecord).toString());
        System.out.println(new StringBuilder().append("New Hash\n").append(newContractHash).toString());
        Assert.assertNotEquals(generatedHash, newContractHash);
    }

    @Test
    public void changeNegotiationIdTest() throws Exception {
        System.out.println("changeNegotiationIdTest");
        ContractSaleRecord contractRecord = generateMockedContractRecord();
        System.out.println(new StringBuilder().append("Contract XML\n").append(contractRecord).toString());
        String generatedHash = contractRecord.generateContractHash();
        System.out.println(new StringBuilder().append("Generated Hash:\n").append(generatedHash).toString());
        contractRecord.setNegotiationId("negotiationid");
        String newGeneratedHash = contractRecord.generateContractHash();
        System.out.println(new StringBuilder().append("Contract XML\n").append(contractRecord).toString());
        System.out.println(new StringBuilder().append("Generated Hash:\n").append(newGeneratedHash).toString());
        Assert.assertNotEquals(generatedHash, newGeneratedHash);
    }

}
