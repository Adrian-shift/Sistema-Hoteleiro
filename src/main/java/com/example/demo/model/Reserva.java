package com.example.demo.model;// <--- TROQUE pelo nome do seu pacote (ex: com.hotel.model)

public class Reserva {

    // Atributos (as informações que vamos guardar)
    private String nomeHospede;
    private String quarto;
    private String dataReserva; // Usando String para facilitar seu teste agora

    // Construtor (chamado quando você faz 'new Reserva(...)')
    public Reserva(String nomeHospede, String quarto, String dataReserva) {
        this.nomeHospede = nomeHospede;
        this.quarto = quarto;
        this.dataReserva = dataReserva;
    }

    // --- Getters (Para o Controller conseguir LER os dados) ---

    public String getNomeHospede() {
        return nomeHospede;
    }

    public String getQuarto() {
        return quarto;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    // --- Setters (Para se você precisar ALTERAR os dados depois) ---

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    // Opcional: Útil para ver os dados no console se der System.out.println(reserva)
    @Override
    public String toString() {
        return "Reserva [Hóspede=" + nomeHospede + ", Quarto=" + quarto + ", Data=" + dataReserva + "]";
    }
}
