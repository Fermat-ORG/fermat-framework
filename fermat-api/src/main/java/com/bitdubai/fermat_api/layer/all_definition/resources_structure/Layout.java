package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public class Layout {

    private UUID id;

    private String filename;

    public Layout(UUID id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
