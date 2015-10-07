package org.kj6682.samarcanda.user;

import org.kj6682.samarcanda.loan.Item;
import org.kj6682.samarcanda.loan.Loan;
import org.kj6682.samarcanda.loan.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luigi on 30/09/15.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, LoanRepository loanRepository) {
        return (args) -> {
            // save a couple of customers
            userRepository.save(new User("John Lennon", "rhythmic guitar", "Liverpool"));
            userRepository.save(new User("Paul McCartney", "bass guitar", "Liverpool"));
            userRepository.save(new User("George Harrison", "lead guitar", "Liverpool"));
            userRepository.save(new User("Ringo Starr", "drums", "Liverpool"));

            Set items = new HashSet();
            Arrays.asList("10,11,12".split(",")).
                    forEach(l -> {
                        items.add(new Item(Long.valueOf(l)));
                    });

            Loan loan = new Loan( Date.valueOf("2015-01-01"),Date.valueOf("2015-10-30"));

            loan.setUser(new org.kj6682.samarcanda.loan.User(101L));
            loan.setItems(items);
            loanRepository.save(new Loan(Date.valueOf("2015-01-01"), Date.valueOf("2015-10-30")));
            loanRepository.save(loan);

        };
    }
}//:)
