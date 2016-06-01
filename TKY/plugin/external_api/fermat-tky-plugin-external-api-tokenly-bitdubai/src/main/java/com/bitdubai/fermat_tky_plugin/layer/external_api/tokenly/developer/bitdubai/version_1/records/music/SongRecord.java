package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.music;

import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public class SongRecord implements Song, Serializable {

    private String id;
    private String name;
    private String[] tokens;
    private String performers;
    private String composers;
    private Date releaseDate;
    private String lyrics;
    private String credits;
    private String copyright;
    private String ownership;
    private String usageRights;
    private String usageProhibitions;
    private String bitcoinAddress;
    private String other;
    private String downloadUrl;

    public SongRecord(
            String id,
            String name,
            String[] tokens,
            String performers,
            String composers,
            Date releaseDate,
            String lyrics,
            String credits,
            String copyright,
            String ownership,
            String usageRights,
            String usageProhibitions,
            String bitcoinAddress,
            String other,
            String downloadUrl) {
        this.id = id;
        this.name = name;
        this.tokens = tokens;
        this.performers = performers;
        this.composers = composers;
        this.releaseDate = releaseDate;
        this.lyrics = lyrics;
        this.credits = credits;
        this.copyright = copyright;
        this.ownership = ownership;
        this.usageRights = usageRights;
        this.usageProhibitions = usageProhibitions;
        this.bitcoinAddress = bitcoinAddress;
        this.other = other;
        this.downloadUrl = downloadUrl;
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
     * This method returns the Song tokens
     * @return
     */
    @Override
    public String[] getTokens() {
        return this.tokens;
    }

    /**
     * This method returns the song performers.
     * @return
     */
    @Override
    public String getPerformers() {
        return this.performers;
    }

    /**
     * This method returns the song composers.
     * @return
     */
    @Override
    public String getComposers() {
        return this.composers;
    }

    /**
     * This method returns the song release date.
     * @return
     */
    @Override
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    /**
     * This method returns the song lyrics.
     * @return
     */
    @Override
    public String getLyrics() {
        return this.lyrics;
    }

    /**
     * This method returns the song credits
     * @return
     */
    @Override
    public String getCredits() {
        return this.credits;
    }

    /**
     * This method returns the song copyright.
     * @return
     */
    @Override
    public String getCopyright() {
        return this.copyright;
    }

    /**
     * This method returns the song ownership.
     * @return
     */
    @Override
    public String getOwnership() {
        return this.ownership;
    }

    /**
     * This method returns the song usage rights.
     * @return
     */
    @Override
    public String getUsageRights() {
        return this.usageRights;
    }

    /**
     * This method returns the song usage prohibitions.
     * @return
     */
    @Override
    public String getUsageProhibitions() {
        return this.usageProhibitions;
    }

    /**
     * This method returns the song bitcoin address.
     * @return
     */
    @Override
    public String getBitcoinAddress() {
        return this.usageProhibitions;
    }

    /**
     * This method returns the song 'other' field.
     * @return
     */
    @Override
    public String getOther() {
        return this.other;
    }

    /**
     * Represents the song download URL.
     * @return
     */
    @Override
    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    @Override
    public String toString() {
        return "SongRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tokens=" + Arrays.toString(tokens) +
                ", performers='" + performers + '\'' +
                ", composers='" + composers + '\'' +
                ", releaseDate=" + releaseDate +
                ", lyrics='" + lyrics + '\'' +
                ", credits='" + credits + '\'' +
                ", copyright='" + copyright + '\'' +
                ", ownership='" + ownership + '\'' +
                ", usageRights='" + usageRights + '\'' +
                ", usageProhibitions='" + usageProhibitions + '\'' +
                ", bitcoinAddress='" + bitcoinAddress + '\'' +
                ", other='" + other + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
