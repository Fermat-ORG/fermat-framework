package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public class Layout {

    private UUID id;

    private String filename;

    private String name;

    public Layout() {

    }


    public Layout(UUID id, String filename, String name) {
        this.id = id;
        this.filename = filename;
        this.name = name;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UUID getId() {
        return id;
    }


    public String getFilename() {
        return filename;
    }


    public String getName() {
        return name;
    }


}
