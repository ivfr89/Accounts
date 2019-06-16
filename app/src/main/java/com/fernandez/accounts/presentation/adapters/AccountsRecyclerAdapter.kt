package com.fernandez.accounts.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fernandez.accounts.R
import com.fernandez.accounts.domain.models.Account
import com.fernandez.accounts.presentation.customview.TextViewLabel

class AccountsRecyclerAdapter(var accounts: List<Account>): RecyclerView.Adapter<AccountsRecyclerAdapter.ViewHolder>()
{
    private var showAll: Boolean=false
    private var listFilterAccounts = accounts.toList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsRecyclerAdapter.ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile,parent,false))


    override fun getItemCount(): Int = listFilterAccounts.size

    override fun onBindViewHolder(holder: AccountsRecyclerAdapter.ViewHolder, position: Int) {

        holder.onBind(listFilterAccounts[position])
    }


    fun showAll() {
        showAll = true
        filterItems()
    }
    fun showVisible() {
        showAll = false
        filterItems()
    }

    private fun filterItems()
    {
        when
        {
            showAll -> { this.listFilterAccounts = accounts.toList() }
            else -> {
                this.listFilterAccounts = accounts.filter { it.isVisible }
            }
        }
        notifyDataSetChanged()
    }



    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val txtAccountName: TextViewLabel = view.findViewById(R.id.txtAccountName)
        private val txtAccountIBAN: TextViewLabel = view.findViewById(R.id.txtAccountIBAN)
        private val txtAccountBalance: TextViewLabel = view.findViewById(R.id.txtAccountBalance)

        fun onBind(account: Account)
        {
            txtAccountName.setTextLabel(account.accountName)
            txtAccountIBAN.setTextLabel(account.iban)
            txtAccountBalance.setTextLabel("${account.accountBalanceInCents} ${account.accountCurrency}")
        }

    }

}