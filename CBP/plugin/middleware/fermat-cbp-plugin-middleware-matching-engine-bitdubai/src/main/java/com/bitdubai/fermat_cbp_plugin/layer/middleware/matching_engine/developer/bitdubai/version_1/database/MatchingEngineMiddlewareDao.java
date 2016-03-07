package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionType;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantRegisterEarningsSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantUpdatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantCreateInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetEarningsPairException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListInputTransactionsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareInputTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDatabaseConstants.*;
/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connection requests by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public MatchingEngineMiddlewareDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                       final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initialize() throws CantInitializeDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME
            );

        } catch (final DatabaseNotFoundException e) {

            try {

                MatchingEngineMiddlewareDatabaseFactory cryptoBrokerActorNetworkServiceDatabaseFactory = new MatchingEngineMiddlewareDatabaseFactory(pluginDatabaseSystem);
                database = cryptoBrokerActorNetworkServiceDatabaseFactory.createDatabase(
                        pluginId,
                        MATCHING_ENGINE_MIDDLEWARE_DATABASE_NAME
                );

            } catch (final CantCreateDatabaseException f) {

                throw new CantInitializeDatabaseException(f, "", "There was a problem and we cannot create the database.");
            } catch (final Exception z) {

                throw new CantInitializeDatabaseException(z, "", "Unhandled Exception.");
            }

        } catch (final CantOpenDatabaseException e) {

            throw new CantInitializeDatabaseException(e, "", "Exception not handled by the plugin, there was a problem and we cannot open the database.");
        } catch (final Exception e) {

            throw new CantInitializeDatabaseException(e, "", "Unhandled Exception.");
        }
    }

    public final void registerWalletReference(final WalletReference walletReference) throws CantRegisterEarningsSettingsException {

        try {

            final DatabaseTable walletsTable = database.getTable(WALLETS_TABLE_NAME);

            DatabaseTableRecord entityRecord = walletsTable.getEmptyRecord();

            walletsTable.insertRecord(
                    buildWalletReferenceDatabaseRecord(
                            entityRecord,
                            walletReference
                    )
            );

        } catch (final CantInsertRecordException e) {

            throw new CantRegisterEarningsSettingsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public final WalletReference loadWalletReference(final String walletPublicKey) throws CantLoadEarningSettingsException, EarningsSettingsNotRegisteredException {

        try {

            final DatabaseTable walletsTable = database.getTable(WALLETS_TABLE_NAME);

            walletsTable.addStringFilter(WALLETS_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);

            walletsTable.loadToMemory();

            final List<DatabaseTableRecord> records = walletsTable.getRecords();

            if (!records.isEmpty()) {

                return buildWalletReferenceRecord(records.get(0));
            } else
                throw new EarningsSettingsNotRegisteredException("walletPublicKey: "+walletPublicKey, "A wallet with this public key cannot be found.");

        }  catch (final CantLoadTableToMemoryException e) {

            throw new CantLoadEarningSettingsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantLoadEarningSettingsException(e, "", "Exception reading records of the table Cannot recognize the codes of the enums.");
        }
    }

    public final List<WalletReference> listWallets() throws CantListWalletsException {

        try {

            final DatabaseTable walletsTable = database.getTable(WALLETS_TABLE_NAME);

            walletsTable.loadToMemory();

            final List<DatabaseTableRecord> records = walletsTable.getRecords();

            final List<WalletReference> walletReferenceList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {

                walletReferenceList.add(
                        buildWalletReferenceRecord(record)
                );
            }

            return walletReferenceList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListWalletsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListWalletsException(e, "", "Exception reading records of the table Cannot recognize the codes of the enums.");
        }
    }

    public final EarningsPair registerEarningsPair(final UUID            id                    ,
                                                   final Currency        earningCurrency       ,
                                                   final Currency        linkedCurrency        ,
                                                   final WalletReference earningWalletReference,
                                                   final String          walletPublicKey       ) throws CantAssociatePairException     ,
                                                                                                        PairAlreadyAssociatedException {

        try {

            if (existsEarningsPair(earningCurrency, linkedCurrency, walletPublicKey))
                throw new PairAlreadyAssociatedException("earningCurrency: "+earningCurrency+ " - linkedCurrency: "+linkedCurrency+" - walletReference: "+walletPublicKey, "The pair already exists in database.");

            if (existsEarningsPair(linkedCurrency, earningCurrency, walletPublicKey))
                throw new PairAlreadyAssociatedException("earningCurrency: "+earningCurrency+ " - linkedCurrency: "+linkedCurrency+" - walletReference: "+walletPublicKey, "The pair already exists in database.");

            final EarningPairState state = EarningPairState.ASSOCIATED;

            final EarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                    id                    ,
                    earningCurrency       ,
                    linkedCurrency        ,
                    earningWalletReference,
                    state                 ,

                    this
            );

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            DatabaseTableRecord entityRecord = earningsPairTable.getEmptyRecord();

            entityRecord = buildEarningsPairDatabaseRecord(entityRecord, earningsPair, walletPublicKey);

            earningsPairTable.insertRecord(entityRecord);

            return earningsPair;

        } catch (final CantInsertRecordException e) {

            throw new CantAssociatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantGetEarningsPairException cantGetEarningsPairException) {

            throw new CantAssociatePairException(cantGetEarningsPairException, "", "Exception not handled by the plugin, there is a problem in database and i cannot validate if the record already exists.");
        }
    }

    public final void associateEarningsPair(final UUID id) throws CantAssociatePairException,
                                                                  PairNotFoundException     {

        try {

            final EarningPairState state = EarningPairState.ASSOCIATED;

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            earningsPairTable.addUUIDFilter(EARNING_PAIR_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            if (!records.isEmpty()) {

                DatabaseTableRecord entityRecord = records.get(0);

                entityRecord.setFermatEnum(EARNING_PAIR_STATE_COLUMN_NAME, state);

                earningsPairTable.updateRecord(entityRecord);

            } else
                throw new PairNotFoundException("id: "+id, "Pair not exists with the given id.");


        } catch (final CantLoadTableToMemoryException e) {

            throw new CantAssociatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final CantUpdateRecordException e) {

            throw new CantAssociatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        }
    }

    public final void disassociateEarningsPair(final UUID id) throws CantDisassociatePairException,
                                                                     PairNotFoundException        {

        try {

            final EarningPairState state = EarningPairState.DISASSOCIATED;

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            earningsPairTable.addUUIDFilter(EARNING_PAIR_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            if (!records.isEmpty()) {

                DatabaseTableRecord entityRecord = records.get(0);

                entityRecord.setFermatEnum(EARNING_PAIR_STATE_COLUMN_NAME, state);

                earningsPairTable.updateRecord(entityRecord);

            } else
                throw new PairNotFoundException("id: "+id, "Pair not exists with the given id.");


        } catch (final CantLoadTableToMemoryException e) {

            throw new CantDisassociatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final CantUpdateRecordException e) {

            throw new CantDisassociatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        }
    }


    public final void updateEarningsPair(final UUID            id             ,
                                         final WalletReference earningsWalletReference) throws CantUpdatePairException,
                                                                                               PairNotFoundException  {

        try {

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            earningsPairTable.addUUIDFilter(EARNING_PAIR_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            if (!records.isEmpty()) {

                DatabaseTableRecord entityRecord = records.get(0);

                entityRecord.setStringValue(EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME, earningsWalletReference.getPublicKey());

                earningsPairTable.updateRecord(entityRecord);

            } else
                throw new PairNotFoundException("id: "+id, "Pair not exists with the given id.");


        } catch (final CantLoadTableToMemoryException e) {

            throw new CantUpdatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final CantUpdateRecordException e) {

            throw new CantUpdatePairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot update the record.");
        }
    }

    public final List<EarningsPair> listEarningPairs(final WalletReference walletReference) throws CantListEarningsPairsException {

        try {

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            earningsPairTable.addStringFilter(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME, walletReference.getPublicKey(), DatabaseFilterType.EQUAL);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            final List<EarningsPair> earningsPairList = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                earningsPairList.add(buildEarningPairRecord(record));

            return earningsPairList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListEarningsPairsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListEarningsPairsException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    public boolean existsEarningsPair(final Currency        earningCurrency,
                                      final Currency        linkedCurrency ,
                                      final String          walletPublicKey) throws CantGetEarningsPairException {

        if (earningCurrency == null)
            throw new CantGetEarningsPairException(null, "", "The earningCurrency is required, can not be null");

        if (linkedCurrency == null)
            throw new CantGetEarningsPairException(null, "", "The linkedCurrency is required, can not be null");

        if (walletPublicKey == null)
            throw new CantGetEarningsPairException(null, "", "The walletPublicKey is required, can not be null");

        try {

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            earningsPairTable.addStringFilter(EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME, earningCurrency.getCode(), DatabaseFilterType.EQUAL);
            earningsPairTable.addStringFilter(EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME, earningCurrency.getType().getCode(), DatabaseFilterType.EQUAL);
            earningsPairTable.addStringFilter(EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME      , linkedCurrency.getCode()           , DatabaseFilterType.EQUAL);
            earningsPairTable.addStringFilter(EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME , linkedCurrency.getType().getCode() , DatabaseFilterType.EQUAL);
            earningsPairTable.addStringFilter(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME    , walletPublicKey                    , DatabaseFilterType.EQUAL);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetEarningsPairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    private DatabaseTableRecord buildEarningsPairDatabaseRecord(final DatabaseTableRecord record         ,
                                                                final EarningsPair        earningsPair   ,
                                                                final String              walletPublicKey) {

        record.setUUIDValue  (EARNING_PAIR_ID_COLUMN_NAME                        , earningsPair.getId()                           );
        record.setFermatEnum (EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME          , earningsPair.getEarningCurrency()              );
        record.setFermatEnum (EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME     , earningsPair.getEarningCurrency().getType()    );
        record.setFermatEnum (EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME           , earningsPair.getLinkedCurrency()               );
        record.setFermatEnum (EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME      , earningsPair.getLinkedCurrency().getType()     );
        record.setStringValue(EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME, earningsPair.getEarningsWallet().getPublicKey());
        record.setStringValue(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME         , walletPublicKey                                );
        record.setFermatEnum (EARNING_PAIR_STATE_COLUMN_NAME                     , earningsPair.getState()                        );

        return record;
    }

    private EarningsPair buildEarningPairRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        UUID   requestId                 = record.getUUIDValue  (EARNING_PAIR_ID_COLUMN_NAME                        );
        String earningCurrencyString     = record.getStringValue(EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME);
        String earningCurrencyTypeString = record.getStringValue(EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME     );
        String linkedCurrencyString      = record.getStringValue(EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME           );
        String linkedCurrencyTypeString  = record.getStringValue(EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME      );
        String walletPublicKey           = record.getStringValue(EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String stateString               = record.getStringValue(EARNING_PAIR_STATE_COLUMN_NAME                     );

        Currency earningCurrency = CurrencyHelper.getCurrency(earningCurrencyTypeString, earningCurrencyString);
        Currency linkedCurrency  = CurrencyHelper.getCurrency(linkedCurrencyTypeString, linkedCurrencyString);

        EarningPairState state = EarningPairState.getByCode(stateString);

        WalletReference earningsWalletReference = new WalletReference(walletPublicKey);

        return new MatchingEngineMiddlewareEarningsPair(
                requestId              ,
                earningCurrency        ,
                linkedCurrency         ,
                earningsWalletReference,
                state                  ,

                this
        );
    }

    public final InputTransaction createInputTransaction(final CurrencyMatching      currencyMatching,
                                                         final UUID                  earningsPairId  ) throws CantCreateInputTransactionException {

        try {

            final UUID                  newId = UUID.randomUUID();

            final InputTransactionType  type  = InputTransactionType .ORIGINAL ;
            final InputTransactionState state = InputTransactionState.UNMATCHED;

            final InputTransaction inputTransaction = new MatchingEngineMiddlewareInputTransaction(
                    newId                                    ,
                    currencyMatching.getOriginTransactionId(),
                    currencyMatching.getCurrencyGiving()     ,
                    currencyMatching.getAmountGiving()       ,
                    currencyMatching.getCurrencyReceiving()  ,
                    currencyMatching.getAmountReceiving()    ,
                    type                                     ,
                    state
            );

            final DatabaseTable inputTransactionTable = database.getTable(INPUT_TRANSACTION_TABLE_NAME);

            DatabaseTableRecord entityRecord = inputTransactionTable.getEmptyRecord();

            entityRecord = buildInputTransactionDatabaseRecord(entityRecord, earningsPairId, inputTransaction);

            inputTransactionTable.insertRecord(entityRecord);

            return inputTransaction;

        } catch (final CantInsertRecordException e) {

            throw new CantCreateInputTransactionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public final DatabaseTransaction getNewDatabaseTransaction() {

        return database.newTransaction();
    }

    public final InputTransaction createPartialInputTransaction(final DatabaseTransaction   databaseTransaction,
                                                                final String                originTransactionId,
                                                                final Currency              currencyGiving     ,
                                                                final float                 amountGiving       ,
                                                                final Currency              currencyReceiving  ,
                                                                final float                 amountReceiving    ,
                                                                final UUID                  earningsPairId     ,
                                                                final InputTransactionState state              ) {

        final InputTransactionType  type  = InputTransactionType .PARTIAL  ;

        final InputTransaction inputTransaction = new MatchingEngineMiddlewareInputTransaction(
                UUID.randomUUID()  ,
                originTransactionId,
                currencyGiving     ,
                amountGiving       ,
                currencyReceiving  ,
                amountReceiving    ,
                type               ,
                state
        );

        final DatabaseTable inputTransactionTable = database.getTable(INPUT_TRANSACTION_TABLE_NAME);

        DatabaseTableRecord entityRecord = inputTransactionTable.getEmptyRecord();

        entityRecord = buildInputTransactionDatabaseRecord(entityRecord, earningsPairId, inputTransaction);

        databaseTransaction.addRecordToInsert(inputTransactionTable, entityRecord);

        return inputTransaction;
    }

    public boolean existsInputTransaction(final String inputTransactionId) throws CantGetInputTransactionException {

        if (inputTransactionId == null)
            throw new CantGetInputTransactionException(null, "", "The inputTransactionId is required, can not be null");

        try {

            final DatabaseTable inputTransactionsTable = database.getTable(INPUT_TRANSACTION_TABLE_NAME);

            inputTransactionsTable.addStringFilter(INPUT_TRANSACTION_ID_COLUMN_NAME, inputTransactionId, DatabaseFilterType.EQUAL);

            inputTransactionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = inputTransactionsTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetInputTransactionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public final InputTransaction getNextUnmatchedSellInputTransaction(final EarningsPair earningsPair) throws CantGetInputTransactionException {

        try {

            final InputTransactionState state = InputTransactionState.UNMATCHED;

            final DatabaseTable inputTransactionsTable = database.getTable(INPUT_TRANSACTION_TABLE_NAME);

            inputTransactionsTable.addUUIDFilter      (INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME, earningsPair.getId()            , DatabaseFilterType.EQUAL);
            inputTransactionsTable.addFermatEnumFilter(INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME, earningsPair.getLinkedCurrency(), DatabaseFilterType.EQUAL);
            inputTransactionsTable.addFermatEnumFilter(INPUT_TRANSACTION_STATE_COLUMN_NAME, state, DatabaseFilterType.EQUAL);

            inputTransactionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = inputTransactionsTable.getRecords();

            if (records.isEmpty())
                return null;
            else
                return buildInputTransactionRecord(records.get(0));

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetInputTransactionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantGetInputTransactionException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    public final List<InputTransaction> listUnmatchedBuyInputTransactions(final EarningsPair earningsPair) throws CantListInputTransactionsException {

        try {

            final InputTransactionState state = InputTransactionState.UNMATCHED;

            final DatabaseTable inputTransactionsTable = database.getTable(INPUT_TRANSACTION_TABLE_NAME);

            inputTransactionsTable.addUUIDFilter      (INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME   , earningsPair.getId()            , DatabaseFilterType.EQUAL);
            inputTransactionsTable.addFermatEnumFilter(INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME, earningsPair.getLinkedCurrency(), DatabaseFilterType.EQUAL);
            inputTransactionsTable.addFermatEnumFilter(INPUT_TRANSACTION_STATE_COLUMN_NAME             , state                           , DatabaseFilterType.EQUAL);

            inputTransactionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = inputTransactionsTable.getRecords();

            List<InputTransaction> inputTransactionList = new ArrayList<>();

            for (final DatabaseTableRecord record : records)
                inputTransactionList.add(buildInputTransactionRecord(record));

            return inputTransactionList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListInputTransactionsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (final InvalidParameterException e) {

            throw new CantListInputTransactionsException(e, "", "Exception reading records of the table Cannot recognize the codes of the currencies.");
        }
    }

    private DatabaseTableRecord buildInputTransactionDatabaseRecord(final DatabaseTableRecord record          ,
                                                                    final UUID                earningPairId   ,
                                                                    final InputTransaction    inputTransaction) {

        record.setUUIDValue  (INPUT_TRANSACTION_ID_COLUMN_NAME                     , inputTransaction.getId()                         );
        record.setStringValue(INPUT_TRANSACTION_ORIGIN_TRANSACTION_ID_COLUMN_NAME, inputTransaction.getOriginTransactionId());
        record.setFermatEnum(INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME, inputTransaction.getCurrencyGiving());
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME   , inputTransaction.getCurrencyGiving().getType()   );
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME     , inputTransaction.getCurrencyReceiving()          );
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME, inputTransaction.getCurrencyReceiving().getType());
        record.setFloatValue(INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME, inputTransaction.getAmountGiving());
        record.setFloatValue (INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME       , inputTransaction.getAmountReceiving()            );
        record.setFermatEnum (INPUT_TRANSACTION_TYPE_COLUMN_NAME                   , inputTransaction.getType()                       );
        record.setFermatEnum (INPUT_TRANSACTION_STATE_COLUMN_NAME                  , inputTransaction.getState()                      );

        record.setUUIDValue  (INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME        , earningPairId                                    );

        return record;
    }

    private InputTransaction buildInputTransactionRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        UUID id                            = record.getUUIDValue  (INPUT_TRANSACTION_ID_COLUMN_NAME                     );
        String originTransactionId         = record.getStringValue(INPUT_TRANSACTION_ORIGIN_TRANSACTION_ID_COLUMN_NAME  );

        String currencyGivingString        = record.getStringValue(INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME        );
        String currencyGivingTypeString    = record.getStringValue(INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME   );
        String currencyReceivingString     = record.getStringValue(INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME     );
        String currencyReceivingTypeString = record.getStringValue(INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME);

        float  amountGiving                = record.getFloatValue (INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME          );
        float  amountReceiving             = record.getFloatValue (INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME       );

        String typeString                  = record.getStringValue(INPUT_TRANSACTION_TYPE_COLUMN_NAME                   );
        String stateString                 = record.getStringValue(INPUT_TRANSACTION_STATE_COLUMN_NAME                  );

        InputTransactionType  type  = InputTransactionType .getByCode(typeString );
        InputTransactionState state = InputTransactionState.getByCode(stateString);

        Currency currencyGiving  = CurrencyHelper.getCurrency(currencyGivingString, currencyGivingTypeString);
        Currency currencyReceiving = CurrencyHelper.getCurrency(currencyReceivingString, currencyReceivingTypeString);

        return new MatchingEngineMiddlewareInputTransaction(
                id                 ,
                originTransactionId,
                currencyGiving     ,
                amountGiving       ,
                currencyReceiving  ,
                amountReceiving    ,
                type               ,
                state
        );
    }


    private DatabaseTableRecord buildWalletReferenceDatabaseRecord(final DatabaseTableRecord record         ,
                                                                   final WalletReference     walletReference) {

        record.setStringValue(WALLETS_PUBLIC_KEY_COLUMN_NAME, walletReference.getPublicKey());

        return record;
    }

    private WalletReference buildWalletReferenceRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        String publicKey = record.getStringValue(WALLETS_PUBLIC_KEY_COLUMN_NAME);

        return new WalletReference(
                publicKey
        );
    }

}
