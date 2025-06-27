package com.example.iot_environmental

class Node (val id:String,var temp:Float,var hum:Float,var lum:Int,var date:String  ) {

    fun update( temp:Float, hum:Float, lum:Int){
        this.temp =temp
        this.hum =hum
        this.lum =lum
    }

}