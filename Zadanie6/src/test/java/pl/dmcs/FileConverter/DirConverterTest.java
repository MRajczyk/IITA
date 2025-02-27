package pl.dmcs.FileConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class DirConverterTest {

    private DirConverter dirConverter;

    @BeforeEach
    void instantiateCalculator() {
        this.dirConverter = new DirConverter(new FileConverter());
    }

    @Test
    public void testNonExistantDirectory() {
        final String TEST_DIRNAME = "target/test-classes/nonExistant";
        final String TEST_EXTENSION = ".txt";
        final String EXPECTED_EXCEPTION_MESSAGE = "The provided path is not a directory: target/test-classes/nonExistant";
        Exception excpt = assertThrows(IllegalArgumentException.class, () -> dirConverter.convertDir(TEST_DIRNAME, TEST_EXTENSION));
        assertEquals(excpt.getMessage(), EXPECTED_EXCEPTION_MESSAGE);
    }

    @Test
    public void testEmptyDirectory() {
        final String TEST_DIRNAME = "target/test-classes/empty";
        final String TEST_EXTENSION = ".txt";

        assertDoesNotThrow(() -> dirConverter.convertDir(TEST_DIRNAME, TEST_EXTENSION));
    }

    @Test
    public void testConvertedFilesTXT() throws IOException {
        final String TEST_DIRNAME = "target/test-classes/test";
        final String TEST_EXTENSION = ".txt";
        final String TEST_FILENAME_1 = "file1.txt";
        final String EXPECTED_FILE_CONTENTS_1 = "TEST";
        final String TEST_FILENAME_2 = "file2.txt";
        final String EXPECTED_FILE_CONTENTS_2 = "TEST\nLINIA2";

        dirConverter.convertDir(TEST_DIRNAME, TEST_EXTENSION);

        //check file 1 contents
        testFileContents(TEST_FILENAME_1, TEST_DIRNAME, EXPECTED_FILE_CONTENTS_1);

        //check file 2 contents
        testFileContents(TEST_FILENAME_2, TEST_DIRNAME, EXPECTED_FILE_CONTENTS_2);
    }

    @Test
    public void testConvertedFileMD() throws IOException {
        final String TEST_DIRNAME = "target/test-classes/test";
        final String TEST_EXTENSION = ".md";
        final String TEST_FILENAME = "file3.md";
        final String EXPECTED_FILE_CONTENTS = "MARKDOWN";

        dirConverter.convertDir(TEST_DIRNAME, TEST_EXTENSION);
        testFileContents(TEST_FILENAME, TEST_DIRNAME, EXPECTED_FILE_CONTENTS);
    }

    private void testFileContents(String testFilename, String testDirName, String testExpectedContents) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(testDirName + "/" + testFilename));
        StringBuilder content = new StringBuilder();
        int c;

        while ((c = reader.read()) != -1) {
            content.append((char) c);
        }

        reader.close();
        assertEquals(testExpectedContents, content.toString());
    }
}
