package com.bitdubai.fermat_api.layer.all_definition.github;

import org.eclipse.egit.github.core.RepositoryContents;

/**
 * Created by memo on 20/10/15.
 */
public class Contents extends RepositoryContents {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
