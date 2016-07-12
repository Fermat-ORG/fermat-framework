package com.bitdubai.reference_niche_wallet.fermat_wallet.common.custom_view;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces.OnWheelChangedListener;
import com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces.WheelAdapter;

public class WheelView extends View {
	private static final String TAG = "WHEELSPINNER";
	private WheelAdapter _adp;
	private int TEXT_SIZE = 20;
	private Boolean update = false;
	private Boolean automove = false;
	private int targetPos;
	private int curPos;
	private int lastPos;
	private float velocity = 0f;
	private float momentum = 0f;
	private float mass = 10f;
	private float resistance = 0.9f;
	private float spring = 0.3f;
	private float ep = 0.5f; // distance between two values before target move to next
	private DecimalFormat df = new DecimalFormat("0");
	private NinePatchDrawable ind9;
	private ScrollView scrollContainer = null;
	private int visible_offset = 3*TEXT_SIZE;

	private float lastTouchY;
	private boolean disable = false;

	public int visibleNum;
	private int preNum;
	private int postNum;
	private int[] center;
	private int[] adpRange;
	private int overflow = (int) (this.TEXT_SIZE);


	// Listeners
	private List<OnWheelChangedListener> changingListeners = new LinkedList<OnWheelChangedListener>();

	/**
	 * Constructor
	 */
	public WheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Bitmap indbp = BitmapFactory.decodeResource(this.getResources(), R.drawable.wheel_ind);
		if (indbp != null) {
			this.ind9 = new NinePatchDrawable(this.getResources(), indbp, indbp.getNinePatchChunk(), new Rect(0, 0, 0,
					0), null);
		}
		this.setBackgroundResource(R.drawable.wheel_bg);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

	}

	public WheelAdapter getAdapter() {
		return this._adp;
	}

	private int[] getCenter() {
		int[] res = { this.getWidth() / 2, this.getHeight() / 2 };
		return res;
	}

	private int[] getTargetOffset() {
		this.adpRange = new int[] { 0, 0 };
		this.center = this.getCenter();
		this.preNum = this._adp.getBeforeNum();
		this.postNum = this._adp.getAfterNum();

		int txtOffset = this.TEXT_SIZE;

		int upperLimit = this.preNum;// Math.min(_cutoff, pre);
		// int lowerLimit = postNum;// Math.min(_cutoff, post);

		// adp_range[0] = _adp.getCurIndex() - upperLimit;
		// adp_range[1] = _adp.getCurIndex() + lowerLimit;
		this.adpRange[0] = 0;
		this.adpRange[1] = this._adp.getLen();

		this.targetPos = this.center[1] - txtOffset * upperLimit + this.TEXT_SIZE / 2;

		return this.adpRange;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (disable)
			this.setBackgroundResource(R.drawable.wheel_disabled_bg);
		else
			this.setBackgroundResource(R.drawable.wheel_bg);

		Paint pText = new Paint();
		pText.setTextSize(this.TEXT_SIZE);
		pText.setAntiAlias(true);
		pText.setColor(Color.BLACK);
		pText.setTextAlign(Paint.Align.CENTER);
		// canvas.drawColor(Color.WHITE);

		if (this._adp == null) {
			return;
		}

		int[] center = this.getCenter();
		int[] adpRange = this.getTargetOffset();

		if (!this.update) {
			this.curPos = this.targetPos;
		}
		// Log.d(TAG, "curOffset:"+curOffset);
		// Log.d(TAG, "targetOffset:"+targetOffset);
		int y = this.curPos - 5;
		for (int i = adpRange[0]; i < adpRange[1]; i++) {
			if ( y > (center[1]-visible_offset) && y < (center[1]+visible_offset))
				canvas.drawText(_adp.getLabel(i), center[0], y, pText);
			y += this.TEXT_SIZE;

		}

		if (this.update) {
			if (this.curPos == this.targetPos) {
				this.update = false;
			} else if (this.automove == true) {
				// sprint back if velocity = 0
				float vmag = Math.abs(this.velocity);
				
				if (vmag < this.TEXT_SIZE/2) {
					float delta = this.targetPos - this.curPos;					
					this.curPos += this.spring * delta;

				} else {
					
					this.velocity = this.velocity * this.resistance;
					if ( vmag < 0.1 )
						this.velocity = 0;
					
					this.curPos += this.velocity;
					
					if ( this.curPos - this.targetPos == 0 
							|| this.isOverflow(this.velocity) != 0 )
					{
						this.velocity = 0;
					}
				}
				this.updateTargetOffset();

			}
			this.invalidate();
		}

		
		int h9 = this.TEXT_SIZE / 2;

		this.ind9.setBounds(0, center[1] - h9, this.getWidth(), center[1] + h9);
		this.ind9.draw(canvas);
		
		// DEBUG
//		Paint ip = new Paint();
//		ip.setColor(Color.RED);
//		ip.setAlpha(220);
//		ip.setStrokeWidth(1);
//		canvas.drawLine(0, center[1], getWidth(), center[1], ip);		
		//canvas.drawText("" + _adp.getCurIndex(), 10, 10, ip);
		//canvas.drawText("" + this.curPos + " -> " +this.targetPos, 10, 20, ip);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int wmode = MeasureSpec.getMode(widthMeasureSpec);
		int hmode = MeasureSpec.getMode(heightMeasureSpec);
		int wsize = MeasureSpec.getSize(widthMeasureSpec);
		int hsize = MeasureSpec.getSize(heightMeasureSpec);

		int width = 60;
		int height = 100;

		if (wmode == MeasureSpec.EXACTLY) {
			width = wsize;
		}
		if (hmode == MeasureSpec.EXACTLY) {
			height = hsize;
		}
		
		this.visible_offset = height/2 + this.TEXT_SIZE;

		this.setMeasuredDimension(width, height);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (disable)
			return true;

		this.getParent().requestDisallowInterceptTouchEvent(true);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.lastTouchY = event.getY();
			this.automove = false;
			// System.out.println("##" + lastTouchY);
			break;

		case MotionEvent.ACTION_MOVE:
			float delta = event.getY() - this.lastTouchY;

			// Log.d(WheelView.TAG, "ACTION_MOVE DELTA:" + delta);
			// Log.d(TAG,"target:"+targetOffset+" -- cur:"+curOffset);
			if (this.isOverflow(delta) != 0) {
				this.velocity = 0;
			} else {
				this.lastPos = this.curPos;
				this.curPos += delta;
				this.velocity = delta;
				this.momentum = velocity * mass;
				this.updateTargetOffset();
			}

			this.lastTouchY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			this.getParent().requestDisallowInterceptTouchEvent(false);
			this.automove = true;
			break;

		}

		this.update = true;
		this.invalidate();

		return true;
	}

	private int isOverflow(float delta) {
		if (this._adp.getCurIndex() == 0 && delta > 0) {
			// I'm at top, and is scrolling up
			this.curPos = (int) Math.min(this.curPos + delta, this.targetPos + this.overflow);
			return 1;

		} else if (this._adp.getCurIndex() == this._adp.getLen() - 1 && delta < 0) {
			// I'm at bottom and scrolling down
			this.curPos = (int) Math.max(this.curPos + delta, this.targetPos - this.overflow);
			return -1;
		}

		return 0;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTrackballEvent(event);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasWindowFocus);
	}

	public void setAdapter(WheelAdapter adp) {
		this._adp = adp;
		this.invalidate();
	}

	private void updateTargetOffset() {
	
		if (this.curPos - this.targetPos > this.TEXT_SIZE * ep) {
			// if we have moved down 70% of the text size, then advance target
			// index
			this._adp.prevIndex();
			this.getTargetOffset();
			// System.out.println("##MOVE");
		} else if (this.curPos - this.targetPos < -1 * this.TEXT_SIZE * ep) {
			this._adp.nextIndex();
			this.getTargetOffset();
			// System.out.println("##UP");
		}
	}

	public void setDisabled(boolean state) {
		disable = state;
	}

	public void addChangingListener(OnWheelChangedListener listener) {
		changingListeners.add(listener);
	}

}
