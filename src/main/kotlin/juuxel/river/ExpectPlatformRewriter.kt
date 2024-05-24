package juuxel.river

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtNamedFunction

object ExpectPlatformRewriter : Rewriter {
    override fun modify(element: PsiElement, context: Context) {
        val functions = PsiTreeUtil.findChildrenOfType(element, KtNamedFunction::class.java)
        for (fn in functions) {
            val mods = fn.modifierList ?: continue
            mods.getModifier(KtTokens.ACTUAL_KEYWORD)?.delete()

            if (mods.hasModifier(KtTokens.EXPECT_KEYWORD)) {
                val expect = mods.getModifier(KtTokens.EXPECT_KEYWORD)!!
                val annotation = context.ktPsiFactory.createAnnotationEntry(
                    "@dev.architectury.injectables.annotations.ExpectPlatform"
                )
                expect.replace(annotation)
                val body = context.ktPsiFactory.createBlock("throw kotlin.AssertionError()")
                fn.addAfter(body, fn.lastChild)
            }
        }
    }
}
