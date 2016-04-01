package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetExtraDataActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
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
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantNewsEventException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 4/02/16.
 */

public class ActorBrokerExtraDataEventActions {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private CryptoBrokerActorDao cryptoBrokerActorDao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public ActorBrokerExtraDataEventActions(
            final CryptoBrokerManager cryptoBrokerANSManager,
            final CryptoBrokerWalletManager cryptoBrokerWalletManager,
            final CryptoBrokerActorDao cryptoBrokerActorDao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
    ){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoBrokerActorDao = cryptoBrokerActorDao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    public void handleNewsEvent() throws CantNewsEventException {
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
        } catch (CantListPendingQuotesRequestsException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewsEventException(e.getMessage(), e, "", "");
        } catch (CantGetExtraDataActorException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewsEventException(e.getMessage(), e, "", "");
        } catch (CantAnswerQuotesRequestException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewsEventException(e.getMessage(), e, "", "");
        } catch (QuotesRequestNotFoundException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewsEventException(e.getMessage(), e, "", "");
        }
    }

    public List<CryptoBrokerQuote> getExtraData(String brokerPublicKey) throws CantGetExtraDataActorException {
        List<CryptoBrokerQuote> quotes = new ArrayList<>();
        try {
            BrokerIdentityWalletRelationship relationship = this.cryptoBrokerActorDao.getBrokerIdentityWalletRelationshipByIdentity(brokerPublicKey);
            String wallerPublicKey = relationship.getWallet();
            CryptoBrokerWallet wallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(wallerPublicKey);
            List<CryptoBrokerWalletAssociatedSetting> settings = wallet.getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings();
            for(CryptoBrokerWalletAssociatedSetting setting_1 : settings){
                Currency merchandise = setting_1.getMerchandise();
                for(CryptoBrokerWalletAssociatedSetting setting_2 : settings) {
                    Currency currencyPayment = setting_2.getMerchandise();
                    if(merchandise != currencyPayment){
                        try {
                            Quote quote = wallet.getQuote(merchandise, 1f, currencyPayment);
                            quotes.add(new CryptoBrokerQuote(quote));
                        } catch (CantGetCryptoBrokerQuoteException e) {
                            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference,
                                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        }
                    }
                }
            }

        } catch (CantGetCryptoBrokerWalletSettingException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CryptoBrokerWalletNotFoundException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetExtraDataActorException(e.DEFAULT_MESSAGE, e, "", "");
        }

        return quotes;
    }
}