package com.jonathan.catfeed.commons;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jonathan.catfeed.commons.GridCell.ItemType.EMPTY_SPACE_CELL;
import static com.jonathan.catfeed.commons.GridCell.ItemType.IMAGE_CELL;
import static com.jonathan.catfeed.commons.GridCell.ItemType.PROGRESS_BAR_CELL;
import static com.jonathan.catfeed.commons.GridCell.ItemType.REFRESH_ICON_CELL;

public interface GridCell {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({IMAGE_CELL, EMPTY_SPACE_CELL, PROGRESS_BAR_CELL, REFRESH_ICON_CELL})
    @interface ItemType {
        int IMAGE_CELL = 0;
        int EMPTY_SPACE_CELL = 1;
        int PROGRESS_BAR_CELL = 2;
        int REFRESH_ICON_CELL = 3;
    }

    int ITEM_TYPE_COUNT = 4;

    @ItemType int getItemType();
}
