package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
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
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAnswerQuotesRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class BrokerActorManager implements CryptoBrokerActorExtraDataManager {

    private final CryptoBrokerActorDao dao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public BrokerActorManager(CryptoBrokerActorDao dao, ErrorManager errorManager, PluginVersionReference pluginVersionReference){
        this.dao = dao;
        this.pluginVersionReference = pluginVersionReference;
        this.errorManager = errorManager;
    }

    /*==============================================================================================
    *
    *   Broker Identity Wallet Relationship
    *
    *==============================================================================================*/

        @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException{
            try {
                return this.dao.createNewBrokerIdentityWalletRelationship(identity, walletPublicKey);
            } catch (CantCreateNewBrokerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(e.getMessage(), e,
                    "identity.getPublicKey()= "+identity.getPublicKey()+
                    "walletPublicKey= "+walletPublicKey
                    ,
                    "Invalid Data"
                );
            }
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                return this.dao.getAllBrokerIdentityWalletRelationship();
            } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBrokerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                return this.dao.getBrokerIdentityWalletRelationshipByIdentity(publicKey);
            } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBrokerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                return this.dao.getBrokerIdentityWalletRelationshipByWallet(walletPublicKey);
            } catch (CantGetListBrokerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListBrokerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }
}