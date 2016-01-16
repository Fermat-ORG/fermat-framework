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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.ActorExtraDataIdentity;
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
                    Collection<Platforms> plasformas = (Collection<Platforms>) list.get(currency);
                    for(Platforms plat : plasformas){
                        record = table.getEmptyRecord();
                        record.setUUIDValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_ID_COLUMN_NAME, UUID.randomUUID());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME, actorExtraData.getBrokerIdentity().getPublicKey());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, currency.getCode());
                        record.setStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME, plat.getCode());
                        table.insertRecord(record);
                    }
                }

            } catch (CantInsertRecordException e) {
                throw new CantCreateNewActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {
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

                }

                return null;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
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
                    QuotesExtraData quote = new QuotesExtraDataInformation(mer, pay, pri);
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
                table.addStringFilter(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_PLATFORM_ID_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
                table.loadToMemory();
                List<DatabaseTableRecord> records = table.getRecords();
                table.clearAllFilters();

                Map<Currency, Collection<Platforms>> currencies = new HashMap<Currency, Collection<Platforms>>();

                for (DatabaseTableRecord record : records) {
                    Currency currency = null;
                    try {
                        currency = FiatCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME));
                    } catch (InvalidParameterException e) {
                        try {
                            currency = CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerActorDatabaseConstants.PLATFORMS_EXTRA_DATA_CURRENCY_COLUMN_NAME));
                        } catch (InvalidParameterException e1) {
                            throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e1, "", "");
                        }
                    }

                    // TODO: terminal la implementacion del metodo
                }

                return null;

            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListActorExtraDataException(e.DEFAULT_MESSAGE, e, "", "");
            }
        }

}