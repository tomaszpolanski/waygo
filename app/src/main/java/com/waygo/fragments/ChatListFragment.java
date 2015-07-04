package com.waygo.fragments;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.data.model.conversation.Person;
import com.waygo.data.model.conversation.User;
import com.waygo.utils.Instrumentation;
import com.waygo.utils.RxBinderUtil;
import com.waygo.viewmodels.ChatViewModel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChatListFragment extends Fragment {

    private static final String TAG = ChatListFragment.class.getSimpleName();

    private final RxBinderUtil rxBinderUtil = new RxBinderUtil(this);

    @Inject
    ChatViewModel viewModel;

    @Inject
    Instrumentation instrumentation;

    private ChatListAdapter adapter;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.chat_list, container, false);
        setupRecyclerView((RecyclerView) rv.findViewById(R.id.recycler_chat_view));

        return rv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.subscribeToDataStore();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        adapter = new ChatListAdapter(getActivity(), new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void setViewModel(@Nullable ChatViewModel viewModel) {
        rxBinderUtil.clear();
        if (viewModel != null) {
            rxBinderUtil.bindProperty(viewModel.getConversation(), this::setConversation);
        }
    }

    private void setConversation(Person person) {
        adapter.addConversation(person);
        mRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewModel(viewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        setViewModel(null);
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

    public static class ChatListAdapter
            extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();

        private final List<Person> mPersons;

        private int mBackground;

        public void addConversation(Person person) {
            mPersons.add(person);
            notifyDataSetChanged();
            notifyItemInserted(mPersons.size() - 1);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            public final ImageView mImageView;

            public final TextView mTextView;

            public final TextView mDateTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(R.id.text);
                mDateTextView = (TextView) view.findViewById(R.id.date);
            }
        }

        public ChatListAdapter(Context context, List<Person> persons) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mPersons = persons;
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
            holder.mTextView.setText(Html.fromHtml(mPersons.get(position).getSentence()));
            holder.mDateTextView.setText(mPersons.get(position).getTime());
            mPersons.get(position)
                    .getUserImage()
                    .iter(id -> holder.mImageView.setImageResource(id));

        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }

        public int getItemViewType(int position) {
            return mPersons.get(position) instanceof User
                    ? ViewType.USER.ordinal()
                    : ViewType.WAY_GO.ordinal() ;
        }
    }

    enum ViewType {

        USER(R.layout.user_item),
        WAY_GO(R.layout.waygo_item);

        private
        @IdRes
        int resourceId;

        ViewType(int resourceId) {
            this.resourceId = resourceId;
        }

    }

}
