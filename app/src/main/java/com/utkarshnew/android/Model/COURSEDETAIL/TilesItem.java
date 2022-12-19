package com.utkarshnew.android.Model.COURSEDETAIL;

import android.service.quicksettings.Tile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TilesItem implements Serializable {
    @SerializedName("revert_api")
    private String revertApi;
    @SerializedName("tile_name")
    private String tileName;
    private String id;
    private String type;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @SerializedName("meta")
    private String meta;

public TilesItem()
{

}

    public TilesItem(String revertApi, String tileName, String id, String type, String meta) {
        this.revertApi = revertApi;
        this.tileName = tileName;
        this.id = id;
        this.type = type;
        this.meta = meta;
    }

    public String getRevertApi() {
        return revertApi;
    }

    public String getTileName() {
        return tileName;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setRevertApi(String revertApi) {
        this.revertApi = revertApi;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
}