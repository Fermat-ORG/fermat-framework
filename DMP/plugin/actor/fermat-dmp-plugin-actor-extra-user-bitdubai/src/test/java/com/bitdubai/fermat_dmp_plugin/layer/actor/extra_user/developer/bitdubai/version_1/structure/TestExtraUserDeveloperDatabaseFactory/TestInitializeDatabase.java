package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import  com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import com.googlecode.catchexception.CatchException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestInitializeDatabase {

    @Mock
    private ExtraUserDeveloperDatabaseFactory mockExtraUserDeveloperFactory;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private ExtraUserDatabaseFactory  mockExtraUserDatabaseFactory;
    @Mock
    private Database database;
    @Before
    public void SetUp() throws Exception{

        mockExtraUserDeveloperFactory= new ExtraUserDeveloperDatabaseFactory(errorManager, pluginDatabaseSystem, UUID.randomUUID());
    }
    @Ignore
    public void testInitializeDatabase_successful() throws Exception {

        when(pluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(database);
        when(mockExtraUserDatabaseFactory.createDatabase(any(UUID.class))).thenReturn(database);

        CatchException.catchException(mockExtraUserDeveloperFactory).initializeDatabase();
        assertThat(CatchException.caughtException()).isNull();
    }

}