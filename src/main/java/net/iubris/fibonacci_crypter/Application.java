package net.iubris.fibonacci_crypter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author massimiliano.leone@capgemini.com
 *
 *         27 Dec 2021
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Autowired
//	private FibonacciCrypter fibonacciCrypter;

	/*-
	@Bean
	CommandLineRunner doAtBoot() {
		return i -> {
			String originalMessage = "Buon CompleNano, caotico... ma non troppo ;D !";
			String crypted = fibonacciCrypter.crypt(originalMessage);
			System.out.println("crypted: " + crypted);

			String cryptedInput = "514229_17711_987_610 832040_987_377_1597_233_8_165580141_1_610_987, 3_1_987_10946_55_3_987... 377_1 610_987_610 10946_4181_987_1597_1597_987 ;1346269 !";
			String decrypt = fibonacciCrypter.decrypt(cryptedInput);
			System.out.println("decrypted: " + decrypt);
		};
	}
	*/
}
