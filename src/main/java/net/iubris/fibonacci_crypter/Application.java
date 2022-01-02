package net.iubris.fibonacci_crypter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author massimiliano.leone@capgemini.com
 *
 *         27 Dec 2021
 *
 */
@SpringBootApplication
public class Application {

	@Autowired
	private FibonacciCrypter fibonacciCrypter;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner doAtBoot() {
		return i -> {
			String originalMessage = "Buon CompleNano, caotico... ma non troppo ;D !";
			String crypted = fibonacciCrypter.crypt(originalMessage);
			System.out.println("crypted: " + crypted);

			String cryptedInput = "514229_17711_987_610 832040_987_377_1597_233_8_165580141_1_610_987_139583862445 3_1_987_10946_55_3_987_86267571272_86267571272_86267571272 377_1 610_987_610 10946_4181_987_1597_1597_987 225851433717_1346269 365435296162";
			String decrypt = fibonacciCrypter.decrypt(cryptedInput);
			System.out.println("decrypted: " + decrypt);
		};
	}

}
