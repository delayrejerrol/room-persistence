package com.example.jerrol.sampleroompersistence.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jerrol.sampleroompersistence.R;
import com.example.jerrol.sampleroompersistence.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }

    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());

        getSupportFragmentManager().beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container, productFragment, null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
