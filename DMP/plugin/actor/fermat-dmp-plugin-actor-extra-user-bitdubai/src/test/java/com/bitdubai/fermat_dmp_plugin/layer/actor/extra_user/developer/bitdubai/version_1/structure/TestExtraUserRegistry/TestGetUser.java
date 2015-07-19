package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserRegistry;

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
    private ExtraUserRegistry mockExtraUserRegistry = mock(ExtraUserRegistry.class);
    @Mock
    private UUID mockUUII_User;
    @Mock
    private User mockUser = new ExtraUser();
    @Mock
    private User user=mock(User.class);

    @Before
    public void SetUp() throws Exception{
        mockUUII_User=UUID.randomUUID();
    }
    @Test
    public void TestGetUser_successful() throws Exception{

        when(mockExtraUserRegistry.getUser(mockUUII_User)).thenReturn(user);
        Assertions.assertThat(user).isNotNull();
    }
}