package org.test.boris.testapp.data.api.domain.entity.other;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author manish.s
 */

public class GridItem implements Serializable {
    Bitmap image;
    String title;
    int iconId = -1;
    int code = -1;
    int lastId = -1;

    public GridItem(int code, int iconId, String title) {
        this.code = code;
        this.iconId = iconId;
        this.title = title;
    }

    public GridItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }
}
