package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 11/09/15.
 */
public class DeveloperBitDubaiTest {


    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getAddon());
    }



    @Test
    public void getAddon() {
        assertThat(developTest.getAddon()).isInstanceOf(DataBaseSystemAndroidAddonRoot.class);
    }
}


