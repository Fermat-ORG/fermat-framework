package com.bitdubai.fermat_api.layer.dmp_actor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.UUID;

/**
 * *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.User</code> is a interface
 *     that define the methods for management the Users settings.
 *
 * Created by natalia on 20/07/15.
 *
 */

public interface Actor {

    UUID getId();

    String getName();

    Actors getType();

    byte[] getPhoto();

    void setId(UUID id);

    void setName(String name);

    void setPhoto(byte[] photo);
}
