package com.mr.k.libmvp.base

interface IEntity<D>{

    fun getCode() : Int
    fun getErrorMessage() : String?
    fun getData() : D?
}