package com.waygo.fragments;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.utils.Instrumentation;
import com.waygo.view.RepositoryView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class AgendaListFragment extends Fragment {

    private static final String TAG = AgendaListFragment.class.getSimpleName();

    @Inject
    Instrumentation instrumentation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.agenda_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instrumentation.getLeakTracing().traceLeakage(this);
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();

        private int mBackground;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(getLayout(viewType), parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        private int getLayout(int viewType) {
            return ViewType.values()[viewType].resourceId;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            setupViewHolder(holder, position);
        }

        private void setupViewHolder(ViewHolder holder, int position) {
            switch (ViewType.values()[position]) {
                case TRANSIT: {
                    holder.mView.setOnClickListener(v -> {
                        View contentView = holder.mView.findViewById(R.id.transit_more_content);
                        contentView.setVisibility(contentView.getVisibility() == View.VISIBLE
                                                          ? View.GONE
                                                          : View.VISIBLE);
                    });
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return ViewType.values().length;
        }

        public int getItemViewType(int position) {
            return ViewType.values()[position].ordinal();
        }
    }

    enum ViewType {

        TODAY(R.layout.today_item),
        NEW_GATE(R.layout.new_gate_flight_item),
        SUN(R.layout.sun_item),
        FLIGHT(R.layout.flight_item),
        COLD(R.layout.cold_item),
        TRANSIT(R.layout.transit_item),
        MAP(R.layout.map_item);

        private @IdRes int resourceId;

        ViewType(int resourceId) {
            this.resourceId = resourceId;
        }

    }
}
