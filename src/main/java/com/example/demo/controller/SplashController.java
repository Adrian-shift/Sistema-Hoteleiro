package com.example.demo.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class SplashController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label lblPercentual;

    @FXML
    private Label lblStatus;

    private double progress = 0.0;
    private String statusAtual = "";

    @FXML
    public void initialize() {
        iniciarProgressoFalso();
    }

    private void iniciarProgressoFalso() {

        Timeline timeline = new Timeline();

        KeyFrame frame = new KeyFrame(
                Duration.millis(220),
                event -> {
                    progress += Math.random() * 0.015;

                    if (progress >= 1.0) {
                        progress = 1.0;
                        atualizarUI();
                        timeline.stop();
                        abrirTelaPrincipal();
                        return;
                    }

                    atualizarUI();
                }
        );

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void atualizarUI() {
        progressBar.setProgress(progress);

        int percentual = (int) (progress * 100);
        lblPercentual.setText(percentual + "%");

        String novoStatus = obterStatusLuxo(progress);

        if (!novoStatus.equals(statusAtual)) {
            trocarStatusComFade(novoStatus);
            statusAtual = novoStatus;
        }
    }

    private void trocarStatusComFade(String novoTexto) {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), lblStatus);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            lblStatus.setText(novoTexto);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), lblStatus);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    private String obterStatusLuxo(double progresso) {

        if (progresso < 0.15) {
            return "Preparando sua experiência exclusiva…";
        } else if (progresso < 0.35) {
            return "Carregando ambientes personalizados…";
        } else if (progresso < 0.55) {
            return "Ajustando detalhes de conforto e elegância…";
        } else if (progresso < 0.75) {
            return "Sincronizando serviços premium…";
        } else if (progresso < 0.95) {
            return "Finalizando os toques de excelência…";
        } else {
            return "Bem-vindo ao Hotel Luxo Master";
        }
    }

    private void abrirTelaPrincipal() {
        System.out.println("Splash finalizado — abrir tela principal");
        // troca de cena aqui
    }
}