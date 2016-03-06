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
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 18/01/16.
 */
public class pruebaExtraData {

    CryptoCustomerActorDao dao;
    String customer;

    public pruebaExtraData(CryptoCustomerActorDao dao){
        this.dao = dao;
        KeyPair key = new ECCKeyPair();
        this.customer = key.getPublicKey();
        test();
    }

    public void test(){
        Map<Currency, Collection<Platforms>> currencies = null;

        KeyPair key = new ECCKeyPair();
        ActorIdentity broker_1 = new ActorExtraDataIdentity("Pedro", key.getPublicKey(), null);
        Collection<QuotesExtraData> quotes_1 = new ArrayList<>();
        QuotesExtraData quote = null;
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), CryptoCurrency.BITCOIN, FiatCurrency.VENEZUELAN_BOLIVAR, 348000f); quotes_1.add(quote);
        quote = new QuotesExtraDataInformation(UUID.randomUUID(), FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 0.00034f); quotes_1.add(quote);

        ActorExtraData actorExtraData_1 = new ActorExtraDataInformation(customer, broker_1, quotes_1, currencies);

        try {
            this.dao.createCustomerExtraData(actorExtraData_1);
            this.dao.createActorQuotes(actorExtraData_1);
        } catch (CantCreateNewActorExtraDataException e) {
            System.out.println("VLZ: Error creando el registro 1");
        }

        try {
            this.dao.getActorExtraDataByPublicKey(customer, broker_1.getPublicKey());
        } catch (CantGetListActorExtraDataException e) {
            System.out.println("VLZ: Error obteniendo el registro 1");
        }

        // ===========================================================================================================================================================

        ActorIdentity broker_2 = new ActorExtraDataIdentity("Juan", key.getPublicKey(), null);
        Collection<QuotesExtraData> quotes_2 = new ArrayList<>();
        QuotesExtraData quote_2 = null;
        quote_2 = new QuotesExtraDataInformation(UUID.randomUUID(), CryptoCurrency.BITCOIN, FiatCurrency.VENEZUELAN_BOLIVAR, 348000f); quotes_2.add(quote_2);
        quote_2 = new QuotesExtraDataInformation(UUID.randomUUID(), FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 0.00034f); quotes_2.add(quote_2);

        ActorExtraData actorExtraData_2 = new ActorExtraDataInformation(customer, broker_2, quotes_2, currencies);

        try {
            this.dao.createCustomerExtraData(actorExtraData_2);
            this.dao.createActorQuotes(actorExtraData_2);
        } catch (CantCreateNewActorExtraDataException e) {
            System.out.println("VLZ: Error creando el registro 1");
        }

        try {
            this.dao.getActorExtraDataByPublicKey(customer, broker_2.getPublicKey());
        } catch (CantGetListActorExtraDataException e) {
            System.out.println("VLZ: Error obteniendo el registro 1");
        }
    }

}
