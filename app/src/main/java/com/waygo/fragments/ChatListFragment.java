package com.waygo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.utils.Instrumentation;
import com.waygo.viewmodels.ChatViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ChatListFragment extends Fragment {

    private static final String TAG = ChatListFragment.class.getSimpleName();

    @Inject
    ChatViewModel viewModel;

    @Inject
    Instrumentation instrumentation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rv = (RelativeLayout) inflater.inflate(R.layout.chat_list, container, false);
        setupRecyclerView((RecyclerView) rv.findViewById(R.id.recycler_chat_view));
        final EditText askField = (EditText) rv.findViewById(R.id.ask_field);
        askField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                askField.setText("");
            }
            return false;
        });

        return rv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.subscribeToDataStore();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                Arrays.asList("1", "2")));
    }

    @Override
    public void onResume() {
        super.onResume();
//        repositoryView.setViewModel(viewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
//        repositoryView.setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        viewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        viewModel.dispose();
//        viewModel = null;
        instrumentation.getLeakTracing().traceLeakage(this);
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTextView = (TextView) view.findViewById(R.id.info_text);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                default:
                    setupViewHolder(holder, position);
            }
        }

        private void setupViewHolder(ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CheeseDetailActivity.class);
//                    intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
//
//                    context.startActivity(intent);
                }
            });

//            Glide.with(holder.mImageView.getContext())
//                 .load(Cheeses.getRandomCheeseDrawable())
//                 .fitCenter()
//                 .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public int getItemViewType(int position) {
            return 0;
        }
    }
}
