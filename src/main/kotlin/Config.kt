import java.util.*

private const val CONFIG = "config.properties"

object PropertiesReader {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader.getResourceAsStream(CONFIG)
        properties.load(file)
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}