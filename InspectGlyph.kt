import java.net.URLClassLoader
import java.io.File
import java.net.URL

fun main() {
    val file = File("C:\\Users\\Mirmir0\\.gradle\\caches\\modules-2\\files-2.1\\com.nexomc\\nexo\\1.17.0\\15d0fe5695d6e0fc727faaf77e92687d14e41513\\nexo-1.17.0.jar")
    val cl = URLClassLoader(arrayOf<URL>(file.toURI().toURL()), ClassLoader.getSystemClassLoader())
    val clazz = cl.loadClass("com.nexomc.nexo.glyphs.Glyph")
    println("Methods:")
    clazz.declaredMethods.forEach { println(" - " + it.name + "() returning " + it.returnType.simpleName) }
    println("Fields:")
    clazz.declaredFields.forEach { println(" - " + it.name + " : " + it.type.simpleName) }
}
