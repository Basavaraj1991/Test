package com.landmark.android.allinterfaces;

import com.landmark.android.AppUtils;
import com.landmark.android.CurrencyType;
import com.landmark.android.network.ApiClient;
import com.landmark.android.network.ApiSerivice;
import com.landmark.android.network.response.ProductResModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductDetailsPresenterImpl implements IGetProductDetailsPresenter {

    IGetProductDetailsView iGetProductDetailsView;

    public GetProductDetailsPresenterImpl(IGetProductDetailsView iGetProductDetailsView) {
        this.iGetProductDetailsView = iGetProductDetailsView;
    }

    @Override
    public void onGetProductDetails() {

            Call<ProductResModel> call =  ApiClient.getClient().create(ApiSerivice.class).getProductDetails();
            call.enqueue(new Callback<ProductResModel>() {
                @Override
                public void onResponse(Call<ProductResModel> call, Response<ProductResModel> response) {

                    if (AppUtils.isSuccessResponse(response)) {

                        if (response.body().getConversion() != null && !response.body().getConversion().isEmpty()) {
                            AppUtils utils = new AppUtils();
                            //print the initial array values
                            utils.print();
                            for (ProductResModel.ConversionBean currency : response.body().getConversion()) {
                                utils.setMultiples(CurrencyType.valueOf(currency.getFrom()).ordinal() + 1, CurrencyType.valueOf(currency.getTo()).ordinal() + 1, Double.parseDouble(currency.getRate()));
                            }
                            //print the array values after adding the currency
                            utils.print();

                            iGetProductDetailsView.onGetProductDetailsSuccess(response.body());
                        }
                    }else {
                        iGetProductDetailsView.onGetProductDetailsError();
                    }
                }

                @Override
                public void onFailure(Call<ProductResModel> call, Throwable t) {
                    iGetProductDetailsView.onGetProductDetailsError();
                }
            });

    }
}
