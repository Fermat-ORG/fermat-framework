//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bitdubai.fermat_ccp_plugin.layer.identity.translator.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.translator.developer.bitdubai.version_1.database.IdentityTranslatorDatabaseConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IdentityTranslatorDeveloperDataBaseFactory {
    String pluginId;
    String databaseName;

    public IdentityTranslatorDeveloperDataBaseFactory(String pluginId, String databaseNAme) {
        this.pluginId = pluginId;
        this.databaseName = databaseNAme;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ArrayList databases = new ArrayList();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(this.databaseName, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        ArrayList tables = new ArrayList();
        ArrayList translatorIdentityTableColumns = new ArrayList();
        translatorIdentityTableColumns.add(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_PUBLIC_KEY_COLUMN_NAME);
         translatorIdentityTableColumns.add(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);
        translatorIdentityTableColumns.add(IdentityTranslatorDatabaseConstants.TRANSLATOR_DEVELOPER_ALIAS_COLUMN_NAME);
        DeveloperDatabaseTable translatorIdentityRegistryTable = developerObjectFactory.getNewDeveloperDatabaseTable(IdentityTranslatorDatabaseConstants.TRANSLATOR_TABLE_NAME, translatorIdentityTableColumns);
        tables.add(translatorIdentityRegistryTable);
        return tables;
    }

    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
        ArrayList returnedRecords = new ArrayList();
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException var11) {
            return returnedRecords;
        }

        List records = selectedTable.getRecords();
        Iterator var6 = records.iterator();

        while(var6.hasNext()) {
            DatabaseTableRecord row = (DatabaseTableRecord)var6.next();
            ArrayList developerRow = new ArrayList();
            Iterator var9 = row.getValues().iterator();

            while(var9.hasNext()) {
                DatabaseRecord field = (DatabaseRecord)var9.next();
                developerRow.add(field.getValue().toString());
            }

            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }

        return returnedRecords;
    }
}
