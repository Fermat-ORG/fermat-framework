package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by mati on 2016.06.07..
 */
public class FermatView extends Artifact{

    public FermatView() {
    }

    public FermatView(int id, SourceLocation sourceLocation) {
        super(id, null, sourceLocation);
    }

    public FermatView(int id, Owner owner, SourceLocation sourceLocation) {
        super(id, owner, sourceLocation);
    }

}
