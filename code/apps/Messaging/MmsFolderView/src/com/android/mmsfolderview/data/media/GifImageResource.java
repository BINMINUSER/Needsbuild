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
package com.android.mmsfolderview.data.media;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.rastermill.FrameSequence;
import android.support.rastermill.FrameSequenceDrawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class GifImageResource extends ImageResource {
    private static final String TAG = "GifImageResource";
    private FrameSequence mFrameSequence;

    public GifImageResource(String key, FrameSequence frameSequence) {
        // GIF does not support exif tags
        super(key, ExifInterface.ORIENTATION_NORMAL);
        mFrameSequence = frameSequence;
    }

    public static GifImageResource createGifImageResource(String key, InputStream inputStream) {
        final FrameSequence frameSequence;
        try {
            frameSequence = FrameSequence.decodeStream(inputStream);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Nothing to do if we fail closing the stream
            }
        }
        if (frameSequence == null) {
            return null;
        }
        return new GifImageResource(key, frameSequence);
    }

    @Override
    public Drawable getDrawable(Resources resources) {
        return new FrameSequenceDrawable(mFrameSequence);
    }

    @Override
    public Bitmap getBitmap() {
        Log.e(TAG, "GetBitmap() should never be called on a gif.");
        return null;
    }

    @Override
    public byte[] getBytes() {
        Log.e(TAG, "GetBytes() should never be called on a gif.");
        return null;
    }

    @Override
    public Bitmap reuseBitmap() {
        return null;
    }

    @Override
    public boolean supportsBitmapReuse() {
        // FrameSequenceDrawable a.) takes two bitmaps and thus does not fit into the current
        // bitmap pool architecture b.) will rarely use bitmaps from one FrameSequenceDrawable to
        // the next that are the same sizes since they are used by attachments.
        return false;
    }

    @Override
    public int getMediaSize() {
        Log.e(TAG, "GifImageResource should not be used by a media cache");
        // Only used by the media cache, which this does not use.
        return 0;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    protected void close() {
        acquireLock();
        try {
            if (mFrameSequence != null) {
                mFrameSequence = null;
            }
        } finally {
            releaseLock();
        }
    }

}
