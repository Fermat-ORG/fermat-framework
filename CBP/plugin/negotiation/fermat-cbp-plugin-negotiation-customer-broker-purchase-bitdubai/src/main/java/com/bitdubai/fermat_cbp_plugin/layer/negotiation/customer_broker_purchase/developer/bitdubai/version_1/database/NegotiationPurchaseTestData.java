package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ActorType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 22/02/16.
 */
public class NegotiationPurchaseTestData {

    private final CustomerBrokerPurchaseNegotiationDao dao;

    public NegotiationPurchaseTestData(CustomerBrokerPurchaseNegotiationDao dao) {
        this.dao = dao;
        load();
    }

    public void load() {

        List<Clause> clauses = new ArrayList<>();

        clauses.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY,
                        CryptoCurrency.BITCOIN.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.BROKER.getCode(),
                        (short) 0
                )
        );

        clauses.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY,
                        FiatCurrency.US_DOLLAR.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 1
                )
        );

        clauses.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.EXCHANGE_RATE,
                        "430.34",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 2
                )
        );

        clauses.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY_QUANTITY,
                        "49,59",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 3
                )
        );

        clauses.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                        "30,047",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 4
                )
        );


        CustomerBrokerPurchaseNegotiation neg = new CustomerBrokerPurchaseNegotiationInformation(
                UUID.randomUUID(),
                "publicKeyCustomer",
                "publicKeyBroker",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 100000,
                NegotiationStatus.WAITING_FOR_BROKER,
                clauses,
                false,
                "",
                "",
                System.currentTimeMillis()
        );

        try {
            this.dao.createCustomerBrokerPurchaseNegotiation(neg);
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {

        }


        List<Clause> clauses2 = new ArrayList<>();

        clauses2.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY,
                        CryptoCurrency.BITCOIN.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.BROKER.getCode(),
                        (short) 0
                )
        );

        clauses2.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY,
                        FiatCurrency.US_DOLLAR.getCode(),
                        ClauseStatus.AGREED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 1
                )
        );

        clauses2.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.EXCHANGE_RATE,
                        "430.34",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 2
                )
        );

        clauses2.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.BROKER_CURRENCY_QUANTITY,
                        "49,59",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 3
                )
        );

        clauses2.add(
                new CustomerBrokerPurchaseClause(
                        UUID.randomUUID(),
                        ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                        "30,047",
                        ClauseStatus.CHANGED,
                        ActorType.CUSTOMER.getCode(),
                        (short) 4
                )
        );


        CustomerBrokerPurchaseNegotiation neg2 = new CustomerBrokerPurchaseNegotiationInformation(
                UUID.randomUUID(),
                "publicKeyCustomer2",
                "publicKeyBroker2",
                System.currentTimeMillis(),
                System.currentTimeMillis() + 100000,
                NegotiationStatus.SENT_TO_BROKER,
                clauses2,
                false,
                "",
                "",
                System.currentTimeMillis()
        );

        try {
            this.dao.createCustomerBrokerPurchaseNegotiation(neg2);
        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {

        }

    }

}
