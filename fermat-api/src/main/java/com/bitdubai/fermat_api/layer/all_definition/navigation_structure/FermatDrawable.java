package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias furszyfer on 2016.06.07..
 */
public class FermatDrawable extends Artifact {

    private String resName;

    public FermatDrawable() {
    }

    public FermatDrawable(int id,String resName, Owner owner, SourceLocation sourceLocation) {
        super(id, owner, sourceLocation);
        this.resName = resName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
}
