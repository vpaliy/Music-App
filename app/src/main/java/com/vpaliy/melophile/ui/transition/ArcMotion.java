package com.vpaliy.melophile.ui.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.transition.PathMotion;
import android.util.AttributeSet;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcMotion  extends PathMotion {
    private static int DEFAULT_RADIUS = 500;
    private float curveRadius;

    public ArcMotion(){}

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurveRadius(float curveRadius) {
        this.curveRadius = curveRadius;
    }

    @Override
    public Path getPath(float startX, float startY, float endX, float endY) {
        Path arcPath = new Path();

        float midX = startX + ((endX - startX) / 2);
        float midY = startY + ((endY - startY) / 2);
        float xDiff = midX - startX;
        float yDiff = midY - startY;

        double angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);

        float pointX = (float) (midX + curveRadius * Math.cos(angleRadians));
        float pointY = (float) (midY + curveRadius * Math.sin(angleRadians));

        arcPath.moveTo(startX, startY);
        arcPath.cubicTo(startX,startY,pointX,pointY, endX, endY);
        return arcPath;
    }
}