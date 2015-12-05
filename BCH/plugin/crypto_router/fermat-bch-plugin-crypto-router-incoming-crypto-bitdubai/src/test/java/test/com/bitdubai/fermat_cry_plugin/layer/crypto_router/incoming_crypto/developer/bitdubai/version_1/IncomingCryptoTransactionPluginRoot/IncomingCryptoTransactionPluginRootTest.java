package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseFactory;


import org.mockito.Mock;

import java.util.List;


/**
 * Created by franklin on 05/08/15.
 */
public class IncomingCryptoTransactionPluginRootTest {

    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private IncomingCryptoDataBaseFactory dbFactory;

    private List<DeveloperDatabaseTableRecord> getDeveloperDatabaseTableRecord() {
        return new java.util.ArrayList<>();
    }

    private List<DeveloperDatabase> getDatabaseList() {

        // Method setup.
        List<DeveloperDatabase> list = new java.util.ArrayList<DeveloperDatabase>();


        // Add new DeveloperDatabase.
        list.add(new DeveloperDatabase() {
                     @Override
                     public String getName() {
                         return null;
                     }

                     @Override
                     public String getId() {
                         return null;
                     }
                 }
        );

        // Return the values.
        return list;
    }

    private List<DeveloperDatabaseTable> getDatabaseTableList() {

        // Method setup.
        List<DeveloperDatabaseTable> list = new java.util.ArrayList<DeveloperDatabaseTable>();


        // Add new DeveloperDatabase.
        list.add(new DeveloperDatabaseTable() {

                     @Override
                     public String getName() {
                         return null;
                     }

                     @Override
                     public List<String> getFieldNames() {
                         return null;
                     }
                 }
        );

        // Return the values.
        return list;
    }

    private DeveloperDatabase getDatabase() {
        // Method setup.
        // Add new DeveloperDatabase.
        return new DeveloperDatabase() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }
        };
    }

}
