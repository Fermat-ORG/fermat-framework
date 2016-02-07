package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantSendExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.*;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
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
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class BrokerActorManager implements CryptoBrokerActorExtraDataManager {

    private CryptoBrokerActorDao dao;
    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    public BrokerActorManager(CryptoBrokerActorDao dao, CryptoBrokerManager cryptoBrokerANSManager, CryptoBrokerWalletManager cryptoBrokerWalletManager){
        this.dao = dao;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
    }

    /*==============================================================================================
    *
    *   Broker Identity Wallet Relationship
    *
    *==============================================================================================*/

        @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException {
            return this.dao.createNewBrokerIdentityWalletRelationship(identity, walletPublicKey);
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getAllBrokerIdentityWalletRelationship();
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getBrokerIdentityWalletRelationshipByIdentity(publicKey);
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getBrokerIdentityWalletRelationshipByWallet(walletPublicKey);
        }

        @Override
        public void sendExtraData(String publicKeyBroker, String publicKeyCustomer) throws CantSendExtraDataActorException {
            try {
                BrokerIdentityWalletRelationship relationship = this.dao.getBrokerIdentityWalletRelationshipByIdentity(publicKeyBroker);
                String wallerPublicKey = relationship.getWallet();
                CryptoBrokerWallet wallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(wallerPublicKey);
                List<CryptoBrokerWalletAssociatedSetting> settings = wallet.getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings();
                List<CryptoBrokerQuote> quotes = new ArrayList<>();
                for(CryptoBrokerWalletAssociatedSetting setting_1 : settings){
                    Currency merchandise = setting_1.getMerchandise();
                    for(CryptoBrokerWalletAssociatedSetting setting_2 : settings) {
                        Currency currencyPayment = setting_2.getMerchandise();
                        if(merchandise != currencyPayment){
                            Quote quote = wallet.getQuote(merchandise, 1f, currencyPayment);
                            CryptoBrokerQuote qou = new CryptoBrokerQuote(
                                    (Currency) quote.getMerchandise(),
                                    quote.getFiatCurrency(),
                                    quote.getPriceReference()
                            );
                            quotes.add(qou);
                        }
                    }
                }
                cryptoBrokerANSManager.requestQuotes(publicKeyCustomer, Actors.CBP_CRYPTO_CUSTOMER, publicKeyBroker, System.currentTimeMillis(), quotes);
            } catch (CantGetCryptoBrokerWalletSettingException e) {
                throw new CantSendExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (CryptoBrokerWalletNotFoundException e) {
                throw new CantSendExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
                throw new CantSendExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetCryptoBrokerQuoteException e) {
                throw new CantSendExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (CantRequestQuotesException e) {
                throw new CantSendExtraDataActorException(e.getMessage(), e, "", "");
            }
        }
}