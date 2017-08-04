package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.google.common.base.Stopwatch
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.concurrent.TimeUnit

class Application : ApplicationAdapter()
{
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

    val gson = GsonBuilder().create()

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

                results.add(ImageResult(f, imageData.score, elapsedTime))
            }
        }

        val file = File("results.json")

        file.writeText(gson.toJson(results, object : TypeToken<MutableList<ImageResult>>(){}.type))
    }

    override fun render()
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    fun makeImageBlurred(texture: Texture)
    {

    }

    override fun dispose()
    {

    }
}
