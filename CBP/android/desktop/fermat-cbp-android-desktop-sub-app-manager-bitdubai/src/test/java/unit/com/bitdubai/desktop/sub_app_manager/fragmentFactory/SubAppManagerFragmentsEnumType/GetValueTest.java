package unit.com.bitdubai.desktop.sub_app_manager.fragmentFactory.SubAppManagerFragmentsEnumType;

import com.bitdubai.desktop.sub_app_manager.fragmentFactory.SubAppManagerFragmentsEnumType;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Unit Test para el metodo getValue() de SubAppManagerFragmentsEnumType
 *
 * Created by nelson on 17/09/15.
 */
public class GetValueTest {
    @Test
    public void recibeString_valueFound() throws Exception {
        final String ENUM_TYPE_CODE = "MF";

        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound_returnNull() throws Exception {
        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue("");
        assertThat(value).isNull();
    }

    @Test
    public void recibeNull_valueNotFound_returnNull() throws Exception {
        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}