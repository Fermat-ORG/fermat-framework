package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 15/01/16.
 */
public class pruebasPurchase {

    CustomerBrokerPurchaseNegotiationDao dao;

    public pruebasPurchase(CustomerBrokerPurchaseNegotiationDao dao){
        this.dao = dao;
        test();
    }

    public void test(){

        Collection<Clause> clauses = new ArrayList<>();

        UUID neg_id_1 = UUID.randomUUID();

        Short orden = (short) 0;
        Clause clause = new CustomerBrokerPurchaseClause(
                UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                FiatCurrency.VENEZUELAN_BOLIVAR.getCode(),
                ClauseStatus.DRAFT,
                "publicKeyCustomer",
                orden
        );

        clauses.add(clause);

        CustomerBrokerPurchaseNegotiation negotiation = new CustomerBrokerPurchaseNegotiationInformation(
                neg_id_1,
                "publicKeyCustomer 1",
                "publicKeyBroker 1",
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                NegotiationStatus.SENT_TO_BROKER,
                clauses,
                false,
                "Memo 1",
                "cancelReason 1",
                System.currentTimeMillis()
        );

        try {
            this.dao.createCustomerBrokerPurchaseNegotiation(negotiation);
            System.out.println("vlz:  Negotiation Purchase [ 1 ] creada exitosamente");
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            System.out.println("vlz:  Error creando la negociacion");
        }

        CustomerBrokerPurchaseNegotiation aux = null;

        try {
            Collection<CustomerBrokerPurchaseNegotiation> negs = this.dao.getNegotiations();

            System.out.println("vlz:  Imprimiendo el listado de negociaciones");

            for(CustomerBrokerPurchaseNegotiation neg : negs){
                if(neg_id_1 == null){
                    neg_id_1 = neg.getNegotiationId();
                }
                if( aux == null ){
                    aux = neg;
                }
                System.out.println("vlz:  \tNegotiationId: "+neg.getNegotiationId());
                System.out.println("vlz:  \tBrokerPublicKey: "+neg.getBrokerPublicKey());
                System.out.println("vlz:  \tCustomerPublicKey: "+neg.getCustomerPublicKey());
                System.out.println("vlz:  \tStartDate: "+neg.getStartDate());
                System.out.println("vlz:  \tNegotiationExpirationDate: "+neg.getNegotiationExpirationDate());
                System.out.println("vlz:  \tStatus: "+neg.getStatus().getCode());
                System.out.println("vlz:  \tNear: "+neg.getNearExpirationDatetime());
                System.out.println("vlz:  \tMemo: "+neg.getMemo());
                System.out.println("vlz:  \tCancelReason: "+neg.getCancelReason());
                System.out.println("vlz:  \tLast: "+neg.getLastNegotiationUpdateDate());

                try {
                    Collection<Clause> clausulas = neg.getClauses();

                    for(Clause c : clausulas){
                        System.out.println("vlz:  \t\tClauseId: "+c.getClauseId());
                        System.out.println("vlz:  \t\tType: "+c.getType().getCode());
                        System.out.println("vlz:  \t\tValue: "+c.getValue());
                        System.out.println("vlz:  \t\tStatus: "+c.getStatus().getCode());
                        System.out.println("vlz:  \t\tProposedBy: "+c.getProposedBy());
                        System.out.println("vlz:  \t\tOrder: "+c.getIndexOrder());
                    }


                } catch (CantGetListClauseException e) {
                    System.out.println("vlz:  Error: "+CantGetListClauseException.DEFAULT_MESSAGE);
                }
            }

            Clause clause2 = new CustomerBrokerPurchaseClause(
                    neg_id_1,
                    ClauseType.BROKER_BANK_ACCOUNT,
                    FiatCurrency.ARGENTINE_PESO.getCode(),
                    ClauseStatus.SENT_TO_BROKER,
                    "publicKeyCustomer modificado",
                    (short) 2
            );

            Collection<Clause> clauses2 = new ArrayList<>();
            clauses2.add(clause2);

            CustomerBrokerPurchaseNegotiation negotiation2 = new CustomerBrokerPurchaseNegotiationInformation(
                    neg_id_1,
                    "publicKeyCustomer modificado",
                    "publicKeyBroker modificado",
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    NegotiationStatus.CLOSED,
                    clauses2,
                    false,
                    "Memo modificado",
                    "cancelReason modificado",
                    System.currentTimeMillis()
            );

            try {
                this.dao.updateCustomerBrokerPurchaseNegotiation(negotiation2);
            } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
                System.out.println("vlz:  Error actualizando la negociacion");
            }

        } catch (CantGetListPurchaseNegotiationsException e) {
            System.out.println("vlz:  Error obteniendo el listado de negociaciones");
        }

    }
}
