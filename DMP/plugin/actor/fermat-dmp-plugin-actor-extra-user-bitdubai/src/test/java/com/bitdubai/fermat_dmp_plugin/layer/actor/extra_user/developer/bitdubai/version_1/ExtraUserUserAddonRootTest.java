package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.pip_user.User;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by francisco on 06/07/15.
 */
public class ExtraUserUserAddonRootTest {

    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();

    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabase developerDatabase;
    DeveloperDatabaseTable developerDatabaseTable;

    UUID id;
    User user=null;


    @Test
    public void testGetDatabaseList() throws Exception {
        Assertions.assertThat(extraUserUserAddonRoot.
                getDatabaseList(developerObjectFactory)).isNotNull();
    }

    @Test
    public void testGetDatabaseTableList() throws Exception {
        Assertions.assertThat(extraUserUserAddonRoot.
                getDatabaseTableList(developerObjectFactory,developerDatabase)).isNotNull();
    }

    @Test
    public void testGetDatabaseTableContent() throws Exception {
        Assertions.assertThat(extraUserUserAddonRoot.
                getDatabaseTableContent(
                        developerObjectFactory,developerDatabase,developerDatabaseTable)).isNotNull();
    }


    @Test
    public void testGetUser() throws Exception {
        user = extraUserUserAddonRoot.getUser(id);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testCreateUser() throws Exception {
        user=null;
        user=extraUserUserAddonRoot.createUser("ExtraUser");
        Assertions.assertThat(user).isNotNull();
    }


}