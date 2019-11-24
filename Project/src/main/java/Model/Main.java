package Model;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;

import javax.servlet.ServletException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        UsersRepository repository;
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/classwork", "postgres", "0205");
            repository = UsersRepositoryImpl.getRepository();
            System.out.println(repository.findUser("user@", null).isPresent());
        } catch (SQLException e) {
            throw new IllegalStateException();
        }

    }
}
