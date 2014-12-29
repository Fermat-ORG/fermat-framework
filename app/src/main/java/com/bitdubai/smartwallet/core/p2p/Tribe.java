package com.bitdubai.smartwallet.core.p2p;

import com.bitdubai.smartwallet.core.p2p.node.TribeDeputy;
import com.bitdubai.smartwallet.core.p2p.node.TribeLeader;

import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */

/**
 * A social group consisting of people of the same	race who have the same beliefs, customs, language etc, and
 * usually live in one particular area ruled by their leader.
 */


public class Tribe implements LocationBasedGroup{

    private PhysicalLocation mAverageLocation;

    private String mTribeId;
    private TribeLeader mTribeLeader;
    private TribeDeputy mTribeDeputy;
    private List<NeighboringTribe> mNeighboringTribes;
    private List<String> mSuccessionList;
    private TribeNetwork mTribeNetwork;

    @Override
    public PhysicalLocation getLocation() {
        return null;
    }
}
