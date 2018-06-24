package com.example.demo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by nirmal on 15/6/18.
 */
@Entity
data class User(@Id @GeneratedValue(strategy= GenerationType.IDENTITY) var id: Long=0, var username: String="" , var password: String=""){

}