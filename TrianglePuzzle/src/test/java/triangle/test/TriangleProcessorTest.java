package triangle.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import triangle.TriangleProcessor;

public class TriangleProcessorTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testProcessTriangle27() throws IOException {
		assertEquals(27, new TriangleProcessor().processTriangleFile("triangle27.txt"));
	}

	@Test
	public void testProcessTriangleLeftFile() throws IOException {
		assertEquals(56, new TriangleProcessor().processTriangleFile("triangle56Left.txt"));
	}

	@Test
	public void testProcessTriangleRightFile() throws IOException {
		assertEquals(56, new TriangleProcessor().processTriangleFile("triangle56Left.txt"));
	}
	
	@Test
	public void testProcessTriangleMiddleFile() throws IOException {
		assertEquals(56, new TriangleProcessor().processTriangleFile("triangle56Middle.txt"));
	}

	@Test
	public void testProcessTriangleIncorrectNumberOfValuesFile() throws IOException {
		exception.expect(NoSuchElementException.class);
		new TriangleProcessor().processTriangleFile("triangleIncorrectNumberOfValues.txt");
	}
	
	@Test
	public void testProcessTriangleNoLines() throws IOException {
		exception.expect(IOException.class);
		new TriangleProcessor().processTriangleFile("triangleNoLines.txt");
	}
	
	@Test
	public void testProcessTriangleWrongDelimiter() throws IOException {
		exception.expect(InputMismatchException.class);
		new TriangleProcessor().processTriangleFile("triangleWrongDelimiter.txt");
	}
}
