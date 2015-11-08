package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.developer_bit_dubai;

import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 05/11/15.
 */
public class GetTimePeriodTest {

    private DeveloperBitDubai developerBitDubai;

    @Before
    public void init() {
        developerBitDubai = new DeveloperBitDubai();
    }

    @Test
    public void getTimePeriod() {
        assertThat(developerBitDubai.getTimePeriod()).isEqualTo(TimeFrequency.MONTHLY);
    }
}
