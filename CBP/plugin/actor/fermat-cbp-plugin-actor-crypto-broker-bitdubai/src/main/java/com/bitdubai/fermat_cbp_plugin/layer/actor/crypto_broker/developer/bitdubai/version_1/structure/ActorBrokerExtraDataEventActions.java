package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantHandleExtraDataRequestEventException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by angel on 4/02/16.
 */

public class ActorBrokerExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private CryptoBrokerActorDao cryptoBrokerActorDao;
    private final CryptoBrokerActorPluginRoot pluginRoot;
    private final PluginVersionReference pluginVersionReference;

    private List<CryptoBrokerWalletAssociatedSetting> associatedWallets;

    public ActorBrokerExtraDataEventActions(
            final CryptoBrokerManager cryptoBrokerANSManager,
            final CryptoBrokerWalletManager cryptoBrokerWalletManager,
            final CryptoBrokerActorDao cryptoBrokerActorDao,
            final CryptoBrokerActorPluginRoot pluginRoot,
            final PluginVersionReference pluginVersionReference
    ) {
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoBrokerActorDao = cryptoBrokerActorDao;
        this.pluginRoot = pluginRoot;
        this.pluginVersionReference = pluginVersionReference;
    }

    public void handleNewsEvent() throws CantHandleExtraDataRequestEventException {
        List<CryptoBrokerExtraData<CryptoBrokerQuote>> dataNS;
        try {
            dataNS = cryptoBrokerANSManager.listPendingQuotesRequests(RequestType.RECEIVED);
            if (dataNS != null) {

                String brokerPublicKey = null;

                for (CryptoBrokerExtraData<CryptoBrokerQuote> extraData : dataNS) {
                    //if(brokerPublicKey == null){
                    brokerPublicKey = extraData.getCryptoBrokerPublicKey();
                    //}
                    final List<CryptoBrokerQuote> quotes = getQuotes(brokerPublicKey);
                    if (quotes == null || quotes.isEmpty()) {
                        //Don't have any quotes to answer
                        continue;
                    }
                    final long updateTime = System.currentTimeMillis();

                    cryptoBrokerANSManager.answerQuotesRequest(extraData.getRequestId(), updateTime, quotes);
                }
            }

        } catch (CantListPendingQuotesRequestsException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleExtraDataRequestEventException(e,
                    "Trying to get the list of quotes request in Network Service database whit state RECEIVED",
                    "Maybe the DB table is empty or the data is not correctly putted");

        } catch (CantGetExtraDataActorException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleExtraDataRequestEventException(e,
                    "Trying to get the quotes from the Crypto Broker Wallet Plugin database",
                    "Can be multiple reasons. See the Stack Trace for more info");

        } catch (CantAnswerQuotesRequestException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleExtraDataRequestEventException(e,
                    "Trying to call the Network Service to answer the request of the extra data",
                    "Can be multiple reasons. Maybe an error occurred trying to update the Request Status. See the Stack Trace for more info");

        } catch (QuotesRequestNotFoundException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleExtraDataRequestEventException(e,
                    "Trying to call the Network Service to answer the request of the extra data",
                    "Verify the request is in data base or the Request ID is correct. See the Stack Trace for more info");
        }
    }

    public List<CryptoBrokerQuote> getQuotes(String brokerPublicKey) throws CantGetExtraDataActorException {
        List<CryptoBrokerQuote> quotes = new ArrayList<>();
        try {
            final BrokerIdentityWalletRelationship relationship = this.cryptoBrokerActorDao.getBrokerIdentityWalletRelationshipByIdentity(brokerPublicKey);
            if (relationship == null) {
                return quotes;
            }
            final String wallerPublicKey = relationship.getWallet();

            final CryptoBrokerWallet wallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(wallerPublicKey);

            final CryptoBrokerWalletSetting setting = wallet.getCryptoWalletSetting();
            associatedWallets = setting.getCryptoBrokerWalletAssociatedSettings();

            for (CryptoBrokerWalletAssociatedSetting merchandiseWallet : associatedWallets) {
                Currency merchandise = merchandiseWallet.getMerchandise();

                for (CryptoBrokerWalletAssociatedSetting paymentWallet : associatedWallets) {
                    Currency currencyPayment = paymentWallet.getMerchandise();

                    if (merchandise != currencyPayment) {
                        try {
                            Quote quote = wallet.getQuote(merchandise, 1f, currencyPayment);
                            String supportedPlatforms = supportedPlatforms(merchandise);
                            quotes.add(new CryptoBrokerQuote(
                                    (Currency) quote.getMerchandise(),
                                    quote.getFiatCurrency(),
                                    quote.getPriceReference(),
                                    supportedPlatforms
                            ));
                        } catch (CantGetCryptoBrokerQuoteException e) {
                            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        }
                    }
                }
            }

        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantGetExtraDataActorException(CantGetExtraDataActorException.DEFAULT_MESSAGE, e,
                    "Trying to get the associated wallets to get its Currencies and iterate over them with the intention of arm a Currency Pair and get the corresponding Quote",
                    "Verify that are wallets associated in Database");

        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantGetExtraDataActorException(CryptoBrokerWalletNotFoundException.DEFAULT_MESSAGE, e,
                    "Trying to get a reference to the Crypto Broker Wallet Plugin the get the associated wallets",
                    "Verify if the plugin started, or is enabled");

        } catch (CantGetRelationBetweenBrokerIdentityAndBrokerWalletException e) {
            throw new CantGetExtraDataActorException(CantGetRelationBetweenBrokerIdentityAndBrokerWalletException.DEFAULT_MESSAGE, e,
                    "Trying to get the Broker Wallet's Public Key by the Broker Identity's Public Key to get a reference to the Crypto Broker Wallet Plugin",
                    "Verify the Broker Identity is really associated with the Crypto Broker Wallet");
        }

        return quotes;
    }

    public String supportedPlatforms(Currency merchandise) {

        String result = "";

        ArrayList<String> list = new ArrayList<>();

        for (CryptoBrokerWalletAssociatedSetting paymentWallet : associatedWallets) {
            Currency currency = paymentWallet.getMerchandise();

            if (merchandise == currency) {
                if (!list.contains(paymentWallet.getPlatform().getCode())) {
                    list.add(paymentWallet.getPlatform().getCode());
                }
            }
        }

        for (String platform : list) {
            result += new StringBuilder().append(platform).append(":").toString();
        }

        return result;
    }
}