package unit.com.bitdubai.desktop.sub_app_manager.fragmentFactory.SubAppManagerFragmentsEnumType;

import com.bitdubai.desktop.sub_app_manager.fragmentFactory.SubAppManagerFragmentsEnumType;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
public class GetValueTest {
    private final String ENUM_TYPE_CODE = "MF";

    @Test
    public void recibeString_valueFound() throws Exception {
        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound() throws Exception {
        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue("");
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeNull_valueNotFound() throws Exception {
        SubAppManagerFragmentsEnumType value = SubAppManagerFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}