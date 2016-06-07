package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias furszyfer on 2016.06.07..
 */
public class Drawable {

    private String id;
    private SourceLocation sourceLocation;

    public Drawable(String id) {
        this.id = id;
        this.sourceLocation = SourceLocation.FERMAT_FRAMEWORK;
    }

    public Drawable(String id, SourceLocation sourceLocation) {
        this.id = id;
        this.sourceLocation = sourceLocation;
    }

    public String getId() {
        return id;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }
}
