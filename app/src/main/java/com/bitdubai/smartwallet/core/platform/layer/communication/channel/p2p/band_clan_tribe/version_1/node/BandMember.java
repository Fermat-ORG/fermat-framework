package com.bitdubai.smartwallet.core.platform.layer.communication.channel.p2p.band_clan_tribe.version_1.node;

import com.bitdubai.smartwallet.core.platform.layer.communication.channel.p2p.band_clan_tribe.version_1.network.Band;
import com.bitdubai.smartwallet.core.platform.layer.communication.channel.p2p.band_clan_tribe.version_1.connection.BandMemberToMember;

import java.util.List;

/**
 * Created by ciencias on 24.12.14.
 */
public class BandMember extends StableNode {
    private Band mBand;
    List<BandMemberToMember> mMembersConnectionsList;
}
