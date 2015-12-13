package com.architjn.acjmusicplayer.ui.layouts.fragments;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.architjn.acjmusicplayer.R;
import com.architjn.acjmusicplayer.utils.ListSongs;
import com.architjn.acjmusicplayer.utils.PermissionChecker;
import com.architjn.acjmusicplayer.utils.SimpleDividerItemDecoration;
import com.architjn.acjmusicplayer.utils.adapters.ArtistsListAdapter;

/**
 * Created by architjn on 27/11/15.
 */
public class ArtistListFragment extends Fragment {

    private Context context;
    private View mainView;
    private RecyclerView rv;
    private ArtistsListAdapter adapter;
    private PermissionChecker permissionChecker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists,
                container, false);
        context = view.getContext();
        mainView = view;
        init();
        return view;
    }

    private void init() {
        rv = (RecyclerView) mainView.findViewById(R.id.songsListContainer);
        checkPermissions();
    }

    private void checkPermissions() {
        permissionChecker = new PermissionChecker(context, getActivity(), mainView);
        permissionChecker.check(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                getResources().getString(R.string.storage_permission),
                new PermissionChecker.OnPermissionResponse() {
                    @Override
                    public void onAccepted() {
                        setArtistList();
                    }

                    @Override
                    public void onDecline() {
                        getActivity().finish();
                    }
                });
    }

    private void setArtistList() {
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new SimpleDividerItemDecoration(context, 75));
        adapter = new ArtistsListAdapter(context, ListSongs.getArtistList(context), this);
        rv.setAdapter(adapter);
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                adapter.recyclerScrolled();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onBackPress() {
        adapter.onBackPressed();
    }

}
