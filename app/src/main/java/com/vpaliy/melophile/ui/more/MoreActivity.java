package com.vpaliy.melophile.ui.more;

import android.os.Bundle;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.bus.event.ExposeEvent;
import com.vpaliy.melophile.ui.utils.BundleUtils;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;

public class MoreActivity extends BaseActivity {

    @BindView(R.id.data)
    protected RecyclerView data;

    @BindView(R.id.title)
    protected TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
    }

    private void setup(Bundle bundle){
        if(bundle==null) bundle=getIntent().getExtras();
        if(bundle!=null){

        }
    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeEvent){
            navigator.navigate(this,(ExposeEvent)(event));
        }
    }
}
