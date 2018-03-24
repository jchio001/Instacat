package com.jonathan.catfeed.commons;

import com.jonathan.catfeed.api.ApiClient;
import com.jonathan.catfeed.api.models.Image;
import com.jonathan.catfeed.commons.GridCell.ItemType;

import java.util.ArrayList;
import java.util.List;

public class GridCellFactory {

    private static final GridCell EMPTY_CELL =
        new GenericGridCell(ItemType.EMPTY_SPACE_CELL);
    private static final GridCell PROGRESS_BAR_CELL =
        new GenericGridCell(ItemType.PROGRESS_BAR_CELL);
    private static final GridCell REFRESH_ICON_CELL =
        new GenericGridCell(ItemType.REFRESH_ICON_CELL);
    private static final GridCell IMAGELESS_IMAGE = new Image();

    public static GridCell getEmptyCell() {
        return EMPTY_CELL;
    }

    public static GridCell getProgressBarCell() {
        return PROGRESS_BAR_CELL;
    }

    public static GridCell getRefreshIconCell() {
        return REFRESH_ICON_CELL;
    }

    public static List<GridCell> getRenderingPage() {
        List<GridCell> renderingPage = new ArrayList<>(ApiClient.PAGE_SIZE);
        for (int i = 0; i < ApiClient.PAGE_SIZE; ++i) {
            renderingPage.add(IMAGELESS_IMAGE);
        }

        return renderingPage;
    }
}
