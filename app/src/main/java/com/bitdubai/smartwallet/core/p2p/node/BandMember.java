package com.bitdubai.smartwallet.core.p2p.node;

import com.bitdubai.smartwallet.core.p2p.Band;
import com.bitdubai.smartwallet.core.p2p.connection.BandMemberToMember;

import java.util.List;

/**
 * Created by ciencias on 24.12.14.
 */
public class BandMember extends StableNode {
    private Band mBand;
    List<BandMemberToMember> mMembersConnectionsList;
}
