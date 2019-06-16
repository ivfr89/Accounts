package com.fernandez.accounts.domain.uc.players

import com.fernandez.accounts.core.Failure
import com.fernandez.accounts.domain.models.Account
import com.fernandez.accounts.domain.repository.PlayerRepository
import com.fernandez.accounts.domain.uc.Either
import com.fernandez.accounts.domain.uc.UseCase
import kotlinx.coroutines.CoroutineScope


class GetAccounts(private val playerRespository: PlayerRepository): UseCase<List<Account>, GetAccounts.Params, CoroutineScope>(){


    override suspend fun run(params: Params): Either<Failure, List<Account>> {


        return if(Cache.mCache.isEmpty() || params.force)
        {
            val result = playerRespository.getPlayers()

            result.either({},{

                it.also {playerList->
                    Cache.mCache = playerList
                }
            })
            result

        }
        else{
            Either.Right(Cache.mCache)
        }

    }


//    it can be more complex
    object Cache
    {
        var mCache: List<Account> = listOf()
    }

    inner class Params(val force: Boolean=false)


}