package com.dimchel.aviasalestestapp.api

sealed class ApiResult<out T> {

	class ApiSuccess<out T>(val result: T) : ApiResult<T>()

	object ApiError : ApiResult<Nothing>()

}

fun <In, Out> ApiResult<In>.mapToOtherApiResult(mappingFunction: (input: In) -> Out): ApiResult<Out> =
	when (this) {
		is ApiResult.ApiSuccess -> ApiResult.ApiSuccess(mappingFunction(this.result))
		is ApiResult.ApiError -> ApiResult.ApiError
	}