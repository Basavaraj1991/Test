package com.landmark.android.allinterfaces;

import com.landmark.android.network.response.ProductResModel;

public interface IGetProductDetailsView {
    void onGetProductDetailsSuccess(ProductResModel productResModel);
    void onGetProductDetailsError();

}
