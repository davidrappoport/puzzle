package triangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

/**
 * TriangleProcessor
 * <p>
 * Process an input file containing integers in triangle form and calculate the
 * heaviest path
 * 
 * @author David Rappoport
 * @since 2017-10-24
 *
 */
public class TriangleProcessor {

	private static final String INPUT_STREAM_COULD_NOT_BE_FOUND_FOR_FILE_NAME = "InputStream could not be found for file name: ";
	private static final String FILE_HAS_NO_LINES = "File has no lines: ";
	private static final String EMPTY_STRING = "";
	private static final String EXPECTING_FILE_NAME_ARGUMENT = "Expecting file name argument";

	public static void main(String[] args) throws IOException {
		if (args.length == 0 || args[0] == null || args[0].trim() == EMPTY_STRING) {
			throw new IllegalArgumentException(EXPECTING_FILE_NAME_ARGUMENT);
		}

		long result = new TriangleProcessor().processTriangleFile(args[0]);
		System.out.println("The heaviest path is: " + result);
	}

	/**
	 * Takes a file containing numbers in a triangle shape (as described in the
	 * requirements) and returns the heaviest path along the way
	 * 
	 * @param fileName
	 *            <p>
	 *            Assumptions:
	 *            <p>
	 *            1: File contains at least one line with a number
	 *            <p>
	 *            2: First line has one number only
	 *            <p>
	 *            3: Every subsequent line has one additional number
	 *            <p>
	 *            4: Values are separated by whitespace
	 *            <p>
	 *            5: Values are integers
	 * @return heaviestWeight long
	 *         <p>
	 *         The heaviest weight is the sum of the values of the nodes along a
	 *         given path from the top of the triangle to the bottom
	 *         <p>
	 * 		(using long, as the addition of an unspecified number of integers may
	 *         result in a long value)
	 * @throws IOException
	 *             if the assumptions above are violated
	 */
	public long processTriangleFile(String fileName) throws IOException {
		/*
		 * Requirement as stated is simply to compute the heaviest path. We do not need
		 * to store intermediate results, nor be able to trace the path back.
		 */
		int lines = 0;
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(getStream(fileName)));
		lnr.skip(Long.MAX_VALUE);// jumps to the end of file
		lines = lnr.getLineNumber();
		lnr.close();
		if (lines == 0)
			throw new IOException(FILE_HAS_NO_LINES + fileName);

		// Assumptions:
		// 1: At least one line with a number
		// 2: First line has one number only
		// 3: Every subsequent line has one additional number
		// 4: Values are separated by whitespace
		// 5: Values are integers
		return processLines(getStream(fileName), lines);
	}

	/**
	 * Reads the lines in the file one at a time. For each value, in each line, add
	 * the previous heaviest value on the path for this value. 
	 * Single scan algorithm with limited memory consumption.
	 * 
	 * @param inputStream
	 * @param lines
	 *            The number of lines in the file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private long processLines(InputStream inputStream, int lines) throws IOException {
		long heaviestWeight = 0;

		// construct two arrays according to the size of the last line
		// (each line should have the same number of values as the line number)
		long[] previousLineValues = new long[lines];
		long[] currentLineValues = new long[lines];

		Scanner scanner = new Scanner(inputStream);
		long value = 0;
		// we know how many lines to expect, we know how many values to expect on each
		// line
		for (int i = 1; i <= lines; i++) {
			for (int j = 0; j < i; j++) {
				value = scanner.nextLong();
				// every value (except the value on the first line) has 1 or two 'parent'
				// values. Add the larger value to the child value
				if (i > 1) {
					if (j == 0) // j only has one parent
						value += previousLineValues[j];
					else if (j == i - 1) // j only has one parent (left)
						value += previousLineValues[j - 1];
					else // choose larger of two parent values
						value += Math.max(previousLineValues[j - 1], previousLineValues[j]);
				}
				if (value > heaviestWeight)
					heaviestWeight = value;
				currentLineValues[j] = value;
			}
			System.arraycopy(currentLineValues, 0, previousLineValues, 0, i);
			scanner.nextLine();
		}
		scanner.close();

		return heaviestWeight;
	}

	private InputStream getStream(String fileName) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null)
			throw new IOException(INPUT_STREAM_COULD_NOT_BE_FOUND_FOR_FILE_NAME + fileName);
		return stream;
	}

}
