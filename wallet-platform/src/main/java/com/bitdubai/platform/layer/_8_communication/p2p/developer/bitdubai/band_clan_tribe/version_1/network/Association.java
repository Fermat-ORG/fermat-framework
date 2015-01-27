package com.bitdubai.platform.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.network;

import com.bitdubai.platform.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.AssociationDeputy;
import com.bitdubai.platform.layer._8_communication.p2p.developer.bitdubai.band_clan_tribe.version_1.node.AssociationPresident;

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
