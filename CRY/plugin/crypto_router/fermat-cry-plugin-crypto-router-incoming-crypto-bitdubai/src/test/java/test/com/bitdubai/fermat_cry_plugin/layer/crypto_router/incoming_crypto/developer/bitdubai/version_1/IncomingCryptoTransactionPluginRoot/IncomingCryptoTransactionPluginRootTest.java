package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by franklin on 05/08/15.
 */
public class IncomingCryptoTransactionPluginRootTest {
    // Database system.
    private PluginDatabaseSystem pluginDatabaseSystem = null;

    // Developer object factory.
    private DeveloperObjectFactory developerObjectFactory = null;

    private IncomingCryptoDataBaseFactory dbFactory = null;

    public IncomingCryptoTransactionPluginRootTest() {

        // Call to super class.
        super();
    }

    @Before
    public void setUp() {

        // Setup test class.
        // Create a plugin mock.

        // Plugin DatabaseSystem.
        this.pluginDatabaseSystem = mock(PluginDatabaseSystem.class);
        this.developerObjectFactory = mock(DeveloperObjectFactory.class);
        this.dbFactory = mock(IncomingCryptoDataBaseFactory.class);
    }

    @After
    public void tearDown() {

        // Release the resources.
        this.pluginDatabaseSystem = null;
        this.developerObjectFactory = null;
        this.dbFactory = null;
    }

    private List<DeveloperDatabaseTableRecord> getDeveloperDatabaseTableRecord() {

        // Method setup.
        List<DeveloperDatabaseTableRecord> list = new java.util.ArrayList<DeveloperDatabaseTableRecord>();


        // Return the values.
        return list;
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
