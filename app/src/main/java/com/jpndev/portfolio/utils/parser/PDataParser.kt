package com.jpndev.portfolio.utils.parser

import android.content.Context
import com.google.gson.Gson
import com.jpndev.portfolio.data.model.PInputData
import com.jpndev.portfolio.utils.Common
import com.jpndev.portfolio.utils.ParserConstants
import com.jpndev.portfolio.utils.extensions.readAssetsFile

/**
 * Default WatchSettings parser used to get the data from watch_settings configuration file
 */
object PDataParser {
    /**
     * function to return Watch Setting List
     * @param context Context used to read raw file.
     * @return ArrayList of WatchSettingList
     */
    fun parse(context: Context): ArrayList<PInputData> {
        val tempData = Gson().fromJson(
            context.assets.readAssetsFile("${ParserConstants.P_DATA_FILE}${Common.JSON_FILE_EXTENSION}"),
                    Array<PInputData>::class.java
        ).toList()
        return tempData as ArrayList<PInputData>
    }
}
