package com.jonathan.catfeed.api.models;

import com.jonathan.catfeed.commons.GridCell;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image")
public class Image implements GridCell {

    @Element(name = "url")
    private String url;

    @Element(name = "id")
    private String id;

    @Element(name = "source_url")
    private String sourceUrl;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public @ItemType int getItemType() {
        return ItemType.IMAGE_CELL;
    }

    public String getUrl() {
        return url;
    }

    public Image setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getId() {
        return id;
    }

    public Image setId(String id) {
        this.id = id;
        return this;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

}
