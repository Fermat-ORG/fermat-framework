package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUser;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetUser {

    @Mock
    private ExtraUserUserAddonRoot extraUserUserAddonRoot = mock(ExtraUserUserAddonRoot.class);
    @Mock
    private ExtraUserRegistry testExtraUserRegistry;
    @Mock
    private User user=mock(User.class);
    @Mock
    private UUID User_Id;
    @Mock
    private User Muser;
    @Mock
    private String databaseName="ExtraUser";
    @Mock
    private String tableName="UserRegistry";
    @Mock
    private User MockUser=  new ExtraUser();
    @Mock
    private DatabaseDataType testDatabaseDataType;

    public void setUpValues(){
        User_Id=UUID.randomUUID();
        MockUser.setName("Extra User");
    }

      @Before
     public void setUp() throws Exception{
        setUpValues();
        when(extraUserUserAddonRoot.createUser("Extra User")).thenReturn(user);

    }
    @Test
    public void testGetUser() throws Exception {

        System.out.println(User_Id);
        when(extraUserUserAddonRoot.getUser(User_Id)).thenReturn(user);

        Assertions.assertThat(user).isNotNull();
    }

}