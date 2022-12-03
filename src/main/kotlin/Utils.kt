import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpCookie
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.time.Duration
import kotlin.io.path.createDirectory
import kotlin.io.path.exists


infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

fun IntArray2D(input:String) : Array<IntArray> {
    return input.lines().map { l -> l.toCharArray().map { it.digitToInt() }.toIntArray() }.toTypedArray()
}

fun CopyArray(array: Array<IntArray>): Array<IntArray> {
    return array.map { it.clone() }.toTypedArray()
}

fun splitOnBlankLines(input:String): List<String> {
    return input.replace("\r\n", "\n").split("\n\n")
}

fun trimTrailingBlankLine(input:String): String {
    return input.trimEnd('\n', '\r')
}

fun getInput(day:Int): String {

    val folder = getFolder().resolve("Input")
    if (!folder.exists()) { folder.createDirectory() }
    val file = folder.resolve("Day$day.txt").toFile()

    if (!file.exists()) {
        file.createNewFile()
        file.writeText(trimTrailingBlankLine(downloadInput(day)))
    }

    return file.readText();
}

private fun getFolder(): Path {
    return Path.of(File(
        Day1::class.java.protectionDomain.codeSource.location.toURI()
    ).path)
}

private fun downloadInput(day: Int): String {
    CookieHandler.setDefault(CookieManager())

    val sessionCookie = HttpCookie("session", PropertiesReader.getProperty("sessionid"))
    sessionCookie.path = "/"
    sessionCookie.version = 0

    (CookieHandler.getDefault() as CookieManager).cookieStore.add(
        URI("https://adventofcode.com"),
        sessionCookie
    )

    val client = HttpClient.newBuilder()
        .cookieHandler(CookieHandler.getDefault())
        .connectTimeout(Duration.ofSeconds(10))
        .build()

    val req = HttpRequest.newBuilder()
        .uri(URI.create("https://adventofcode.com/2022/day/$day/input"))
        .GET().build()


    return client.send(req, HttpResponse.BodyHandlers.ofString()).body()
}
