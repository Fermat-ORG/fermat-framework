package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 5/6/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAddressesTest extends TestCase {

    String apiCode = "91c646ef-c3fd-4dd0-9dc9-eba5c5600549";
    Wallet wallet;

    @Before
    public void setUp() {
        wallet = new Wallet("7a9dc256-0e67-441f-886a-c4364fec9369", "Blockchain91-");
        wallet.setApiCode(apiCode);
    }

    @Test
    public void testGetBalance_NotNull() {
        List<Address> addresses = new ArrayList<>();

        try {
            addresses = wallet.listAddresses(0);
        } catch (Exception e) {
            System.out.println(e);
        }

        for (Address address : addresses){
            System.out.println("i'm an address: "+address.getAddress());
        }


    }
}
