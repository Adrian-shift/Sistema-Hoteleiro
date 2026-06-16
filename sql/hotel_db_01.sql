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
                         email VARCHAR(120),
                         senha_hash VARCHAR(255),
                         ativo BOOLEAN DEFAULT TRUE,
                         data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hospede (
                         id_hospede INT AUTO_INCREMENT PRIMARY KEY,
                         nome_completo VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14),
                         email VARCHAR(120),
                         telefone VARCHAR(20),
                         data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(20) DEFAULT 'ATIVO'
);

CREATE TABLE tipo_quarto (
                             id_tipo_quarto INT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(50),
                             descricao VARCHAR(255),
                             valor_diaria DECIMAL(10,2) DEFAULT 0.00,
                             capacidade INT DEFAULT 1
);

CREATE TABLE quarto (
                        id_quarto INT AUTO_INCREMENT PRIMARY KEY,
                        id_tipo_quarto INT,
                        numero VARCHAR(10),
                        status_ocupacao VARCHAR(20) DEFAULT 'LIVRE',
                        ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE reserva (
                         id_reserva INT AUTO_INCREMENT PRIMARY KEY,
                         id_hospede INT,
                         id_quarto INT,
                         data_checkin_prevista DATE,
                         data_checkout_prevista DATE,
                         status_reserva VARCHAR(20) DEFAULT 'CRIADA',
                         valor_total DECIMAL(10,2) DEFAULT 0.00,
                         data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                         data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pagamento (
                           id_pagamento INT AUTO_INCREMENT PRIMARY KEY,
                           id_reserva INT,
                           forma_pagamento VARCHAR(30),
                           status_pagamento VARCHAR(20) DEFAULT 'PENDENTE',
                           valor_total DECIMAL(10,2) DEFAULT 0.00,
                           valor_pago DECIMAL(10,2) DEFAULT 0.00,
                           parcelas INT DEFAULT 1,
                           data_pagamento DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE checkin_checkout (
                                  id_checkin_checkout INT AUTO_INCREMENT PRIMARY KEY,
                                  id_reserva INT,
                                  data_checkin_real DATETIME,
                                  data_checkout_real DATETIME,
                                  responsavel_checkin VARCHAR(100),
                                  responsavel_checkout VARCHAR(100),
                                  status_hospedagem VARCHAR(30) DEFAULT 'AGUARDANDO_CHECKIN'
);