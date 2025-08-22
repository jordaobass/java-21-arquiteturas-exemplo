package com.nexttag.agendalayered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgendaLayeredApplication {

    /**
     * Classe principal da aplicação Spring Boot.
     * <p>
     * Responsável por inicializar o contexto Spring e executar a aplicação.
     * Em arquitetura em camadas, esta classe serve como ponto de entrada para o sistema inteiro.
     */
    public static void main(String[] args) {
        SpringApplication.run(AgendaLayeredApplication.class, args);
    }

}
