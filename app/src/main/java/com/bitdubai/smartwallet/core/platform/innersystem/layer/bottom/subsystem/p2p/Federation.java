package com.bitdubai.smartwallet.core.platform.innersystem.layer.bottom.subsystem.p2p;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.bottom.subsystem.p2p.node.FederationDeputy;
import com.bitdubai.smartwallet.core.platform.innersystem.layer.bottom.subsystem.p2p.node.FederationPresident;

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

