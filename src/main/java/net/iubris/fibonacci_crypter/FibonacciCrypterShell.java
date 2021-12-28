package net.iubris.fibonacci_crypter;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;

/**
 * @author massimiliano.leone@capgemini.com
 *
 *         27 Dec 2021
 *
 */
@ShellComponent
public class FibonacciCrypterShell {

//	@Lazy
	@Autowired
	private FibonacciCrypter fibonacciCrypter;

	@ShellMethod(value = "crypt the input")
	public String crypt(String input) {
		return fibonacciCrypter.crypt(input);
	}

	@ShellMethod(value = "decrypt the input")
	public String decrypt(String input) {
		return fibonacciCrypter.decrypt(input);
	}

	@Component
	public static class CustomPromptProvider implements PromptProvider {

//		private ConnectionDetails connection;

		@Override
		public AttributedString getPrompt() {
			return new AttributedString("fibonacciCrypter :> ",
					AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
		}

//		@EventListener
//		public void handle(ConnectionUpdatedEvent event) {
//			this.connection = event.getConnectionDetails();
//		}
	}

}
