package com.jpndev.portfolio.data.model


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jpndev.portfolio.BR
import java.io.Serializable


@Entity(
    tableName = "pitem_table"
)
 class PItem   (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,

/*    @SerializedName("key1")
    var key1: String="Username",*/

    @SerializedName("value1")
    var value1: String="",

    var value1_encrypted: Boolean=false,
/*    @SerializedName("key2")
    var key2: String="password",*/
    @SerializedName("value2")
    var value2:  String="",

    var value2_encrypted: Boolean=true,

    @SerializedName("key3")
    var key3:  String="",
    @SerializedName("value3")
    var value3: String="",
    var value3_encrypted: Boolean=false,

):Serializable, BaseObservable()
{

    @get:Bindable
    @SerializedName("key1")
    var key1: String = "Username"
        set(value) {
            field = value
            notifyPropertyChanged(BR.key1)
        }
    @get:Bindable
    @SerializedName("key2")
    var key2: String = "password"
        set(value) {
            field = value
            notifyPropertyChanged(BR.key2)
        }
}



/*
@Entity(
    tableName = "pitem_table"
)
data class PItem   (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,

    @SerializedName("key1")
    var key1: String="Username",
    */
/*@SerializedName("key1")
    var key1: ObservableField<String> = ObservableField<String>("Username"),*//*


    @SerializedName("value1")
    var value1: String="",
    @SerializedName("key2")

    var key2: String="password",
    @SerializedName("value2")
    var value2:  String="",

    @SerializedName("key3")
    var key3:  String="",
    @SerializedName("value3")
    var value3: String="",

):Serializable, BaseObservable()*/
/*

    @SerializedName("text1_color")
    var text1_color: String?,
    @SerializedName("text2_color")
    var answer1_color: String?,
    @SerializedName("web_url")
    var web_url: String?,
    @SerializedName("icon")
    var icon: String?
*/