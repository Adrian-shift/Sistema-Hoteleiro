package db;

import java.io.Serial;

/**
 * EXCEÇÃO PERSONALIZADA DE BANCO DE DADOS
 * * CONCEITO DIDÁTICO: Estendemos 'RuntimeException' (Exceção Não-Checada) para que
 * o código não fique poluído com blocos try-catch obrigatórios em todos os lugares.
 * Ela serve para envelopar erros graves de infraestrutura (ex: senha errada do banco).
 */
public class DbException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    // Construtor que repassa a mensagem original do erro para a classe mãe (RuntimeException)
    public DbException(String msg) {
        super(msg);
    }
}
