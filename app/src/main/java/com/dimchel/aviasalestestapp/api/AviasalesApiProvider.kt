package com.dimchel.aviasalestestapp.api

import com.dimchel.aviasalestestapp.api.schemes.AutocompleteResponseScheme

interface AviasalesApiProvider {

	suspend fun autocomplete(query: String, language: String): ApiResult<AutocompleteResponseScheme>

}