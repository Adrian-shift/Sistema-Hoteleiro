package com.example.demo.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.paint.Color;


public class GerenciadorDeTelas {
    public static void trocarCena(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(GerenciadorDeTelas.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene cena = new Scene(root);
            cena.setFill(Color.BLACK);

            cena.getStylesheets().add(GerenciadorDeTelas.class.getResource("/Style/estilo.css").toExternalForm());

            // --- INÍCIO DA ANIMAÇÃO ---
            // Define a opacidade inicial como 0 (invisível)
            root.setOpacity(0);

            // Cria a transição de esmaecer (Fade)
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.millis(1500)); // Duração: 800 milissegundos
            fade.setNode(root);                     // O que vai animar (a nova tela)
            fade.setFromValue(0);                   // Começa em 0% de opacidade
            fade.setToValue(1);                     // Vai até 100% de opacidade

            stage.setScene(cena);

            /*
            if(fxmlPath == "/com/example/demo/principal.fxml"){
                stage.setX(0);
                stage.setY(0);
                stage.setMaximized(true);
                stage.setResizable(true);
            }else{
                stage.setResizable(false);
                stage.centerOnScreen();
            }
            */
            stage.show();

            // Inicia a animação logo após mostrar a cena
            fade.play();
            // --- FIM DA ANIMAÇÃO ---

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}