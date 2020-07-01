package com.mina_mikhail.fixed_solutions_task.utils;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/*
 * *
 *  * Created by Mina Mikhail on 29/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class ZoomOutTransformation
    implements ViewPager.PageTransformer {

  private static final float MIN_SCALE = 0.65f;
  private static final float MIN_ALPHA = 0.3f;

  @Override
  public void transformPage(@NonNull View page, float position) {
    if (position < -1) {  // [-Infinity,-1)
      // This page is way off-screen to the left.
      page.setAlpha(0);
    } else if (position <= 1) { // [-1,1]

      page.setScaleX(Math.max(MIN_SCALE, 1 - Math.abs(position)));
      page.setScaleY(Math.max(MIN_SCALE, 1 - Math.abs(position)));
      page.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));
    } else {  // (1,+Infinity]
      // This page is way off-screen to the right.
      page.setAlpha(0);
    }
  }
}