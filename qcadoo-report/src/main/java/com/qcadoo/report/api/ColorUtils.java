package com.qcadoo.report.api;

import java.awt.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ColorUtils.class);

    private static Color lineLightColor;

    private static Color lineDarkColor;

    private static Color backgroundColor;

    private static Color lightColor;

    private static Color darkColor;

    private ColorUtils() {
    }

    public static void prepare() {
        if (backgroundColor == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Pdf colors initialization");
            }
            lightColor = new Color(77, 77, 77);
            darkColor = new Color(26, 26, 26);
            lineDarkColor = new Color(102, 102, 102);
            lineLightColor = new Color(153, 153, 153);
            backgroundColor = new Color(230, 230, 230);
        }
    }

    public static Color getLineDarkColor() {
        return lineDarkColor;
    }

    public static Color getLineLightColor() {
        return lineLightColor;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static Color getLightColor() {
        return lightColor;
    }

    public static Color getDarkColor() {
        return darkColor;
    }
}
