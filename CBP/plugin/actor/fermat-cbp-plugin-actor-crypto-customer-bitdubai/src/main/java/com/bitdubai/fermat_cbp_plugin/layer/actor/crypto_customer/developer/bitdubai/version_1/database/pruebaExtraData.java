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

        KeyPair key = new ECCKeyPair();
        ActorIdentity Customer = new ActorExtraDataIdentity("Angel", key.getPublicKey());

        Collection<QuotesExtraData> quotes = new ArrayList<>();

        QuotesExtraData quote = new QuotesExtraDataInformation(
            UUID.randomUUID(),
            CryptoCurrency.BITCOIN,
            FiatCurrency.VENEZUELAN_BOLIVAR,
            348f
        );

        quotes.add(quote);

        Map<Currency, Collection<Platforms>> currencies = new HashMap<Currency, Collection<Platforms>>();

        Collection<Platforms> pla = new ArrayList<>();
        pla.add(Platforms.BANKING_PLATFORM);
        pla.add(Platforms.CASH_PLATFORM);

        currencies.put(FiatCurrency.VENEZUELAN_BOLIVAR, pla);

        ActorExtraData actorExtraData = new ActorExtraDataInformation(Customer, quotes, currencies);

        try {
            this.dao.createCustomerExtraData(actorExtraData);
            System.out.println("VLZ: Actor Extra Data Creada Exitosamente");
        } catch (CantCreateNewActorExtraDataException e) {
            System.out.println("VLZ: Error creando el registro");
        }

        try {
            Collection<ActorExtraData> datas = this.dao.getAllActorExtraData();

            for(ActorExtraData data : datas){
                System.out.println("VLZ: getAlias: "+data.getBrokerIdentity().getAlias());
                System.out.println("VLZ: getPublicKey: "+data.getBrokerIdentity().getPublicKey());
            }

        } catch (CantGetListActorExtraDataException e) {
            System.out.println("VLZ: Error recuperando el registro 1");
        }
        
    }

}
