package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 06/07/15.
 */
public class ExtraUserDatabaseFactoryTest {


    ExtraUserDatabaseFactory extraUserDatabaseFactory = new ExtraUserDatabaseFactory();
    Database database;

    @Test
    public void testCreateDatabase() throws Exception {
        database=extraUserDatabaseFactory.createDatabase();
        database.executeQuery();
        assertThat(database).isNotNull();
    }
}