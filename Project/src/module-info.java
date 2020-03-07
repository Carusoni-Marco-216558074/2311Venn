module Example {
    requires javafx.fxml;
    requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	requires jdk.scripting.nashorn;

    opens application;
} 