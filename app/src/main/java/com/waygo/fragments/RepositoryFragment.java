package com.waygo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.activities.MainActivity;
import com.waygo.network.LufthansaAccountService;
import com.waygo.network.ServiceGenerator;
import com.waygo.utils.Instrumentation;
import com.waygo.view.RepositoryView;
import com.waygo.viewmodels.RepositoryViewModel;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepositoryFragment extends Fragment {

    private static final String TAG = RepositoryFragment.class.getSimpleName();

    private RepositoryView repositoryView;

    @Inject
    RepositoryViewModel viewModel;

    @Inject
    Instrumentation instrumentation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repository_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repositoryView = (RepositoryView) view.findViewById(R.id.repository_view);
        viewModel.subscribeToDataStore();

        view.findViewById(R.id.repository_fragment_choose_repository_button)
                .setOnClickListener(e -> ((MainActivity) getActivity()).chooseRepository());
    }

    @Override
    public void onResume() {
        super.onResume();
        repositoryView.setViewModel(viewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        repositoryView.setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.dispose();
        viewModel = null;
        instrumentation.getLeakTracing().traceLeakage(this);
    }
}
