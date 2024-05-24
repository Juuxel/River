package juuxel.river

import org.jetbrains.kotlin.idea.KotlinFileType

fun main() {
    val ctx = Context()
    val file = ctx.psiFileFactory.createFileFromText("Test.kt", KotlinFileType.INSTANCE, "expect fun foo(): Int")
    ExpectPlatformRewriter.modify(file, ctx)
    println(file.text)
}
