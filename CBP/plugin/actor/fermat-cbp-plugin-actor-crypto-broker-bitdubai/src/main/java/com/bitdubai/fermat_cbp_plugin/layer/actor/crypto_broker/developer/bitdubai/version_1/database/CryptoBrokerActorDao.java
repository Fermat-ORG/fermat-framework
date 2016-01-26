package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.ActorExtraDataIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.ActorExtraDataInformation;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.BrokerIdentityWalletRelationshipInformation;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.QuotesExtraDataInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 19/11/15.
 */
public class CryptoBrokerActorDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
    */

        public CryptoBrokerActorDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCryptoBrokerActorDatabaseException{
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCryptoBrokerActorDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CryptoBrokerActorDatabaseFactory BrokerActorDatabaseFactory = new CryptoBrokerActorDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = BrokerActorDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCryptoBrokerActorDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }

            new pruebaExtraData(this);
        }

        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException {

            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = RelationshipTable.getEmptyRecord();
                UUID relationshipId = UUID.randomUUID();
                loadRecordAsNew(
                        recordToInsert,
                        relationshipId,
                        identity.getPublicKey(),
                        wallet
                );
                RelationshipTable.insertRecord(recordToInsert);
                return constructCryptoBrokerActorRelationshipFromRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantCreateNewBrokerIdentityWalletRelationshipException(CantCreateNewBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                Collection<BrokerIdentityWalletRelationship> resultados = new ArrayList<BrokerIdentityWalletRelationship>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCryptoBrokerActorRelationshipFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.addStringFilter(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey(), DatabaseFilterType.EQUAL);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    return  constructCryptoBrokerActorRelationshipFromRecord(record);
                }
                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            try {
                DatabaseTable RelationshipTable = this.database.getTable(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME);
                RelationshipTable.addUUIDFilter(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME, wallet, DatabaseFilterType.EQUAL);
                RelationshipTable.loadToMemory();
                List<DatabaseTableRecord> records = RelationshipTable.getRecords();
                RelationshipTable.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    return  constructCryptoBrokerActorRelationshipFromRecord(record);
                }
                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListBrokerIdentityWalletRelationshipException(CantGetListBrokerIdentityWalletRelationshipException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
        Private methods
     */

        private void loadRecordAsNew(
                DatabaseTableRecord databaseTableRecord,
                UUID   relationshipId,
                String publicKeyBroker,
                UUID   walletId
        ) {
            databaseTableRecord.setUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId);
            databaseTableRecord.setStringValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME, walletId);
        }

        private BrokerIdentityWalletRelationship newCryptoBrokerActorRelationship(
                UUID   relationshipId,
                String publicKeyBroker,
                UUID   walletId
        ){
            return new BrokerIdentityWalletRelationshipInformation(relationshipId, publicKeyBroker, walletId);
        }

        private BrokerIdentityWalletRelationship constructCryptoBrokerActorRelationshipFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
            UUID    relationshipId       = record.getUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME);
            String  publicKeyBroker     = record.getStringValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME);
            UUID    walletId            = record.getUUIDValue(CryptoBrokerActorDatabaseConstants.CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME);
            return newCryptoBrokerActorRelationship(relationshipId, publicKeyBroker, walletId);
        }


    /*==============================================================================================
    *
    *   Actor Extra Data
    *
    *==============================================================================================*/


        public void createBrokerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException{
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record   = table.getEmptyRecord();
                record.setStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                record.setStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME, actorExtraData.getBrokerIdentity().getAlias());
                table.insertRecord(record);
                createActorQoutes(actorExtraData);
                createActorPlasforms(actorExtraData);
            } catch (CantInsertRecordException e) {
                throw new CantCreateNewActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private void createActorQoutes(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException{
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record;
                for(QuotesExtraData quote : actorExtraData.getQuotes()){
                    record = table.getEmptyRecord();
                    record.setUUIDValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_QUOTE_ID_COLUMN_NAME, UUID.randomUUID());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME, quote.getMerchandise().getCode());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME, quote.getMerchandise().getCode());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME, quote.getPaymentMethod().getCode());
                    record.setFloatValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME, quote.getPrice());
                    table.insertRecord(record);
                }
            } catch (CantInsertRecordException e) {
                throw new CantCreateNewActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private void createActorPlasforms(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException{
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record;
                Map list = actorExtraData.getCurrencies();
                Iterator it = list.keySet().iterator();
                while(it.hasNext()){
                    Currency currency = (Currency) it.next();
                    Collection<Platforms> plasforms = (Collection<Platforms>) list.get(currency);
                    for(Platforms plat : plasforms){
                        record = table.getEmptyRecord();
                        record.setUUIDValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_ID_COLUMN_NAME, UUID.randomUUID());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, currency.getCode());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_COLUMN_NAME, plat.getCode());
                        table.insertRecord(record);
                    }
                }
            } catch (CantInsertRecordException e) {
                throw new CantCreateNewActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateBrokerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();
                table.addStringFilter(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey(), DatabaseFilterType.EQUAL);
                record.setStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME, actorExtraData.getBrokerIdentity().getAlias());
                table.updateRecord(record);
                updateQuotes(actorExtraData);
                updatePlasforms(actorExtraData);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private  void updateQuotes(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();
                for(QuotesExtraData quote : actorExtraData.getQuotes()){
                    table.clearAllFilters();;
                    table.addStringFilter(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey(), DatabaseFilterType.EQUAL);
                    table.addUUIDFilter(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_QUOTE_ID_COLUMN_NAME, quote.getQuoteId(), DatabaseFilterType.EQUAL);
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME, quote.getMerchandise().getCode());
                    record.setStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME, quote.getPaymentMethod().getCode());
                    record.setFloatValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME, quote.getPrice());
                    table.updateRecord(record);
                }
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private  void updatePlasforms(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();
                record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                table.deleteRecord(record);
                Map list = actorExtraData.getCurrencies();
                Iterator it = list.keySet().iterator();
                while(it.hasNext()){
                    Currency currency = (Currency) it.next();
                    Collection<Platforms> plasforms = (Collection<Platforms>) list.get(currency);
                    for(Platforms plat : plasforms){
                        record = table.getEmptyRecord();
                        record.setUUIDValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_ID_COLUMN_NAME, UUID.randomUUID());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, currency.getCode());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_COLUMN_NAME, plat.getCode());
                        table.insertRecord(record);
                    }
                }
            } catch (CantInsertRecordException e) {
                throw new CantUpdateActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (CantDeleteRecordException e) {
                throw new CantUpdateActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }

        }

        public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_TABLE_NAME);
                table.loadToMemory();
                List<DatabaseTableRecord> records = table.getRecords();
                table.clearAllFilters();
                Collection<ActorExtraData> actoresExtraDatas =  new ArrayList<ActorExtraData>();
                for (DatabaseTableRecord record : records) {
                    String alias = record.getStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME);
                    String publicKey = record.getStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME);
                    ActorIdentity identity = new ActorExtraDataIdentity(alias, publicKey);
                    Collection<QuotesExtraData> quotes = this.getQuotesByIdentity(publicKey);
                    Map<Currency, Collection<Platforms>> currencies =  getPlatformsByIdentity(publicKey);
                    ActorExtraData data = new ActorExtraDataInformation(
                            identity,
                            quotes,
                            currencies
                    );
                    actoresExtraDatas.add(data);
                }
                return actoresExtraDatas;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public ActorExtraData getActorExtraDataByPublicKey(String _publicKey) throws CantGetListActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_TABLE_NAME);
                table.loadToMemory();
                List<DatabaseTableRecord> records = table.getRecords();
                table.clearAllFilters();
                for (DatabaseTableRecord record : records) {
                    String alias = record.getStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME);
                    String publicKey = record.getStringValue(CryptoBrokerActorDatabaseConstants.ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME);
                    ActorIdentity identity = new ActorExtraDataIdentity(alias, publicKey);
                    Collection<QuotesExtraData> quotes = this.getQuotesByIdentity(publicKey);
                    Map<Currency, Collection<Platforms>> currencies =  getPlatformsByIdentity(publicKey);
                    ActorExtraData data = new ActorExtraDataInformation(
                            identity,
                            quotes,
                            currencies
                    );
                    return data;
                }
                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<Platforms> getPlatformsSupport(String brokerPublicKey, Currency currency) throws CantGetListPlatformsException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_TABLE_NAME);
                table.clearAllFilters();
                table.addStringFilter(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerPublicKey, DatabaseFilterType.EQUAL);
                table.addStringFilter(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, currency.getCode(), DatabaseFilterType.EQUAL);
                table.loadToMemory();
                List<DatabaseTableRecord> records2 = table.getRecords();
                Collection<Platforms> platform = new ArrayList<>();
                for (DatabaseTableRecord record : records2) {
                    platform.add( Platforms.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_COLUMN_NAME)) );
                }
                return platform;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListPlatformsException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListPlatformsException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private Collection<QuotesExtraData> getQuotesByIdentity(String publicKey) throws CantGetListActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_TABLE_NAME);
                table.addStringFilter(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
                table.loadToMemory();
                List<DatabaseTableRecord> records = table.getRecords();
                table.clearAllFilters();
                Collection<QuotesExtraData> quotes = new ArrayList<QuotesExtraData>();
                for (DatabaseTableRecord record : records) {
                    Currency mer = null;
                    Currency pay = null;
                    try {
                        mer = FiatCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME));
                    } catch (InvalidParameterException e) {
                        try {
                            mer = CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME));
                        } catch (InvalidParameterException e1) {
                            throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e1, "", "");
                        }
                    }
                    try {
                        pay = FiatCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME));
                    } catch (InvalidParameterException e) {
                        try {
                            pay = CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME));
                        } catch (InvalidParameterException e1) {
                            throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e1, "", "");
                        }
                    }
                    Float pri = record.getFloatValue(CryptoBrokerActorDatabaseConstants.QUOTE_EXTRA_DATA_PRICE_COLUMN_NAME);
                    QuotesExtraData quote = new QuotesExtraDataInformation(UUID.randomUUID(), mer, pay, pri);
                    quotes.add(quote);
                }
                return quotes;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        private Map<Currency, Collection<Platforms>> getPlatformsByIdentity(String publicKey) throws CantGetListActorExtraDataException {
            try {
                DatabaseTable table = this.database.getTable(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_TABLE_NAME);
                String Query = "SELECT DISTINCT " +
                        CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME +
                        " FROM " +
                        CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_TABLE_NAME;

                Collection<DatabaseTableRecord> records = null;
                records = table.customQuery(Query, true);
                Collection<Currency> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    Currency currency = null;
                    try {
                        currency = FiatCurrency.getByCode(record.getStringValue("Column0"));
                    } catch (InvalidParameterException e) {
                        try {
                            currency = CryptoCurrency.getByCode(record.getStringValue("Column0"));
                        } catch (InvalidParameterException e1) {
                            throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e1, "", "");
                        }
                    }
                    resultados.add(currency);
                }
                Map<Currency, Collection<Platforms>> currencies = new HashMap<Currency, Collection<Platforms>>();
                for(Currency currency : resultados){
                    table.clearAllFilters();
                    table.addStringFilter(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
                    table.addStringFilter(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, currency.getCode(), DatabaseFilterType.EQUAL);
                    table.loadToMemory();
                    List<DatabaseTableRecord> records2 = table.getRecords();
                    Collection<Platforms> platform = new ArrayList<>();
                    for (DatabaseTableRecord record : records2) {
                            platform.add( Platforms.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_COLUMN_NAME)) );
                    }
                    currencies.put(currency, platform);
                }
                return currencies;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

}