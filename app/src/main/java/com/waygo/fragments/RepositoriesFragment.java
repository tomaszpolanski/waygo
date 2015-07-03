package com.waygo.fragments;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.activities.ChooseRepositoryActivity;
import com.waygo.utils.Instrumentation;
import com.waygo.view.RepositoriesView;
import com.waygo.viewmodels.RepositoriesViewModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class RepositoriesFragment extends Fragment {

    private RepositoriesView repositoriesView;

    @Inject
    RepositoriesViewModel repositoriesViewModel;

    @Inject
    Instrumentation instrumentation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WaygoApplication.getInstance().getGraph().inject(this);

        repositoriesViewModel.getSelectRepository()
                .subscribe(repository ->
                        ((ChooseRepositoryActivity) getActivity())
                                .chooseRepository(repository.getId()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repositories_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repositoriesView = (RepositoriesView) view.findViewById(R.id.repositories_view);
        repositoriesViewModel.subscribeToDataStore();
    }

    @Override
    public void onResume() {
        super.onResume();
        repositoriesView.setViewModel(repositoriesViewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        repositoriesView.setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repositoriesViewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repositoriesViewModel.dispose();
        repositoriesViewModel = null;
        instrumentation.getLeakTracing().traceLeakage(this);
    }
}
