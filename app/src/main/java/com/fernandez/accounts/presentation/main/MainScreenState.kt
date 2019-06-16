package com.fernandez.accounts.presentation.main

import com.fernandez.accounts.domain.models.Account

sealed class MainScreenState
{
    class GetAccountsFromServer(val listAccounts: List<Account>): MainScreenState()
}