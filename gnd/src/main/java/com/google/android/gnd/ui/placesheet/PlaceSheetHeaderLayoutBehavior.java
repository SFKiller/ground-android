/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gnd.ui.placesheet;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gnd.R;
import com.google.android.gnd.ui.OnBottomSheetSlideBehavior;

public class PlaceSheetHeaderLayoutBehavior extends OnBottomSheetSlideBehavior {
  public PlaceSheetHeaderLayoutBehavior(
    Context context,
    AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onSheetScrolled(
    CoordinatorLayout parent, View child, SheetSlideMetrics metrics) {
    View cardView = child.findViewById(R.id.place_sheet_card_view);
    // Let header and body overlap to cover bottom corner radius.
    cardView.setPadding(0, 0, 0, 8);
    child.setTranslationY(metrics.getTop() + 8 - child.getHeight());
  }
}
