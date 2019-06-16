package com.fernandez.accounts.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.fernandez.accounts.core.Model
import com.fernandez.accounts.core.extensions.empty
import com.fernandez.accounts.data.server.AccountEntity



data class Account(
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
): Model(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: String.empty(),
        parcel.readLong(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    companion object
    {
        fun empty() = Account(0,
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


        @JvmField
        val CREATOR: Parcelable.Creator<Account> = object : Parcelable.Creator<Account> {
            override fun createFromParcel(parcel: Parcel): Account {
                return Account(parcel)
            }

            override fun newArray(size: Int): Array<Account?> {
                return arrayOfNulls(size)
            }

        }
    }

    override fun isEmpty(): Boolean = this == empty()

    fun toEntity() = AccountEntity(accountBalanceInCents,
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



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(accountBalanceInCents)
        parcel.writeString(accountCurrency)
        parcel.writeLong(accountId)
        parcel.writeString(accountName)
        parcel.writeString(accountNumber)
        parcel.writeString(accountType)
        parcel.writeString(alias)
        parcel.writeString(iban)
        parcel.writeByte(if (isVisible) 1 else 0)
        parcel.writeValue(linkedAccountId)
        parcel.writeString(productName)
        parcel.writeValue(productType)
        parcel.writeValue(savingsTargetReached)
        parcel.writeValue(targetAmountInCents)
    }

    override fun describeContents(): Int {
        return 0
    }



}

