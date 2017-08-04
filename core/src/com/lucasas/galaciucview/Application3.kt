package com.lucasas.galaciucview

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/*
 * Created by Sas on 8/4/2017.
 * Copyright (c) 2017 Team Kappa.
 * All rights reserved.
 */

class Application3(val imagePickerRequester: () -> Unit) : ApplicationAdapter(), InputProcessor
{
    companion object
    {
        lateinit var spriteBatch: SpriteBatch
        lateinit var background: Texture
    }

    var button: Button = Button()

    override fun create()
    {
        spriteBatch = SpriteBatch()
        background = Texture("page.png")

        button.sprite.x = (1080 / 2).f()
        button.sprite.y = 30.f()

        Gdx.input.inputProcessor = this
    }

    override fun render()
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        spriteBatch.draw(background, 0f, 0f)

        button.render()
    }

    override fun touchDown(x: Int, y: Int, pointer: Int, btn: Int): Boolean
    {
        button.onClickDown(x.f(), y.f())

        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, btn: Int): Boolean
    {
        button.onClickUp(imagePickerRequester)

        return true
    }

    //<editor-fold desc="Useless">
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyTyped(character: Char): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrolled(amount: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyUp(keycode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyDown(keycode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //</editor-fold>
}
