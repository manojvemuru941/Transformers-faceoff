package com.manoj.transformersae.ui.detailview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.custom.CriteriaComponent
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.BOT_MODEL_KEY
import com.manoj.transformersae.util.AppUtill.VIEW_TYPE_KEY

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : androidx.fragment.app.Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private lateinit var mCriteriaStrengthView: CriteriaComponent
    private lateinit var mCriteriaIntelligenceView: CriteriaComponent
    private lateinit var mCriteriaSpeedView: CriteriaComponent
    private lateinit var mCriteriaEnduranceView: CriteriaComponent
    private lateinit var mCriteriaRankView: CriteriaComponent
    private lateinit var mCriteriaCourageView: CriteriaComponent
    private lateinit var mCriteriaFirePowerView: CriteriaComponent
    private lateinit var mCriteriaSkillView: CriteriaComponent

    private lateinit var mBotModel: BotModel

    private var mViewType:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            if (it.containsKey(VIEW_TYPE_KEY)) {
                mViewType = it.getInt(VIEW_TYPE_KEY, 0)
            }

            if (it.containsKey(BOT_MODEL_KEY)) {
                mBotModel = it.getParcelable(BOT_MODEL_KEY)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        mCriteriaStrengthView = rootView.findViewById(R.id.strength)
        mCriteriaIntelligenceView = rootView.findViewById(R.id.intelligence)
        mCriteriaSpeedView = rootView.findViewById(R.id.speed)
        mCriteriaEnduranceView = rootView.findViewById(R.id.endurance)
        mCriteriaRankView = rootView.findViewById(R.id.rank)
        mCriteriaCourageView = rootView.findViewById(R.id.courage)
        mCriteriaFirePowerView = rootView.findViewById(R.id.firepower)
        mCriteriaSkillView = rootView.findViewById(R.id.skill)

        when(mViewType) {
            AppUtill.TYPE.VIEW.value -> {
                mCriteriaStrengthView.setValues(getString(R.string.criteria_strength), mBotModel.strength, true)
                mCriteriaIntelligenceView.setValues(getString(R.string.criteria_intelligence), mBotModel.intelligence, true)
                mCriteriaSpeedView.setValues(getString(R.string.criteria_speed), mBotModel.speed, true)
                mCriteriaEnduranceView.setValues(getString(R.string.criteria_endurance), mBotModel.endurance, true)
                mCriteriaRankView.setValues(getString(R.string.criteria_rank), mBotModel.rank, true)
                mCriteriaCourageView.setValues(getString(R.string.criteria_courage), mBotModel.courage, true)
                mCriteriaFirePowerView.setValues(getString(R.string.criteria_firepower), mBotModel.firepower, true)
                mCriteriaSkillView.setValues(getString(R.string.criteria_skill), mBotModel.skill, true)
            }

            AppUtill.TYPE.UPDATE.value -> {
                mCriteriaStrengthView.setValues(getString(R.string.criteria_strength), mBotModel.strength, false)
                mCriteriaIntelligenceView.setValues(getString(R.string.criteria_intelligence), mBotModel.intelligence, false)
                mCriteriaSpeedView.setValues(getString(R.string.criteria_speed), mBotModel.speed, false)
                mCriteriaEnduranceView.setValues(getString(R.string.criteria_endurance), mBotModel.endurance, false)
                mCriteriaRankView.setValues(getString(R.string.criteria_rank), mBotModel.rank, false)
                mCriteriaCourageView.setValues(getString(R.string.criteria_courage), mBotModel.courage, false)
                mCriteriaFirePowerView.setValues(getString(R.string.criteria_firepower), mBotModel.firepower, false)
                mCriteriaSkillView.setValues(getString(R.string.criteria_skill), mBotModel.skill, false)
            }

            AppUtill.TYPE.CREATE.value -> {
                mCriteriaStrengthView.setValues(getString(R.string.criteria_strength), 1, false)
                mCriteriaIntelligenceView.setValues(getString(R.string.criteria_intelligence), 1, false)
                mCriteriaSpeedView.setValues(getString(R.string.criteria_speed), 1, false)
                mCriteriaEnduranceView.setValues(getString(R.string.criteria_endurance), 1, false)
                mCriteriaRankView.setValues(getString(R.string.criteria_rank), 1, false)
                mCriteriaCourageView.setValues(getString(R.string.criteria_courage), 1, false)
                mCriteriaFirePowerView.setValues(getString(R.string.criteria_firepower), 1, false)
                mCriteriaSkillView.setValues(getString(R.string.criteria_skill), 1, false)
            }
        }

        return rootView
    }

    fun getBotModel(botModel: BotModel) : BotModel {
        botModel.strength = mCriteriaStrengthView.criteriaValue
        botModel.intelligence = mCriteriaIntelligenceView.criteriaValue
        botModel.speed = mCriteriaSpeedView.criteriaValue
        botModel.endurance = mCriteriaEnduranceView.criteriaValue
        botModel.rank = mCriteriaRankView.criteriaValue
        botModel.courage = mCriteriaCourageView.criteriaValue
        botModel.firepower = mCriteriaFirePowerView.criteriaValue
        botModel.skill = mCriteriaSkillView.criteriaValue

        return botModel
    }
}
