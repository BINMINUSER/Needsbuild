/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.messaging.ui.mediapicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.messaging.R;
import com.android.messaging.datamodel.data.MessagePartData;
import com.android.messaging.util.OsUtil;
import android.provider.ContactsContract;
import android.content.Intent;
import android.app.Activity;

/**
 * Chooser which allows the user to record audio
 */
class AudioAttachMediaChooser extends MediaChooser implements
        AudioAttachView.AudioAttachViewListener {
    private View mEnabledView;
    private View mMissingPermissionView;

    AudioAttachMediaChooser(final MediaPicker mediaPicker) {
        super(mediaPicker);
    }

    @Override
    public int getSupportedMediaTypes() {
        return MediaPicker.MEDIA_TYPE_ATTACH_AUDIO;
    }

    @Override
    public int[] getIconResource() {
        return new int[] {R.drawable.ic_attach_audio_light, R.drawable.ic_attach_audio_dark};
    }

    @Override
    public int getIconDescriptionResource() {
        return R.string.attach_sound;
    }

    public void onAudioAttachPickerTouch() {
        mMediaPicker.launchAudioAttachPicker();
    }

    @Override
    protected View createView(final ViewGroup container) {
        final LayoutInflater inflater = getLayoutInflater();
        final AudioAttachView view = (AudioAttachView) inflater.inflate(
                R.layout.mediapicker_audioacttach_chooser,
                container /* root */, false /* attachToRoot */);
        mEnabledView = view.findViewById(R.id.mediapicker_enabled);
        mMissingPermissionView = view
                .findViewById(R.id.missing_permission_view);
        view.setHostInterface(this);
        return view;
    }

    @Override
    int getActionBarTitleResId() {
        return R.string.attach_sound;
    }

    @Override
    protected void setSelected(final boolean selected) {
        super.setSelected(selected);
        if (selected && !OsUtil.hasRecordAudioPermission()) {
            requestRecordAudioPermission();
        }
    }

    private void requestRecordAudioPermission() {

    }

    @Override
    protected void onRequestPermissionsResult(final int requestCode,
            final String permissions[], final int[] grantResults) {
    }
}
