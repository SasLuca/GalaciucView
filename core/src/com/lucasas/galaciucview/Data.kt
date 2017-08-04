package com.lucasas.galaciucview

/*
 * Created by Sas on 8/3/2017.
 * Copyright (c) 2017 Team Kappa. 
 * All rights reserved.
 */


data class ImageResult(val name: String, val score: Float)
data class Results(val scores: Array<ImageResult>)