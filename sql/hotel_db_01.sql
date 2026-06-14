CREATE DATABASE IF NOT EXISTS hotel_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE hotel_db;

DROP TABLE IF EXISTS checkin_checkout;
DROP TABLE IF EXISTS pagamento;
DROP TABLE IF EXISTS reserva;
DROP TABLE IF EXISTS quarto;
DROP TABLE IF EXISTS tipo_quarto;
DROP TABLE IF EXISTS hospede;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(120) NOT NULL,
                         senha_hash VARCHAR(255) NOT NULL,
                         ativo BOOLEAN NOT NULL DEFAULT TRUE,
                         data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT uk_usuario_email UNIQUE (email)
);

CREATE TABLE hospede (
                         id_hospede INT AUTO_INCREMENT PRIMARY KEY,
                         nome_completo VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) NOT NULL,
                         email VARCHAR(120) NOT NULL,
                         telefone VARCHAR(20) NOT NULL,
                         data_cadastro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',

                         CONSTRAINT uk_hospede_cpf UNIQUE (cpf),
                         CONSTRAINT ck_hospede_status
                             CHECK (status IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE tipo_quarto (
                             id_tipo_quarto INT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(50) NOT NULL,
                             descricao VARCHAR(255),
                             valor_diaria DECIMAL(10,2) NOT NULL,
                             capacidade INT NOT NULL DEFAULT 1,

                             CONSTRAINT uk_tipo_quarto_nome UNIQUE (nome),
                             CONSTRAINT ck_tipo_quarto_valor_diaria
                                 CHECK (valor_diaria >= 0),
                             CONSTRAINT ck_tipo_quarto_capacidade
                                 CHECK (capacidade > 0)
);

CREATE TABLE quarto (
                        id_quarto INT AUTO_INCREMENT PRIMARY KEY,
                        id_tipo_quarto INT NOT NULL,
                        numero VARCHAR(10) NOT NULL,
                        status_ocupacao VARCHAR(20) NOT NULL DEFAULT 'LIVRE',
                        ativo BOOLEAN NOT NULL DEFAULT TRUE,

                        CONSTRAINT uk_quarto_numero UNIQUE (numero),

                        CONSTRAINT fk_quarto_tipo_quarto
                            FOREIGN KEY (id_tipo_quarto)
                                REFERENCES tipo_quarto (id_tipo_quarto),

                        CONSTRAINT ck_quarto_status_ocupacao
                            CHECK (status_ocupacao IN ('LIVRE', 'OCUPADO', 'LIMPEZA', 'MANUTENCAO'))
);

CREATE TABLE reserva (
                         id_reserva INT AUTO_INCREMENT PRIMARY KEY,
                         id_hospede INT NOT NULL,
                         id_quarto INT NOT NULL,
                         data_checkin_prevista DATE NOT NULL,
                         data_checkout_prevista DATE NOT NULL,
                         status_reserva VARCHAR(20) NOT NULL DEFAULT 'CRIADA',
                         valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                         data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         data_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                         CONSTRAINT fk_reserva_hospede
                             FOREIGN KEY (id_hospede)
                                 REFERENCES hospede (id_hospede),

                         CONSTRAINT fk_reserva_quarto
                             FOREIGN KEY (id_quarto)
                                 REFERENCES quarto (id_quarto),

                         CONSTRAINT ck_reserva_datas
                             CHECK (data_checkout_prevista > data_checkin_prevista),

                         CONSTRAINT ck_reserva_status
                             CHECK (status_reserva IN ('CRIADA', 'CONFIRMADA', 'CANCELADA', 'FINALIZADA')),

                         CONSTRAINT ck_reserva_valor_total
                             CHECK (valor_total >= 0)
);

CREATE TABLE pagamento (
                           id_pagamento INT AUTO_INCREMENT PRIMARY KEY,
                           id_reserva INT NOT NULL,
                           forma_pagamento VARCHAR(30) NOT NULL,
                           status_pagamento VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
                           valor_total DECIMAL(10,2) NOT NULL,
                           valor_pago DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                           parcelas INT NOT NULL DEFAULT 1,
                           data_pagamento DATETIME,

                           CONSTRAINT fk_pagamento_reserva
                               FOREIGN KEY (id_reserva)
                                   REFERENCES reserva (id_reserva),

                           CONSTRAINT uk_pagamento_reserva UNIQUE (id_reserva),

                           CONSTRAINT ck_pagamento_forma
                               CHECK (forma_pagamento IN ('DINHEIRO', 'PIX', 'CARTAO_CREDITO', 'CARTAO_DEBITO')),

                           CONSTRAINT ck_pagamento_status
                               CHECK (status_pagamento IN ('PENDENTE', 'CONFIRMADO', 'PAGO', 'CANCELADO')),

                           CONSTRAINT ck_pagamento_valor_total
                               CHECK (valor_total >= 0),

                           CONSTRAINT ck_pagamento_valor_pago
                               CHECK (valor_pago >= 0),

                           CONSTRAINT ck_pagamento_parcelas
                               CHECK (parcelas > 0)
);

CREATE TABLE checkin_checkout (
                                  id_checkin_checkout INT AUTO_INCREMENT PRIMARY KEY,
                                  id_reserva INT NOT NULL,
                                  data_checkin_real DATETIME,
                                  data_checkout_real DATETIME,
                                  responsavel_checkin VARCHAR(100),
                                  responsavel_checkout VARCHAR(100),
                                  status_hospedagem VARCHAR(30) NOT NULL DEFAULT 'AGUARDANDO_CHECKIN',

                                  CONSTRAINT fk_checkin_checkout_reserva
                                      FOREIGN KEY (id_reserva)
                                          REFERENCES reserva (id_reserva),

                                  CONSTRAINT uk_checkin_checkout_reserva UNIQUE (id_reserva),

                                  CONSTRAINT ck_checkin_checkout_status
                                      CHECK (status_hospedagem IN (
                                                                   'AGUARDANDO_CHECKIN',
                                                                   'HOSPEDADO',
                                                                   'CHECKOUT_REALIZADO'
                                          )),

                                  CONSTRAINT ck_checkin_checkout_datas
                                      CHECK (
                                          data_checkout_real IS NULL
                                              OR data_checkin_real IS NULL
                                              OR data_checkout_real >= data_checkin_real
                                          )
);

CREATE INDEX idx_reserva_hospede
    ON reserva (id_hospede);

CREATE INDEX idx_reserva_quarto_datas
    ON reserva (id_quarto, data_checkin_prevista, data_checkout_prevista);

CREATE INDEX idx_reserva_status
    ON reserva (status_reserva);

CREATE INDEX idx_quarto_status
    ON quarto (status_ocupacao);

CREATE INDEX idx_hospede_nome
    ON hospede (nome_completo);