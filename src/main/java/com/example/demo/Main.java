package com.example.demo;

import javafx.scene.paint.Color;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.paint.Color;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. CONFIGURAÇÃO DA SPLASH

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/splash-screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/Style/estilo.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/icone_1.png")));

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();


        primaryStage.setX((bounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((bounds.getHeight() - primaryStage.getHeight()) / 2);

        // 2. O AGENDAMENTO DA TROCA

        PauseTransition delay = new PauseTransition(Duration.seconds(25));

        delay.setOnFinished(event -> {
            primaryStage.close();
            abrirLogin();
        });
        delay.play();

        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/demo/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Carregando o CSS da pasta Style
        scene.getStylesheets().add(getClass().getResource("/Style/estilo.css").toExternalForm());
        // Carrega a imagem da pasta de resources
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Logo-Of.png")));

        //stage.setTitle("Login Futurista");

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();

        stage.show();
        */
    }

    public void abrirLogin() {
        try {
            Stage loginStage = new Stage();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/demo/login.fxml")
            );
            Parent root = loader.load();

            // Estado inicial
            root.setOpacity(0);
            root.setScaleX(0.93);
            root.setScaleY(0.93);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            loginStage.initStyle(StageStyle.TRANSPARENT);
            loginStage.setScene(scene);

            scene.getStylesheets().add(
                    getClass().getResource("/Style/estilo.css").toExternalForm()
            );

            loginStage.getIcons().add(
                    new Image(getClass().getResourceAsStream("/Images/icone_1.png"))
            );

            loginStage.show();
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();


            loginStage.setX((bounds.getWidth() - loginStage.getWidth()) / 2);
            loginStage.setY((bounds.getHeight() - loginStage.getHeight()) / 2);

            //Fade elegante
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.4), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setInterpolator(Interpolator.EASE_OUT);

            //Zoom in
            ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(2.2), root);
            zoomIn.setFromX(0.96);
            zoomIn.setFromY(0.96);
            zoomIn.setToX(1);
            zoomIn.setToY(1);
            zoomIn.setInterpolator(Interpolator.EASE_BOTH);

            //Executa juntos
            ParallelTransition transition =
                    new ParallelTransition(fadeIn, zoomIn);
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    public static void main(String[] args) {
        launch();
    }
}