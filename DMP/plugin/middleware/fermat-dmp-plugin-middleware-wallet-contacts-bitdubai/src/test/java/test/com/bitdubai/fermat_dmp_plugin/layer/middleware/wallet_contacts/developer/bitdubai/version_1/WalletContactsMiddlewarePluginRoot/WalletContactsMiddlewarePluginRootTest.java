/*
 * @(#WalletContactsMiddlewarePluginRootTest.java 07/08/2015
 * Copyright 2015 BitDubai, Inc. All rights reserved.
 * BitDubai/CONFIDENTIAL
 * */

package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;


// Packages and classes to import of jdk 1.7
import java.util.List;
import java.util.UUID;

// Packages and classes to import of JUnit core.
import org.junit.*;
import static org.junit.Assert.assertNotNull;

// Packages and classes to import of mockito API.
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

// Packages and classes to import of fermat suite.
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;


/**
 * <p>The class <code>test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot.WalletContactsMiddlewarePluginRootTest</code> is a
 * junit component for test the component WalletContactsMiddlewarePluginRoot.
 *
 * @author Raul Geomar Pena (raul.pena@gmail.com)
 * @version 1.0.0
 * @since 07/08/2015
 */
public class WalletContactsMiddlewarePluginRootTest {


    // Private instance fields declarations.
    // Database system.
    private PluginDatabaseSystem    pluginDatabaseSystem = null;

    // Developer object factory.
    private DeveloperObjectFactory developerObjectFactory = null;

    // Middleware plugin.
    private WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = null;

    // Public constructor declarations.

    /**
     * <p>Unique constructor without parameters.
     */
    public WalletContactsMiddlewarePluginRootTest() {

        // Call to super class.
        super();
    }


    // Public instance method declarations.

    /**
     * <p>Setup internal resources.
     *
     * @see {@link org.junit.Before}
     */
    @Before
    public void setUp() {

        // Setup test class.
        // Create a plugin mock.

        // Plugin DatabaseSystem.
        this.pluginDatabaseSystem   = mock (PluginDatabaseSystem.class);
        this.developerObjectFactory = mock (DeveloperObjectFactory.class);
        this.dbFactory              = mock (WalletContactsMiddlewareDeveloperDatabaseFactory.class);
    }

    /**
     * <p>Release internal resources.
     *
     * @see {@link org.junit.After}
     */
    @After
    public void tearDown() {

        // Release the resources.
        this.pluginDatabaseSystem   = null;
        this.developerObjectFactory = null;
        this.dbFactory              = null;
    }


    // Private instance methods declarations.
    /*
     *
     *  <p>Method that return the recodrs.
     *
     *  @return DeveloperDatabaseTableRecord list.
     * */
    private List<DeveloperDatabaseTableRecord> getDeveloperDatabaseTableRecord () {

        // Method setup.
        List<DeveloperDatabaseTableRecord> list = new java.util.ArrayList<DeveloperDatabaseTableRecord> ();




        // Return the values.
        return list;
    }

    /*
     *
     *  <p>Method that return the database list.
     *
     *  @return Database list.
     * */
    private List<DeveloperDatabase> getDatabaseList () {

        // Method setup.
        List<DeveloperDatabase> list = new java.util.ArrayList<DeveloperDatabase> ();


            // Add new DeveloperDatabase.
            list.add (new DeveloperDatabase () {


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

    /*
     *
     *  <p>Method that return the database list.
     *
     *  @return Database list.
     * */
    private List<DeveloperDatabaseTable> getDatabaseTableList () {

        // Method setup.
        List<DeveloperDatabaseTable> list = new java.util.ArrayList<DeveloperDatabaseTable> ();


        // Add new DeveloperDatabase.
        list.add (new DeveloperDatabaseTable () {

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

    /*
     *
     *  <p>Method that return the database.
     *
     *  @return Database.
     * */
    private DeveloperDatabase getDatabase () {

        // Method setup.
        // Add new DeveloperDatabase.
        return new DeveloperDatabase () {


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


    // Public instance methods declarations.
    /**
     * <p>Method that test the database list.
     *
     * @see {@link org.junit.Test}
     * @see {@link java.lang.Exception}
     */
    @Test
    public void shouldGetDatabaseList () throws Exception {

        // Setup method.
        this.dbFactory.setPluginId (UUID.randomUUID ());
        this.dbFactory.setPluginDatabaseSystem (this.pluginDatabaseSystem);

        // Setup services component.
        when(this.developerObjectFactory.getNewDeveloperDatabase(Matchers.anyString(), Matchers.anyString())).thenReturn (this.getDatabase());
        when (dbFactory.getDatabaseList (Matchers.<DeveloperObjectFactory>any ())).thenReturn(this.getDatabaseList ());


        // Execute mock services.
        dbFactory.initializeDatabase();
        List<DeveloperDatabase> result = dbFactory.getDatabaseList (this.developerObjectFactory);


        // Verify the business execution.
        verify (dbFactory).getDatabaseList(Matchers.<DeveloperObjectFactory>any ());


        // Check the test.
        assertNotNull (result);
    }

    /**
     *
     * <p>Method that test the database table list.
     *
     * @see {@link org.junit.Test}
     * @see {@link java.lang.Exception}
     */
    @Test
    public void shouldGetDatabaseTableList () throws Exception {

        // Setup method.
        this.dbFactory.setPluginId(UUID.randomUUID());
        this.dbFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        // Setup services component.
        when(this.developerObjectFactory.getNewDeveloperDatabase(Matchers.anyString(), Matchers.anyString())).thenReturn (this.getDatabase());
        when (dbFactory.getDatabaseTableList(Matchers.<DeveloperObjectFactory>any())).thenReturn(this.getDatabaseTableList());


        // Execute mock services.
        dbFactory.initializeDatabase();
        List<DeveloperDatabaseTable> result = dbFactory.getDatabaseTableList(this.developerObjectFactory);


        // Verify the business execution.
        verify (dbFactory).getDatabaseTableList(Matchers.<DeveloperObjectFactory>any());


        // Check the test.
        assertNotNull (result);
    }

    /**
     *
     * <p>Method that test the database table list.
     *
     * @see {@link org.junit.Test}
     * @see {@link java.lang.Exception}
     */
    @Test
    public void shouldGetDatabaseTableContent () throws Exception {

        // Setup method.
        this.dbFactory.setPluginId(UUID.randomUUID());
        this.dbFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        // Setup services component.
        when(this.developerObjectFactory.getNewDeveloperDatabase(Matchers.anyString(), Matchers.anyString())).thenReturn (this.getDatabase());
        when (dbFactory.getDatabaseTableContent (Matchers.<DeveloperObjectFactory>any(), Matchers.<DeveloperDatabaseTable>any())).thenReturn (this.getDeveloperDatabaseTableRecord ());


        // Execute mock services.
        dbFactory.initializeDatabase();
        List<DeveloperDatabaseTableRecord> result = dbFactory.getDatabaseTableContent(this.developerObjectFactory, this.getDatabaseTableList ().get (0));


        // Verify the business execution.
        verify (dbFactory).getDatabaseTableContent(Matchers.<DeveloperObjectFactory>any(), Matchers.<DeveloperDatabaseTable>any());


        // Check the test.
        assertNotNull (result);
    }
}