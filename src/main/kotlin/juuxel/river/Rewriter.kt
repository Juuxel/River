package juuxel.river

import org.jetbrains.kotlin.com.intellij.psi.PsiElement

fun interface Rewriter {
    fun modify(element: PsiElement, context: Context)
}
