package com.lucasas.galaciucview

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite

/*
 * Created by Sas on 8/4/2017.
 * Copyright (c) 2017 Team Kappa. 
 * All rights reserved.
 */

class Button()
{
    companion object
    {
        val unpressedTexture = Texture("btnunclicked.png")
        val pressedTexture = Texture("btnclicked.png")
    }

    val sprite = Sprite(unpressedTexture)
    private var isPressed = false

    fun render()
    {
        sprite.draw(Application3.spriteBatch)
    }

    fun onClickDown(x: Float, y: Float)
    {
        if (x >= sprite.x && x <= sprite.x + sprite.width && y >= sprite.y && y <= sprite.y + sprite.height && !isPressed)
        {
            isPressed = true
        }
    }

    fun onClickUp(foo: () -> Unit)
    {
        if (isPressed)
        {
            isPressed = false
            foo()
        }
    }
}