package com.peng.saishi.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;

import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.ProgressBarDrawable;

public class CircleProgressDrawable extends ProgressBarDrawable {

	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int mBackgroundColor = 0x80000000;
	private int mColor = 0xaa59c8cc;
	private int mBarWidth = 20;
	private int mLevel = 0;
	private boolean mHideWhenZero = false;
	private int radius = 50;
	private Paint text_paint;

	public CircleProgressDrawable() {
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(10f);
		text_paint = new Paint();
		text_paint.setColor(Color.WHITE);
		text_paint.setTextAlign(Align.CENTER);
		text_paint.setTextSize(27);

	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Sets the progress bar color.
	 */
	public void setColor(int color) {
		if (mColor != color) {
			mColor = color;
			invalidateSelf();
		}
	}

	/**
	 * Gets the progress bar color.
	 */
	public int getColor() {
		return mColor;
	}

	/**
	 * Sets the progress bar background color.
	 */
	public void setBackgroundColor(int backgroundColor) {
		if (mBackgroundColor != backgroundColor) {
			mBackgroundColor = backgroundColor;
			invalidateSelf();
		}
	}

	/**
	 * Gets the progress bar background color.
	 */
	public int getBackgroundColor() {
		return mBackgroundColor;
	}

	/**
	 * Sets the progress bar width.
	 */
	public void setBarWidth(int barWidth) {
		if (mBarWidth != barWidth) {
			mBarWidth = barWidth;
			invalidateSelf();
		}
	}

	/**
	 * Gets the progress bar width.
	 */
	public int getBarWidth() {
		return mBarWidth;
	}

	/**
	 * Sets whether the progress bar should be hidden when the progress is 0.
	 */
	public void setHideWhenZero(boolean hideWhenZero) {
		mHideWhenZero = hideWhenZero;
	}

	/**
	 * Gets whether the progress bar should be hidden when the progress is 0.
	 */
	public boolean getHideWhenZero() {
		return mHideWhenZero;
	}

	@Override
	protected boolean onLevelChange(int level) {
		mLevel = level;
		invalidateSelf();
		return true;
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return DrawableUtils.getOpacityFromColor(mPaint.getColor());
	}

	@Override
	public void draw(Canvas canvas) {
		if (mHideWhenZero || mLevel == 0||mLevel==10000||mLevel==9900) {
			return;
		}
	Rect bounds = getBounds();
		Rect rect = new Rect();
		drawCircle(canvas, mBackgroundColor);
		drawArc(canvas, mLevel, mColor);
		text_paint.getTextBounds(mLevel / 100 + "%", 0,
				(mLevel / 100 + "%").length(), rect);
		canvas.drawText(mLevel / 100 + "%",
				(bounds.width())/2,
				(bounds.height())/2+rect.height()/2, text_paint);
	}

	private final int MAX_LEVEL = 10000;

	private void drawArc(Canvas canvas, int level, int color) {
		mPaint.setColor(color);

		Rect bounds = getBounds();
		// find center point
		int xpos = bounds.left + bounds.width() / 2;
		int ypos = bounds.bottom - bounds.height() / 2;
		RectF rectF = new RectF(xpos - radius, ypos - radius, xpos + radius,
				ypos + radius);
		float degree = (float) level / (float) MAX_LEVEL * 360;
		canvas.drawArc(rectF, 270, degree, false, mPaint);
	}

	private void drawCircle(Canvas canvas, int color) {
		mPaint.setColor(color);
		Rect bounds = getBounds();
		int xpos = bounds.left + bounds.width() / 2;
		int ypos = bounds.bottom - bounds.height() / 2;
		canvas.drawCircle(xpos, ypos, radius, mPaint);
	}
}