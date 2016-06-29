package org.fermat.fermat_dap_android_wallet_asset_user.v2.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class Issuer {
    private ActorAssetIssuer actorAssetIssuer;

    private byte[] image;
    private String name;
    private List<Asset> assets;

    public Issuer(String name) {
        this.name = name;
    }

    public Issuer(ActorAssetIssuer actorAssetIssuer) {
        this.actorAssetIssuer = actorAssetIssuer;
        setImage(actorAssetIssuer.getProfileImage());
        setName(actorAssetIssuer.getName());
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public static List<Issuer> getTestData() {
        Issuer issuer;
        Asset asset;
        List<Asset> assets;
        List<Issuer> issuers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            issuer = new Issuer("Issuer " + i);
            assets = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                asset = new Asset("Asset " + j);
                assets.add(asset);
            }
            issuer.setAssets(assets);
            issuers.add(issuer);
        }
        return issuers;
    }
}
