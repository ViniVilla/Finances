package br.edu.ifsp.finances.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.User
import br.edu.ifsp.finances.endpoint.LoginEndpoint
import br.edu.ifsp.finances.fragment.AccountFragment
import br.edu.ifsp.finances.fragment.AccountTypeFragment
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*
import kotlinx.android.synthetic.main.nav_header_drawer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        onNavigationItemSelected(nav_view.checkedItem!!)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_account_types -> {
                showFragment(AccountTypeFragment())
                supportActionBar?.title = getString(R.string.account_type)
            }
            R.id.nav_accounts -> {
                showFragment(AccountFragment())
                supportActionBar?.title = getString(R.string.account)
            }
            R.id.nav_transaction_categories -> {
            }
            R.id.nav_transactions -> {
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
}
