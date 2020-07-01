package com.mina_mikhail.fixed_solutions_task.ui.gallery_slider.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by mina-mikhail on 29/08/18.
 */

public class SliderPager
    extends ViewPager {

  private boolean isLocked;

  public SliderPager(Context context) {
    super(context);
    isLocked = false;
  }

  public SliderPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    isLocked = false;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isLocked) {
      try {
        return super.onInterceptTouchEvent(ev);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return !isLocked && super.onTouchEvent(ev);
  }

  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }

  public boolean isLocked() {
    return isLocked;
  }
}
