package com.example.ticketmodule.model

import com.google.gson.JsonElement

data class HttpGenericResponse( val message: String,
                                val data : JsonElement? = null )