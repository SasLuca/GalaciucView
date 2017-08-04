package com.lucasas.galaciucview

import java.io.File
import javax.imageio.ImageIO

/*
 * Created by Sas on 8/3/2017.
 * Copyright (c) 2017 Team Kappa. 
 * All rights reserved.
 */

infix inline fun <reified T> T.dot(foo: T.() -> Unit): T
{
    foo()
    return this
}

fun Number.f() = this.toFloat()

fun <T : Number> T.between(a: Comparable<T>, b: Comparable<T>) = a.compareTo(this) <= 0 && b.compareTo(this) >= 0

fun oneOrLess(vararg data: Int): Boolean
{
    var s1 = true

    for (d in data)
    {
        if (d == 0 && s1) s1 = false
        else if (d == 0)
        {
            return true
        }
    }

    return false
}

fun rgbToHsv(rp: Float, gp: Float, bp: Float): Array<Float>
{
    var computedH = 0f
    var computedS = 0f
    var computedV = 0f

    val r = rp / 255f
    val g = gp / 255f
    val b = bp / 255f

    val minRGB = Math.min(r,Math.min(g, b))
    val maxRGB = Math.max(r,Math.max(g, b))

    // Black-gray-white
    if (minRGB == maxRGB)
    {
        computedV = minRGB
        return arrayOf(0f, 0f, computedV)
    }

    // Colors other than black-gray-white:
    val d = if (r == minRGB) g - b else if(b == minRGB) r - g else b - r
    val h = if(r == minRGB) 3 else if(b == minRGB) 1 else 5

    computedH = 60 * (h - d / (maxRGB - minRGB))
    computedS = (maxRGB - minRGB) / maxRGB
    computedV = maxRGB

    computedS *= 100
    computedV *= 100
    computedH = Math.floor(computedH.toDouble()).toFloat()
    computedS = Math.floor(computedS.toDouble()).toFloat()
    computedV = Math.ceil(computedV.toDouble()).toFloat()

    if (computedS == Float.NaN) computedS = 0f
    if (computedV == Float.NaN) computedV = 0f

    return arrayOf(computedH, computedS, computedV)
}

enum class ColorType
{
    Brown, Green, Blue, Red, Grey, Other
}

fun getColorType(h: Float, s: Float, v: Float) : ColorType
{
    if (s < 13 || v < 13) return ColorType.Grey

    when
    {
        h.between(25f, 56f) -> return ColorType.Brown
        h.between(75f, 144f) -> return ColorType.Green
        h.between(160f, 260f) -> return ColorType.Blue
        h.between(300f, 350f) && s.between(24f, 50f) -> return ColorType.Red
        else -> return ColorType.Other
    }
}

data class ImageColorType(var brown: Int = 0, var green: Int = 0, var blue: Int = 0, var red: Int = 0, var grey: Int = 0, var other: Int = 0, var blueS: Float = 0f, var blueV: Float = 0f, var averageV: Float = 0f)

fun colorTypesPerImage(name: String): ImageColorType
{
    val result = ImageColorType()

    val img = ImageIO.read(File("$testType/$name"))
    val dominantColor = ColorThief.getColorMap(img, 5).vboxes[0].avg(false)
    var hsv = rgbToHsv(dominantColor[0].f(), dominantColor[1].f(), dominantColor[2].f())

    when (getColorType(hsv[0], hsv[1], hsv[2]))
    {
        ColorType.Blue -> {
            result.blue++
            result.blueS = hsv[1]
            result.blueV = hsv[2]
        }
        ColorType.Red -> result.red++
        ColorType.Grey -> result.grey++
        ColorType.Green -> result.green++
        ColorType.Other -> result.other++
        ColorType.Brown -> result.brown++
    }

    result.averageV = hsv[2]

    //Full palette
    val palette = ColorThief.getColorMap(img, 20)

    var skip = true

    for (vbox in palette.vboxes)
    {
        if (skip)
        {
            skip = false
            continue
        }

        val rgbValues = vbox.avg(false)

        hsv = rgbToHsv(rgbValues[0].f(), rgbValues[1].f(), rgbValues[2].f())

        when (getColorType(hsv[0], hsv[1], hsv[2]))
        {
            ColorType.Blue -> {
                result.blue++
                result.blueS += hsv[1]
                result.blueV += hsv[2]
            }
            ColorType.Red -> result.red++
            ColorType.Grey -> result.grey++
            ColorType.Green -> result.green++
            ColorType.Other -> result.other++
            ColorType.Brown -> result.brown++
        }

        result.averageV += hsv[2]
    }

    result.blueS /= result.blue
    result.blueV /= result.blue
    result.averageV /= palette.vboxes.size

    if (result.blueS == (Float.NaN as Number)) result.blueS = 0f
    if (result.blueV == (Float.NaN as Number)) result.blueV = 0f

    return result
}