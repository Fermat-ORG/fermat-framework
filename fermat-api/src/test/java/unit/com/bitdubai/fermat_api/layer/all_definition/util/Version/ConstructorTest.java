package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by rodrigo on 7/21/15.
 */
public class ConstructorTest {

    @Test
    public void constructorIntOk() {
        Version version = new Version(1, 0, 0);

        Assert.assertEquals(version.getMajor(), 1);
        Assert.assertEquals(version.getMinor(), 0);
        Assert.assertEquals(version.getPatch(), 0);
    }

    @Test
    public void constructorStringOk() {
        Version version = new Version("1.0.0");

        Assert.assertEquals(version.getMajor(), 1);
        Assert.assertEquals(version.getMinor(), 0);
        Assert.assertEquals(version.getPatch(), 0);
    }

    @Test
    public void getterAndSetterTest() {
        Version version = new Version(1, 0, 0);

        version.setMajor(2);
        version.setMinor(1);
        version.setPatch(3);

        Assert.assertEquals(version.getMajor(), 2);
        Assert.assertEquals(version.getMinor(), 1);
        Assert.assertEquals(version.getPatch(), 3);

    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullValue() {
        Version version = new Version(null);
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructorInvalidVersion1() {

        expectedException.expect(IllegalArgumentException.class);
        Version version1 = new Version("1,3,5");
    }

    @Test
    public void constructorInvalidVersion2() {

        expectedException.expect(IllegalArgumentException.class);
        Version version1 = new Version("1.3.5.5");
    }

    @Test
    public void constructorInvalidVersion3() {

        expectedException.expect(IllegalArgumentException.class);
        Version version1 = new Version("1.a.5");
    }
}
