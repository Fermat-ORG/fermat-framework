package com.bitdubai.sub_app.developer.common;

import java.io.Serializable;

public class Resource implements Serializable {

        public static final int TYPE_PLUGIN=1;
        public static final int TYPE_ADDON=2;


        private static final long serialVersionUID = -8730067026050196758L;

        public String picture;

        public String resource;

        public String developer;

        public int type;

        @Override
        public String toString() {
                return "Resource{" +
                        "picture='" + picture + '\'' +
                        ", resource='" + resource + '\'' +
                        ", developer='" + developer + '\'' +
                        ", type=" + type +
                        '}';
        }
}