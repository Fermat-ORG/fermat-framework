package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.DeveloperBitDubaiTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginDeveloperException;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by MACUARE on 19/04/16.
 */
public class Teststart {
    private DeveloperBitDubai developerBitDubai;


    @Before
    public void setUp(){
        System.out.println("initializing");
        Assert.assertNull(developerBitDubai);
        developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai);
    }

    @Test
    public void testStart() throws Exception {
        System.out.println("testStart");
        developerBitDubai.start();
        int actual = developerBitDubai.getClass().getDeclaredMethods().length;
        int expected = 5;
        Assert.assertEquals(expected, actual);
    }

}// end of class
