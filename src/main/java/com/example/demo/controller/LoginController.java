package com.example.demo.controller;

import com.example.demo.Main; // Se não usar, pode remover
import com.example.demo.dao.BancoDeDados;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Importante: Importar Label
import javafx.scene.control.PasswordField; // Importante: Importar PasswordField
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha; // Alterado de TextField para PasswordField

    @FXML
    private Label mensErroEmail; // Novo: Para mostrar erro de email

    @FXML
    private Label mensErroSenha; // Novo: Para mostrar erro de senha

    @FXML
    private ImageView iconeRoda;

    @FXML
    private Button btnFechar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Animação do ícone
        RotateTransition rt = new RotateTransition(Duration.seconds(10), iconeRoda);
        rt.setByAngle(360);
        rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        // Limpar mensagens de erro ao iniciar
        limparMensagensErro();
    }

    @FXML
    private void irParaCadastro(ActionEvent event) {

        GerenciadorDeTelas.trocarCena(event, "/com/example/demo/cadastro.fxml");
    }

    @FXML
    private void entrar(ActionEvent event) {
        // 1. Limpa erros anteriores
        limparMensagensErro();

        String email = campoUsuario.getText();
        String senha = campoSenha.getText();
        boolean temErro = false;

        // 2. Validação de Campos Vazios
        if (email == null || email.trim().isEmpty()) {
            mensErroEmail.setText("O e-mail é obrigatório.");
            temErro = true;
        }

        if (senha == null || senha.isEmpty()) {
            mensErroSenha.setText("A senha é obrigatória.");
            temErro = true;
        }

        if (temErro) {
            return; // Para a execução se houver campos vazios
        }

        // 3. Validação de Credenciais (Simulação de Banco de Dados)
        if (validarLoginNoBanco(email, senha)) {
            abrirTelaPrincipal(event);
        } else {
            mensErroSenha.setText("E-mail ou senha incorretos.");
            // Opcional: Limpar a senha se errar
            campoSenha.setText("");
        }
    }

    // Método simulado - AQUI VOCÊ CONECTARÁ SEU DAO/BANCO DE DADOS
    private boolean validarLoginNoBanco(String email, String senha) {
        // Chama nosso banco fictício para verificar
        return BancoDeDados.validarLogin(email, senha);
    }

    private void abrirTelaPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/demo/principal.fxml")
            );

            Scene scene = new Scene(loader.load());

            // Verificação de segurança caso o CSS não exista
            URL cssUrl = getClass().getResource("/Style/estilo.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            Stage stagePrincipal = new Stage();
            stagePrincipal.setTitle("Sistema de Gerenciamento de Hotel");

            // Verificação do ícone
            if (getClass().getResourceAsStream("/Images/icone_1.png") != null) {
                stagePrincipal.getIcons().add(new Image(getClass().getResourceAsStream("/Images/icone_1.png")));
            }

            stagePrincipal.setScene(scene);
            stagePrincipal.setMaximized(true);
            stagePrincipal.initStyle(StageStyle.UNDECORATED);
            stagePrincipal.show();

            // fecha o login
            Stage stageLogin = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageLogin.close();

        } catch (IOException e) {
            e.printStackTrace();
            mensErroSenha.setText("Erro ao carregar sistema.");
        }
    }

    private void limparMensagensErro() {
        if (mensErroEmail != null) mensErroEmail.setText("");
        if (mensErroSenha != null) mensErroSenha.setText("");
    }

    @FXML
    private void fecharAplicacao() {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }
}