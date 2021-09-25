package com.example.weatherapp.model

class Weather {
    var time: String? = null
    var status: String? = null
    var icon: String? = null

    constructor()
    constructor(time: String, status: String, icon: String) {
        this.time = time
        this.status = status
        this.icon = icon
    }
}

