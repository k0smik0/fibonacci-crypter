package net.iubris.fibonacci_crypter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
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

	private static final String EMPTY = "";
	private static final String SPACE = " ";
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

//		System.out.println("fibonacciSeries:");
//		fibonacciSeries.forEach(i -> System.out.println(i));
//		System.out.println("numberToCharMap:");
//		positionToCharMap.entrySet().forEach((e) -> System.out.println(e.getKey() + ":" + e.getValue()));
//		System.out.println("charToNumberMap:");
//		charToPositionMap.entrySet().forEach((e) -> System.out.println(e.getKey() + ":" + e.getValue()));
//		System.out.println("");

	}

	private void populateAlphabetMaps() {
		for (int i = START; i <= ALPHABET_SIZE; i++) {
			String _char = getChar(i);

			int offset = 0;
			String uppercaseChar = _char.toUpperCase();
			positionToCharMap.put(offset + i, uppercaseChar);
			charToPositionMap.put(uppercaseChar, offset + i);

			offset = ALPHABET_SIZE;
			String lowercaseChar = _char.toLowerCase();
			positionToCharMap.put(offset + i, lowercaseChar);
			charToPositionMap.put(lowercaseChar, offset + i);
		}
		// SPACE
		charToPositionMap.put(SPACE, 0);
		positionToCharMap.put(0, SPACE);

		AtomicInteger offset = new AtomicInteger(charToPositionMap.size());
		List.of(".", ",", ";", "!").stream().forEach(c -> {
			int incrementedPosition = offset.incrementAndGet();
			int position = offset.get();
			positionToCharMap.put(incrementedPosition, c);
			charToPositionMap.put(c, position);
		});
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
//				String uppercaseChar = c.toUpperCase();
				Integer ctp = charToPositionMap.get(c);
//				System.out.println("c:" + uppercaseChar + " n:" + Long);
				if (ctp == null) {
					System.err.println(c + " null!");
				}
				Long byFibonacci = fibonacciSeries.get(ctp);
//				System.out.println("\t bf:" + byFibonacci);
				return EMPTY + byFibonacci;
			})
			.collect(Collectors.joining(SPACE));
	}

	public String decrypt(String input) {
//		String _input = input.replaceAll(" 0 ", SPACE);
//		System.out.println("_input:" + input);
		return Arrays.stream(input.split(SPACE))
			.map(c -> {
				int position = fibonacciSeries.indexOf(Long.parseLong(c));
//				System.out.println(c);
				String string = positionToCharMap.get(position);
				return string;
			})
			.collect(Collectors.joining(EMPTY));
	}

	/*-
	public void sampleToString() {
		for (int i = START; i <= END; i++) {
			String _char = positionToCharMap.get(i);
			Integer pos = charToPositionMap.get(_char);
		}
	}
	*/

}
