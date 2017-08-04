package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import java.io.File

class Application : ApplicationAdapter()
{
    override fun create()
    {
        //testColors("positive2.jpg")
        //testColorResults("positive1.jpg")
        //searchForPics()
        testColorResults("negative17.jpg")
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

                if (averageVSmaller || oneColor || averageVSmaller || greenBlueRatioBig || greyBig || brownBlueRatioBig || brownBlueNoGreen || result.white >= 2) failCount++

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

    fun makeImageBlurred(texture: Texture)
    {

    }

    override fun dispose()
    {

    }
}
