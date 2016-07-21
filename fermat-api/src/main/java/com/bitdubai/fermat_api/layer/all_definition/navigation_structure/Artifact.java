package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
public class Artifact implements Serializable {

    private int id;
    private Owner owner;
    private SourceLocation sourceLocation;


    public Artifact() {
    }

    public Artifact(int id, Owner owner, SourceLocation sourceLocation) {
        this.id = id;
        this.owner = owner;
        this.sourceLocation = sourceLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
