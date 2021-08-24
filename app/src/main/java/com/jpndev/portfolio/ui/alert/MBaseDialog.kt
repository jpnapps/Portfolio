package com.jpndev.portfolio.ui.alert

import android.os.Parcel
import android.os.Parcelable

//import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

//@Parcelize
data class MBaseDialog(

        var title: String?="", // 5.011000
        var desc: String?="", // 2
        var ok_text: String?="Ok", // 4556347952822508
        var cancel_text: String?="Cancel", // 5.011000
        var extra_text: String?="",
        var from: String?="",
        var is_default_linked_wallet:Boolean=false,
        var html_text: Boolean?=false,
        var close_icon: Boolean?=true, // 2018-11-28 09:48:56
        var cancel_icon:  Boolean?=true,
        var icon_url: String?=null,
        var position: Int=-1,
        var upto_value: Double?=null 
): Parcelable/*  var ok_click: View.OnClickListener,*/ {
        constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(),
                parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(),
                parcel.readByte() != 0.toByte(),
                parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
                parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
                parcel.readValue(Boolean::class.java.classLoader) as? Boolean, parcel.readString(),
                parcel.readInt(), parcel.readValue(Double::class.java.classLoader) as? Double) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(title)
                parcel.writeString(desc)
                parcel.writeString(ok_text)
                parcel.writeString(cancel_text)
                parcel.writeString(extra_text)
                parcel.writeString(from)
                parcel.writeByte(if (is_default_linked_wallet) 1 else 0)
                parcel.writeValue(html_text)
                parcel.writeValue(close_icon)
                parcel.writeValue(cancel_icon)
                parcel.writeString(icon_url)
                parcel.writeInt(position)
                parcel.writeValue(upto_value)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<MBaseDialog> {
                override fun createFromParcel(parcel: Parcel): MBaseDialog {
                        return MBaseDialog(parcel)
                }

                override fun newArray(size: Int): Array<MBaseDialog?> {
                        return arrayOfNulls(size)
                }
        }
}