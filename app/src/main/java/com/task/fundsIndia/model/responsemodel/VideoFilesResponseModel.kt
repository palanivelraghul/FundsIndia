package com.task.fundsIndia.model.responsemodel

import com.google.gson.annotations.SerializedName

class VideoFilesResponseModel {

    @SerializedName("dateTime")
    var dateTime: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("fileSize")
    var fileSize: String? = null

    @SerializedName("id")
    var id: Int? = 0
}
