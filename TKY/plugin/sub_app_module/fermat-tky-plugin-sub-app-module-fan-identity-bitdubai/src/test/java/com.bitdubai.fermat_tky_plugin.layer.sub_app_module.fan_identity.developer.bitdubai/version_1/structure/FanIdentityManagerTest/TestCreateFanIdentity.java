package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure.FanIdentityManagerTest;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure.FanIdentityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by MACUARE on 21/04/16.
 */
public class TestCreateFanIdentity {

    private FanIdentityManager fanIdentityManager;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        System.out.println("initializing");
        Assert.assertNull(fanIdentityManager);
        fanIdentityManager = new FanIdentityManager(null,null,null);
        Assert.assertNotNull(fanIdentityManager);
    }


    @Test
    public void testCreateFanIdentityException() throws Exception {
        System.out.println("testCreateFanIdentityException");
        exception.expect(NullPointerException.class);
        Fan fan = fanIdentityManager.createFanIdentity(
                "JON DOE",
                "Picture".getBytes(),
                "123",
                ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM
        );

        Assert.assertNull(fan);
    }

}//end of class
