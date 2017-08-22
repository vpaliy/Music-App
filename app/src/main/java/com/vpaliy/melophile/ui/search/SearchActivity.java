package com.vpaliy.melophile.ui.search;

import android.app.SearchManager;
import android.os.Bundle;
import com.vpaliy.domain.model.Playlist;
import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.component.DaggerViewComponent;
import com.vpaliy.melophile.di.module.PresenterModule;
import com.vpaliy.melophile.ui.base.BaseActivity;
import java.util.List;
import android.text.InputType;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import butterknife.ButterKnife;
import android.widget.SearchView;
import static com.vpaliy.melophile.ui.search.SearchContract.Presenter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

public class SearchActivity extends BaseActivity
            implements SearchContract.View{

    private Presenter presenter;

    @BindView(R.id.search_view)
    protected SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupSearch();
    }

    private void setupSearch(){
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // hint, inputType & ime options seem to be ignored from XML! Set in code
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
                EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.query(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (TextUtils.isEmpty(query)) {
                    //clearResults();
                }
                return true;
            }
        });
    }

    @Override
    public void inject() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }

    @Override
    @Inject
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showTracks(@NonNull List<Track> tracks) {

    }

    @Override
    public void showPlaylists(@NonNull List<Playlist> playlists) {

    }

    @Override
    public void showUsers(@NonNull List<User> users) {

    }

    @Override
    public void showEmptyMessage() {
        //TODO add empty message
    }

    @Override
    public void showErrorMessage() {
        //TODO add error message
    }
}
