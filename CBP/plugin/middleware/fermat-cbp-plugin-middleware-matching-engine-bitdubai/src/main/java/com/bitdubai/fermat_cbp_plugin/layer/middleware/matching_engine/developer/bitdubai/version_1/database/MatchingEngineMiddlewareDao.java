package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
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
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantUpdatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import static com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDatabaseConstants.*;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantCreateInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetEarningsPairException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantLoadOrCreateWalletReferenceException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareInputTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public final void loadOrCreateWalletReference(final WalletReference walletReference) throws CantLoadOrCreateWalletReferenceException {

        try {

            final DatabaseTable walletsTable = database.getTable(WALLETS_TABLE_NAME);

            walletsTable.addStringFilter(WALLETS_PUBLIC_KEY_COLUMN_NAME, walletReference.getPublicKey(), DatabaseFilterType.EQUAL);

            walletsTable.loadToMemory();

            final List<DatabaseTableRecord> records = walletsTable.getRecords();

            if (records.isEmpty()) {

                DatabaseTableRecord entityRecord = walletsTable.getEmptyRecord();

                entityRecord.setStringValue(WALLETS_PUBLIC_KEY_COLUMN_NAME, walletReference.getPublicKey());

                walletsTable.insertRecord(entityRecord);

            }

        } catch (final CantInsertRecordException e) {

            throw new CantLoadOrCreateWalletReferenceException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantLoadOrCreateWalletReferenceException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public final List<WalletReference> listWallets() throws CantListWalletsException {

        try {

            final DatabaseTable walletsTable = database.getTable(WALLETS_TABLE_NAME);

            walletsTable.loadToMemory();

            final List<DatabaseTableRecord> records = walletsTable.getRecords();

            final List<WalletReference> walletReferenceList = new ArrayList<>();

            for (DatabaseTableRecord record : records) {

                WalletReference walletReference = new WalletReference(
                        record.getStringValue(WALLETS_PUBLIC_KEY_COLUMN_NAME)
                );

                walletReferenceList.add(walletReference);
            }

            return walletReferenceList;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantListWalletsException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public final EarningsPair registerEarningsPair(final UUID            id                    ,
                                                   final Currency        earningCurrency       ,
                                                   final Currency        linkedCurrency        ,
                                                   final WalletReference earningWalletReference,
                                                   final WalletReference walletReference       ) throws CantAssociatePairException     ,
                                                                                                        PairAlreadyAssociatedException {

        try {

            if (existsEarningsPair(earningCurrency, linkedCurrency, walletReference))
                throw new PairAlreadyAssociatedException("earningCurrency: "+earningCurrency+ " - linkedCurrency: "+linkedCurrency+" - walletReference: "+walletReference, "The pair already exists in database.");

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

            entityRecord = buildEarningsPairDatabaseRecord(entityRecord, earningsPair, walletReference);

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
                                      final WalletReference walletReference) throws CantGetEarningsPairException {

        if (earningCurrency == null)
            throw new CantGetEarningsPairException(null, "", "The earningCurrency is required, can not be null");

        if (linkedCurrency == null)
            throw new CantGetEarningsPairException(null, "", "The linkedCurrency is required, can not be null");

        if (walletReference == null)
            throw new CantGetEarningsPairException(null, "", "The walletReference is required, can not be null");

        try {

            final DatabaseTable earningsPairTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            final List<DatabaseTableFilter> tableFilters1 = new ArrayList<>();

            tableFilters1.add(earningsPairTable.getNewFilter(EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME     , DatabaseFilterType.EQUAL, earningCurrency.getCode())          );
            tableFilters1.add(earningsPairTable.getNewFilter(EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME, DatabaseFilterType.EQUAL, earningCurrency.getType().getCode()));
            tableFilters1.add(earningsPairTable.getNewFilter(EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME      , DatabaseFilterType.EQUAL, linkedCurrency.getCode())           );
            tableFilters1.add(earningsPairTable.getNewFilter(EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME , DatabaseFilterType.EQUAL, linkedCurrency.getType().getCode()) );
            tableFilters1.add(earningsPairTable.getNewFilter(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME    , DatabaseFilterType.EQUAL, walletReference.getPublicKey()     ));

            final DatabaseTableFilterGroup filterGroup1 = earningsPairTable.getNewFilterGroup(tableFilters1, null, DatabaseFilterOperator.AND);

            final List<DatabaseTableFilter> tableFilters2 = new ArrayList<>();

            tableFilters2.add(earningsPairTable.getNewFilter(EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME     , DatabaseFilterType.EQUAL, linkedCurrency.getCode())           );
            tableFilters2.add(earningsPairTable.getNewFilter(EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME, DatabaseFilterType.EQUAL, linkedCurrency.getType().getCode()) );
            tableFilters2.add(earningsPairTable.getNewFilter(EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME      , DatabaseFilterType.EQUAL, earningCurrency.getCode())          );
            tableFilters2.add(earningsPairTable.getNewFilter(EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME , DatabaseFilterType.EQUAL, earningCurrency.getType().getCode()));
            tableFilters2.add(earningsPairTable.getNewFilter(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME    , DatabaseFilterType.EQUAL, walletReference.getPublicKey()     ));

            final DatabaseTableFilterGroup filterGroup2 = earningsPairTable.getNewFilterGroup(tableFilters2, null, DatabaseFilterOperator.AND);

            List<DatabaseTableFilterGroup> filterGroups = new ArrayList<>();

            filterGroups.add(filterGroup1);
            filterGroups.add(filterGroup2);

            final DatabaseTableFilterGroup filterGroupFinal = earningsPairTable.getNewFilterGroup(null, filterGroups, DatabaseFilterOperator.OR);

            earningsPairTable.setFilterGroup(filterGroupFinal);

            earningsPairTable.loadToMemory();

            final List<DatabaseTableRecord> records = earningsPairTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetEarningsPairException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    private DatabaseTableRecord buildEarningsPairDatabaseRecord(final DatabaseTableRecord record         ,
                                                                final EarningsPair        earningsPair   ,
                                                                final WalletReference     walletReference) {

        record.setUUIDValue  (EARNING_PAIR_ID_COLUMN_NAME                        , earningsPair.getId()                           );
        record.setFermatEnum (EARNING_PAIR_EARNING_CURRENCY_COLUMN_NAME          , earningsPair.getEarningCurrency()              );
        record.setFermatEnum (EARNING_PAIR_EARNING_CURRENCY_TYPE_COLUMN_NAME     , earningsPair.getEarningCurrency().getType()    );
        record.setFermatEnum (EARNING_PAIR_LINKED_CURRENCY_COLUMN_NAME           , earningsPair.getLinkedCurrency()               );
        record.setFermatEnum (EARNING_PAIR_LINKED_CURRENCY_TYPE_COLUMN_NAME      , earningsPair.getLinkedCurrency().getType()     );
        record.setStringValue(EARNING_PAIR_EARNINGS_WALLET_PUBLIC_KEY_COLUMN_NAME, earningsPair.getEarningsWallet().getPublicKey());
        record.setStringValue(EARNING_PAIR_WALLET_PUBLIC_KEY_COLUMN_NAME         , walletReference.getPublicKey()                 );
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


    public final InputTransaction createInputTransaction(final CurrencyMatching currencyMatching,
                                                         final UUID             earningsPairId  ) throws CantCreateInputTransactionException {

        try {

            final InputTransactionState state = InputTransactionState.UNMATCHED;

            final InputTransaction inputTransaction = new MatchingEngineMiddlewareInputTransaction(
                    currencyMatching.getOriginTransactionId()             ,
                    currencyMatching.getCurrencyGiving(),
                    currencyMatching.getAmountGiving(),
                    currencyMatching.getCurrencyReceiving(),
                    currencyMatching.getAmountReceiving(),
                    state
            );

            final DatabaseTable inputTransactionTable = database.getTable(EARNING_PAIR_TABLE_NAME);

            DatabaseTableRecord entityRecord = inputTransactionTable.getEmptyRecord();

            entityRecord = buildInputTransactionDatabaseRecord(entityRecord, earningsPairId, inputTransaction);

            inputTransactionTable.insertRecord(entityRecord);

            return inputTransaction;

        } catch (final CantInsertRecordException e) {

            throw new CantCreateInputTransactionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
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

    private DatabaseTableRecord buildInputTransactionDatabaseRecord(final DatabaseTableRecord record          ,
                                                                    final UUID                earningPairId   ,
                                                                    final InputTransaction    inputTransaction) {

        record.setStringValue(INPUT_TRANSACTION_ID_COLUMN_NAME, inputTransaction.getId());
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME        , inputTransaction.getCurrencyGiving());
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME   , inputTransaction.getCurrencyGiving().getType()   );
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME     , inputTransaction.getCurrencyReceiving()          );
        record.setFermatEnum (INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME, inputTransaction.getCurrencyReceiving().getType());
        record.setFloatValue (INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME          , inputTransaction.getAmountGiving()               );
        record.setFloatValue (INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME       , inputTransaction.getAmountReceiving()            );
        record.setFermatEnum (INPUT_TRANSACTION_STATE_COLUMN_NAME                  , inputTransaction.getState()                      );

        record.setUUIDValue  (INPUT_TRANSACTION_EARNING_PAIR_ID_COLUMN_NAME        , earningPairId                                    );

        return record;
    }

    private InputTransaction buildInputTransactionRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        String id                          = record.getStringValue(INPUT_TRANSACTION_ID_COLUMN_NAME                     );

        String currencyGivingString        = record.getStringValue(INPUT_TRANSACTION_CURRENCY_GIVING_COLUMN_NAME        );
        String currencyGivingTypeString    = record.getStringValue(INPUT_TRANSACTION_CURRENCY_GIVING_TYPE_COLUMN_NAME   );
        String currencyReceivingString     = record.getStringValue(INPUT_TRANSACTION_CURRENCY_RECEIVING_COLUMN_NAME     );
        String currencyReceivingTypeString = record.getStringValue(INPUT_TRANSACTION_CURRENCY_RECEIVING_TYPE_COLUMN_NAME);

        float  amountGiving                = record.getFloatValue (INPUT_TRANSACTION_AMOUNT_GIVING_COLUMN_NAME          );
        float  amountReceiving             = record.getFloatValue (INPUT_TRANSACTION_AMOUNT_RECEIVING_COLUMN_NAME       );

        String stateString                 = record.getStringValue(INPUT_TRANSACTION_STATE_COLUMN_NAME                  );

        InputTransactionState state = InputTransactionState.getByCode(stateString);

        Currency currencyGiving  = CurrencyHelper.getCurrency(currencyGivingString, currencyGivingTypeString);
        Currency currencyReceiving = CurrencyHelper.getCurrency(currencyReceivingString, currencyReceivingTypeString);

        return new MatchingEngineMiddlewareInputTransaction(
                id               ,
                currencyGiving   ,
                amountGiving     ,
                currencyReceiving,
                amountReceiving  ,
                state
        );
    }

}
