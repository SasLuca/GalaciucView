package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.google.common.base.Stopwatch
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.concurrent.TimeUnit

class Application : ApplicationAdapter()
{
    val gson = GsonBuilder().create()

    override fun create()
    {
        while (true)
        {
            TimeUnit.SECONDS.sleep(1)

            val f = File("incepe.txt")

            if (f.exists())
            {
                scoreImages()

                f.delete()
            }
        }
    }

    fun scoreImages()
    {
        val results = mutableListOf<ImageResult>()
        val folder = File("uploads")

        if (folder.isDirectory && folder.list().size > 0)
        {
            for (f in folder.list())
            {
                val stopwatch = Stopwatch.createStarted()

                val imageData = colorTypesPerImage(f)

                stopwatch.stop()
                val elapsedTime = stopwatch.elapsed(TimeUnit.MILLISECONDS)

                results.add(ImageResult(f, imageData.score, elapsedTime.f()))
            }
        }

        val file = File("results.json")

        file.writeText(gson.toJson(results, object : TypeToken<MutableList<ImageResult>>(){}.type))
    }
}
