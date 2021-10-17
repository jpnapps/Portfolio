package com.jpndev.portfolio.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "qa"
)
data class QA(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @SerializedName("text1")
    val text1: String?,
    @SerializedName("text2")
    val answer1: String?,
    @SerializedName("text1_color")
    val text1_color: String?,
    @SerializedName("text2_color")
    val answer1_color: String?,
    @SerializedName("web_url")
    val web_url: String?,
    @SerializedName("icon")
    val icon: String?
):Serializable


@Entity(
    tableName = "profile"
)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @SerializedName("name")
    val name: String?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("phone1")
    val phone1: String?,
    @SerializedName("phone2")
    val phone2: String?,
    @SerializedName("mail1")
    val mail1: String?,
    @SerializedName("mail2")
    val mail2: String?,
    @SerializedName("stackoverflow")
    val stackoverflow: String?,
    @SerializedName("linkedln")
    val linkedln: String?,
    @SerializedName("web_url")
    val web_url: String?,
    @SerializedName("icon")
    val icon: String?
):Serializable