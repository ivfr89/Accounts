package com.fernandez.players.data.server

import com.fernandez.players.core.ModelEntity
import com.fernandez.players.core.extensions.empty
import com.fernandez.players.core.extensions.fromJson
import com.fernandez.players.domain.models.Account
import com.google.gson.annotations.SerializedName


data class AccountEntity(
    val accountBalanceInCents: Int,
    val accountCurrency: String,
    val accountId: Long,
    val accountName: String,
    val accountNumber: String,
    val accountType: String,
    val alias: String,
    val iban: String,
    val isVisible: Boolean,
    val linkedAccountId: Int?=null,
    val productName: String?=null,
    val productType: Int?=null,
    val savingsTargetReached: Int?=null,
    val targetAmountInCents: Int?=null
): ModelEntity()
{

    override fun isEmpty(): Boolean = this == empty()

    companion object {
        fun empty() = AccountEntity(0,
            String.empty(),
            -1,
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            String.empty(),
            false,
            null,
            null,
            null,
            null,
            null)


    }

    fun toDomain() = Account(accountBalanceInCents,
                                accountCurrency,
                                accountId,
                                accountName,
                                accountNumber,
                                accountType,
                                alias,
                                iban,
                                isVisible,
                                linkedAccountId,
                                productName,
                                productType,
                                savingsTargetReached,
                                targetAmountInCents)


}
