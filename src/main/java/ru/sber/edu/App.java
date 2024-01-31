package ru.sber.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sber.edu.entity.BankEntity;
import ru.sber.edu.entity.ClientEntity;

@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
