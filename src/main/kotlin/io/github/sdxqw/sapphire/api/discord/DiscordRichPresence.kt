package io.github.sdxqw.sapphire.api.discord

import net.arikia.dev.drpc.DiscordEventHandlers
import net.arikia.dev.drpc.DiscordRPC

class DiscordRichPresence private constructor(private val id: String, private val imageID: String) {

    companion object {
        @JvmStatic
        fun create(id: String, imageID: String): DiscordRichPresence {
            val rpc = DiscordRichPresence(id, imageID)
            rpc.start()
            return rpc
        }
    }

    private var running: Boolean = false
    private var created: Long = 0L

    private fun start() {
        this.running = true
        this.created = System.currentTimeMillis()

        val builder = DiscordEventHandlers.Builder()
        builder.setReadyEventHandler { this.update("Playing...", "") }

        DiscordRPC.discordInitialize(id, builder.build(), true)
        Thread {
            DiscordRPC.discordRunCallbacks()
        }.start()
    }

    fun shutdown() {
        this.running = false
        DiscordRPC.discordShutdown()
    }

    fun update(firstLine: String, secondLine: String) {
        val b = net.arikia.dev.drpc.DiscordRichPresence.Builder(secondLine)
        b.setBigImage(imageID, "")
        b.setDetails(firstLine)
        b.setStartTimestamps(created)
        DiscordRPC.discordUpdatePresence(b.build())
    }

}