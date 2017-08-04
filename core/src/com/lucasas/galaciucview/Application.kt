package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import java.io.File

class Application : ApplicationAdapter()
{
    var images = mutableListOf<Texture>()
    var blurredImages = mutableListOf<Texture>()

    override fun create()
    {
        //testColors("positive2.jpg")
        //testColorResults("positive1.jpg")
        searchForPics()
        //testColorResults("positive33.jpg")
    }

    override fun render()
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    fun searchForPics()
    {
        val folder = File("$testType")

        var brown = 0
        var green = 0
        var blue = 0
        var red = 0
        var grey = 0
        var other = 0
        var blueS = 0f
        var blueV = 0f
        var averageV = 0f
        var highestGrey = 0
        var oneColor = false
        var averageVSmaller = false
        var minGreenBlueRatio = 0f
        var greyBig = false
        var failCount = 0

        if (folder.isDirectory && folder.list().size > 0)
        {
            for (f in folder.list())
            {
                oneColor = false
                averageVSmaller = false
                greyBig = false

                testColorResults(f)

                val result = colorTypesPerImage(f)

                brown += result.brown
                green += result.green
                blue += result.blue
                red += result.red
                grey += result.grey
                other += result.other
                blueS += result.blueS
                blueV += result.blueV
                averageV += result.averageV

                oneColor = oneOrLess(result.brown, result.green, result.blue)
                averageVSmaller = result.averageV < 30
                greyBig = result.grey > 10

                var greenBlueRatio = result.green.f() / result.blue.f()

                if (greenBlueRatio == (Float.NaN as Number)) greenBlueRatio = 0f

                if (minGreenBlueRatio < greenBlueRatio)
                    minGreenBlueRatio = greenBlueRatio

                if (averageVSmaller || oneColor || averageVSmaller) failCount++

                if (result.grey > highestGrey)
                    highestGrey = result.grey
            }

            println()
            println("$testType average color ${if(failCount > 0) "FAIL" else ""}")
            println("Brown:${brown / folder.list().size}")
            println("Green:${green / folder.list().size}")
            println("Blue:${blue / folder.list().size}")
            println("Red:${red / folder.list().size}")
            println("Grey:${grey / folder.list().size}")
            println("Other:${other / folder.list().size}")
            println("BlueS:${blueS / folder.list().size}")
            println("BlueV:${blueV / folder.list().size}")
            println("AverageV:${averageV / folder.list().size}")
            println("HighestGrey:${highestGrey}")
            println("FailCount:${failCount}")
        }
    }

    fun makeImageBlurred(texture: Texture)
    {

    }

    override fun dispose()
    {

    }
}
