package com.salah;

import com.salah.entity.User;
import com.salah.entity.UserRole;
import com.salah.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/* contains these 3
@ComponentScan(basePackages = "com.salah")
@EnableAutoConfiguration
@Configuration
*/

@EnableJpaAuditing
@SpringBootApplication
@RestController
@RequestMapping("api/v1/users")
@EnableAsync
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }
    @Bean
    CommandLineRunner run(UserRepository userRepository) {

        return args -> {

                userRepository.save(new User("Salah", "Gouja", "salahgouja11@gmail.com",
                        "$2a$10$gaKY6VrRkLNyauC63CZaa.bswMiZFS3j4rfyspqXQa6RFEo8061IC",false,false, UserRole.ADMIN));
                userRepository.save(new User("Thouraya", "Gouja", "thourayagouja@gmail.com",
                        "$2a$10$gaKY6VrRkLNyauC63CZaa.bswMiZFS3j4rfyspqXQa6RFEo8061IC",false,false, UserRole.DOCTOR));
                userRepository.save(new User("Dhafer", "Gouja", "dhafergouja@gmail.com",
                    "$2a$10$1Lckw/wW.7u1GpWPXLe5NOtbHnGQRMuHc0W9NM1r7VT4lKiEDYiD.",false,false, UserRole.PATIENT));
                userRepository.save(new User("Rania", "Gouja", "raniaagouja@gmail.com",
                    "$2a$10$1Lckw/wW.7u1GpWPXLe5NOtbHnGQRMuHc0W9NM1r7VT4lKiEDYiD.",false,false, UserRole.RECEPTION));


        };

    }
}

/*
@GetMapping("/greet")
    public GreetingResponse greet() {
        GreetingResponse response = new GreetingResponse(
                " hello",
                List.of("java", "js","php"),
                new Person("salah", "gouja", 23)
        );
        return response;
    }

    record Person(String nom, String prenom, int age) {
    }

    record GreetingResponse(String greet, List<String> favprogramminglanguage, Person person) {
    }

}
*/
//return a json file {"greet":"hello"}

	// record replace all the code bellow toString+hashcode+getmethode
   /* class GreetingResponse{
        private final String greet;

        GreetingResponse(String greet){
            this.greet=greet;

        }
        public String getGreet(){
            return greet ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GreetingResponse that = (GreetingResponse) o;
            return Objects.equals(greet, that.greet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(greet);
        }
    }

*/
//netstat -ao |find /i "listening" to find and close the port
