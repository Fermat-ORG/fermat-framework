package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

import java.io.Serializable;

/**
 * Created by Matias furszyfer on 2016.06.07..
 */
public class FermatDrawable extends Artifact implements Serializable {

    private String resName;

    public FermatDrawable() {
    }

    /**
     * @param id             reference a number with is possible obtain the resource (only possible with method @Override getResource(int id) for class in package Android with lastName FermatAppConnection)
     * @param resName        reference the name have set the resource (soon have action)
     * @param owner          indicate the Owner this resource, any publicKey can reference here has owner the resource for use
     * @param sourceLocation indicate any Location where find the resource
     */
    public FermatDrawable(int id, String resName, Owner owner, SourceLocation sourceLocation) {
        super(id, owner, sourceLocation);
        this.resName = resName;
    }

    public FermatDrawable(int id, String resName, SourceLocation sourceLocation) {
        super(id, null, sourceLocation);
        this.resName = resName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
}
