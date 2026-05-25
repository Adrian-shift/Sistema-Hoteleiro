package com.example.demo.controller;

import com.example.demo.dao.BancoDeDados;
import com.example.demo.model.Usuario;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroController {

    // --- VINCULANDO OS CAMPOS DO FXML ---
    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private PasswordField campoConfirmarSenha;

    // --- VINCULANDO OS LABELS DE ERRO ---
    @FXML private Label mensCadNome;
    @FXML private Label mensCadEmail;
    @FXML private Label mensCadSenha;
    @FXML private Label mensCadSenhaConf;
    @FXML private Label sucessoConta; // Label de sucesso

    @FXML private Button btnCriar, btnVoltar;

    @FXML
    public void initialize() {
        limparMensagens();
    }

    @FXML
    private void voltarParaLogin(ActionEvent event) {
        GerenciadorDeTelas.trocarCena(event, "/com/example/demo/login.fxml");
    }

    @FXML
    private void confirmarCadastro(ActionEvent event) {
        // 1. Limpa erros anteriores
        limparMensagens();

        boolean temErro = false;

        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();
        String confSenha = campoConfirmarSenha.getText();

        // --- VALIDAÇÃO DE NOME ---
        if (nome == null || nome.trim().isEmpty()) {
            mensCadNome.setText("O nome é obrigatório.");
            temErro = true;
        }

        // --- VALIDAÇÃO DE EMAIL ---
        if (email == null || email.trim().isEmpty()) {
            mensCadEmail.setText("O e-mail é obrigatório.");
            temErro = true;
        } else if (!emailValido(email)) {
            mensCadEmail.setText("Formato de e-mail inválido.");
            temErro = true;
        }

        // --- VALIDAÇÃO DE SENHA ---
        if (senha == null || senha.isEmpty()) {
            mensCadSenha.setText("Crie uma senha.");
            temErro = true;
        } else if (senha.length() < 6) {
            mensCadSenha.setText("A senha deve ter no mínimo 6 caracteres.");
            temErro = true;
        }

        // --- VALIDAÇÃO DE CONFIRMAÇÃO DE SENHA ---
        if (confSenha == null || confSenha.isEmpty()) {
            mensCadSenhaConf.setText("Confirme sua senha.");
            temErro = true;
        } else if (!senha.equals(confSenha)) {
            mensCadSenhaConf.setText("As senhas não coincidem.");
            temErro = true;
        }

        // SE HOUVER ERRO, PARA AQUI
        if (temErro) {
            return;
        }
        /*
        // --- VERIFICAÇÃO EXTRA: O email já existe? ---
        if (BancoDeDados.emailJaCadastrado(email)) {
            mensCadEmail.setText("Este e-mail já está em uso.");
            return;
        }

         */

        // --- SALVAR NO BANCO FICTÍCIO ---
        Usuario novoUsuario = new Usuario(nome, email, senha);
        BancoDeDados.adicionarUsuario(novoUsuario);

        // --- SUCESSO ---
        // Aqui você chamaria o banco de dados: UsuarioDAO.salvar(novoUsuario);

        System.out.println("Cadastro Validado! Salvando: " + nome);

        // Exibe mensagem de sucesso
        sucessoConta.setVisible(true);

        // Desabilita o botão para evitar cliques duplos
        btnCriar.setDisable(true);
        btnVoltar.setDisable(true);

        // Aguarda 2 segundos mostrando a mensagem de sucesso e troca para o Login
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            GerenciadorDeTelas.trocarCena(event, "/com/example/demo/login.fxml");
        });
        pause.play();
    }

    // Método auxiliar para limpar textos de erro
    private void limparMensagens() {
        mensCadNome.setText("");
        mensCadEmail.setText("");
        mensCadSenha.setText("");
        mensCadSenhaConf.setText("");
        sucessoConta.setVisible(false);
    }

    // Método auxiliar para verificar formato de email (Regex padrão)
    private boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}