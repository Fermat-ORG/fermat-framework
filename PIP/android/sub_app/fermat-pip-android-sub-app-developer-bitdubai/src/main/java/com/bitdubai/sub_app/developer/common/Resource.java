package com.bitdubai.sub_app.developer.common;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.io.Serializable;

public class Resource implements Serializable {

    public static final int TYPE_PLUGIN = 1;
    public static final int TYPE_ADDON = 2;


    private static final long serialVersionUID = -8730067026050196758L;

    public String picture;

    public String label;

    public String code;

    public PluginVersionReference pluginVersionReference;

    public String developer;

    public int type;

    public Resource() {
    }

    public Resource(final String picture,
                    final String label,
                    final String code,
                    final String developer,
                    final int type) {

        this.picture = picture;
        this.label = label;
        this.code = code;
        this.developer = developer;
        this.type = type;
    }

    public Resource(final String picture,
                    final String label,
                    final PluginVersionReference pluginVersionReference,
                    final String developer,
                    final int type) {

        this.picture = picture;
        this.label = label;
        this.pluginVersionReference = pluginVersionReference;
        this.developer = developer;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "picture=\'" + picture + "\'" +
                ", label=\'" + label + "\'" +
                ", code=\'" + code + "\'" +
                ", pluginVersionReference=\'" + pluginVersionReference + "\'" +
                ", developer=\'" + developer + "\'" +
                ", type=" + type +
                '}';
    }
}