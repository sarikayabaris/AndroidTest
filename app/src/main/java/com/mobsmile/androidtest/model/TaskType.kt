package com.mobsmile.androidtest.model

import com.google.gson.annotations.SerializedName

enum class TaskType {
    @SerializedName("general")
    GENERAL,
    @SerializedName("medication")
    MEDICATION,
    @SerializedName("hydration")
    HYDRATION,
    @SerializedName("nutrition")
    NUTRITION
}