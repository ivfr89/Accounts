package com.fernandez.players.core

import com.fernandez.players.domain.uc.Either
import retrofit2.Call

abstract class BaseRepository
{
    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure,R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {

                    Either.Left(Failure.ServerErrorCode(response.code()))
                }
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }

}