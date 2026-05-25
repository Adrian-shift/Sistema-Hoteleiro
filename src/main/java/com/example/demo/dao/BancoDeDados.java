package com.example.demo.dao;// Sugestão de pacote: dao (Data Access Object)

import com.example.demo.model.ReservaTwo;
import com.example.demo.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.Reserva;

public class BancoDeDados {

    // USUÁRIOS (LOGIN)
    private static List<Usuario> tabelaUsuarios = new ArrayList<>();

    static {
        tabelaUsuarios.add(new Usuario("Admin", "admin", "admin"));
    }

    public static void adicionarUsuario(Usuario usuario) {
        tabelaUsuarios.add(usuario);
    }

    public static boolean validarLogin(String email, String senha) {
        for (Usuario u : tabelaUsuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) return true;
        }
        return false;
    }

    //  QUARTOS
    private static List<String> todosQuartos = new ArrayList<>();
    private static List<String> quartosOcupados = new ArrayList<>();


    public static List<String> getQuartosDisponiveis() {
        List<String> disponiveis = new ArrayList<>();
        for (String quarto : todosQuartos) {
            if (!quartosOcupados.contains(quarto)) {
                disponiveis.add(quarto);
            }
        }
        return disponiveis;
    }
    public static void ocuparQuarto(String numeroQuarto) {
        if (!quartosOcupados.contains(numeroQuarto)) {
            quartosOcupados.add(numeroQuarto);
        }
    }

    //  RESERVAS

    private static List<ReservaTwo> listaReservas = new ArrayList<>();
    public static void salvarReserva(ReservaTwo reserva) {
        listaReservas.add(reserva);
        ocuparQuarto(reserva.getNumeroQuarto());
        System.out.println("Reserva salva no Banco: " + reserva.getNomeHospede());
    }

    public static void desocuparQuarto(String numeroQuarto) {
        quartosOcupados.remove(numeroQuarto);
    }

    public static void removerReserva(ReservaTwo reserva) {
        if (reserva != null) {
            listaReservas.remove(reserva);
            desocuparQuarto(reserva.getNumeroQuarto()); // Libera o quarto para outro usar
            System.out.println("Reserva removida e quarto " + reserva.getNumeroQuarto() + " liberado.");
        }
    }

    public static List<ReservaTwo> getReservas() {
        return listaReservas;
    }
    public static List<String> getTodosQuartos() {
        return new ArrayList<>(todosQuartos);
    }

    static {
        //quartos do 101 ao 110
        for (int i = 101; i <= 110; i++) {
            todosQuartos.add(String.valueOf(i));
        }
    }
}
