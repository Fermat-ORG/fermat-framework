package com.bitdubai.smartwallet.core.p2p;

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

