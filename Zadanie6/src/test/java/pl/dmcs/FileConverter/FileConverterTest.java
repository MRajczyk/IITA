package pl.dmcs.FileConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileConverterTest {

    private FileConverter fileConverter;

    @BeforeEach
    void instantiateCalculator() {
        this.fileConverter = new FileConverter();
    }

    @Test
    public void testOneLineFile() throws IOException {
        final String TEST_FILENAME = "target/test-classes/test.txt";
        final String EXPECTED_FILE_CONTENTS = "TEST";

        testFileContents(TEST_FILENAME, EXPECTED_FILE_CONTENTS);
    }

    @Test
    public void testOneLineFile2() throws IOException {
        final String TEST_FILENAME = "target/test-classes/test_one_line.txt";
        final String EXPECTED_FILE_CONTENTS = "SECOND_COMBINATION";

        testFileContents(TEST_FILENAME, EXPECTED_FILE_CONTENTS);
    }

    @Test
    public void testMultipleLinesFile() throws IOException {
        final String TEST_FILENAME = "target/test-classes/test2.txt";
        final String EXPECTED_FILE_CONTENTS = "TEST\nLINIA2";

        testFileContents(TEST_FILENAME, EXPECTED_FILE_CONTENTS);
    }

    @Test
    public void testMultipleLinesFile2() throws IOException {
        final String TEST_FILENAME = "target/test-classes/test3.txt";
        final String EXPECTED_FILE_CONTENTS = "TEST\nLINIA2\nLINIA3\nKOMBINACJA";

        testFileContents(TEST_FILENAME, EXPECTED_FILE_CONTENTS);
    }

    @Test
    public void testFileNonExistent() {
        final String TEST_FILENAME = "target/test-classes/notexisting.txt";
        assertThrows(IOException.class, () -> fileConverter.convertFile(TEST_FILENAME));
    }

    private void testFileContents(String testFilename, String expectedContent) throws IOException {
        fileConverter.convertFile(testFilename);
        BufferedReader reader = new BufferedReader(new FileReader(testFilename));
        StringBuilder content = new StringBuilder();
        int c;

        while ((c = reader.read()) != -1) {
            content.append((char) c);
        }

        reader.close();
        assertEquals(expectedContent, content.toString());
    }
}
