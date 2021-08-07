package id.haweje.bukankompas.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.haweje.bukankompas.home.headlines.HeadlinesFragment
import id.haweje.bukankompas.home.technology.TechFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position) {
            0 -> fragment = HeadlinesFragment()
            1 -> fragment = TechFragment()
        }

        return fragment as Fragment
    }


}