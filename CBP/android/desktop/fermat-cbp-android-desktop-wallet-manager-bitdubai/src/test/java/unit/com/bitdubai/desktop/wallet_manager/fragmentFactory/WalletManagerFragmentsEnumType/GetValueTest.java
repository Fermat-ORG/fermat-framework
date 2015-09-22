package unit.com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentsEnumType;

import com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentsEnumType;

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

        WalletManagerFragmentsEnumType value = WalletManagerFragmentsEnumType.getValue(ENUM_TYPE_CODE);
        assertThat(value).isNotNull();
    }

    @Test
    public void recibeString_valueNotFound_returnNull() throws Exception {
        WalletManagerFragmentsEnumType value = WalletManagerFragmentsEnumType.getValue("");
        assertThat(value).isNull();
    }

    @Test
    public void recibeNull_valueNotFound_returnNull() throws Exception {
        WalletManagerFragmentsEnumType value = WalletManagerFragmentsEnumType.getValue(null);
        assertThat(value).isNull();
    }
}