package unit.com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 7/21/15.
 */
public class isAboveTest {

    @Test
    public void test_Major_True() {
        Version version1 = new Version("2.4.5");
        Version version2 = new Version("1.0.0");
        Assert.assertTrue(version1.isAbove(version2));

        Version version3 = new Version(4, 2, 1);
        Assert.assertTrue(version3.isAbove(version1));
    }

    @Test
    public void test_Major_False() {
        Version version1 = new Version("2.4.5");
        Version version2 = new Version("1.0.0");
        Assert.assertFalse(version2.isAbove(version1));

        Version version3 = new Version(1, 4, 5);
        Assert.assertFalse(version3.isAbove(version1));
    }

    @Test
    public void test_Minor_True() {
        Version version1 = new Version("1.4.5");
        Version version2 = new Version("1.0.0");
        Assert.assertTrue(version1.isAbove(version2));

        Version version3 = new Version(1, 6, 1);
        Assert.assertTrue(version3.isAbove(version1));
    }

    @Test
    public void test_Minor_False() {
        Version version1 = new Version("1.4.5");
        Version version2 = new Version("1.0.0");
        Assert.assertFalse(version2.isAbove(version1));

        Version version3 = new Version(1, 3, 1);
        Assert.assertFalse(version3.isAbove(version1));
    }

    @Test
    public void test_Patch_True() {
        Version version1 = new Version("1.4.5");
        Version version2 = new Version("1.4.0");
        Assert.assertTrue(version1.isAbove(version2));

        Version version3 = new Version(1, 4, 7);
        Assert.assertTrue(version3.isAbove(version1));
    }

    @Test
    public void test_Path_False() {
        Version version1 = new Version("1.4.5");
        Version version2 = new Version("1.4.0");
        Assert.assertFalse(version2.isAbove(version1));

        Version version3 = new Version(1, 4, 1);
        Assert.assertFalse(version3.isAbove(version1));
    }

    @Test
    public void test_Equal_False() {
        Version version1 = new Version("1.4.5");
        Version version2 = new Version("1.4.5");
        Assert.assertFalse(version2.isAbove(version1));
    }
}
