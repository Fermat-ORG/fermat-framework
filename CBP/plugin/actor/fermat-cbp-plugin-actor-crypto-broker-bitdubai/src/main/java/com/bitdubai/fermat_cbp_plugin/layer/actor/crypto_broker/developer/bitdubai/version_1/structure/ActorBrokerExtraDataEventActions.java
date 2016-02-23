package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorQuotes;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAnswerQuotesRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.QuotesRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
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

/**
 * Created by angel on 4/02/16.
 */

public class ActorBrokerExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private CryptoBrokerActorDao cryptoBrokerActorDao;

    public ActorBrokerExtraDataEventActions(CryptoBrokerManager cryptoBrokerANSManager, CryptoBrokerWalletManager cryptoBrokerWalletManager, CryptoBrokerActorDao cryptoBrokerActorDao){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoBrokerActorDao = cryptoBrokerActorDao;
    }

    public void handleNewsEvent(){
        List<CryptoBrokerExtraData<CryptoBrokerQuote>> dataNS;
        try {
            dataNS = cryptoBrokerANSManager.listPendingQuotesRequests(RequestType.RECEIVED);
            if(dataNS != null) {
                for (CryptoBrokerExtraData<CryptoBrokerQuote> extraDate : dataNS) {
                    String brokerPublicKey = extraDate.getCryptoBrokerPublicKey();
                    cryptoBrokerANSManager.answerQuotesRequest(
                        extraDate.getRequestId(),
                        System.currentTimeMillis(),
                        getExtraData(brokerPublicKey)
                    );
                }
            }
        } catch (CantListPendingQuotesRequestsException ignore) {

        } catch (CantGetExtraDataActorException e) {

        } catch (CantAnswerQuotesRequestException e) {

        } catch (QuotesRequestNotFoundException e) {

        }
    }

    public List<CryptoBrokerQuote> getExtraData(String brokerPublicKey) throws CantGetExtraDataActorException {
        try {
            List<CryptoBrokerQuote> quotes = new ArrayList<>();
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
                        quotes.add(
                            new CryptoBrokerQuote(
                                (Currency) quote.getMerchandise(),
                                quote.getFiatCurrency(),
                                quote.getPriceReference()
                            )
                        );
                    }
                }
            }
            return quotes;
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetExtraDataActorException(CantGetCryptoBrokerWalletSettingException.DEFAULT_MESSAGE, e, "", "");
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetExtraDataActorException(CryptoBrokerWalletNotFoundException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
            throw new CantGetExtraDataActorException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetCryptoBrokerQuoteException e) {
            throw new CantGetExtraDataActorException(CantGetCryptoBrokerQuoteException.DEFAULT_MESSAGE, e, "", "");
        }
    }
}
