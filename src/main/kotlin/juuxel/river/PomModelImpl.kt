package juuxel.river

import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
import org.jetbrains.kotlin.com.intellij.pom.PomModel
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect
import org.jetbrains.kotlin.com.intellij.pom.PomTransaction
import org.jetbrains.kotlin.com.intellij.pom.tree.TreeAspect

class PomModelImpl : UserDataHolderBase(), PomModel {
    @Suppress("UNCHECKED_CAST")
    override fun <T : PomModelAspect> getModelAspect(type: Class<T>): T? {
        if (type === TreeAspect::class.java) {
            return TreeAspect() as T
        }

        return null
    }

    override fun runTransaction(transaction: PomTransaction) {
        transaction.run()
    }
}
