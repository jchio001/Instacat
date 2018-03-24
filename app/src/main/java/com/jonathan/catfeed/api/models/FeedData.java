package com.jonathan.catfeed.api.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "data")
public class FeedData {

    @Element(name = "images")
    private Images images;

    public Images getImages() {
        return images;
    }
}
