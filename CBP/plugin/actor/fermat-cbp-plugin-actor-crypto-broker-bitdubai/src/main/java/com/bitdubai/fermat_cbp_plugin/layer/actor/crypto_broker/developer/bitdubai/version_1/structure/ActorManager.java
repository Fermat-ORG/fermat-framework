package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.*;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class ActorManager implements CryptoBrokerActorManager {

    private CryptoBrokerActorDao dao;
    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    public ActorManager(CryptoBrokerActorDao dao, CryptoBrokerManager cryptoBrokerANSManager, CryptoBrokerWalletManager cryptoBrokerWalletManager){
        this.dao = dao;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;

        /*
        try {
            CryptoBrokerWallet wallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet("WalletId");
            List<CryptoBrokerWalletAssociatedSetting> settings = wallet.getCryptoWalletSetting().getCryptoBrokerWalletAssociatedSettings();
            wallet.getQuote(CryptoCurrency.BITCOIN, 1f, FiatCurrency.VENEZUELAN_BOLIVAR);
        } catch (CryptoBrokerWalletNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            e.printStackTrace();
        } catch (CantGetCryptoBrokerQuoteException e) {
            e.printStackTrace();
        }
        */

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
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.dao.getBrokerIdentityWalletRelationshipByWallet(wallet);
        }

}