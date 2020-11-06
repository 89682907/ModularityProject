package com.modularity.x.camera.enums;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({PreviewAdjustType.NONE, PreviewAdjustType.WIDTH_FIRST, PreviewAdjustType.HEIGHT_FIRST, PreviewAdjustType.SMALLER_FIRST, PreviewAdjustType.LARGER_FIRST})
@Retention(RetentionPolicy.SOURCE)
public @interface PreviewAdjustType {

    /**
     * The imagery will be stretched to fit the view
     */
    int NONE = 0;

    /**
     * Use the width of view,
     * the height will be stretched to meet the aspect ratio.
     * <p>
     * Example:
     * <p>
     * +-------------+
     * |             |
     * |/////////////|
     * |/////////////|
     * |//  image  //|
     * |/////////////|
     * |/////////////|
     * |             |
     * +-------------+
     */
    int WIDTH_FIRST = 1;

    /**
     * Use the height of view,
     * the width will be stretched to meet the aspect ratio.
     * <p>
     * Example:
     * <p>
     * +-------------+
     * |  /////////  |
     * |  /////////  |
     * |  /////////  |
     * |  / image /  |
     * |  /////////  |
     * |  /////////  |
     * |  /////////  |
     * +-------------+
     */
    int HEIGHT_FIRST = 2;

    /**
     * Use the smaller side between height and width,
     * another will be stretched to meet the aspect ratio.
     *
     * @see #WIDTH_FIRST
     * @see #HEIGHT_FIRST
     */
    int SMALLER_FIRST = 3;

    /**
     * Use the larger side between height and width,
     * another will be stretched to meet the aspect ratio.
     *
     * @see #WIDTH_FIRST
     * @see #HEIGHT_FIRST
     */
    int LARGER_FIRST = 4;
}
