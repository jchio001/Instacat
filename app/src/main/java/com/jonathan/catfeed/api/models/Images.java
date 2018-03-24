package com.jonathan.catfeed.api.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "imageList")
public class Images {

    @ElementList(inline = true)
    private List<Image> imageList;

    public List<Image> getImageList() {
        return imageList;
    }
}
