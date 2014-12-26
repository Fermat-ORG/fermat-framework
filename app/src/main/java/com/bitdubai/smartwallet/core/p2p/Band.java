package com.bitdubai.smartwallet.core.p2p;

/**
 * Created by ciencias on 24.12.14.
 */

import com.bitdubai.smartwallet.core.p2p.node.BandDeputy;
import com.bitdubai.smartwallet.core.p2p.node.BandLeader;

import java.util.List;

/**
 * Bands have a loose organization. Their power structure is often egalitarian and has informal leadership;
 * the older members of the band generally are looked to for guidance and advice, and decisions are often
 * made on a consensus basis,but there are no written laws and none of the specialised coercive roles
 * (e.g., police) typically seen in more complex societies.
 *
 * Bands are distinguished from tribes in that tribes are generally larger, consisting of many families.
 * Tribes have more social institutions, such as a chief, big man, or elders. Tribes are also more permanent
 * than bands; a band can cease to exist if only a small group split off or die. Many tribes are sub-divided
 * into bands.
 */

public class Band implements LocationBasedGroup {

    private PhysicalLocation mAverageLocation;

    private String mBandId;
    private BandLeader mLeader;
    private BandDeputy mDeputy;
    private List<NeighboringBand> mNeighboringBands;
    private List<String> mSuccessionList;


    @Override
    public PhysicalLocation getLocation() {
        return null;
    }
}
