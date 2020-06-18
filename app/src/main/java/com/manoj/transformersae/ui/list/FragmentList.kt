package com.manoj.transformersae.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.MainActivity
import com.manoj.transformersae.ui.MainViewModel
import com.manoj.transformersae.ui.adapter.Adapter
import java.util.*

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class FragmentList : Fragment() {
    private var mAdapter: Adapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mMainViewModel: MainViewModel? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.list_fragment, container, false)
        mRecyclerView = rootView.findViewById(R.id.bot_list)
        // Create the grid progress_layout manager with 2 columns.
        val gridLayoutManager = GridLayoutManager(activity, 2)
        mRecyclerView?.layoutManager = gridLayoutManager
        mAdapter = Adapter((activity as MainActivity?)!!, ArrayList())
        mRecyclerView?.adapter = mAdapter
        return rootView
    }

    private fun updateAllItems(botModelList: List<BotModel>) {
        mRecyclerView?.visibility = View.VISIBLE
        mAdapter?.updateList(botModelList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
            mMainViewModel?.mutableBotFragmentLiveData?.observe(this, androidx.lifecycle.Observer { botsList ->
                botsList?.run {
                    updateAllItems(this)
                }
            })
        }
    }
}