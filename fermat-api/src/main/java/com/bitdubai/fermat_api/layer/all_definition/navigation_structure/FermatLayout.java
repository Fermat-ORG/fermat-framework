package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class FermatLayout extends Artifact{

    public FermatLayout() {
    }

    public FermatLayout(int id, Owner owner, SourceLocation sourceLocation) {
        super(id, owner, sourceLocation);
    }
}
