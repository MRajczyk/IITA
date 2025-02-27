package pl.dmcs.FileConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DirConverterBehavioralTest {
    private FileConverter fileConverterMock;
    private DirConverter dirConverter;
    private Path testDir;

    @BeforeEach
    public void setUp() throws IOException {
        fileConverterMock = mock(FileConverter.class);
        dirConverter = new DirConverter(fileConverterMock);

        testDir = Files.createTempDirectory("testDir");

        Files.createFile(testDir.resolve("test1.txt"));
        Files.createFile(testDir.resolve("test2.txt"));
        Files.createFile(testDir.resolve("test1.md"));
    }

    @Test
    public void testConvertDirSuccess() throws IOException {
        String dirPath = testDir.toString();
        String extension = ".txt";

        doNothing().when(fileConverterMock).convertFile(anyString());

        dirConverter.convertDir(dirPath, extension);
        verify(fileConverterMock).convertFile(testDir.resolve("test1.txt").toString());
        verify(fileConverterMock).convertFile(testDir.resolve("test2.txt").toString());

        verify(fileConverterMock, times(2)).convertFile(Mockito.anyString());
    }

    @Test
    public void testConvertDirNoMatchingFiles() throws IOException {
        String dirPath = testDir.toString();
        String extension = ".xyz";

        dirConverter.convertDir(dirPath, extension);

        verify(fileConverterMock, never()).convertFile(anyString());
    }

    @Test
    public void testConvertDirThrowsIllegalArgumentExceptionForNonDirectory() throws IOException {
        String filePath = testDir.resolve("test1.txt").toString();
        String extension = ".txt";

        doThrow(new RuntimeException()).when(fileConverterMock).convertFile(testDir.resolve("test1.txt").toString());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dirConverter.convertDir(filePath, extension));
        assertEquals("The provided path is not a directory: " + filePath, exception.getMessage());
    }

    @Test
    public void testConvertDirEmptyDirectory() throws IOException {
        Path emptyDir = Files.createTempDirectory("emptyDir");
        String extension = ".txt";

        dirConverter.convertDir(emptyDir.toString(), extension);

        verify(fileConverterMock, never()).convertFile(anyString());
    }
}
