package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import com.googlecode.catchexception.CatchException;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestInitializeDatabase {

    @Mock
    private ExtraUserDeveloperDatabaseFactory mockExtraUserDeveloperFactory = mock(ExtraUserDeveloperDatabaseFactory.class);
    @Mock
    private ErrorManager errorManager=mock(ErrorManager.class);
    @Mock
    private PlatformDatabaseSystem platformDatabaseSystem=mock(PlatformDatabaseSystem.class);
    @Mock
    private DatabaseFactory mockDatabaseFactory = mock(DatabaseFactory.class);
    @Mock
    private ExtraUserDatabaseFactory mockExtraUserDatabaseFactory = mock(ExtraUserDatabaseFactory.class);
    @Mock
    private Database database;
    @Before
    public void SetUp() throws Exception{

        mockExtraUserDeveloperFactory= new ExtraUserDeveloperDatabaseFactory(errorManager,platformDatabaseSystem);
    }
    @Ignore
    public void testInitializeDatabase_successful() throws Exception {

        when(platformDatabaseSystem.createDatabase("Extra User")).thenReturn(database);
        when(mockExtraUserDatabaseFactory.createDatabase()).thenReturn(database);

        CatchException.catchException(mockExtraUserDeveloperFactory).initializeDatabase();
        assertThat(CatchException.caughtException()).isNull();
    }

}