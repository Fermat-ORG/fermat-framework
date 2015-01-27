package platform.layer._8_communication.cloud.developer.bitdubai;

import platform.layer._8_communication.CommunicationChannel;
import platform.layer._8_communication.CommunicationChannelDeveloper;
import platform.layer._8_communication.cloud.developer.bitdubai.version_1.CloudCommunicationChannel;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements CommunicationChannelDeveloper {

    CommunicationChannel mCommunicationChannel;

    @Override
    public CommunicationChannel getCommunicationChannel() {
        return mCommunicationChannel;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mCommunicationChannel = new CloudCommunicationChannel();

    }
}
