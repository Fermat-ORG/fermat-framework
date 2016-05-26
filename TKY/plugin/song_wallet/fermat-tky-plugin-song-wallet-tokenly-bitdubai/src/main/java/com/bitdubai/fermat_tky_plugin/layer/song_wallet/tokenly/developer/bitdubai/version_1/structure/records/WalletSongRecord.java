package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.structure.records;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_tky_api.all_definitions.enums.SongStatus;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music.Song;
import com.bitdubai.fermat_tky_api.layer.song_wallet.interfaces.WalletSong;
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CanGetTokensArrayFromSongWalletException;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/03/16.
 */
public class WalletSongRecord implements WalletSong, Serializable {

    //WALLET SONG FIELDS
    private SongStatus songStatus;
    private UUID songId;
    private byte[] songBytes;
    //TOKENLY SONG FIELDS
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

    /**
     * Default constructor
     * @param songStatus
     * @param songId
     * @param id
     * @param name
     * @param tokens
     * @param performers
     * @param composers
     * @param releaseDate
     * @param lyrics
     * @param credits
     * @param copyright
     * @param ownership
     * @param usageRights
     * @param usageProhibitions
     * @param bitcoinAddress
     * @param other
     */
    public WalletSongRecord(
            SongStatus songStatus,
            UUID songId,
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
            String other) {
        this.songStatus = songStatus;
        this.songId = songId;
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
    }

    /**
     * Constructor that uses String to define String[] tokens
     * @param songStatus
     * @param songId
     * @param id
     * @param name
     * @param tokensXML
     * @param performers
     * @param composers
     * @param releaseDate
     * @param lyrics
     * @param credits
     * @param copyright
     * @param ownership
     * @param usageRights
     * @param usageProhibitions
     * @param bitcoinAddress
     * @param other
     */
    public WalletSongRecord(
            SongStatus songStatus,
            UUID songId,
            String id,
            String name,
            String tokensXML,
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
            String downloadUrl) throws
            CanGetTokensArrayFromSongWalletException {
        this.songStatus = songStatus;
        this.songId = songId;
        this.id = id;
        this.name = name;
        //Set the Array tokens from a String XML
        setTokens(tokensXML);
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
     * Constructor that uses String to define String[] tokens
     * @param songStatus
     * @param songId
     * @param id
     * @param name
     * @param tokensXML
     * @param performers
     * @param composers
     * @param releaseDate
     * @param lyrics
     * @param credits
     * @param copyright
     * @param ownership
     * @param usageRights
     * @param usageProhibitions
     * @param bitcoinAddress
     * @param other
     */
    public WalletSongRecord(
            SongStatus songStatus,
            UUID songId,
            String id,
            String name,
            String tokensXML,
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
            String other) throws
            CanGetTokensArrayFromSongWalletException {
        this.songStatus = songStatus;
        this.songId = songId;
        this.id = id;
        this.name = name;
        //Set the Array tokens from a String XML
        setTokens(tokensXML);
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
        this.downloadUrl = "";
    }

    /**
     * Constructor with parameters
     * @param song
     * @param songStatus
     * @param songId
     */
    public WalletSongRecord(
            Song song,
            SongStatus songStatus,
            UUID songId){
        //Set the WalletSong fields
        this.songStatus = songStatus;
        this.songId = songId;
        //Set the song fields
        this.id = song.getId();
        this.name = song.getName();
        this.tokens = song.getTokens();
        this.performers = song.getPerformers();
        this.composers = song.getComposers();
        this.releaseDate = song.getReleaseDate();
        this.lyrics = song.getLyrics();
        this.credits = song.getCredits();
        this.copyright = song.getCopyright();
        this.ownership = song.getOwnership();
        this.usageRights = song.getUsageRights();
        this.usageProhibitions = song.getUsageProhibitions();
        this.bitcoinAddress = song.getBitcoinAddress();
        this.other = song.getOther();
        this.downloadUrl = song.getDownloadUrl();

    }

    /**
     * Constructor with parameters.
     * Can be used to set the complete song to UI.
     * @param walletSong
     * @param songBytes
     */
    public WalletSongRecord(
            WalletSong walletSong,
            byte[] songBytes){
        //Set the WalletSong fields
        this.songStatus = walletSong.getSongStatus();
        this.songId = walletSong.getSongId();
        this.songBytes = songBytes;
        //Set the song fields
        this.id = walletSong.getId();
        this.name = walletSong.getName();
        this.tokens = walletSong.getTokens();
        this.performers = walletSong.getPerformers();
        this.composers = walletSong.getComposers();
        this.releaseDate = walletSong.getReleaseDate();
        this.lyrics = walletSong.getLyrics();
        this.credits = walletSong.getCredits();
        this.copyright = walletSong.getCopyright();
        this.ownership = walletSong.getOwnership();
        this.usageRights = walletSong.getUsageRights();
        this.usageProhibitions = walletSong.getUsageProhibitions();
        this.bitcoinAddress = walletSong.getBitcoinAddress();
        this.other = walletSong.getOther();
        //Set an empty string in this constructor.
        this.downloadUrl = "";
    }

    //WALLET SONG IMPLEMENTATIONS
    /**
     * This method returns the Song status.
     * @return
     */
    @Override
    public SongStatus getSongStatus() {
        return this.songStatus;
    }

    /**
     * This method returns the WalletSong Id.
     * This Id is assigned by the Song Wallet Tokenly implementation, can be different to the
     * Tonkenly Id.
     * @return
     */
    @Override
    public UUID getSongId() {
        return this.songId;
    }

    /**
     * This method returns a byte array that represents the song ready to be played.
     * @return
     */
    @Override
    public byte[] getSongBytes() {
        if(songBytes==null){
            return new byte[0];
        }
        return this.songBytes;
    }

    //TOKENLY SONG IMPLEMENTATIONS
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
     * This method returns a XML String with the String[] associated to this object
     * @return
     */
    public String getTokensXML(){
        if(this.tokens==null){
            this.tokens=new String[]{""};
        }
        return XMLParser.parseObject(this.tokens);
    }

    /**
     * This method requires a valid String[] XML String to set this list to this object
     * @param songTokens
     * @throws CanGetTokensArrayFromSongWalletException
     */
    public void setTokens(String songTokens) throws CanGetTokensArrayFromSongWalletException {
        if(songTokens==null||songTokens.isEmpty()){
            /*throw new CanGetTokensArrayFromSongWalletException(
                    "The XML with the tokens is null or empty");*/
            this.tokens=new String[0];
            return;
        }
        try{
            String[] tokensFromXML=new String[0];
            Object xmlObject =XMLParser.parseXML(songTokens, tokensFromXML);
            tokensFromXML=(String[]) xmlObject;
            this.tokens=tokensFromXML;
        } catch (Exception exception){
            throw new CanGetTokensArrayFromSongWalletException(
                    exception,
                    "Parsing the XML String to a String array",
                    "Unexpected exception");
        }

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
        return this.bitcoinAddress;
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
        return downloadUrl;
    }

    @Override
    public String toString() {
        return "WalletSongRecord{" +
                "songStatus=" + songStatus +
                ", songId=" + songId +
                ", id='" + id + '\'' +
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
