package com.msa.basenetworkcore

sealed class Event {


    object fetchUserData2 : Event()
    object fetchUserData : Event()
}