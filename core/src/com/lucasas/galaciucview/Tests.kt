package com.lucasas.galaciucview

import org.jetbrains.annotations.TestOnly
import java.io.File
import javax.imageio.ImageIO


/*
 * Created by Sas on 8/3/2017.
 * Copyright (c) 2017 Team Kappa. 
 * All rights reserved.
 */

val testType = "negative"

fun testColorResults(image: String = "positive1")
{
    val result = colorTypesPerImage(image)

    var oneColor = false
    var averageVSmaller = false
    var greyBig = false
    var greenBlueRatioBig = false
    var brownBlueRatioBig = false
    var brownBlueNoGreen = (if (result.blue != 0) result.brown / result.blue else 0) > 1 && result.green == 0
    var blueBrownRatio = if (result.brown == 0) 0 else result.blue / result.brown
    var greenBrownRatio = if (result.brown == 0) 0 else result.green / result.brown

    if (oneOrLess(result.brown, result.green, result.blue))
        oneColor = true

    averageVSmaller = result.averageV < 30
    greyBig = result.grey > 10
    if (result.blue != 0) greenBlueRatioBig = (result.green / result.blue) > 3
    if (result.blue != 0) brownBlueRatioBig = (result.brown / result.blue) > 4

    println()
    println("#$image ${if(oneColor || averageVSmaller || greyBig || greenBlueRatioBig || brownBlueRatioBig || brownBlueNoGreen || result.white > 2) "FAIL" else ""}")
    println("Brick: ${result.brick}")
    println("Brown: ${result.brown}")
    println("Green: ${result.green}")
    println("Blue: ${result.blue}")
    println("Red: ${result.red}")
    println("Grey: ${result.grey}")
    println("White: ${result.white}")
    println("Other: ${result.other}")
    println("Brown/Blue no Green: ${brownBlueNoGreen}")
    println("Blue/Brown: ${blueBrownRatio}")
    println("Green/Blue: ${if (result.blue != 0) result.green / result.blue else 0}")
    println("Green/Brown: $greenBrownRatio")
    println("Brown/Green: ${if (result.green != 0) result.brown / result.green else 0}")
    println("Brown/Blue: ${if (result.blue != 0) result.brown / result.blue else 0}")
    println("BlueS: ${result.blueS}")
    println("BlueV: ${result.blueV}")
    println("AverageV: ${result.averageV}")
    println("Score:${result.score}")
}

fun testColors(texture: String)
{
    val img = ImageIO.read(File("$testType/$texture"))

    // The dominant color is taken from a 5-map
    //println("Dominant Color")
    var result = ColorThief.getColorMap(img, 5)
    val dominantColor = result.vboxes[0].avg(false)

    println("Hex:${createRGBHexString(dominantColor)}")

    // Get the full palette
    println("Palette")
    result = ColorThief.getColorMap(img, 10)

    var ct = 0

    for (vbox in result.vboxes)
    {
        println()
        println("Color #${++ct}")
        println("Hex:${createRGBHexString(vbox.avg(false))}")
    }
}

private fun createRGBHexString(rgb: IntArray): String
{
    var rgbHex = Integer.toHexString(rgb[0] shl 16 or (rgb[1] shl 8) or rgb[2])

    // Left-pad with 0s
    val length = rgbHex.length

    if (length < 6) rgbHex = "00000".substring(0, 6 - length) + rgbHex

    return rgbHex
}

@TestOnly fun testInBetweenFun()
{
    println("Testing Number.between function")
    assert(1.between(0, 1))
    assert(2.between(0, 100))
    assert(!2.between(0, 1))

    assert(0.1f.between(0f, 0.1f))
    assert(0.1f.between(0f, 0.2f))
    assert(!0.2f.between(0f, 0.1f))
}

@TestOnly fun testRgbToHsvFun()
{
    val result = rgbToHsv(89f, 92f, 114f)

    println("${result[0]} ${result[1]} ${result[2]}")
}
