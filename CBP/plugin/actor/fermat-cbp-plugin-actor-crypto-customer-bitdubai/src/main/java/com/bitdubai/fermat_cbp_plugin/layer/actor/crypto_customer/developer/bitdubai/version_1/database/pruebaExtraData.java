package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorExtraDataIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorExtraDataInformation;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.QuotesExtraDataInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 18/01/16.
 */
public class pruebaExtraData {

    CryptoCustomerActorDao dao;

    public pruebaExtraData(CryptoCustomerActorDao dao){
        this.dao = dao;
        test();
    }

    public void test(){
        Collection<QuotesExtraData> quotes = null;
        QuotesExtraData quote = null;
        Collection<Platforms> pla = null;
        Map<Currency, Collection<Platforms>> currencies = null;
        ActorIdentity Customer = null;
        ActorExtraData actorExtraData = null;


        KeyPair key = new ECCKeyPair();
        Customer = new ActorExtraDataIdentity("Pedro", key.getPublicKey());
        quotes = new ArrayList<>();
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), CryptoCurrency.BITCOIN, FiatCurrency.VENEZUELAN_BOLIVAR, 348000f); quotes.add(quote);
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 0.00034f); quotes.add(quote);

        currencies = new HashMap<Currency, Collection<Platforms>>();
        pla = new ArrayList<>();
            pla.add(Platforms.BANKING_PLATFORM);
            pla.add(Platforms.CASH_PLATFORM);
        currencies.put(FiatCurrency.VENEZUELAN_BOLIVAR, pla);

        actorExtraData = new ActorExtraDataInformation(Customer, quotes, currencies);

        try {
            this.dao.createCustomerExtraData(actorExtraData);
        } catch (CantCreateNewActorExtraDataException e) {
            System.out.println("VLZ: Error creando el registro 1");
        }

        // ===========================================================================================================================================================

        Customer = new ActorExtraDataIdentity("Juan", key.getPublicKey());
        quotes = new ArrayList<>();
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR, 1000f); quotes.add(quote);
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR, 0.001f); quotes.add(quote);

        currencies = new HashMap<Currency, Collection<Platforms>>();
        pla = new ArrayList<>();
        pla.add(Platforms.BANKING_PLATFORM);
        currencies.put(FiatCurrency.US_DOLLAR, pla);

        pla = new ArrayList<>();
            pla.add(Platforms.BANKING_PLATFORM);
            pla.add(Platforms.CASH_PLATFORM);
        currencies.put(FiatCurrency.VENEZUELAN_BOLIVAR, pla);

        actorExtraData = new ActorExtraDataInformation(Customer, quotes, currencies);

        try {
            this.dao.createCustomerExtraData(actorExtraData);
        } catch (CantCreateNewActorExtraDataException e) {
            System.out.println("VLZ: Error creando el registro 2");
        }

        /*
        try {
            Collection<ActorExtraData> datas = this.dao.getAllActorExtraData();

            for(ActorExtraData data : datas){
                System.out.println("VLZ: getAlias: "+data.getBrokerIdentity().getAlias());
                System.out.println("VLZ: getPublicKey: "+data.getBrokerIdentity().getPublicKey());

                for(QuotesExtraData quo : data.getQuotes()){
                    System.out.println("VLZ: \tgetQuoteId: "+quo.getQuoteId() );
                    System.out.println("VLZ: \tgetMerchandise: "+quo.getMerchandise() );
                    System.out.println("VLZ: \tgetPaymentCurrency: "+quo.getPaymentCurrency() );
                    System.out.println("VLZ: \tgetPrice: "+quo.getPrice() );
                }

            }

        } catch (CantGetListActorExtraDataException e) {
            System.out.println("VLZ: Error recuperando el registro");
        }
        */
    }

}
