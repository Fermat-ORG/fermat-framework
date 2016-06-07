package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias furszyfer on 2016.06.07..
 */
public class FermatDrawable {

    private String id;
    private String ownerAppPublicKey;
    private SourceLocation sourceLocation;

    public FermatDrawable(String id) {
        this.id = id;
        this.sourceLocation = SourceLocation.FERMAT_FRAMEWORK;
    }

    public FermatDrawable(String id, String ownerAppPublicKey, SourceLocation sourceLocation) {
        this.id = id;
        this.ownerAppPublicKey = ownerAppPublicKey;
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

    public String getOwnerAppPublicKey() {
        return ownerAppPublicKey;
    }
}
