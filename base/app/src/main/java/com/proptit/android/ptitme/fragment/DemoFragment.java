package com.proptit.android.ptitme.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proptit.android.ptitme.R;
import com.proptit.android.ptitme.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DemoFragment extends BaseFragment {
    @BindView(R.id.tv_text)
    TextView tvData;

    public DemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_test)
    public void test() {
        compositeDisposable.add(
                networkService.getApiService().getData()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .doFinally(() -> {

                        })
                        .doOnSubscribe(disposable -> {

                        })
                        .subscribe(response -> {
                            if (response == null) {
                                return;
                            }
                            tvData.setText("Data :"+response.toString());
                        }, (Throwable throwable) -> {
                            // error
                            Log.e("errorData", throwable.getMessage());
                        }));
    }
}
