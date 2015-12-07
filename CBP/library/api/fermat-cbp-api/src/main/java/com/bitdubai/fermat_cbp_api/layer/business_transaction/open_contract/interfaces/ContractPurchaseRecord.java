package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;

import java.util.Collection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractPurchaseRecord implements CustomerBrokerContractPurchase {

    Collection<ContractClause> contractClauses;
    long dayTime;
    CurrencyType merchandiseCurrency;
    float merchandiseAmount;
    long merchandiseDeliveryExpirationDate;
    String negotiationId;
    float paymentAmount;
    CurrencyType paymentCurrency;
    long paymentExpirationDate;
    String publicKeyBroker;
    String publicKeyCustomer;
    ReferenceCurrency referenceCurrency;
    float referencePrice;
    ContractStatus status;
    String contractHash;
    /**
     * Represents the contract id/hash
     * @return
     */
    //@Override
    public String getContractId() {
        return contractHash;
    }

    @Override
    public String getNegotiatiotId() {
        return null;
    }

    /**
     * This method generate a hash that contains the all the encrypted contract in XML.
     * This hash is used as the contract Id
     * @return
     */
    public String generateContractHash(){
        this.contractHash=CryptoHasher.performSha256(this.toString());
        return this.contractHash;
    }

    //@Override
    public float getMerchandiseAmount() {
        return this.merchandiseAmount;
    }

    //@Override
    public CurrencyType getMerchandiseCurrency() {
        return this.merchandiseCurrency;
    }

    //@Override
    public long getMerchandiseDeliveryExpirationDate() {
        return this.merchandiseDeliveryExpirationDate;
    }

    public String getNegotiationId(){
        return this.negotiationId;
    }

    //@Override
    public float getPaymentAmount() {
        return this.paymentAmount;
    }

    //@Override
    public CurrencyType getPaymentCurrency() {
        return this.paymentCurrency;
    }

    //@Override
    public long getPaymentExpirationDate() {
        return this.paymentExpirationDate;
    }

    //@Override
    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    @Override
    public long getDateTime() {
        return this.dayTime;
    }

    //@Override
    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    //@Override
    public ReferenceCurrency getReferenceCurrency() {
        return this.referenceCurrency;
    }

    //@Override
    public float getReferencePrice() {
        return this.referencePrice;
    }

    //@Override
    public ContractStatus getStatus() {
        return status;
    }

    @Override
    public Collection<ContractClause> getContractClause() {
        return this.contractClauses;
    }

    public void setContractClauses(Collection<ContractClause> contractClauses){
        this.contractClauses=contractClauses;
    }

    public void setDayTime(long dayTime){
        this.dayTime=dayTime;
    }

    public void setMerchandiseAmount(float merchandiseAmount) {
        this.merchandiseAmount=merchandiseAmount;
    }

    public void setMerchandiseCurrency(CurrencyType merchandiseCurrency) {
        this.merchandiseCurrency=merchandiseCurrency;
    }

    public void setMerchandiseDeliveryExpirationDate(long merchandiseDeliveryExpirationDate) {
        this.merchandiseDeliveryExpirationDate=merchandiseDeliveryExpirationDate;
    }

    /**
     * The negotiationId is used to link this contract with the genesis negotiation.
     * Also, this negotiation id helps to create an unique contract hash (id) for this contract record.
     * @param negotiationId
     */
    public void setNegotiationId(String negotiationId){
        this.negotiationId=negotiationId;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount=paymentAmount;
    }

    public void setPaymentCurrency(CurrencyType paymentCurrency) {
        this.paymentCurrency=paymentCurrency;
    }

    public void setPaymentExpirationDate(long paymentExpirationDate) {
        this.paymentExpirationDate=paymentExpirationDate;
    }

    public void setPublicKeyBroker(String publicKeyBroker) {
        this.publicKeyBroker=publicKeyBroker;
    }

    public void setPublicKeyCustomer(String publicKeyCustomer) {
        this.publicKeyCustomer=publicKeyCustomer;
    }

    public void setReferencePrice(float referencePrice) {
        this.referencePrice=referencePrice;
    }

    public void setReferenceCurrency(ReferenceCurrency referenceCurrency) {
        this.referenceCurrency=referenceCurrency;
    }

    public void setStatus(ContractStatus status){
        this.status=status;
    }

    /**
     * The string of the ContractSaleRecord will be used to generate a unique Hash.
     * This hash will be used as Id.
     * I generate an XML with the class structure.
     * @return
     */
    //@Override
    public String toString() {
        String contractString="Contract details: {\n" +
                "Contract Clauses: "+contractClauses+"\n" +
                "Day time: "+dayTime+"\n" +
                "Merchandise Currency: "+merchandiseCurrency+"\n" +
                "Merchandise Amount: "+merchandiseAmount+"\n" +
                "Merchandise Delivery Expiration Date: "+merchandiseDeliveryExpirationDate+"\n" +
                "Negotiation Id: "+negotiationId+"\n" +
                "Payment Amount: "+paymentAmount+"\n" +
                "Payment Currency: "+paymentCurrency+"\n" +
                "Payment Expiration Date: "+paymentExpirationDate+"\n" +
                "Broker Public Key: "+publicKeyBroker+"\n" +
                "Customer Public Key: "+publicKeyCustomer+"\n" +
                "Reference Currency: "+referenceCurrency+"\n" +
                "Reference Price: "+referencePrice+"\n" +
                "Status: "+status+"\n" +
                "Contract Hash: "+contractHash+"\n" +
                "}";
        return contractString;
    }

}
