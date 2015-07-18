package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.pip_user.User;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 18/07/15.
 */
public class TestCreateUser {

    @Mock
    private ExtraUserUserAddonRoot testExtraUserUserAddonRoot=mock(ExtraUserUserAddonRoot.class);
    @Mock
    private User user=mock(User.class);

    @Test
    public void TestCreateUser_successful() throws Exception{

        when(testExtraUserUserAddonRoot.createUser("Extra User")).thenReturn(user);
        Assertions.assertThat(user).isNotNull();

    }
}
