package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestCrateDatabase {
    @Mock
    private ExtraUserDatabaseFactory mockExtraUserDatabaseFactory = mock(ExtraUserDatabaseFactory.class);
    @Mock
    private Database database=mock(Database.class);


    @Test
    public void testCreateDatabase() throws Exception {

        when(mockExtraUserDatabaseFactory.createDatabase()).thenReturn(database);

        Assertions.assertThat(database).isNotNull();
    }

}