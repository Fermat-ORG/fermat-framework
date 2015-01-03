package com.bitdubai.smartwallet.platform.layer._6_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.network;

/**
 * Created by ciencias on 24.12.14.
 */

import com.bitdubai.smartwallet.platform.layer._6_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.ClanDeputy;
import com.bitdubai.smartwallet.platform.layer._6_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.ClanLeader;

import java.util.List;

/**
 * A clan is a group of people united by actual or perceived kinship and descent. Even if lineage details
 * are unknown, clan members may be organized around a founding member or apical ancestor. The kinship-based
 * bonds may be symbolical, whereby the clan shares a "stipulated" common ancestor that is a symbol of the
 * clan's unity. When this "ancestor" is non-human, it is referred to as a totem, which is frequently an animal.
 * Clans can be most easily described as tribes or sub-groups of tribes.
 */

public class Clan implements LocationBasedGroup {

    private PhysicalLocation mAverageLocation;

    private String mClanId;
    private ClanLeader mLeader;
    private ClanDeputy mDeputy;
    private List<NeighboringClan> mNeighboringClans;
    private List<String> mSuccessionList;

    @Override
    public PhysicalLocation getLocation() {
        return null;
    }
}
