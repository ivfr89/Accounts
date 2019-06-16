package com.fernandez.players.domain.uc.players

import com.fernandez.players.core.Failure
import com.fernandez.players.domain.models.Account
import com.fernandez.players.domain.repository.PlayerRepository
import com.fernandez.players.domain.uc.Either
import com.fernandez.players.domain.uc.UseCase
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