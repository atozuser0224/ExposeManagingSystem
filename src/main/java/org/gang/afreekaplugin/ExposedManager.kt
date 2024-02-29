package org.gang.afreekaplugin



import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.*
import xyz.icetang.lib.kommand.kommand


class ExposedManager : JavaPlugin(),Listener {

    companion object {
        val logger = Bukkit.getLogger()
        lateinit var instance: ExposedManager
    }

    override fun onLoad() {
        instance = this
    }
    override fun onEnable() {
        this.kommand {
        }
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onDisable() {

    }

    fun expose(db : String,transaction: Transaction.() -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            Database.connect("jdbc:mysql://109.199.111.137:3306/$db", driver = "com.mysql.cj.jdbc.Driver",
                user = "server", password = "abcd1234*")
            org.jetbrains.exposed.sql.transactions.transaction {
                transaction
            }
        }
    }
}
