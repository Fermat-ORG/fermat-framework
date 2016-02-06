package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorQuotes;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by angel on 4/02/16.
 */
public class ActorExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private CryptoBrokerActorDao cryptoBrokerActorDao;

    public ActorExtraDataEventActions(CryptoBrokerManager cryptoBrokerANSManager, CryptoBrokerWalletManager cryptoBrokerWalletManager, CryptoBrokerActorDao cryptoBrokerActorDao){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoBrokerActorDao = cryptoBrokerActorDao;
    }

    public void handleNewsEvent(){
        // TODO: obtener la brokerPublicKey desde el network service
        String brokerPublicKey = "";
        try {
            CryptoBrokerActorExtraData data = getExtraData(brokerPublicKey);
            // TODO: enviar el objeto data a traves del NS
        } catch (CantGetExtraDataActorException e) {
            new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }

    public CryptoBrokerActorExtraData getExtraData(String brokerPublicKey) throws CantGetExtraDataActorException {
        try {
            Collection<CryptoBrokerActorQuotes> quotes = new ArrayList<>();
            BrokerIdentityWalletRelationship relationship = this.cryptoBrokerActorDao.getBrokerIdentityWalletRelationshipByIdentity(brokerPublicKey);
            String wallerPublicKey = relationship.getWallet();
            CryptoBrokerWallet wallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(wallerPublicKey);
            List<CryptoBrokerWalletAssociatedSetting> settings = wallet.getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings();
            for(CryptoBrokerWalletAssociatedSetting setting_1 : settings){
                Currency merchandise = setting_1.getMerchandise();
                for(CryptoBrokerWalletAssociatedSetting setting_2 : settings) {
                    Currency currencyPayment = setting_2.getMerchandise();
                    if(merchandise != currencyPayment){
                        Quote quote = wallet.getQuote(merchandise, 1f, currencyPayment);
                        CryptoBrokerActorQuotes qou = new CryptoBrokerActorQuotesInformation(
                            (Currency) quote.getMerchandise(),
                            quote.getFiatCurrency(),
                            quote.getPriceReference()
                        );
                        quotes.add(qou);
                    }
                }
            }
            return new CryptoBrokerActorExtraDataInformation(quotes);
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetCryptoBrokerQuoteException e) {
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        }
    }
}
