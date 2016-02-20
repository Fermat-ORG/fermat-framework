package com.mati.horizontalscrollview;

/**
 * Created by mati on 2016.02.19..
 */
public class SetttingsItems {

    private int type;
    private int src;
    private String name;

    public SetttingsItems(int type,int src, String name) {
        this.src = src;
        this.name = name;
        this.type = type;
    }

    public int getSrc() {
        return src;
    }

    public String getName() {
        return name;
    }
}
