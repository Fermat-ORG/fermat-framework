package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleNegotiationInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 22/02/16.
 */
public class NegotiationSaleTestData {

    private final CustomerBrokerSaleNegotiationDao dao;

    public NegotiationSaleTestData(CustomerBrokerSaleNegotiationDao dao){
        this.dao = dao;
        load();
    }

    public void load(){

        List<Clause> clauses = new ArrayList<>();

        clauses.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY,
                        CryptoCurrency.BITCOIN.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.BROKER.getCode(),
                        (short) 0
                )
        );

        clauses.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY,
                        FiatCurrency.US_DOLLAR.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 1
                )
        );

        clauses.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.EXCHANGE_RATE,
                        "430.34",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 2
                )
        );

        clauses.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY_QUANTITY,
                        "49,59",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 3
                )
        );

        clauses.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                        "30,047",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 4
                )
        );


        CustomerBrokerSaleNegotiation neg = new CustomerBrokerSaleNegotiationInformation(
                UUID.randomUUID(),
                "publicKeyCustomer",
                "publicKeyBroker",
                System.currentTimeMillis(),
                System.currentTimeMillis()+100000,
                NegotiationStatus.WAITING_FOR_BROKER,
                clauses,
                false,
                "",
                "",
                System.currentTimeMillis()
        );

        try {
            this.dao.createCustomerBrokerSaleNegotiation(neg);
        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {

        }




        List<Clause> clauses2 = new ArrayList<>();

        clauses2.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY,
                        CryptoCurrency.BITCOIN.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.BROKER.getCode(),
                        (short) 0
                )
        );

        clauses2.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY,
                        FiatCurrency.US_DOLLAR.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 1
                )
        );

        clauses2.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.EXCHANGE_RATE,
                        "430.34",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 2
                )
        );

        clauses2.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY_QUANTITY,
                        "49,59",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 3
                )
        );

        clauses2.add(
                new CustomerBrokerSaleClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                        "30,047",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 4
                )
        );


        CustomerBrokerSaleNegotiation neg2 = new CustomerBrokerSaleNegotiationInformation(
                UUID.randomUUID(),
                "publicKeyCustomer2",
                "publicKeyBroker2",
                System.currentTimeMillis(),
                System.currentTimeMillis()+100000,
                NegotiationStatus.SENT_TO_BROKER,
                clauses2,
                false,
                "",
                "",
                System.currentTimeMillis()
        );

        try {
            this.dao.createCustomerBrokerSaleNegotiation(neg2);
        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {

        }


    }

}
