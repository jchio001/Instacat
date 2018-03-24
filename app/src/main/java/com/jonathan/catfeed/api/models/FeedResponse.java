package com.jonathan.catfeed.api.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class FeedResponse {

    @Element(name = "data")
    private FeedData feedData;

    public FeedData getFeedData() {
        return feedData;
    }
}
