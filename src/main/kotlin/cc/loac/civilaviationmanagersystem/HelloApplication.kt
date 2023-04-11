package cc.loac.civilaviationmanagersystem

import cc.loac.civilaviationmanagersystem.common.Tool
import cc.loac.civilaviationmanagersystem.myenum.OS
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.kordamp.bootstrapfx.BootstrapFX

class HelloApplication : Application() {
    companion object {
        val os = Tool.getOSName()
    }

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("home-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 800.0, 700.0)
        scene.stylesheets.add(BootstrapFX.bootstrapFXStylesheet())
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

/**
 * 初始化
 */
private fun init() {

}

fun main() {
    init()
    Application.launch(HelloApplication::class.java)
}
