package id.haweje.bukankompas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import id.haweje.bukankompas.account.AccountFragment
import id.haweje.bukankompas.databinding.ActivityMainBinding
import id.haweje.bukankompas.home.HeadlinesFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(HeadlinesFragment())

        binding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> setCurrentFragment(HeadlinesFragment())
                R.id.navigation_account -> setCurrentFragment(AccountFragment())
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
}