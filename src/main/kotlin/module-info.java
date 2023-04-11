module cc.loac.civilaviationmanagersystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires ini4j;

    opens cc.loac.civilaviationmanagersystem to javafx.fxml;
    opens cc.loac.civilaviationmanagersystem.controller to javafx.fxml;
    exports cc.loac.civilaviationmanagersystem;
}