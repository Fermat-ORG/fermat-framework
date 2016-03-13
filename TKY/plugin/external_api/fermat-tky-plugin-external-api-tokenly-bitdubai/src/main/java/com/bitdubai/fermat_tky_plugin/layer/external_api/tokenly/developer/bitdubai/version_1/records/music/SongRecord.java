package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public class SongRecord implements Song {

    String id;
    String name;
    String description;
    String downloadURL;
    long downloadSize;

    /**
     * Constructor with parameters
     * @param id
     * @param name
     * @param description
     * @param downloadURL
     * @param downloadSize
     */
    public SongRecord(String id, String name, String description, String downloadURL, long downloadSize) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.downloadURL = downloadURL;
        this.downloadSize = downloadSize;
    }

    /**
     * This method returns the Song Id.
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * This method returns the Song name.
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * This method returns the Song description.
     * @return
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * This method returns the Song download URL.
     * @return
     */
    @Override
    public String getDownloadURL() {
        return this.downloadURL;
    }

    /**
     * This method returns the Song download size
     * @return
     */
    @Override
    public long getDownloadSize() {
        return this.downloadSize;
    }

    @Override
    public String toString() {
        return "SongRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", downloadURL='" + downloadURL + '\'' +
                ", downloadSize=" + downloadSize +
                '}';
    }
}
