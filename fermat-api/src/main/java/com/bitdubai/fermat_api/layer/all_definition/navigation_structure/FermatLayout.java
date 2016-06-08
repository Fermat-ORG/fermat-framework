package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class FermatLayout {

    private String id;
    private String ownerAppPublicKey;
    private SourceLocation sourceLocation;


    public FermatLayout() {
    }

    public FermatLayout(String id, String ownerAppPublicKey, SourceLocation sourceLocation) {
        this.id = id;
        this.ownerAppPublicKey = ownerAppPublicKey;
        this.sourceLocation = sourceLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerAppPublicKey() {
        return ownerAppPublicKey;
    }

    public void setOwnerAppPublicKey(String ownerAppPublicKey) {
        this.ownerAppPublicKey = ownerAppPublicKey;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(SourceLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }
}
