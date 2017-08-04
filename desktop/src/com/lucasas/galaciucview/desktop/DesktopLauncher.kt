package com.lucasas.galaciucview.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.lucasas.galaciucview.Application
import com.lucasas.galaciucview.Application2

object DesktopLauncher
{
    @JvmStatic fun main(arg: Array<String>)
    {
        val config = LwjglApplicationConfiguration()

        LwjglApplication(if (arg.isEmpty()) Application() else Application2(arg), config)
    }
}
