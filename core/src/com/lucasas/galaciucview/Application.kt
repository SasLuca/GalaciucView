package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture

class Application : ApplicationAdapter()
{
    override fun create()
    {
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
