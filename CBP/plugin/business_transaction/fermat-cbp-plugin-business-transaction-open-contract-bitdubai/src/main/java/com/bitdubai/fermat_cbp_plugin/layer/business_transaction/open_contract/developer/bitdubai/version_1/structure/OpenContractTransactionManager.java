package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.OpenContractStatus;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantGetNegotiationStatusException;

import java.util.Collection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class OpenContractTransactionManager implements OpenContractManager{

    /**
     * Represents the contract Type, this type will use to select the actors involved in the
     * contract generation and the network service communication
     */
    ContractType contractType;

    /**
     * Represents the purchase contract
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the sale contract
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the purchase negotiation
     */
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the sale negotiation
     */
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the negotiation ID.
     */
    //private String negotiationId;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public OpenContractTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            TransactionTransmissionManager transactionTransmissionManager){

        customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
        customerBrokerSaleNegotiationManager=customerBrokerSaleNegotiationManager;
        transactionTransmissionManager=transactionTransmissionManager;

    }


    @Override
    public OpenContractStatus getOpenContractStatus(String negotiationId) {
        return null;
    }

   /* private boolean isNegotiationClosed(String negotiationId)throws CantGetNegotiationStatusException{
        try{
            switch (this.contractType){

                case PURCHASE:
                    //TODO: to implement

                    break;
                case SALE:
                    //TODO: to implement
                    break;

            }
        } catch (CantGetListPurchaseNegotiationsException exception) {
            throw new CantGetNegotiationStatusException(exception,
                    "Checking if Negotiation is closed",
                    "Cannot get the Purchase Negotiation list");
        }

        return false;
    }*/

    private CustomerBrokerPurchaseNegotiation findPurchaseNegotiation(String negotiationId) throws CantGetNegotiationStatusException {

        try{
            Collection<CustomerBrokerPurchaseNegotiation> negotiationCollection= customerBrokerPurchaseNegotiationManager.getNegotiations(NegotiationStatus.CLOSED);
            for(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation : negotiationCollection){
                String negotiationUUID=customerBrokerPurchaseNegotiation.getNegotiationId().toString();
                if(negotiationId.equals(negotiationUUID)){
                    return customerBrokerPurchaseNegotiation;
                }
            }
            throw new CantGetNegotiationStatusException("Cannot find the Negotiation Id \n"+
                    negotiationId+"\n" +
                    "in the Purchase Negotiation Database in CLOSED status");
        } catch (CantGetListPurchaseNegotiationsException exception) {
            throw new CantGetNegotiationStatusException(exception,
                    "Checking if Negotiation is closed",
                    "Cannot get the Purchase Negotiation list");
        }

    }

    private void openContract(String negotiationId) throws CantGetNegotiationStatusException {

        NegotiationStatus negotiationStatus;
        try {
            switch (this.contractType) {
                case PURCHASE:
                    CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation= findPurchaseNegotiation(negotiationId);
                    Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();

                    break;
                case SALE:
                    break;
            }
        } catch (CantGetListClauseException e) {
            e.printStackTrace();
        }
    }

    private ContractRecord createContractRecord(Collection<Clause> negotiationClauses) throws InvalidParameterException {
        ContractRecord contractRecord=new ContractRecord();
        CurrencyType merchandiseCurrency;
        float merchandiseAmount;
        long merchandiseDeliveryExpirationDate;
        float paymentAmount;
        CurrencyType paymentCurrency;
        long paymentExpirationDate;
        String publicKeyBroker;
        String publicKeyCustomer;
        ReferenceCurrency referenceCurrency;
        float referencePrice;
        ContractStatus status;
        for(Clause clause : negotiationClauses){
            //Purchase case
            switch (clause.getType()){

                case BROKER_CURRENCY:
                    merchandiseCurrency=CurrencyType.getByCode(clause.getValue());
                    contractRecord.setMerchandiseCurrency(merchandiseCurrency);
                    break;
                case BROKER_CURRENCY_QUANTITY:
                    merchandiseAmount=parseToFloat(clause.getValue());
                    contractRecord.setMerchandiseAmount(merchandiseAmount);
            }
        }
        return contractRecord;
    }

    private float parseToFloat(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to float");
        }else{
            try{
                return Float.valueOf(stringValue);
            }catch (Exception exception){
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Paring String object to float",
                        "Cannot parse string value to float");
            }

        }
    }

    @Override
    public void openSaleContract(String negotiationId) throws CantOpenContractException{
        contractType=contractType.SALE;
        //openContract(negotiationId);
    }

    @Override
    public void openPurchaseContract(String negotiationId) throws CantOpenContractException{
        contractType=contractType.PURCHASE;
        //openContract(negotiationId);
    }

}
