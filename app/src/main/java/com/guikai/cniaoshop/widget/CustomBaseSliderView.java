package com.guikai.cniaoshop.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public abstract class CustomBaseSliderView extends BaseSliderView {

    private String mUrl;
    private File mFile;
    private int mRes;
    private ImageLoadListener mLoadListener;

    protected CustomBaseSliderView(Context context) {
        super(context);
    }

    @Override
    public BaseSliderView image(String url) {
        mUrl = url;
        return super.image(url);
    }

    @Override
    public BaseSliderView image(File file) {
        mFile = file;
        return super.image(file);
    }

    @Override
    public BaseSliderView image(int res) {
        mRes = res;
        return super.image(res);
    }

    protected void bindEventAndShow(final View v, ImageView targetImageView, boolean disableCacheAndStore) {
        if (!disableCacheAndStore) {
            super.bindEventAndShow(v, targetImageView);
            return;
        }

        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

        if (mLoadListener != null) {
            mLoadListener.onStart(me);
        }

        Picasso p = (getPicasso() != null) ? getPicasso() : Picasso.with(mContext);
        RequestCreator requestCreator = null;
        if (getUrl() != null) {
            requestCreator = p.load(mUrl);
        } else if (mFile != null) {
            requestCreator = p.load(mFile);
        } else if (mRes != 0) {
            requestCreator = p.load(mRes);
        } else {
            return;
        }

        if (requestCreator == null) {
            return;
        }

        // To Disable Image Caching
        requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);

        if (getEmpty() != 0) {
            requestCreator.placeholder(getEmpty());
        }

        if (getError() != 0) {
            requestCreator.error(getError());
        }

        switch (getScaleType()) {
            case Fit:
                requestCreator.fit();
                break;
            case CenterCrop:
                requestCreator.fit().centerCrop();
                break;
            case CenterInside:
                requestCreator.fit().centerInside();
                break;
        }

        requestCreator.into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
                if (v.findViewById(com.daimajia.slider.library.R.id.loading_bar) != null) {
                    v.findViewById(com.daimajia.slider.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onError() {
                if (mLoadListener != null) {
                    mLoadListener.onEnd(false, me);
                }
                if (v.findViewById(com.daimajia.slider.library.R.id.loading_bar) != null) {
                    v.findViewById(com.daimajia.slider.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
