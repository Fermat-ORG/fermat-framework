String developerName = "Alejandro Bicelis"
String developerMail = "abicelis@gmail.com"
String pluginName = "Ccex"
String layerName = "Provider"
String packageRoot = "com.bitdubai.fermat_cer_plugin"
String jdkVersion = "1.7"


List databaseTables = []
// Los tipos de datos son valores del enum DatabaseDataType, esta en el fermat-api
// Table Definition Example
databaseTables << [
        name: "Provider Info",
        columns: [
                ["ID", "STRING", "100", "true"],
                ["Name", "STRING", "100", "false"]
        ],
        firstKey:"ID"
]

databaseTables << [
        name: "Current Exchange Rates",
        columns: [
                ["ID", "STRING", "100", "true"],
                ["From Currency", "STRING", "100", "false"],
                ["To Currency", "STRING", "100", "false"],
                ["Sale Price", "STRING", "100", "false"],
                ["Purchase Price", "STRING", "100", "false"],
                ["Timestamp", "LONG_INTEGER", "100", "false"]

        ],
        firstKey:"ID"
]


/**
 * No more changes from here.
 */

String upperCaseUnderscore(String  string) {
    return string.replaceAll(" ", "_").toUpperCase()
}

String lowerCaseUnderscore(String  string) {
    return string.replaceAll(" ", "_").toLowerCase()
}

String constantsClassName = """${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseConstants"""

def templateConstants = """package ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database;

/**
 * The Class <code>${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database.${constantsClassName}</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by ${developerName} - (${developerMail}) on ${(new Date()).format("dd/MM/yy")}.
 *
 * @version 1.0
 * @since Java JDK ${jdkVersion}
 */
public class ${constantsClassName} {

"""
databaseTables.each{ table ->
    templateConstants += """    /**
     * $table.name database table definition.
     */
    static final String ${upperCaseUnderscore(table.name)}_TABLE_NAME = "${lowerCaseUnderscore(table.name)}";\n\n"""
    table.columns.each { column ->
        templateConstants += """    static final String ${upperCaseUnderscore(table.name)}_${upperCaseUnderscore(column[0])}_COLUMN_NAME = "${lowerCaseUnderscore(column[0])}";\n"""
    }
    if (table.firstKey) {
        templateConstants += """\n    static final String ${upperCaseUnderscore(table.name)}_FIRST_KEY_COLUMN = "${lowerCaseUnderscore(table.firstKey)}";\n\n"""
    }
}

println templateConstants+"}"

println "\n***************************************************************\n********************************************************\n"

def templateFactory = """package   ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database.${pluginName.replaceAll("  ", "")}${layerName.replaceAll(" ", "")}DatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by ${developerName} - (${developerMail}) on ${(new Date()).format("dd/MM/yy")}.
 *
 * @version 1.0
 * @since Java JDK ${jdkVersion}
 */
public class ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

"""

databaseTables.each{ table ->
    templateFactory += """           /**
            * Create $table.name table.
            */
           table = databaseFactory.newTableFactory(ownerId, ${constantsClassName}.${upperCaseUnderscore(table.name)}_TABLE_NAME);\n\n"""
    table.columns.each { column ->
        templateFactory += """            table.addColumn(${constantsClassName}.${upperCaseUnderscore(table.name)}_${upperCaseUnderscore(column[0])}_COLUMN_NAME, DatabaseDataType.${column[1]}, ${column[2]}, Boolean.${column[3].toUpperCase()});\n"""
    }
    if (table.firstKey) {
        templateFactory += """\n             table.addIndex(${constantsClassName}.${upperCaseUnderscore(table.name)}_FIRST_KEY_COLUMN);\n\n"""
    }

    templateFactory += """            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }"""
}

templateFactory += """
} catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
"""

println templateFactory
println "\n***************************************************************\n********************************************************\n"


def templateDatabaseException = """
package ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.exceptions.CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by ${developerName} - (${developerMail}) on ${(new Date()).format("dd/MM/yy")}.
 *
 * @version 1.0
 * @since Java JDK ${jdkVersion}
 */
public class CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE ${pluginName.toUpperCase()} ${layerName.toUpperCase()} DATABASE EXCEPTION";

    public CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException(final String message) {
        this(message, null);
    }

    public CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

"""
println templateDatabaseException

println "\n***************************************************************\n********************************************************\n"


def templateDeveloperFactory = """package ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import ${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.exceptions.CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>${packageRoot}.layer.${lowerCaseUnderscore(layerName)}.${lowerCaseUnderscore(pluginName)}.developer.bitdubai.version_1.database.${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by ${developerName} - (${developerMail}) on ${(new Date()).format("dd/MM/yy")}.
 *
 * @version 1.0
 * @since Java JDK ${jdkVersion}
 */

public class ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException
     */
    public void initializeDatabase() throws CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseFactory ${pluginName.replaceAll(" ", "")[0].toLowerCase()}${pluginName.replaceAll(" ", "")[1..-1]}${layerName.replaceAll(" ", "")}DatabaseFactory = new ${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = ${pluginName.replaceAll(" ", "")[0].toLowerCase()}${pluginName.replaceAll(" ", "")[1..-1]}${layerName.replaceAll(" ", "")}DatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitialize${pluginName.replaceAll(" ", "")}${layerName.replaceAll(" ", "")}DatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("${pluginName}", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

"""

databaseTables.each{ table ->
    templateDeveloperFactory += """           /**
            * Table $table.name columns.
            */
           List<String> ${table.name[0].replaceAll(" ", "").toLowerCase()}${table.name[1..-1].replaceAll(" ", "")}Columns = new ArrayList<String>();\n\n"""
    table.columns.each { column ->
        templateDeveloperFactory += """              ${table.name[0].replaceAll(" ", "").toLowerCase()}${table.name[1..-1].replaceAll(" ", "")}Columns.add(${constantsClassName}.${upperCaseUnderscore(table.name)}_${upperCaseUnderscore(column[0])}_COLUMN_NAME);\n"""
    }
    templateDeveloperFactory += """           /**
            * Table $table.name addition.
            */
                   DeveloperDatabaseTable ${table.name[0].replaceAll(" ", "").toLowerCase()}${table.name[1..-1].replaceAll(" ", "")}Table = developerObjectFactory.getNewDeveloperDatabaseTable(${constantsClassName}.${upperCaseUnderscore(table.name)}_TABLE_NAME, ${table.name[0].replaceAll(" ", "").toLowerCase()}${table.name[1..-1].replaceAll(" ", "")}Columns);
                   tables.add(${table.name[0].replaceAll(" ", "").toLowerCase()}${table.name[1..-1].replaceAll(" ", "")}Table);\n\n"""
}

templateDeveloperFactory += """

        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        for (DatabaseTableRecord row : records) {
            List<String> developerRow = new ArrayList<String>();
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
"""

println templateDeveloperFactory