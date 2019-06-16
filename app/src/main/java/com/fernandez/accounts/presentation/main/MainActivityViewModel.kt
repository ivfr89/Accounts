package com.fernandez.accounts.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandez.accounts.core.BaseViewModel
import com.fernandez.accounts.core.ScreenState
import com.fernandez.accounts.domain.models.Account
import com.fernandez.accounts.domain.uc.players.GetAccounts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainActivityViewModel(val getAccounts: GetAccounts): BaseViewModel()
{
    private val _state: MutableLiveData<ScreenState<MainScreenState>> = MutableLiveData()

    val state: LiveData<ScreenState<MainScreenState>>
        get()= _state

    private var job = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun accountsFromServer(force: Boolean=false)
    {
        this._state.value = ScreenState.Loading

        getAccounts.execute({it.either(::handleFailure, ::handlePlayers)},getAccounts.Params(force) ,coroutineScope)
    }

    private fun handlePlayers(list: List<Account>) {

        this._state.value = ScreenState.Render(MainScreenState.GetAccountsFromServer(list))
        this._failure.value = null

    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}