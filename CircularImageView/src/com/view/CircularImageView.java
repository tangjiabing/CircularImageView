package com.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @author tangjiabing
 * 
 * @see 开源时间：2016年03月31日
 * 
 *      记得给我个star哦~
 * 
 */
public class CircularImageView extends ImageView {

	public CircularImageView(Context context) {
		this(context, null);
	}

	public CircularImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable srcDrawable = getDrawable();
		if (srcDrawable == null)
			super.onDraw(canvas);
		else
			drawToCanvas(srcDrawable, canvas);
	}

	private void drawToCanvas(Drawable srcDrawable, Canvas canvas) {
		int viewWidth = getWidth();
		int viewHeight = getHeight();
		int srcWidth = srcDrawable.getIntrinsicWidth();
		int srcHeight = srcDrawable.getIntrinsicHeight();
		double wSampleSize = srcWidth / 1.0 / viewWidth;
		double hSampleSize = srcHeight / 1.0 / viewHeight;
		double sampleSize = Math.max(wSampleSize, hSampleSize);
		int width = (int) (srcWidth / sampleSize);
		int height = (int) (srcHeight / sampleSize);

		if (viewWidth > viewHeight) {
			if (width > height)
				sampleSize = viewHeight / 1.0 / height;
			else
				sampleSize = viewHeight / 1.0 / width;
		} else {
			if (width > height)
				sampleSize = viewWidth / 1.0 / height;
			else
				sampleSize = viewWidth / 1.0 / width;
		}
		width = (int) (width * sampleSize);
		height = (int) (height * sampleSize);

		width = width - getPaddingLeft() - getPaddingRight();
		height = height - getPaddingTop() - getPaddingBottom();

		int saveCount = canvas.saveLayer(0, 0, viewWidth, viewHeight, null,
				Canvas.ALL_SAVE_FLAG);
		canvas.translate((viewWidth - width) / 2, (viewHeight - height) / 2);
		srcDrawable.setBounds(0, 0, width, height);
		srcDrawable.draw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		Bitmap bitmap = getCircularBitmap(width, height);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		canvas.restoreToCount(saveCount);
		bitmap.recycle();
	}

	private Bitmap getCircularBitmap(int w, int h) {
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		int d = Math.min(w, h);
		canvas.drawCircle((float) w / 2, (float) h / 2, (float) d / 2, paint);
		return bitmap;
	}

}
