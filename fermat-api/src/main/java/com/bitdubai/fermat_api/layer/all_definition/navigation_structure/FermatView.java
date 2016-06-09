package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by mati on 2016.06.07..
 */
public class FermatView {

    private int id;
    private SourceLocation sourceLocation;


    public FermatView() {
    }

    public FermatView(int id, SourceLocation sourceLocation) {
        this.id = id;
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
}
