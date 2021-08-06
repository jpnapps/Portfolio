package com.jpndev.portfolio.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "pitem_table"
)
data class PItem   (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @SerializedName("key1")
    val key1: String?,
    @SerializedName("value1")
    val value1: String?,
    @SerializedName("key2")
    val key2: String?,
    @SerializedName("value2")
    val value2: String?,

    @SerializedName("key3")
    val key3: String?,
    @SerializedName("value3")
    val value3: String?,

    @SerializedName("text1_color")
    val text1_color: String?,
    @SerializedName("text2_color")
    val answer1_color: String?,
    @SerializedName("web_url")
    val web_url: String?,
    @SerializedName("icon")
    val icon: String?
):Serializable