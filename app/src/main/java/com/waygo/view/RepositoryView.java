package com.waygo.view;

import com.waygo.R;
import com.waygo.pojo.GitHubRepository;
import com.waygo.utils.RxBinderUtil;
import com.waygo.viewmodels.RepositoryViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import rx.android.internal.Preconditions;

public class RepositoryView extends FrameLayout {
    private final RxBinderUtil rxBinderUtil = new RxBinderUtil(this);

    private TextView titleTextView;
    private TextView stargazersTextView;
    private TextView forksTextView;

    public RepositoryView(Context context) {
        super(context);
    }

    public RepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleTextView = (TextView) findViewById(R.id.widget_layout_title);
        stargazersTextView = (TextView) findViewById(R.id.widget_layout_stargazers);
        forksTextView = (TextView) findViewById(R.id.widget_layout_forks);
    }

    public void setViewModel(@Nullable RepositoryViewModel viewModel) {
        rxBinderUtil.clear();
        if (viewModel != null) {
            rxBinderUtil.bindProperty(viewModel.getRepository(), this::setRepository);
        }
    }

    private void setRepository(@NonNull GitHubRepository repository) {
        Preconditions.checkNotNull(repository, "Repository cannot be null.");

        titleTextView.setText(repository.getName());
        stargazersTextView.setText("stars: " + repository.getStargazersCount());
        forksTextView.setText("forks: " + repository.getForksCount());
    }
}
