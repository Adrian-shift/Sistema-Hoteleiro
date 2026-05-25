package com.example.demo.controller;

import com.example.demo.dao.BancoDeDados;
import com.example.demo.model.ReservaTwo;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.demo.model.Reserva;

public class PricipalController {

    public void voltarLoguin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/demo/login.fxml")
            );

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(
                    getClass().getResource("/Style/estilo.css").toExternalForm()
            );

            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/icone_1.png")));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            // fecha o login
            Stage stagePrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stagePrincipal.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void configurarModoLeitura() {
        campoNome2.setEditable(false);
        campoCpf2.setEditable(false);
        campoEmail2.setEditable(false);
        campoTelefone2.setEditable(false);

        boxNumQuarto2.setMouseTransparent(true);
        boxFormaPag2.setMouseTransparent(true);
        boxStatusPag2.setMouseTransparent(true);
        dataCheckIn2.setMouseTransparent(true);
        dataCheckOut2.setMouseTransparent(true);

        boxNumQuarto2.setFocusTraversable(false);
        boxFormaPag2.setFocusTraversable(false);
        boxStatusPag2.setFocusTraversable(false);
        dataCheckIn2.setFocusTraversable(false);
        dataCheckOut2.setFocusTraversable(false);


    }








    //CONFIGURAÇÃO DE MÁSCARAS E RESTRIÇÕES
    private void configurarMascaras() {

        campoNome.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (!valorNovo.matches("[a-zA-Z\\sçÇãÃõÕáÁéÉíÍóÓúÚ]*")) {
                campoNome.setText(valorAntigo);
            }
        });


        campoCpf.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
            String apenasDigitos = valorNovo.replaceAll("[^0-9]", "");

            if (apenasDigitos.length() > 11) apenasDigitos = apenasDigitos.substring(0, 11);

            String formatado = "";
            if (apenasDigitos.length() <= 3) {
                formatado = apenasDigitos;
            } else if (apenasDigitos.length() <= 6) {
                formatado = apenasDigitos.substring(0, 3) + "." + apenasDigitos.substring(3);
            } else if (apenasDigitos.length() <= 9) {
                formatado = apenasDigitos.substring(0, 3) + "." + apenasDigitos.substring(3, 6) + "." + apenasDigitos.substring(6);
            } else {
                formatado = apenasDigitos.substring(0, 3) + "." + apenasDigitos.substring(3, 6) + "." + apenasDigitos.substring(6, 9) + "-" + apenasDigitos.substring(9);
            }

            if (!valorNovo.equals(formatado)) {
                campoCpf.setText(formatado);
                campoCpf.positionCaret(formatado.length());
            }
        });

        campoTelefone.textProperty().addListener((obs, valorAntigo, valorNovo) -> {
            String limpo = valorNovo.replaceAll("[^0-9]", "");

            if (limpo.length() > 11) limpo = limpo.substring(0, 11);

            String formatado = "";
            if (limpo.length() == 0) {
                formatado = "";
            } else if (limpo.length() <= 2) {
                formatado = "(" + limpo;
            } else if (limpo.length() <= 6) {
                formatado = "(" + limpo.substring(0, 2) + ") " + limpo.substring(2);
            } else if (limpo.length() <= 10) {
                formatado = "(" + limpo.substring(0, 2) + ") " + limpo.substring(2, 6) + "-" + limpo.substring(6);
            } else {
                formatado = "(" + limpo.substring(0, 2) + ") " + limpo.substring(2, 7) + "-" + limpo.substring(7);
            }

            if (!valorNovo.equals(formatado)) {
                campoTelefone.setText(formatado);
                campoTelefone.positionCaret(formatado.length());
            }
        });
    }

    private boolean validarEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }




    @FXML private StackPane overlayDetalhes2;
    @FXML private TextField campoNome2, campoCpf2, campoEmail2, campoTelefone2;
    @FXML private ComboBox<String> boxNumQuarto2, boxFormaPag2, boxStatusPag2;
    @FXML private DatePicker dataCheckIn2, dataCheckOut2;
    @FXML private Label lblTotal2;
    @FXML private Button btnSair;

    private void abrirDetalhes(ReservaTwo reserva) {
        campoNome2.setText(reserva.getNomeHospede());
        campoCpf2.setText(reserva.getCpf());
        campoEmail2.setText(reserva.getEmail());
        campoTelefone2.setText(reserva.getTelefone());


        boxNumQuarto2.setValue(reserva.getNumeroQuarto());
        boxFormaPag2.setValue(reserva.getFormaPagamento());
        boxStatusPag2.setValue(reserva.getStatusPagamento());

        dataCheckIn2.setValue(reserva.getDataCheckIn());
        dataCheckOut2.setValue(reserva.getDataCheckOut());

        lblTotal2.setText("R$ " + String.format("%.2f", reserva.getValorTotal()));

        overlayDetalhes2.setVisible(true);
        overlayDetalhes2.setManaged(true);

        overlayDetalhes2.toFront();
    }

    private void fecharDetalhes() {
        overlayDetalhes2.setVisible(false);
        overlayDetalhes2.setManaged(false);
    }



    @FXML private TextField campoNome;
    @FXML private TextField campoCpf;
    @FXML private TextField campoEmail;
    @FXML private TextField campoTelefone;

    @FXML private ComboBox<String> boxNumQuarto;

    @FXML private DatePicker dataCheckIn;
    @FXML private DatePicker dataCheckOut;

    @FXML private ComboBox<String> boxFormaPag;
    @FXML private ComboBox<String> boxStatusPag;

    @FXML private Label lblTotal;

    private final double VALOR_DIARIA = 250.00;


    @FXML
    private void abrirNovaReserva() {
        // Limpa os campos
        campoNome.clear();
        campoCpf.clear();
        campoEmail.clear();
        campoTelefone.clear();
        lblTotal.setText("R$ 0,00");

        // Carrega quartos livres
        boxNumQuarto.getItems().clear();
        boxNumQuarto.getItems().addAll(BancoDeDados.getQuartosDisponiveis());

        overlayNovaReserva.setVisible(true);
        overlayNovaReserva.setManaged(true);
    }

    private void calcularTotal() {
        if (dataCheckIn.getValue() != null && dataCheckOut.getValue() != null) {
            LocalDate entrada = dataCheckIn.getValue();
            LocalDate saida = dataCheckOut.getValue();

            // Calcula a diferença de dias
            long dias = ChronoUnit.DAYS.between(entrada, saida);

            if (dias > 0) {
                double total = dias * VALOR_DIARIA;
                lblTotal.setText(String.format("R$ %.2f", total)); // Formata bonito
            } else {
                lblTotal.setText("Data Inválida");
            }
        }
    }

    @FXML
    private void salvarNovaReserva() {
        // 1. Validação Básica
        if (campoNome.getText().isEmpty() || boxNumQuarto.getValue() == null ||
                dataCheckIn.getValue() == null || dataCheckOut.getValue() == null) {

            System.out.println("Erro: Preencha os campos obrigatórios!");
            return;
        }
        //Validação
        String erro = "";

        if (campoNome.getText().trim().isEmpty())
            erro += "- Nome é obrigatório\n";

        if (campoCpf.getText().length() < 14)
            erro += "- CPF incompleto\n";

        if (campoTelefone.getText().length() < 14)
            erro += "- Telefone incompleto\n";

        if (!validarEmail(campoEmail.getText()))
            erro += "- Email inválido\n";

        if (boxNumQuarto.getValue() == null)
            erro += "- Selecione um quarto\n";

        if (dataCheckIn.getValue() == null || dataCheckOut.getValue() == null)
            erro += "- Selecione as datas\n";

        // SE TIVER ERRO, PARA TUDO E AVISA
        if (!erro.isEmpty()) {
            System.out.println("ERRO DE VALIDAÇÃO:\n" + erro);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Inválidos");
            alert.setHeaderText("Por favor, corrija os erros:");
            alert.setContentText(erro);
            alert.showAndWait();
            return;
        }

        // 2. Pegando os dados da tela
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String email = campoEmail.getText();
        String tel = campoTelefone.getText();
        String quarto = boxNumQuarto.getValue();
        LocalDate checkIn = dataCheckIn.getValue();
        LocalDate checkOut = dataCheckOut.getValue();
        String formaPag = boxFormaPag.getValue();
        String statusPag = boxStatusPag.getValue();

        // Recalcula o total só pra garantir
        long dias = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (dias <= 0) dias = 1; // Mínimo 1 dia
        double total = dias * VALOR_DIARIA;

        //Criando o Objeto Reserva
        ReservaTwo novaReserva = new ReservaTwo(nome, cpf, email, tel, quarto, checkIn, checkOut, formaPag, statusPag, total);

        //salvando no Banco
        BancoDeDados.salvarReserva(novaReserva);

        //Fecha o popup e atualiza a tela se necessário
        fecharNovaReserva();
        System.out.println("Reserva criada com sucesso! Total: " + total);
        exibirReservas();
    }




    @FXML
    private Button botaoFechar;

    @FXML
    private Button botaoMin;

    @FXML
    private Button botaoMax;

    @FXML
    private void minimizar(){
        Stage stage = (Stage) botaoMin.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void maximizar(){
        Stage stage = (Stage) botaoMax.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }





    @FXML
    private void fecharAplicacao() {
        Stage stage = (Stage) botaoFechar.getScene().getWindow();
        stage.close(); // fecha só a janela
    }


    @FXML
    private StackPane overlayNovaReserva;


    @FXML
    private void fecharNovaReserva() {
        overlayNovaReserva.setVisible(false);
        overlayNovaReserva.setManaged(false);
    }











    @FXML
    private StackPane overlayConfirmar;
    @FXML
    private void abrirConfirmar() {
        overlayConfirmar.setVisible(true);
        overlayConfirmar.setManaged(true);
    }

    @FXML
    private void fecharConfirmar() {
        overlayConfirmar.setVisible(false);
        overlayConfirmar.setManaged(false);
    }




    @FXML
    private Label numLivre = new Label();
    @FXML
    private Label numOcupado;
    @FXML
    private Label numLimpeza;
    @FXML
    private Label numManu;


    @FXML
    private ImageView iconeRoda;


    @FXML
    private PieChart pieOcupacao;

    @FXML
    private Label lblPercentual;

    @FXML private Label lblOcupados;
    @FXML private Label lblLivres;
    @FXML private Label lblManutencao;

    public void initialize() {

        int ocupados = 5;
        int livres = 43;
        int manutencao = 0;

        numLivre.setText(String.valueOf(livres));
        numManu.setText(String.valueOf(manutencao));
        numOcupado.setText(String.valueOf(ocupados));

        //Limpa os itens antigos
        gridReservas.getChildren().clear();

        gridReservas.getRowConstraints().clear();

        lblOcupados.setText(String.valueOf(ocupados));
        lblLivres.setText(String.valueOf(livres));
        lblManutencao.setText(String.valueOf(manutencao));

        int total = ocupados + livres + manutencao;
        double percentual = (ocupados * 100.0) / total;
        lblPercentual.setText(String.format("%.0f%%", percentual));

        PieChart.Data d3 = new PieChart.Data("Ocupados", ocupados);
        PieChart.Data d1 = new PieChart.Data("Livres", livres);
        PieChart.Data d2 = new PieChart.Data("Manutenção", manutencao);

        pieOcupacao.getData().addAll(d1, d2, d3);

        Platform.runLater(() -> {
            d1.getNode().setStyle("-fx-pie-color: #a8913d");
            d2.getNode().setStyle("-fx-pie-color: #2a2a2a;");
            d3.getNode().setStyle("-fx-pie-color: #cf8a6c;");
        });

        RotateTransition rt = new RotateTransition(Duration.seconds(10), iconeRoda);
        rt.setByAngle(360);
        rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        txtPesquisa.textProperty().addListener((observable, valorAntigo, valorNovo) -> {
            filtrarResultados(valorNovo); // Chama a função de pesquisa a cada letra digitada
        });


        boxFormaPag2.getItems().addAll("Dinheiro", "Pix", "Cartão Crédito", "Cartão Débito");
        boxStatusPag2.getItems().addAll("Pendente", "Confirmado", "Pago");

        boxNumQuarto2.getItems().addAll(BancoDeDados.getTodosQuartos());

        btnSair.setOnAction(e -> fecharDetalhes());

        //sem click em telhes
        configurarModoLeitura();

        vboxResultados.setSpacing(10);
        carregarListaQuartos();
        carregarCheckins("TODOS");

        boxFormaPag.getItems().addAll("Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Pix");
        boxStatusPag.getItems().addAll("Pendente", "Confirmado", "Pago");

        //Adicionar "Ouvintes" nas datas para calcular o total automaticamente
        dataCheckIn.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotal());
        dataCheckOut.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotal());

        if (dataCheckIn1 != null && dataCheckOut1 != null) {
            dataCheckIn1.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotalEdicao());
            dataCheckOut1.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotalEdicao());
        }
        exibirReservas();

        configurarMascaras();
    }










    @FXML
    private GridPane gridReservas;


    public void exibirReservas() {
        System.out.println("--- INICIANDO EXIBIÇÃO DE RESERVAS ---");

        //Verifica se o Grid existe
        if (gridReservas == null) {
            System.out.println("ERRO CRÍTICO: gridReservas está NULL! Verifique o fx:id no FXML.");
            return;
        }
        System.out.println("Grid encontrado com sucesso.");

        //Limpa o grid
        gridReservas.getChildren().clear();

        //Pega a lista do banco
        List<ReservaTwo> lista = BancoDeDados.getReservas();
        System.out.println("Tamanho da lista no banco: " + lista.size());

        if (lista.isEmpty()) {
            System.out.println("AVISO: Nenhuma reserva encontrada no banco para exibir.");
        }

        int coluna = 0;
        int linha = 0;

        for (ReservaTwo reserva : lista) {
            System.out.println("Criando card para: " + reserva.getNomeHospede());

            VBox card = criarCardReserva(reserva);

            // Verifica se o card foi criado
            if (card == null) {
                System.out.println("ERRO: O método criarCardReserva retornou null.");
                continue;
            }

            gridReservas.add(card, coluna, linha);
            coluna++;
            if (coluna == 2) {
                coluna = 0;
                linha++;
            }
        }
        System.out.println("--- FIM DA EXIBIÇÃO ---");
    }

    private VBox criarCardReserva(ReservaTwo reserva) {
        VBox card = new VBox();
        card.getStyleClass().add("luxo-pane");
        card.setSpacing(15);
        card.setPadding(new Insets(20));
        card.setPrefWidth(400);

        //CABEÇALHO
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(15);

        // Ícone
        Label lblIcone = new Label("👤");
        lblIcone.getStyleClass().add("icone-luxo");

        // Nome e Telefone
        VBox infoUsuario = new VBox();
        infoUsuario.setSpacing(2);

        Label lblNome = new Label(reserva.getNomeHospede());
        lblNome.getStyleClass().add("txt-nome-luxo");

        String telefoneTexto = (reserva.getTelefone() == null || reserva.getTelefone().isEmpty())
                ? "Sem telefone" : "📞 " + reserva.getTelefone();
        Label lblTel = new Label(telefoneTexto);
        lblTel.getStyleClass().add("txt-telefone-luxo");

        infoUsuario.getChildren().addAll(lblNome, lblTel);

        // Espaçador
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String status = reserva.getStatusPagamento();
        Label lblStatus = new Label(status != null ? status : "Pendente");
        lblStatus.getStyleClass().add("badge-status-luxo");

        if ("Pago".equalsIgnoreCase(status) || "Confirmado".equalsIgnoreCase(status)) {
            lblStatus.setStyle("-fx-text-fill: #2ecc71; -fx-border-color: #2ecc71;");
        } else {
            lblStatus.setStyle("-fx-text-fill: #f1c40f; -fx-border-color: #f1c40f;");
        }

        header.getChildren().addAll(lblIcone, infoUsuario, spacer, lblStatus);


        HBox faixaDados = new HBox();
        faixaDados.getStyleClass().add("faixa-dados-luxo");
        faixaDados.setSpacing(40);
        faixaDados.setPadding(new Insets(15));
        faixaDados.setAlignment(Pos.CENTER_LEFT);

        // Formatador de data
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");
        String dataIn = reserva.getDataCheckIn() != null ? reserva.getDataCheckIn().format(fmt) : "--/--";
        String dataOut = reserva.getDataCheckOut() != null ? reserva.getDataCheckOut().format(fmt) : "--/--";

        // Colunas preenchidas com getters da reserva
        VBox colQuarto = criarColunaDado("QUARTO", reserva.getNumeroQuarto());
        VBox colCheckIn = criarColunaDado("CHECK-IN", dataIn);
        VBox colCheckOut = criarColunaDado("CHECK-OUT", dataOut);

        faixaDados.getChildren().addAll(colQuarto, colCheckIn, colCheckOut);


        HBox boxBotoes = new HBox();
        boxBotoes.setSpacing(10);

        Button btnEditar = new Button("Editar");
        btnEditar.getStyleClass().add("btn-luxo-outline");
        btnEditar.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnEditar, Priority.ALWAYS);


        btnEditar.setOnAction(e -> System.out.println("Editar: " + reserva.getNomeHospede()));
        btnEditar.setOnAction(event -> {
            abrirEditarReserva(reserva); // Passa a reserva do card para o popup
        });

        Button btnDetalhes = new Button("Ver Detalhes");
        btnDetalhes.getStyleClass().add("btn-luxo-filled");
        btnDetalhes.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnDetalhes, Priority.ALWAYS);

        btnDetalhes.setOnAction(event -> {
            abrirDetalhes(reserva);
        });


        boxBotoes.getChildren().addAll(btnEditar, btnDetalhes);

        card.getChildren().addAll(header, faixaDados, boxBotoes);

        return card;
    }



    private VBox criarColunaDado(String titulo, String valor) {
        VBox v = new VBox();
        v.setSpacing(3);

        Label lblTit = new Label(titulo);

        lblTit.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 10px; -fx-font-weight: bold;");

        Label lblVal = new Label(valor);

        lblVal.setStyle("-fx-text-fill: #cda434; -fx-font-weight: bold; -fx-font-size: 14px;");

        v.getChildren().addAll(lblTit, lblVal);
        return v;
    }









    private ReservaTwo reservaEmEdicao;


    @FXML private StackPane overlayEditarReserva;
    @FXML private TextField campoNome1;
    @FXML private TextField campoCpf1;
    @FXML private TextField campoEmail1;
    @FXML private TextField campoTelefone1;
    @FXML private ComboBox<String> boxNumQuarto1;
    @FXML private DatePicker dataCheckIn1;
    @FXML private DatePicker dataCheckOut1;
    @FXML private ComboBox<String> boxFormaPag1;
    @FXML private ComboBox<String> boxStatusPag1;
    @FXML private Label lblTotal1;


    private void abrirEditarReserva(ReservaTwo reserva) {
        this.reservaEmEdicao = reserva;


        campoNome1.setText(reserva.getNomeHospede());
        campoCpf1.setText(reserva.getCpf());
        campoEmail1.setText(reserva.getEmail());
        campoTelefone1.setText(reserva.getTelefone());
        dataCheckIn1.setValue(reserva.getDataCheckIn());
        dataCheckOut1.setValue(reserva.getDataCheckOut());
        lblTotal1.setText(String.format("R$ %.2f", reserva.getValorTotal()));


        boxNumQuarto1.getItems().clear();
        List<String> quartos = BancoDeDados.getQuartosDisponiveis();
        if (!quartos.contains(reserva.getNumeroQuarto())) {
            quartos.add(0, reserva.getNumeroQuarto());
        }
        boxNumQuarto1.getItems().addAll(quartos);
        boxNumQuarto1.setValue(reserva.getNumeroQuarto());

        // 3. Pagamentos
        boxFormaPag1.getItems().setAll("Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Pix");
        boxFormaPag1.setValue(reserva.getFormaPagamento());

        boxStatusPag1.getItems().setAll("Pendente", "Confirmado", "Pago");
        boxStatusPag1.setValue(reserva.getStatusPagamento());


        overlayEditarReserva.setVisible(true);
        overlayEditarReserva.setManaged(true);
    }



    @FXML
    private void salvarEdicao() {
        if (reservaEmEdicao == null) return;

        System.out.println("Atualizando reserva de: " + reservaEmEdicao.getNomeHospede());

        // 1. Verifica se mudou de quarto para liberar o antigo
        String quartoAntigo = reservaEmEdicao.getNumeroQuarto();
        String novoQuarto = boxNumQuarto1.getValue();

        if (!quartoAntigo.equals(novoQuarto)) {
            BancoDeDados.desocuparQuarto(quartoAntigo);
            BancoDeDados.ocuparQuarto(novoQuarto);
        }


        reservaEmEdicao.setNomeHospede(campoNome1.getText());
        reservaEmEdicao.setCpf(campoCpf1.getText());
        reservaEmEdicao.setEmail(campoEmail1.getText());
        reservaEmEdicao.setTelefone(campoTelefone1.getText());
        reservaEmEdicao.setNumeroQuarto(novoQuarto);
        reservaEmEdicao.setDataCheckIn(dataCheckIn1.getValue());
        reservaEmEdicao.setDataCheckOut(dataCheckOut1.getValue());
        reservaEmEdicao.setFormaPagamento(boxFormaPag1.getValue());
        reservaEmEdicao.setStatusPagamento(boxStatusPag1.getValue());


        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataCheckIn1.getValue(), dataCheckOut1.getValue());
        if (dias <= 0) dias = 1;
        reservaEmEdicao.setValorTotal(dias * 250.00);

        exibirReservas();
        fecharEditar();
    }

    @FXML
    private void apagarReserva() {
        if (reservaEmEdicao != null) {
            BancoDeDados.removerReserva(reservaEmEdicao);
            exibirReservas(); // Atualiza a tela
            fecharEditar();   // Fecha o popup
            System.out.println("Reserva removida!");
        }
    }

    @FXML
    private void fecharEditar() {
        overlayEditarReserva.setVisible(false);
        overlayEditarReserva.setManaged(false);
        reservaEmEdicao = null;
    }

    private void calcularTotalEdicao() {
        if (dataCheckIn1.getValue() != null && dataCheckOut1.getValue() != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(dataCheckIn1.getValue(), dataCheckOut1.getValue());
            if (dias > 0) {
                lblTotal1.setText(String.format("R$ %.2f", dias * 250.00));
            }
        }
    }












    @FXML private FlowPane containerQuartos;
    private void carregarListaQuartos() {
        // 1. Limpa o container visual (se ele existir)
        if (containerQuartos != null) {
            containerQuartos.getChildren().clear();
        }


        List<ReservaTwo> reservasAtuais = BancoDeDados.getReservas();
        List<String> todosOsQuartos = BancoDeDados.getTodosQuartos();


        int contOcupados = 0;
        int contLivres = 0;
        int contManutencao = 0;


        for (String numeroQuarto : todosOsQuartos) {

            String status = "Livre";
            String nomeHospede = "";

            // Verifica se está ocupado
            for (ReservaTwo r : reservasAtuais) {
                if (r.getNumeroQuarto().equals(numeroQuarto)) {
                    status = "Ocupado";
                    nomeHospede = r.getNomeHospede();

                    break;
                }
            }

            if (status.equals("Ocupado")) {
                contOcupados++;
            } else if (status.equals("Manutenção") || status.equals("Limpeza")) {
                contManutencao++;
            } else {
                contLivres++;
            }

            if (containerQuartos != null) {
                containerQuartos.getChildren().add(criarCardQuarto(numeroQuarto, status, nomeHospede));
            }
        }


        if (lblOcupados != null) lblOcupados.setText(String.valueOf(contOcupados));
        if (lblLivres != null) lblLivres.setText(String.valueOf(contLivres));


        atualizarGrafico(contOcupados, contLivres);




        if (numLivre != null) numLivre.setText(String.valueOf(contLivres));
        if (numOcupado != null) numOcupado.setText(String.valueOf(contOcupados));
        if (numManu != null) numManu.setText(String.valueOf(contManutencao));
    }


    private void atualizarGrafico(int ocupados, int livres) {
        if (pieOcupacao != null) {
            pieOcupacao.getData().clear();
            pieOcupacao.getData().add(new PieChart.Data("Ocupados", ocupados));
            pieOcupacao.getData().add(new PieChart.Data("Livres", livres));

        }
    }









    private VBox criarCardQuarto(String numero, String status, String nomeHospede) {

        VBox card = new VBox();
        card.setPrefSize(180, 140);
        card.getStyleClass().add("card-quarto");
        card.setSpacing(5);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.TOP_CENTER);


        String corHex = "#888888";
        String iconeStatus = "?";
        String cssClass = "";

        switch (status.toLowerCase()) {
            case "livre":      corHex = "#2ecc71"; iconeStatus = "✔"; cssClass = "status-livre"; break;
            case "ocupado":    corHex = "#3498db"; iconeStatus = "👤"; cssClass = "status-ocupado"; break;
            case "limpeza":    corHex = "#f1c40f"; iconeStatus = "🧹"; cssClass = "status-limpeza"; break;
            case "manutenção": corHex = "#e74c3c"; iconeStatus = "🛠"; cssClass = "status-manutencao"; break;
        }
        card.getStyleClass().add(cssClass);


        HBox topo = new HBox();
        topo.setAlignment(Pos.CENTER_LEFT);

        Label lblCama = new Label("🛏");
        lblCama.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        StackPane boxIcone = new StackPane(lblCama);
        boxIcone.setPrefSize(35, 35);
        boxIcone.setStyle("-fx-background-color: " + corHex + "; -fx-background-radius: 8;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label lblIconeStatus = new Label(iconeStatus);
        lblIconeStatus.setStyle("-fx-text-fill: " + corHex + "; -fx-font-size: 16px;");

        topo.getChildren().addAll(boxIcone, spacer, lblIconeStatus);


        VBox meio = new VBox(2);
        meio.setAlignment(Pos.CENTER);
        Label lblNumero = new Label(numero);
        lblNumero.getStyleClass().add("txt-numero-quarto");
        Label lblTextoStatus = new Label(status);
        lblTextoStatus.setStyle("-fx-text-fill: " + corHex + "; -fx-font-size: 12px; -fx-font-weight: bold;");
        meio.getChildren().addAll(lblNumero, lblTextoStatus);


        Region spacerBaixo = new Region();
        VBox.setVgrow(spacerBaixo, Priority.ALWAYS);
        Label lblHospede = new Label(nomeHospede.isEmpty() ? "---" : nomeHospede);
        lblHospede.getStyleClass().add("txt-hospede-quarto");

        card.getChildren().addAll(topo, spacerBaixo, meio, lblHospede);

        return card;
    }





















    @FXML private TextField txtPesquisa;
    @FXML private VBox vboxResultados;
    List<ReservaTwo> lista = BancoDeDados.getReservas();



    private void filtrarResultados(String termoPesquisa) {
        vboxResultados.getChildren().clear();

        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            return;
        }

        String termo = termoPesquisa.toLowerCase();

        for (ReservaTwo r : lista) {
            boolean achouNoNome = r.getNomeHospede().toLowerCase().startsWith(termo);
            boolean achouNoQuarto = r.getNumeroQuarto().toLowerCase().startsWith(termo);


            if (achouNoNome || achouNoQuarto) {
                VBox item = criarItemResultado(r, achouNoQuarto);

                vboxResultados.getChildren().add(item);
            }
        }
    }





    private VBox criarItemResultado(ReservaTwo reserva, boolean destaqueQuarto) {
        VBox item = new VBox();
        item.setSpacing(5);
        item.setPadding(new Insets(10));
        item.setAlignment(Pos.CENTER_LEFT);
        item.setMinHeight(60);
        item.getStyleClass().add("item-resultado");


        String textoTitulo;
        String textoDetalhe;

        if (destaqueQuarto) {

            textoTitulo = "Quarto " + reserva.getNumeroQuarto();
            textoDetalhe = "Hóspede: " + reserva.getNomeHospede() + " | Data: " + reserva.getTelefone();
        } else {

            textoTitulo = reserva.getNomeHospede();
            textoDetalhe = "Quarto: " + reserva.getNumeroQuarto() + " | Data: " + reserva.getTelefone();
        }

        Label lblDestaque = new Label(textoTitulo);
        lblDestaque.getStyleClass().add("texto-resultado-titulo");

        Label lblInfo = new Label(textoDetalhe);
        lblInfo.getStyleClass().add("texto-resultado-detalhe");

        item.getChildren().addAll(lblDestaque, lblInfo);

        return item;
    }





    @FXML private VBox containerCheckin;


    private void carregarCheckins(String tipoFiltro) {
        if (containerCheckin == null) return;

        containerCheckin.getChildren().clear();

        List<ReservaTwo> lista = BancoDeDados.getReservas();
        LocalDate hoje = LocalDate.now();


        for (ReservaTwo r : lista) {


            if (r.getDataCheckIn().isEqual(hoje) && (tipoFiltro.equals("TODOS") || tipoFiltro.equals("IN"))) {

                if (!"Hospedado".equals(r.getStatusPagamento())) {
                    containerCheckin.getChildren().add(criarCardMovimentacao(r, "Check-in"));
                }
            }

            if (r.getDataCheckOut().isEqual(hoje) && (tipoFiltro.equals("TODOS") || tipoFiltro.equals("OUT"))) {
                containerCheckin.getChildren().add(criarCardMovimentacao(r, "Check-out"));
            }
        }


        if (containerCheckin.getChildren().isEmpty()) {
            Label lblVazio = new Label("Nenhuma movimentação para hoje.");
            lblVazio.setStyle("-fx-text-fill: #888; -fx-padding: 10;");
            containerCheckin.getChildren().add(lblVazio);
        }
    }






    private HBox criarCardMovimentacao(ReservaTwo reserva, String tipo) {
        HBox card = new HBox();
        card.getStyleClass().add("card-movimentacao");
        card.setSpacing(10);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefHeight(70);

        card.setStyle("-fx-background-color: #2a2a2a; -fx-background-radius: 5; -fx-border-color: #444; -fx-border-radius: 5;");


        Label lblIcone = new Label();
        lblIcone.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        StackPane boxIcone = new StackPane(lblIcone);
        boxIcone.setPrefSize(40, 40);
        boxIcone.setStyle("-fx-background-radius: 5;");

        String textoBotao = "";
        String estiloBotao = "";

        if (tipo.equals("Check-in")) {
            lblIcone.setText("📥");
            boxIcone.setStyle(boxIcone.getStyle() + "-fx-background-color: #2ecc71;"); // Verde
            textoBotao = "Confirmar";
            estiloBotao = "-fx-background-color: transparent; -fx-text-fill: #2ecc71; -fx-border-color: #2ecc71; -fx-border-radius: 3;";
        } else {
            lblIcone.setText("📤");
            boxIcone.setStyle(boxIcone.getStyle() + "-fx-background-color: #e74c3c;");
            textoBotao = "Finalizar";
            estiloBotao = "-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-border-color: #e74c3c; -fx-border-radius: 3;";
        }


        VBox info = new VBox(2);
        info.setAlignment(Pos.CENTER_LEFT);

        Label lblNome = new Label(reserva.getNomeHospede());
        lblNome.setStyle("-fx-text-fill: #cda434; -fx-font-weight: bold; -fx-font-size: 13px;");

        Label lblQuarto = new Label("Quarto " + reserva.getNumeroQuarto());
        lblQuarto.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11px;");

        info.getChildren().addAll(lblNome, lblQuarto);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Button btnAcao = new Button(textoBotao);
        btnAcao.setStyle(estiloBotao + "-fx-cursor: hand; -fx-font-size: 11px;");


        btnAcao.setOnAction(e -> {
            if (tipo.equals("Check-in")) {
                // Realizar Check-in
                reserva.setStatusPagamento("Hospedado"); // Exemplo de mudança de status
                // BancoDeDados.atualizar(reserva); // Se tiver esse método
                System.out.println("Check-in realizado para " + reserva.getNomeHospede());
            } else {
                // Realizar Check-out
                BancoDeDados.removerReserva(reserva); // Libera o quarto e remove da lista
                System.out.println("Check-out realizado. Quarto liberado.");
            }


            carregarCheckins("TODOS"); // Remove esse card da lista visual
            carregarListaQuartos();    // Atualiza a cor do quarto (Livre/Ocupado)
            exibirReservas();          // Atualiza a lista principal
        });

        card.getChildren().addAll(boxIcone, info, spacer, btnAcao);
        return card;
    }







    @FXML
    private void filtrarCheckIn() {
        carregarCheckins("IN");
    }

    @FXML
    private void filtrarCheckOut() {
        carregarCheckins("OUT");
    }



}
