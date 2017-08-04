package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.google.common.base.Stopwatch
import java.io.File
import java.util.concurrent.TimeUnit

/*
 * Created by Sas on 8/4/2017.
 * Copyright (c) 2017 Team Kappa.
 * All rights reserved.
 */

class Application2(private val arg: Array<String>) : ApplicationAdapter()
{
    override fun create()
    {
        scoreImages()
    }

    fun scoreImages()
    {
        val file = File("results.csv")
        val stringBuilder = StringBuilder()
        val folder = File(arg[0])
        var ct = 0

        if (folder.isDirectory && folder.list().size > 0)
        {
            try
            {
                stringBuilder.append("\"File name\",Score,\"Execution time\",\n")

                for (f in folder.list())
                {
                    ++ct

                    val stopwatch = Stopwatch.createStarted()

                    val imageData = colorTypesPerImage(f)

                    stopwatch.stop()
                    val elapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS).toFloat() / 1000f

                    ImageResult(f, imageData.score, elapsedTime)

                    stringBuilder.append("$f,${imageData.score},${elapsedTime}${if (ct == folder.list().size - 1) "" else ","}\n")

                    println("Image #${ct}: $f , ${imageData.score} , ${elapsedTime}")
                }
            }
            catch(e: Exception) { }

            file.writeText(stringBuilder.toString())

            Gdx.app.exit()
        }
    }
}
