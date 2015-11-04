package unit.com.bitdubai.sub_app.crypto_customer_community.fragmentFactory.CryptoCustomerCommunityFragmentsEnumType;

import com.bitdubai.sub_app.crypto_customer_community.fragmentFactory.CryptoCustomerCommunityFragmentsEnumType;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Unit Test para el metodo getValue() de SubAppManagerFragmentsEnumType
 * <p/>
 * Created by nelson on 17/09/15.
 */
public class GetValueTest {
    @Test
    public void recibeString_valueFound() throws Exception {
        final String ENUM_TYPE_CODE = "MF";

        CryptoCustomerCommunityFragmentsEnumType value = CryptoCustomerCommunityFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound_returnNull() throws Exception {
        CryptoCustomerCommunityFragmentsEnumType value = CryptoCustomerCommunityFragmentsEnumType.getValue("");
        assertThat(value).isNull();
    }

    @Test
    public void recibeNull_valueNotFound_returnNull() throws Exception {
        CryptoCustomerCommunityFragmentsEnumType value = CryptoCustomerCommunityFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}