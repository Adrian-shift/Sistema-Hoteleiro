module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop; // Adicione esta linha por segurança
    requires javafx.media;

    // Abre os pacotes para o JavaFX conseguir ler suas classes e arquivos
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;

    exports com.example.demo;
    exports com.example.demo.controller;
}