package id.haweje.bukankompas.home

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.haweje.bukankompas.R
import id.haweje.bukankompas.core.ui.MainPagerAdapter
import id.haweje.bukankompas.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    @StringRes
    private val TABTITLES = intArrayOf(
        R.string.headlines_title,
        R.string.tech_title
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)
        val mainPagerAdapter = MainPagerAdapter(this)
        val viewPager: ViewPager2? = binding?.viewPager
        viewPager?.adapter = mainPagerAdapter
        val tabs : TabLayout? = binding?.tabs
        if (tabs != null && viewPager != null) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TABTITLES[position])
            }.attach()
        }

    }



}