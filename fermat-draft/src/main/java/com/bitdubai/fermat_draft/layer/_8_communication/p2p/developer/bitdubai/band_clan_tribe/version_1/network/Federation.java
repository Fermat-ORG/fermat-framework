package com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.network;

import com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.FederationDeputy;
import com.bitdubai.fermat_draft.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.FederationPresident;

import java.util.List;

/**
 * Created by ciencias on 24.12.14.
 */
public class Federation implements InterestBasedGroup, LocationBasedGroup{
    private String mInterest;
    private PhysicalLocation mAverageLocation;

    private String mFederationId;
    private FederationPresident mPresident;
    private FederationDeputy mDeputy;
    private List<NeighboringFederation> mNeighboringFederations;
    private List<String> mSuccessionList;

    private FederationNetwork mFederationNetwork;

    @Override
    public String getInterest() {
        return null;
    }

    @Override
    public PhysicalLocation getLocation() {
        return null;
    }
}

