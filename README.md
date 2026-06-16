# Sistema de Gestão Hoteleira

Projeto acadêmico desenvolvido com o objetivo de aplicar os conceitos de Programação Orientada a Objetos, persistência de dados e desenvolvimento de interfaces gráficas. O sistema simula as principais operações de gerenciamento de um hotel, permitindo o cadastro e controle de hóspedes, quartos, reservas, pagamentos e processos de hospedagem.

## Integrantes do grupo

* Adrian Luis Alves Rocha
* Ana Clara Seixas Dourado
* Guilherme Roque dos Anjos
* Júlio Cézar Rosa Sales

---

## Tecnologias utilizadas

* Java 21
* JavaFX
* JDBC
* MySQL
* IntelliJ IDEA

---

## Funcionalidades implementadas

* Cadastro de usuários do sistema;
* Cadastro de hóspedes;
* Cadastro de tipos de quarto;
* Cadastro de quartos;
* Realização de reservas;
* Registro de pagamentos;
* Controle de check-in e check-out.

---

## Configuração do banco de dados

1. Certifique-se de que o MySQL Server esteja instalado e em execução.

2. Execute o script SQL disponibilizado no projeto para criar o banco de dados e suas respectivas tabelas.

3. Verifique se o arquivo `db.properties`, localizado na raiz do projeto, está configurado corretamente:

```properties
user=root
password=root
dburl=jdbc:mysql://127.0.0.1:3306/hotel_db
useSSL=false
```

> **Observação:** Caso utilize credenciais diferentes das apresentadas acima, ajuste os valores conforme a configuração do seu ambiente.

---

## Como executar a aplicação

1. Clone este repositório:

```bash
git clone https://github.com/Adrian-shift/Sistema-Hoteleiro.git
```

2. Abra o projeto utilizando a IDE IntelliJ IDEA.

3. Configure o **Project SDK** para utilizar o **Java 21**.

4. Certifique-se de que as bibliotecas do **JavaFX** estejam corretamente configuradas no projeto.

5. Adicione o **MySQL Connector/J** às dependências do projeto, caso ele não esteja presente.

6. Confirme que o servidor MySQL está em execução e que o banco de dados foi criado conforme descrito anteriormente.

7. Execute a classe principal da aplicação pela IDE.

---

## Estrutura básica do sistema

O sistema está organizado em camadas, contemplando:

* **Model:** representação das entidades do sistema;
* **DAO:** acesso e manipulação dos dados no banco de dados;
* **Controller:** gerenciamento das ações e regras da interface;
* **View:** telas desenvolvidas com JavaFX;
* **DB:** gerenciamento das conexões com o banco de dados.

---

## Considerações finais

Este projeto foi desenvolvido exclusivamente para fins acadêmicos, visando consolidar conhecimentos relacionados ao desenvolvimento de aplicações desktop integradas a bancos de dados relacionais.
