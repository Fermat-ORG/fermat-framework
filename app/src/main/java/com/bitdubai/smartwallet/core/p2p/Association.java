package com.bitdubai.smartwallet.core.p2p;

import java.util.List;

/**
 * Created by ciencias on 24.12.14.
 */
public class Association implements InterestBasedGroup, LocationBasedGroup{
    private String mInterest;
    private PhysicalLocation mAverageLocation;

    private String mAssociationId;
    private AssociationPresident mPresident;
    private AssociationDeputy mDeputy;
    private List<NeighboringAssociation> mNeighboringFederations;
    private List<String> mSuccessionList;

    @Override
    public String getInterest() {
        return null;
    }

    @Override
    public PhysicalLocation getLocation() {
        return null;
    }
}
