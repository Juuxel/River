package juuxel.river

import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.pom.PomModel
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeCopyHandler
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtPsiFactory

class Context {
    val project = run {
        val disposable = Disposer.newDisposable()
        val compilerConfiguration = CompilerConfiguration()
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        try {
            val project = KotlinCoreEnvironment.createForProduction(
                disposable,
                compilerConfiguration,
                EnvironmentConfigFiles.JVM_CONFIG_FILES
            ).projectEnvironment.project

            project.registerService(PomModel::class.java, PomModelImpl())

            for (area in arrayOf(project.extensionArea, Extensions.getRootArea())) {
                if (!area.hasExtensionPoint(TreeCopyHandler.EP_NAME)) {
                    area.registerExtensionPoint(
                        TreeCopyHandler.EP_NAME.name,
                        TreeCopyHandler::class.java.name,
                        ExtensionPoint.Kind.INTERFACE
                    )
                }
            }

            project
        } finally {
            disposable.dispose()
        }
    }

    internal val psiFileFactory: PsiFileFactory =
        PsiFileFactory.getInstance(project)
    val ktPsiFactory = KtPsiFactory(project)
}
