package user;

import org.kj6682.samarcanda.loan.Item;
import org.kj6682.samarcanda.loan.Loan;
import org.kj6682.samarcanda.loan.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by luigi on 30/09/15.
 */

@SpringBootApplication
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // save a couple of customers
            userRepository.save(new User("John Lennon", "rhythmic guitar", "Liverpool"));
            userRepository.save(new User("Paul McCartney", "bass guitar", "Liverpool"));
            userRepository.save(new User("George Harrison", "lead guitar", "Liverpool"));
            userRepository.save(new User("Ringo Starr", "drums", "Liverpool"));
        };
    }
}//:)
