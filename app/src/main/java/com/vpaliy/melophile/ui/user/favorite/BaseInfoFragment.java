package com.vpaliy.melophile.ui.user.favorite;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.melophile.ui.utils.Constants;
import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.Nullable;
import butterknife.OnClick;
import butterknife.ButterKnife;
import static com.vpaliy.melophile.ui.user.favorite.UserInfoContract.Presenter;

public abstract class BaseInfoFragment<T>  extends BottomSheetDialogFragment
        implements UserInfoContract.View<T>{
    //TODO private access ??
    protected String id;

    @BindView(R.id.favorites)
    protected RecyclerView favorites;

    @Inject
    protected RxBus rxBus;

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.action_bar)
    protected View actionBar;

    protected Presenter<T> presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        inject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_user_favorite,container,false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractId(savedInstanceState);
        if(view!=null){
            presenter.start(id);
            favorites.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    actionBar.setActivated(dy>0);
                }
            });
        }
    }

    protected abstract void inject();

    @OnClick(R.id.close)
    public void close(){
        getActivity().onBackPressed();
    }

    @Override
    public void showEmpty() {
        //TODO show add a message
    }

    @Override
    public void showError() {
        //TODO add an error message
    }

    private void extractId(Bundle bundle){
        if(bundle==null) bundle=getArguments();
        id=bundle.getString(Constants.EXTRA_ID);
    }
}
