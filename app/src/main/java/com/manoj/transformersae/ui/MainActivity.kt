package com.manoj.transformersae.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manoj.transformersae.R
import com.manoj.transformersae.base.BaseActivity
import com.manoj.transformersae.databinding.ActivityItemListBinding
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.detailview.ItemDetailActivity
import com.manoj.transformersae.ui.list.FragmentList
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.VIEW_TYPE_KEY
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BaseActivity<MainViewModel>(), View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var mFragmentList: FragmentList
//    private lateinit var mDisposable:Disposable
//    private lateinit var mwarDisposable:Disposable
    private lateinit var mDialog: Dialog
    private lateinit var activityItemListBinding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityItemListBinding = DataBindingUtil.setContentView(this, R.layout.activity_item_list)
        activityItemListBinding.lifecycleOwner = this
//        setSupportActionBar(toolbar)
//        toolbar.title = title

        fab.setOnClickListener { _->
            goToCreateView()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        setUpListFragment()
        war_button.setOnClickListener {
            viewModel.startWar()
        }

        viewModel.mutableBotWarResponseLiveData.observe(this, warResultObserver)
        viewModel.mutableBotListLiveData.observe(this, listObserver)
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestToRefreshList()
    }

    private val warResultObserver = Observer<String> {showWarResult(it)}

    private val listObserver = Observer<List<BotModel>> {listen(it) }

    private fun listen(list: List<BotModel>) {
        if (list.isNotEmpty()) {
            viewModel.allBots = list
            viewModel.mutableBotFragmentLiveData.value = list
        } else {
            goToCreateView()
        }
    }

    private fun goToCreateView() {
        if(!AppUtill.isTesting) {
            val intent = Intent(this, ItemDetailActivity::class.java)
            intent.putExtra(VIEW_TYPE_KEY, AppUtill.TYPE.CREATE.value)
            startActivity(intent)
        }
    }

    private fun setUpListFragment() {
        mFragmentList = FragmentList()
        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, mFragmentList)
                .commit()
    }

    override fun onClick(view: View?) {
        val id = view?.getTag(R.id.item_list_layout) as String
        val botModel = viewModel.getBotById(id)
        botModel?.let {
            when(view.id) {
                R.id.item_list_layout -> {
                    goToDetail(AppUtill.TYPE.VIEW.value, botModel)
                }
                R.id.edit -> {
                    goToDetail(AppUtill.TYPE.UPDATE.value, botModel)
                }
                R.id.delete -> {
                    viewModel.deleteBot(botModel)
                }
            }
        }
    }

    private fun goToDetail(viewType:Int, botModel: BotModel) {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra(AppUtill.BOT_MODEL_KEY, botModel)
        intent.putExtra(VIEW_TYPE_KEY, viewType)
        startActivity(intent)
    }

    private fun showWarResult(result: String) {
        mDialog = Dialog(this);
        mDialog.setContentView(R.layout.dialog_layout)
        val text = mDialog.findViewById(R.id.content) as TextView
        text.text = result
        val ok_button = mDialog.findViewById(R.id.ok_button) as Button
        ok_button.setOnClickListener {
            mDialog.dismiss()
        }
        mDialog.show()

    }

    @VisibleForTesting
    fun  updateList(list: List<BotModel>) {
        viewModel.mutableBotFragmentLiveData.value = list
    }

    @VisibleForTesting
    fun getDialogView() : Dialog? {
        return if(mDialog != null) {
            mDialog
        } else {
            null
        }
    }

    override fun setViewModel(): MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
}
