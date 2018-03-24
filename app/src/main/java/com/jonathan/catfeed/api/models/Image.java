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
    public @ItemType int getItemType() {
        return ItemType.IMAGE_CELL;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

}
