package com.fernandez.accounts.domain.repository

import com.fernandez.accounts.core.BaseRepository
import com.fernandez.accounts.core.Failure
import com.fernandez.accounts.core.extensions.empty
import com.fernandez.accounts.data.server.*
import com.fernandez.accounts.domain.models.Account
import com.fernandez.accounts.domain.uc.Either
import com.google.gson.JsonSyntaxException
import org.json.JSONObject

interface PlayerRepository
{

    fun getPlayers(): Either<Failure,List<Account>>



    class Network
    constructor(
        private val networkHandler: NetworkHandler,
        private val service: ApiService,
        private val serverMapper: ServerResponseMapper
    ) : BaseRepository(), PlayerRepository {

        override fun getPlayers(): Either<Failure, List<Account>> {

            return if(networkHandler.isConnected)
            {
                request(service.getAccounts(),{ response->

                    try {

                        val accountString = JSONObject(response).getJSONArray("accounts").toString()

                        serverMapper.parseArrayResponse<AccountEntity>(accountString).map { it.toDomain() }

                    }catch (e: JsonSyntaxException)
                    {
                        listOf<Account>()
                    }

                },String.empty())
            }else
                Either.Left(Failure.NetworkConnection())
        }



    }


}