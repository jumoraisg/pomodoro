package net.clubedocomputador.pomodoro.features.principal

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import net.clubedocomputador.pomodoro.R
import net.clubedocomputador.pomodoro.features.base.BaseActivity
import net.clubedocomputador.pomodoro.features.history.HistoryFragment
import net.clubedocomputador.pomodoro.features.timer.TimerFragment
import net.clubedocomputador.pomodoro.messaging.Events
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class PrincipalActivity : BaseActivity(), PrincipalMvpView {


    companion object {
        var activityVisibility = false
    }

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private val timerFragment = TimerFragment()
    private val historyFragment = HistoryFragment()

    private val presenter = PrincipalPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewPager = findViewById(R.id.view_pager_main)
        tabLayout = findViewById(R.id.tab_layout_main)

        setupViewPagerAdapter()
        setupNestedScrollViewToWorkWithViewPager()
        setupTabLayout()

    }

    private fun setupViewPagerAdapter() {
        viewPager.adapter =  PrincipalViewPagerAdapter(supportFragmentManager, arrayOf(timerFragment, historyFragment))
    }

    private fun setupNestedScrollViewToWorkWithViewPager(){
        val nestedScrolView: NestedScrollView = findViewById(R.id.nested_scroll_view_main)
        nestedScrolView.isFillViewport = true
    }

    private fun setupTabLayout(){
        tabLayout.setupWithViewPager(viewPager)
    }


    @Suppress("UNUSED")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvents(event: Events) {

        when (event.action) {

        }
    }


    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }

    }

    override fun onStart() {
        super.onStart()
        activityVisibility = true
    }

    override fun onStop() {
        activityVisibility = false
        super.onStop()

    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }


}
