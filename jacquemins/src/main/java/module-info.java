module org.helmo {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.sql;
    opens org.helmo.gbeditor to com.google.gson;
    exports org.helmo.gbeditor;
}