package com.fernandez.players.presentation.main

import com.fernandez.players.domain.models.Account

sealed class MainScreenState
{
    class GetAccountsFromServer(val listAccounts: List<Account>): MainScreenState()
}