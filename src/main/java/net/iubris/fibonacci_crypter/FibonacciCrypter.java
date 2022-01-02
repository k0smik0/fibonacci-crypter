package net.iubris.fibonacci_crypter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author massimiliano.leone@capgemini.com
 *
 *         25 Dec 2021
 *
 */
@Component
public class FibonacciCrypter {

	/**
	 *
	 */
	private static final String SKIP = "SKIP";
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String UNDERSCORE = "_";
	private static final String PIPE = "|";
	private static final String DOT = ".";
	private static final String COMMA = ",";
	private static final String SEMICOLON = ";";
	private static final String MARK_EXCLAMATION = "!";
	private static final String MARK_QUOTE = "?";

	private static final String SPLIT_REGEX = "(?!^)";

	private static final int ALPHABET_SIZE = 26;

	private static final int START = 1;

	private final int END = 60;

	private final NavigableMap<Integer, String> positionToCharMap = new TreeMap<>();
	private final NavigableMap<String, Integer> charToPositionMap = new TreeMap<>();

	private final List<Long> fibonacciSeries = new LinkedList<>();

	@PostConstruct
	private void init() {
		populateAlphabetMaps();
		generateFibonacci(END);
	}

	private void populateAlphabetMaps() {
		for (int i = START; i <= ALPHABET_SIZE; i++) {
			String _char = getChar(i);

			int offset = 0;
			String lowercaseChar = _char.toLowerCase();
			positionToCharMap.put(offset + i, lowercaseChar);
			charToPositionMap.put(lowercaseChar, offset + i);

			offset = ALPHABET_SIZE;
			String uppercaseChar = _char.toUpperCase();
			positionToCharMap.put(offset + i, uppercaseChar);
			charToPositionMap.put(uppercaseChar, offset + i);
		}
		// SPACE
//		charToPositionMap.put(SPACE, 0);
//		positionToCharMap.put(0, SPACE);

		/*-
		AtomicInteger offset = new AtomicInteger(charToPositionMap.size());
		List.of(".", ",", ";", "!").stream().forEach(c -> {
			int incrementedPosition = offset.incrementAndGet();
			int position = offset.get();
			positionToCharMap.put(incrementedPosition, c);
			charToPositionMap.put(c, position);
		});
		*/
	}

	private void generateFibonacci(int limit) {
		fibonacciSeries.add(0L);
		fibonacciSeries.add(1L);
		fibonacciSeries.add(1L);
		for (int i = 2; i <= limit; i++) {
			int last = i;
			int secondLast = i - 1;
			long actual = fibonacciSeries.get(last) + fibonacciSeries.get(secondLast);
			fibonacciSeries.add(actual);
		}
		fibonacciSeries.remove(1);
	}

	private static String getChar(int i) {
		String a;
		if (i <= 0) {
			a = "_";
		} else {
			a = (char) ('A' + --i % ALPHABET_SIZE) + EMPTY;
		}
		return a;
	}

	public String crypt(String input) {
		return Arrays.stream(input.split(SPLIT_REGEX))
			.map(c -> {
				Integer ctp = charToPositionMap.get(c);
				if (ctp == null) {
					return c;
				}
				Long byFibonacci = fibonacciSeries.get(ctp);
				return EMPTY + byFibonacci;
			})
			.collect(Collectors.joining(UNDERSCORE))
			.replace(UNDERSCORE + SPACE + UNDERSCORE, SPACE)
			.replace(SEMICOLON + UNDERSCORE, SEMICOLON)
			.replace(UNDERSCORE + COMMA, COMMA)
			.replace(UNDERSCORE + DOT, DOT);

	}

	public String decrypt(String input) {
		if (input == null || input.isEmpty()) {
			return EMPTY;
		}
		AtomicReference<String> toReturn = new AtomicReference<>(input.replace(SPACE, PIPE));

		AtomicReference<String> _input = new AtomicReference<>(input);
		List.of(DOT, COMMA, SEMICOLON, MARK_EXCLAMATION)
			.stream()
			.forEach(p -> _input.updateAndGet(t -> t.replace(p, EMPTY)));
		System.out.println("_input: " + _input);

		Arrays.stream(_input.get().split(SPACE))
			.flatMap(w -> Arrays.stream(w.split(UNDERSCORE)))
			.map(numberAsString -> {
				if (numberAsString.isEmpty()) {
					return new String[] { SKIP, EMPTY };
				}
				int position = fibonacciSeries.indexOf(Long.parseLong(numberAsString));
				String _char = positionToCharMap.get(position);
				return new String[] { numberAsString, _char };
			})
			.forEach(sa -> {
				String n = sa[0];
				String c = sa[1];
				toReturn.updateAndGet(t -> t
					.replace(UNDERSCORE + n + UNDERSCORE, UNDERSCORE + c + UNDERSCORE)
					.replace(UNDERSCORE + n + PIPE, UNDERSCORE + c + PIPE)
					.replace(UNDERSCORE + n + SPACE, UNDERSCORE + c + SPACE)
					.replace(PIPE + n + UNDERSCORE, PIPE + c + UNDERSCORE)
//					.replace(n + UNDERSCORE, c + UNDERSCORE)
//					.replace(UNDERSCORE + n, UNDERSCORE + c)
				);

				List.of(DOT, COMMA, SEMICOLON, MARK_EXCLAMATION).stream()
					.forEach(p -> toReturn
						.updateAndGet(t -> t
							.replace(n + p, c + p)
							.replace(p + n + PIPE, p + c + PIPE)));
			});

		// handle first
		toReturn.updateAndGet(t -> {
			String first = toReturn.get().split(UNDERSCORE)[0];
			String s = positionToCharMap.get(fibonacciSeries.indexOf(Long.parseLong(first)));
			return t.replace(first, s);
		});

		// handle last
		/*-
		toReturn.updateAndGet(t -> {
			String[] split = input.split(SPACE);
			String last = split[split.length - 1];
			String s = positionToCharMap.get(fibonacciSeries.indexOf(Long.parseLong(last)));
			return t.replace(last, s);
		});
		*/

		return toReturn.get()
			.replace(UNDERSCORE, EMPTY)
			.replace(PIPE, SPACE);
	}

}
