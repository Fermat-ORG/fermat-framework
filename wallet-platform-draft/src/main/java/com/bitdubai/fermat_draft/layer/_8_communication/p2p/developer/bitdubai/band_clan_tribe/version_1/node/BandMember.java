package com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node;

import com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.network.Band;
import com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.connection.BandMemberToMember;

import java.util.List;

/**
 * Created by ciencias on 24.12.14.
 */
public class BandMember extends StableNode {
    private Band mBand;
    List<BandMemberToMember> mMembersConnectionsList;
}
