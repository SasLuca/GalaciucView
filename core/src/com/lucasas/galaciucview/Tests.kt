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

@TestOnly fun searchForPicsTest()
{
    val folder = File("$testType")

    var brown = 0
    var green = 0
    var blue = 0
    var red = 0
    var grey = 0
    var brick = 0
    var white = 0
    var other = 0
    var blueS = 0f
    var blueV = 0f
    var averageV = 0f
    var highestGrey = 0
    var oneColor = false
    var averageVSmaller = false
    var maxGreenBlueRatio = 0
    var maxBrownGreenRatio = 0
    var maxBrownBlueRatio = 0
    var greyBig = false
    var greenBlueRatioBig = false
    var brownBlueRatioBig = false
    var brownBlueNoGreen = false
    var failCount = 0
    var maxBlueBrownRatio = 0
    var minGreenS = 9999f
    var minGreenV = 9999f
    var maxGreenS = 0f
    var maxGreenV = 0f

    var score = 0


    if (folder.isDirectory && folder.list().size > 0)
    {
        for (f in folder.list())
        {
            oneColor = false
            averageVSmaller = false
            greyBig = false

            testColorResults(f)

            val result = colorTypesPerImage(f)

            brick += result.brick
            brown += result.brown
            green += result.green
            blue += result.blue
            red += result.red
            grey += result.grey
            white += result.white
            other += result.other
            blueS += result.blueS
            blueV += result.blueV
            averageV += result.averageV

            oneColor = oneOrLess(result.brown, result.green, result.blue)
            averageVSmaller = result.averageV < 30
            greyBig = result.grey > 10

            val greenBlueRatio = if (result.blue != 0) result.green / result.blue else 0
            val brownGreenRatio = if (result.green != 0) result.brown / result.green else 0
            val brownBlueRatio = if (result.blue != 0) result.brown / result.blue else 0
            var blueBrownRatio = if (result.brown == 0) 0 else result.blue / result.brown

            brownBlueNoGreen = (if (result.blue != 0) result.brown / result.blue else 0) > 1 && result.green == 0

            if (result.blue != 0) brownBlueRatioBig = (result.brown / result.blue) > 4

            if (result.blue != 0) greenBlueRatioBig = (result.green / result.blue) > 3

            if (maxGreenBlueRatio < greenBlueRatio)
                maxGreenBlueRatio = greenBlueRatio

            if (maxBrownGreenRatio < brownGreenRatio)
                maxBrownGreenRatio = brownGreenRatio

            if (maxBrownBlueRatio < brownBlueRatio)
                maxBrownBlueRatio = brownBlueRatio

            if (maxBlueBrownRatio < blueBrownRatio)
                maxBlueBrownRatio = blueBrownRatio

            if (minGreenS > result.greenS && result.green > 0)
                minGreenS = result.greenS

            if (minGreenV > result.greenV && result.green > 0)
                minGreenV = result.greenV

            if (maxGreenS < result.greenS && result.green > 0)
                maxGreenS = result.greenS

            if (maxGreenV < result.greenV && result.green > 0)
                maxGreenV = result.greenV

            if (averageVSmaller || oneColor || averageVSmaller || greenBlueRatioBig || greyBig || brownBlueRatioBig || brownBlueNoGreen || result.white >= 2 || result.greenV > 79f)
                failCount++

            if (result.grey > highestGrey)
                highestGrey = result.grey

            score += result.score
        }

        println()
        println("$testType average color ${if(failCount > 0) "FAIL" else ""}")
        println("Brick: ${brick}")
        println("Brown:${brown / folder.list().size}")
        println("Green:${green / folder.list().size}")
        println("Blue:${blue / folder.list().size}")
        println("Red:${red / folder.list().size}")
        println("Grey:${grey / folder.list().size}")
        println("White: ${white / folder.list().size}")
        println("Other:${other / folder.list().size}")
        println("BlueS:${blueS / folder.list().size}")
        println("BlueV:${blueV / folder.list().size}")
        println("MinGreenS:${minGreenS}")
        println("MinGreenV:${minGreenV}")
        println("MaxGreenS:${maxGreenS}")
        println("MaxGreenV:${maxGreenV}")
        println("MaxGreenBlueRatio:${maxGreenBlueRatio}")
        println("MaxBrownGreenRatio:${maxBrownGreenRatio}")
        println("MaxBlueBrownRatio:${maxBlueBrownRatio}")
        println("MaxBrownBlueRatio:${maxBrownBlueRatio}")
        println("AverageV:${averageV / folder.list().size}")
        println("HighestGrey:${highestGrey}")
        println("FailCount:${failCount}")
        println("Score:${score / folder.list().size}")
    }
}

@TestOnly fun testColorResults(image: String = "positive1")
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
    var greyGreenRatio = if (result.green == 0) 0 else result.grey / result.green

    if (oneOrLess(result.brown, result.green, result.blue))
        oneColor = true

    averageVSmaller = result.averageV < 30
    greyBig = result.grey > 10
    if (result.blue != 0) greenBlueRatioBig = (result.green / result.blue) > 3
    if (result.blue != 0) brownBlueRatioBig = (result.brown / result.blue) > 4

    println()
    println("#$image ${if(oneColor || averageVSmaller || greyBig || greenBlueRatioBig || brownBlueRatioBig || brownBlueNoGreen || result.white >= 2 || result.greenV > 79f) "FAIL" else ""}")
    println("Brick: ${result.brick}")
    println("Brown: ${result.brown}")
    println("Green: ${result.green}")
    println("Blue: ${result.blue}")
    println("Red: ${result.red}")
    println("Grey: ${result.grey}")
    println("White: ${result.white}")
    println("Other: ${result.other}")
    println("Grey/Green: $greyGreenRatio")
    println("Brown/Blue no Green: ${brownBlueNoGreen}")
    println("Blue/Brown: ${blueBrownRatio}")
    println("Green/Blue: ${if (result.blue != 0) result.green / result.blue else 0}")
    println("Green/Brown: $greenBrownRatio")
    println("Brown/Green: ${if (result.green != 0) result.brown / result.green else 0}")
    println("Brown/Blue: ${if (result.blue != 0) result.brown / result.blue else 0}")
    println("BlueS: ${result.blueS}")
    println("BlueV: ${result.blueV}")
    println("GreenS: ${result.greenS}")
    println("GreenV: ${result.greenV}")
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
