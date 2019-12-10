package com.central

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.tuxbear.dinos.DinosGame

object DesktopLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 1080
        config.height = 1980

        LwjglApplication(DinosGame(), config)
    }
}
