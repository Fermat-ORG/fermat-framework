package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.developer_bit_dubai;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.DeveloperBitDubai;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 05/11/15.
 */
public class GetAmountToPayTest {

    private DeveloperBitDubai developerBitDubai;

    @Before
    public void init() {
        developerBitDubai = new DeveloperBitDubai();
    }

    @Test
    public void getAmountToPayTest() {
        Assert.assertEquals(100, developerBitDubai.getAmountToPay());
    }
}
