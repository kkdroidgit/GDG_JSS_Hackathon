package com.paperwrk.gdgjsshackathon.utils.model;

/**
 * Created by Kartik on 13-8-17.
 */

public class TvSeriesModel {
    private String name;
    private String linkPlaylist;
    private int drawable;

    public TvSeriesModel(String name, int drawable, String linkPlaylist) {
        this.name = name;
        this.drawable = drawable;
        this.linkPlaylist = linkPlaylist;
    }

    public String getName()
    {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getLinkPlaylist(){
        return linkPlaylist;
    }
}
