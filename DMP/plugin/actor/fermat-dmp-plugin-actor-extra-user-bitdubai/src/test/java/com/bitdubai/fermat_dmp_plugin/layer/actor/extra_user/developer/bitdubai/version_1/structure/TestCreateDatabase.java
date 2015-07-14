package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 08/07/15.
 */
public class TestCreateDatabase {
        ExtraUserDatabaseFactory extraUserDatabaseFactory = new ExtraUserDatabaseFactory();
        Database database;
    /*@Test
    public void testCreateDatabase() throws Exception {
       database=extraUserDatabaseFactory.createDatabase();

        assertThat(extraUserDatabaseFactory.createDatabase()).isNotNull();
    }
    @Test
    public void testCreatedatabase_Not_Exceptions() throws Exception{
        Exception exception=null;
        try {
            extraUserDatabaseFactory.createDatabase();
        } catch (CantCreateDatabaseException e) {
            e.printStackTrace();
            exception=e;
        }
        Assertions.assertThat(exception).isNull();
    }*/
}