package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Test;

/**
 * Created by rodrigo on 7/21/15.
 */
public class toStringTest {

    @Test
    public void toStringOk() {
        Version version = new Version(1, 0, 0);

        org.junit.Assert.assertEquals(version.toString(), "1.0.0");
    }

    @Test
    public void toStringEquals() {
        Version version = new Version(1, 0, 0);
        Version version2 = new Version(1, 0, 0);

        org.junit.Assert.assertEquals(version.toString(), version2.toString());
    }
}
