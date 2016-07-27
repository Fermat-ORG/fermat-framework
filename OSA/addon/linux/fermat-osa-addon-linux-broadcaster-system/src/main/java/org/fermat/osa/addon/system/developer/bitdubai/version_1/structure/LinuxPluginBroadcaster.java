package org.fermat.osa.addon.system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class LinuxPluginBroadcaster implements Broadcaster {

    public LinuxPluginBroadcaster() {

    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code) {

    }

    @Override
    public void publish(BroadcasterType broadcasterType, String appCode, String code) {

    }

    @Override
    public void publish(BroadcasterType broadcasterType, String appCode, FermatBundle bundle) {

    }

    @Override
    public int publish(BroadcasterType broadcasterType, FermatBundle bundle, String channelReceiversCode) {
        return 0;
    }
}
