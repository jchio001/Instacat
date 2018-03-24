package com.jonathan.catfeed.commons;


public class GenericGridCell implements GridCell {

    private @ItemType int itemType;

    public GenericGridCell(@ItemType int itemType) {
        this.itemType = itemType;
    }

    @Override
    public @ItemType int getItemType() {
        return itemType;
    }
}
