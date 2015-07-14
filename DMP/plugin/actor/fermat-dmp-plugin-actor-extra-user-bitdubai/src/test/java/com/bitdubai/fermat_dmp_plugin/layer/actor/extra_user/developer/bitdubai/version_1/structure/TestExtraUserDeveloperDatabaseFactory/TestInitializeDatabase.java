package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by francisco on 08/07/15.
 */
public class TestInitializeDatabase {

    ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory;
    CantCreateDatabaseException cantCreateDatabaseException;

    CantInitializeExtraUserRegistryException cantInitializeExtraUserRegistryException;
    @Ignore
    @Test
    public void testInitializeDatabase_InitializeExtraUserRegistry() throws Exception {
        cantCreateDatabaseException=null;
        try {
            extraUserDeveloperDatabaseFactory.initializeDatabase();
        } catch (CantInitializeExtraUserRegistryException e) {
            e.printStackTrace();
            cantInitializeExtraUserRegistryException=e;
        }
        Assertions.assertThat(cantInitializeExtraUserRegistryException).isNull();

    }

}