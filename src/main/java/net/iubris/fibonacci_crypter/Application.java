package net.iubris.fibonacci_crypter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author massimiliano.leone@capgemini.com
 *
 *         27 Dec 2021
 *
 */
//@Import({ SpringShellAutoConfiguration.class })
//@ComponentScan(basePackages = { "net.iubris.fibonacci_crypter" })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		String originalMessage = "Buon NaSale, caotico... ma non troppo ;D !";
//		String crypted = fibonacciRotor.crypt("Buon NaSale, caotico ma non troppo ;D !");
//		System.out.println("crypted:" + crypted);
//
//		String admitted = "2 4807526976 267914296 165580141 0 610 317811 6765 317811 63245986 2178309 225851433717 0 832040 317811 267914296 2971215073 14930352 832040 267914296 0 102334155 317811 0 165580141 267914296 165580141 0 2971215073";
//		System.out.println("decrypted:" + fibonacciRotor.decrypt(admitted));

//		fibonacciRotor.sampleToString();
	}

}
