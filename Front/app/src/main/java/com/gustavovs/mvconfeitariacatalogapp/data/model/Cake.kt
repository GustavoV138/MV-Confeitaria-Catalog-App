package com.gustavovs.mvconfeitariacatalogapp.data.model

import java.io.Serializable
import java.math.BigDecimal

data class Cake(
    val id: Long? = null,
    val title: String,
    val description: String?,
    val slices: Int,
    val price: BigDecimal
) : Serializable
