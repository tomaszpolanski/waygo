package com.waygo.fragments

import com.waygo.R
import com.waygo.WaygoApplication
import com.waygo.data.model.conversation.Person
import com.waygo.data.model.conversation.User
import com.waygo.data.model.conversation.Waygo
import com.waygo.utils.Instrumentation
import com.waygo.utils.RxBinderUtil
import com.waygo.viewmodels.ChatViewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

import javax.inject.Inject

import jet.runtime.typeinfo.JetValueParameter
import kotlin.Function1
import kotlin.Unit

public class ChatListFragment : Fragment() {

    private val rxBinderUtil = RxBinderUtil(this)

    var viewModel: ChatViewModel? = null
        @Inject set

    var instrumentation: Instrumentation? = null
        @Inject set

    private var adapter: ChatListAdapter? = null

    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WaygoApplication.getInstance().getGraph().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rv = inflater?.inflate(R.layout.chat_list, container, false)
        setupRecyclerView(rv?.findViewById(R.id.recycler_chat_view) as RecyclerView)

        return rv
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.subscribeToDataStore()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        adapter = ChatListAdapter(getActivity(), ArrayList<Person>())
        recyclerView.setLayoutManager(LinearLayoutManager(recyclerView.getContext()))
        recyclerView.setAdapter(adapter)
    }

    public fun setChatViewModel(viewModel: ChatViewModel?) {
        rxBinderUtil.clear()
        if (viewModel != null) {
            rxBinderUtil.bindProperty(viewModel.getConversation()) {setConversation(it)}
        }
    }

    private fun setConversation(person: Person) {
        val ada = adapter;
        if (ada != null) {
            ada.addConversation(person)
            mRecyclerView?.smoothScrollToPosition(ada.getItemCount() - 1)
        }
    }

    override fun onResume() {
        super.onResume()
        setChatViewModel(viewModel)
    }

    override fun onPause() {
        super.onPause()
        setChatViewModel(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.unsubscribeFromDataStore()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.dispose()
        viewModel = null
        instrumentation?.getLeakTracing()?.traceLeakage(this)
    }

    public class ChatListAdapter(context: Context, private val mPersons: MutableList<Person>) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

        private val mTypedValue = TypedValue()

        private val mBackground: Int

        public fun addConversation(person: Person) {
            mPersons.add(person)
            notifyDataSetChanged()
            notifyItemInserted(mPersons.size() - 1)
        }

        public class ViewHolder(public val mView: View) : RecyclerView.ViewHolder(mView) {

            public val mImageView: ImageView

            public val mTextView: TextView

            public val mDateTextView: TextView

            public val mMapImageView: View?

            init {
                mImageView = mView.findViewById(R.id.avatar) as ImageView
                mTextView = mView.findViewById(R.id.text) as TextView
                mDateTextView = mView.findViewById(R.id.date) as TextView
                mMapImageView = mView.findViewById(R.id.map)
            }
        }

        init {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true)
            mBackground = mTypedValue.resourceId
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false)
            view.setBackgroundResource(mBackground)
            return ViewHolder(view)
        }

        private fun getLayout(viewType: Int): Int = ViewType.values().get(viewType).resourceId


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val textView = holder.mTextView
            textView.setClickable(true)
            textView.setMovementMethod(LinkMovementMethod.getInstance())
            val person = mPersons.get(position);
            textView.setText(Html.fromHtml(person.sentence))
            holder.mDateTextView.setText(person.time)
            person.userImage.iter{ holder.mImageView.setImageResource(it) }

            if (person is Waygo) {
                holder.mMapImageView?.setVisibility(View.GONE)
                person.image.iter {holder.mMapImageView?.setVisibility(View.VISIBLE)}
            }
        }

        override fun getItemCount(): Int {
            return mPersons.size()
        }

        override fun getItemViewType(position: Int): Int {
            return if (mPersons.get(position) is User)
                ViewType.USER.ordinal()
            else
                ViewType.WAY_GO.ordinal()
        }
    }

    enum class ViewType(IdRes
                        public val resourceId: Int) {

        USER(R.layout.user_item),
        WAY_GO(R.layout.waygo_item)

    }
}
