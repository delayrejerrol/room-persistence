package com.example.jerrol.sampleroompersistence.ui;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jerrol.sampleroompersistence.R;
import com.example.jerrol.sampleroompersistence.databinding.ListFragmentBinding;
import com.example.jerrol.sampleroompersistence.model.Product;
import com.example.jerrol.sampleroompersistence.viewmodel.ProductListViewModel;

/**
 * Created by Jerrol on 11/28/2017.
 */

public class ProductListFragment extends Fragment {

    public static final String TAG = "ProductListViewModel";

    private ProductAdapter mProductAdapter;

    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mProductAdapter = new ProductAdapter(mProductClickCallback);
        mBinding.productsList.setAdapter(mProductAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ProductListViewModel viewModel =
                ViewModelProviders.of(this).get(ProductListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(ProductListViewModel viewModel) {
        viewModel.getProducts().observe(this, productEntities -> {
            if (productEntities != null) {
                mBinding.setIsLoading(false);
                mProductAdapter.setProductList(productEntities);
            } else {
                mBinding.setIsLoading(true);
            }

            mBinding.executePendingBindings();
        });
    }

    private final ProductClickCallback mProductClickCallback = product -> {
      if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
          ((MainActivity) getActivity()).show(product);
      }
    };
}
