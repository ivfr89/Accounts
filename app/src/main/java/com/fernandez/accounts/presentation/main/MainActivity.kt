package com.fernandez.accounts.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fernandez.accounts.R
import com.fernandez.accounts.core.Failure
import com.fernandez.accounts.core.ScreenState
import com.fernandez.accounts.presentation.adapters.AccountsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

/*
* @author  Iván Fernández Rico
*/

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    private lateinit var mAccountsAdapter: AccountsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initStates()
        initListeners()

        configureUI()
        configureToolbar()

        mViewModel.accountsFromServer()

    }

    private fun initStates()
    {
        mViewModel.state.observe(::getLifecycle,::updateUI)
        mViewModel.failure.observe(::getLifecycle,::handleErrors)
    }

    private fun initListeners()
    {
        swipeRefresh.setOnRefreshListener {
            mViewModel.accountsFromServer(force = true)
        }
    }


    private fun configureUI()
    {
        mAccountsAdapter = AccountsRecyclerAdapter(listOf())
        rcvPlayers.layoutManager = LinearLayoutManager(this)
        rcvPlayers.adapter = mAccountsAdapter
    }

    private fun configureToolbar()
    {
        swViewAll.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked)
            {
                true->{ mAccountsAdapter.showAll()}
                false -> { mAccountsAdapter.showVisible()}
            }
        }
    }



    private fun handleErrors(failure: Failure?) {

//        Handle the errors and hide loader
        hideProgress()

        when(failure)
        {
            is Failure.NullResult -> {
                Toast.makeText(this, getString(R.string.accounts_null),Toast.LENGTH_LONG).show()
            }
            is Failure.NetworkConnection -> {
                Toast.makeText(this, getString(R.string.no_connection),Toast.LENGTH_LONG).show()
            }

            is Failure.ServerErrorCode ->{

                handleErrorCodes(failure)

            }
        }

    }

    private fun handleErrorCodes(failure: Failure.ServerErrorCode) {


        when(failure.code)
        {
            404 -> Toast.makeText(this, getString(R.string.accounts_404),Toast.LENGTH_LONG).show()
        }

//        This method depends on the failure request code given by the server.
//        In this case there isnt specification but the method exists for this purpose
    }




    private fun updateUI(screenState: ScreenState<MainScreenState>?) {

        when(screenState)
        {
            is ScreenState.Loading ->{
                showProgress()
            }

            is ScreenState.Render -> {
                hideProgress()
                renderScreenStates(screenState.renderState)
            }

        }

    }

    private fun renderScreenStates(renderState: MainScreenState) {

        when(renderState)
        {
            is MainScreenState.GetAccountsFromServer -> {

                mAccountsAdapter.accounts = (renderState.listAccounts)


                if(swViewAll.isChecked)
                {
                    mAccountsAdapter.showAll()
                } else
                    mAccountsAdapter.showVisible()

            }
        }


    }


    private fun showProgress()
    {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgress()
    {
        swipeRefresh.isRefreshing = false
    }





}
