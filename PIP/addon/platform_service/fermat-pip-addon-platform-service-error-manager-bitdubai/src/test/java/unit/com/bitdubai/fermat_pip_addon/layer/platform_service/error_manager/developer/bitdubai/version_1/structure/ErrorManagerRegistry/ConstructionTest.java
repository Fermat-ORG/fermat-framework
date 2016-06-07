package unit.com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerRegistry;

import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerRegistry;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class ConstructionTest {

    private ErrorManagerRegistry testRegistry;

    @Test
    public void Construction_DefaultConstructor_ObjectNotNull() {
        testRegistry = new ErrorManagerRegistry();

        assertThat(testRegistry).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_ParametrizedConstructorWithNullParameters_ThrowsIllegalParameterException() {
        testRegistry = new ErrorManagerRegistry(null);
    }
}
