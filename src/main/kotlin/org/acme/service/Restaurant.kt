package org.acme.service

data class Restaurant(val name: String, val address: String, val averageRating: Int=0, val ratings: Array<String>)