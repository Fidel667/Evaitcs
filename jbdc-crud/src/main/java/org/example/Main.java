package org.example;

import org.example.config.DatabaseConnection;
import org.example.entity.Sub;
import org.example.repository.SubRepository;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            boolean ok = DatabaseConnection
                    .getInstance()
                    .getConnection()
                    .isValid(5);

            if (!ok) {
                System.err.println("DATABASE NOT CONNECTED");
                return;
            }

        } catch (Exception e) {
            System.err.println("DATABASE NOT CONNECTED");
            System.err.println(e.getMessage());
            return;
        }

        Sub subhub = new Sub("Astute", "Virginia-class", 4_300_000.00);

        SubRepository subRepository = new SubRepository();
        subRepository.save(subhub);

        System.out.println(
                "Sub Name: "
                        .concat(subhub.getSubName())
                        .concat(" Sub Id: ")
                        .concat(String.valueOf(subhub.getSubId()))
        );

    }

}