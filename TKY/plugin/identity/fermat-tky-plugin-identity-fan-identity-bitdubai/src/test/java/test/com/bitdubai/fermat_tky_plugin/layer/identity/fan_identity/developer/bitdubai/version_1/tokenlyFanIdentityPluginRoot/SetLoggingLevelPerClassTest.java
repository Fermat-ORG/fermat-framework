package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetLoggingLevelPerClassTest {

    @Test
    public void setLoggingLevelPerClassTest() {
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        Map<String, LogLevel> newLoggingLevel = new Map<String, LogLevel>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public LogLevel get(Object key) {
                return null;
            }

            @Override
            public LogLevel put(String key, LogLevel value) {
                return null;
            }

            @Override
            public LogLevel remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends String, ? extends LogLevel> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<String> keySet() {
                return null;
            }

            @Override
            public Collection<LogLevel> values() {
                return null;
            }

            @Override
            public Set<Entry<String, LogLevel>> entrySet() {
                return null;
            }
        };

        doCallRealMethod().when(tokenlyFanIdentityPluginRoot).setLoggingLevelPerClass(newLoggingLevel);

    }

}
