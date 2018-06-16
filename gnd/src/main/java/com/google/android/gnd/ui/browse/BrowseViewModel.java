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
package com.google.android.gnd.ui.browse;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.google.android.gnd.repository.GndDataRepository;
import com.google.android.gnd.repository.ProjectState;
import com.google.android.gnd.rx.RxLiveData;
import com.google.android.gnd.ui.browse.AddPlaceDialogFragment.AddPlaceRequest;
import com.google.android.gnd.ui.map.MapMarker;
import com.google.android.gnd.vo.Point;
import com.google.android.gnd.vo.Project;
import io.reactivex.Completable;
import java.util.List;
import javax.inject.Inject;

public class BrowseViewModel extends ViewModel {
  private static final String TAG = BrowseViewModel.class.getSimpleName();
  private final GndDataRepository dataRepository;
  private final LiveData<ProjectState> projectState;
  private final MutableLiveData<List<Project>> showProjectSelectorDialogRequests;
  private final MutableLiveData<Point> addPlaceDialogRequests;
  private final MutableLiveData<PlaceSheetEvent> placeSheetEvents;

  @Inject
  BrowseViewModel(GndDataRepository dataRepository) {
    this.dataRepository = dataRepository;
    this.showProjectSelectorDialogRequests = new MutableLiveData<>();
    this.addPlaceDialogRequests = new MutableLiveData<>();
    this.projectState = RxLiveData.fromFlowable(dataRepository.getProjectState());
    this.placeSheetEvents = new MutableLiveData<>();
  }

  public LiveData<List<Project>> getShowProjectSelectorDialogRequests() {
    return showProjectSelectorDialogRequests;
  }

  public LiveData<ProjectState> getProjectState() {
    return projectState;
  }

  public LiveData<Point> getShowAddPlaceDialogRequests() {
    return addPlaceDialogRequests;
  }

  public LiveData<PlaceSheetEvent> getPlaceSheetEvents() {
    return placeSheetEvents;
  }

  public Completable showProjectSelectorDialog() {
    // TODO: Show spinner while loading project summaries.
    return dataRepository
        .loadProjectSummaries()
        .doOnSuccess(showProjectSelectorDialogRequests::setValue)
        .toCompletable();
  }

  public void onMarkerClick(MapMarker marker) {
    marker.getPlace().ifPresent(place -> placeSheetEvents.setValue(PlaceSheetEvent.show(place)));
  }

  public void onAddPlaceBtnClick(Point location) {
    if (projectState.getValue().isActivated()) {
      // TODO: Pause location updates while dialog is open.
      addPlaceDialogRequests.setValue(location);
    }
  }

  public void onAddPlace(AddPlaceRequest addPlaceRequest) {
    Log.e(TAG, "TODO: Implement Add Place functionality");
  }

  public void onBottomSheetHidden() {
    placeSheetEvents.setValue(PlaceSheetEvent.hide());
  }
}
