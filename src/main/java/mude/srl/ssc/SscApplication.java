package mude.srl.ssc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SscApplication {

	public static void main(String[] args) {
		SpringApplication.run(SscApplication.class, args);
	}

}
