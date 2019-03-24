package com.clarkstoro.android_kotlin_examples

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.clarkstoro.android_kotlin_examples.AdMob.AdMob
import com.clarkstoro.android_kotlin_examples.ArrayList.ArrayListFragment
import com.clarkstoro.android_kotlin_examples.FCM.FirebaseCloudMessagging
import com.clarkstoro.android_kotlin_examples.FirebaseAuth.FirebaseAuth
import com.clarkstoro.android_kotlin_examples.FirebaseData.FirebaseData
import com.clarkstoro.android_kotlin_examples.FirebaseList.FirebaseList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.clarkstoro.android_kotlin_examples.JSONArrayList.JSONArrayListFragment
import com.clarkstoro.android_kotlin_examples.Translations.Translations
import com.clarkstoro.android_kotlin_examples.dbLocale.dbLocaleFragment
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.fragment_db_locale.*
import java.lang.ClassCastException


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Start the home fragment
        selectFragment(HomeFragment())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun openNav(){
        drawer_layout.openDrawer(GravityCompat.START)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }



    //Create fragment selected for showing
    private fun selectFragment(frgClass: Fragment){
        var fragment: Fragment? = null
        val fragmentClass =  frgClass::class.java
        try{
            fragment = fragmentClass.newInstance() as Fragment
        }catch (e: ClassCastException){
            e.printStackTrace()
        }

        replaceFragment(fragment as Fragment)
    }//end replaceFragment

    //Show the fragment selected
    private fun replaceFragment(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if(fragment::class == HomeFragment::class){
            if(firstTime){ //the first time the system opens Home Fragment is onCreate
                firstTime = false
            }else{
                supportFragmentManager.popBackStack() //pop last fragment
            }
            fragmentTransaction.replace(R.id.content_main, fragment)
        }else {
            //When user click on back button return back to HomeFragment before quitting
            supportFragmentManager.popBackStack() //pop last fragment
            fragmentTransaction.replace(R.id.content_main, fragment).addToBackStack(null)//add to stack the new fragment
        }
        fragmentTransaction.commit()
    }//end replaceFragment


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       when (item.itemId) {
            R.id.nav_home -> {
                selectFragment(HomeFragment())
            }
            R.id.nav_array_list -> {
                selectFragment(ArrayListFragment())
            }
            R.id.nav_json_array_list ->{
                selectFragment(JSONArrayListFragment())
            }
            R.id.nav_dbLocale ->{
                selectFragment(dbLocaleFragment())
            }
            R.id.nav_translations -> {
               selectFragment(Translations())
            }
            R.id.nav_firebaseData ->{
                FirebaseApp.initializeApp(this)
                selectFragment(FirebaseData())
            }
            R.id.nav_firebaseList ->{
                selectFragment(FirebaseList())
            }
            R.id.nav_firebaseAuth ->{
               selectFragment(FirebaseAuth())
            }
            R.id.nav_fcm -> {
                selectFragment(FirebaseCloudMessagging())
            }
            R.id.nav_admob -> {
                selectFragment(AdMob())
            }

            else ->{
                null
           }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }//end onNavigationItemSelected
}
