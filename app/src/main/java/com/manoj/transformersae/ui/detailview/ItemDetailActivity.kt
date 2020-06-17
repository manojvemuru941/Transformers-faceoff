package com.manoj.transformersae.ui.detailview

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.manoj.transformersae.R
import com.manoj.transformersae.base.BaseActivity
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.MainActivity
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.BOT_MODEL_KEY
import com.manoj.transformersae.util.AppUtill.VIEW_TYPE_KEY
import com.manoj.transformersae.util.AppUtill.bindImage
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class ItemDetailActivity : BaseActivity<DetailViewModel>() {

    private lateinit var mRadioButtonGroup:RadioGroup
    private lateinit var mRadioButtonA:RadioButton
    private lateinit var mRadioButtonD:RadioButton
    private lateinit var mDetailFragment: ItemDetailFragment
    private lateinit var mNameEditText:EditText
    private lateinit var mSaveButton: Button

    private var mViewType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)
        var botModel: BotModel? = null
        if (intent.extras != null) {
            val bundle: Bundle = intent?.extras!!
            bundle.let {
                if (it.containsKey(BOT_MODEL_KEY)) {
                    botModel = it.getParcelable(BOT_MODEL_KEY)
                }
                if (it.containsKey(VIEW_TYPE_KEY)) {
                    mViewType = it.getInt(VIEW_TYPE_KEY, 0)
                }
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
        mDetailFragment = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(VIEW_TYPE_KEY, mViewType)
                if (botModel != null)
                    putParcelable(BOT_MODEL_KEY, botModel)
            }
        }

        mNameEditText = findViewById(R.id.name_edit_text)
        mSaveButton = findViewById(R.id.button_save)

        mRadioButtonGroup = findViewById(R.id.radio_group)
        mRadioButtonA = findViewById(R.id.radio_a)
        mRadioButtonD = findViewById(R.id.radio_d)

        if (mViewType == AppUtill.TYPE.VIEW.value || mViewType == AppUtill.TYPE.UPDATE.value) {
            viewModel.setBotModel(botModel!!)
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, mDetailFragment)
                .commit()
        mSaveButton.setOnClickListener { _ -> saveTransformer() }
        setViewType()
    }

    private fun setViewType() {
        when (mViewType) {
            AppUtill.TYPE.VIEW.value -> {
                bindImage(viewModel.getBotModel().teamIcon, fab, false)
                toolbar_layout.title = viewModel.getBotModel().name
                setupCreateViews(true)
            }
            AppUtill.TYPE.CREATE.value -> {
                mSaveButton.text = "Create"
                setupCreateViews(false)
            }
            AppUtill.TYPE.UPDATE.value -> {
                mNameEditText.setText(viewModel.getBotModel().name)
                mNameEditText.hint = ""
                mSaveButton.text = "Update"
                when (viewModel.getBotModel().team) {
                    AppUtill.TEAM_A_KEY -> {
                        mRadioButtonA.isChecked = true
                        mRadioButtonD.isChecked = false
                        mRadioButtonA.isSelected = true
                        mRadioButtonD.isSelected = false
                    }
                    AppUtill.TEAM_D_KEY -> {
                        radio_a.isChecked = false
                        radio_d.isChecked = true
                        radio_a.isSelected = false
                        radio_d.isSelected = true
                    }
                }
                setupCreateViews(false)
            }
        }
    }

    private fun setupCreateViews(isVisible: Boolean) {
        var visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        app_bar.visibility = visibility
        fab.visibility = visibility

        visibility = when (!isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        button_save.visibility = visibility
        create_layout.visibility = visibility
        name_edit_text_layout.visibility = visibility
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, MainActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun getSelectedTeam(): String {
        return when (mRadioButtonGroup.checkedRadioButtonId) {
            R.id.radio_a -> "A"
            R.id.radio_d -> "D"
            else -> "D"
        }
    }

    private fun saveTransformer() {
        when (mViewType) {
            AppUtill.TYPE.VIEW.value -> {
                mSaveButton.text = "View"
                mSaveButton.isClickable = false
                mSaveButton.isEnabled = false
            }
            AppUtill.TYPE.CREATE.value -> {
                mSaveButton.isClickable = true
                mSaveButton.isEnabled = true
                val botModel = mDetailFragment.getBotModel(BotModel())
                botModel.name = mNameEditText.text.toString()
                botModel.team = getSelectedTeam()
                viewModel.save(botModel)
            }
            AppUtill.TYPE.UPDATE.value -> {
                mSaveButton.isClickable = true
                mSaveButton.isEnabled = true
                val botModel = mDetailFragment.getBotModel(viewModel.getBotModel())
                botModel.name = mNameEditText.text.toString()
                botModel.team = getSelectedTeam()
                viewModel.update(botModel)
            }
        }
    }

    override fun setViewModel(): DetailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

}
