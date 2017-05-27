/*
 * Copyright (C) 2011,2013 Thundersoft Corporation
 * All rights Reserved
 */
package com.ucamera.ucam.modules.ugif.edit.cate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.camera2.R;
import com.ucamera.ucam.modules.ugif.edit.BackGroundWorkTask;
import com.ucamera.ucam.modules.ugif.edit.callback.ProcessCallback;
import com.ucamera.ucam.modules.utils.LogUtils;
import com.ucamera.ucam.modules.utils.UiUtils;
import com.ucamera.ucomm.downloadcenter.DownloadCenter;
import com.ucamera.ucomm.downloadcenter.Constants;

public class TextureCate extends GifBasicCate {
    private static final String TAG = "TextureCate";
    private String[] mTextures = null;

    public TextureCate(Context context, ProcessCallback callback) {
        super(context, callback);
        mSelected = -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mTextures == null) {
            return null;
        }
        View view = null;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.ugif_edit_menu_item, null);
        } else {
            view = convertView;
        }
        ImageView iv =  (ImageView)view.findViewById(R.id.iv_ugif_edit_item_bk);
        TextView ivFreeDecor =  (TextView)view.findViewById(R.id.ugif_decorations_free);
        LogUtils.debug(TAG, "mTextures[position] :" +mTextures[position]);
        if(position == 0 && DownloadCenter.RESOURCE_DOWNLOAD_ON) {
            iv.setImageBitmap(null);
            iv.setBackgroundResource(R.drawable.bg_download_normal);
            ivFreeDecor.setVisibility(View.VISIBLE);
        } else {
            ivFreeDecor.setVisibility(View.GONE);
            iv.setImageBitmap(DownloadCenter.thumbNameToThumbBitmap(mContext, mTextures[position],
                    Constants.EXTRA_TEXTURE_VALUE));
            iv.setBackgroundResource(R.drawable.ugif_edit_menu_item_bg_selector);
        }

        if(mItemMaxHeight > 0) {
            LayoutParams layoutparameter = iv.getLayoutParams();
            layoutparameter.height = mItemMaxHeight - UiUtils.dpToPixel(3);
            layoutparameter.width = mItemMaxWidth - UiUtils.dpToPixel(3);
            iv.setLayoutParams(layoutparameter);
        }
        iv.setSelected(mSelected == position);
        return view;
    }

    @Override
    public int getCount() {
        return mTextures != null ? mTextures.length : 0;
    }

    @Override
    public void updateContents(String[] strings) {
//        mSelected = -1;
        mTextures = strings;
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0 && DownloadCenter.RESOURCE_DOWNLOAD_ON) {
            DownloadCenter.openResourceCenter((Activity)mContext, Constants.EXTRA_TEXTURE_VALUE);
            //mSelected = -1;
        } else {
            if (mCallback != null) {
                mCallback.beforeProcess();
            }
            setHighlight(position);
            String fileName = DownloadCenter.getFullResourcePath(mTextures[position], Constants.EXTRA_TEXTURE_VALUE);
            handleOverlay(fileName);
            handleOverlay(fileName);
            if (mCallback != null) {
                mCallback.afterProcess(position);
            }
        }
    }
}
