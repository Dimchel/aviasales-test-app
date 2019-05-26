package com.dimchel.aviasalestestapp.api

sealed class ApiResult<out T> {

	class ApiSuccess<out T>(val result: T) : ApiResult<T>()

	object ApiError : ApiResult<Nothing>()

}