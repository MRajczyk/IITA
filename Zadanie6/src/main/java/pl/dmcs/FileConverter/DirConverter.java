package pl.dmcs.FileConverter;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirConverter {
    private final FileConverter fileConverter;

    public DirConverter(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
    }

    public void convertDir(String dirPath, String extension) throws IOException {
        Path startDir = Paths.get(dirPath);

        if (!Files.isDirectory(startDir) || extension.isEmpty()) {
            throw new IllegalArgumentException("The provided path is not a directory: " + dirPath);
        }

        List<Path> files;
        try (Stream<Path> stream = Files.list(startDir)) {
            files = stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(extension))
                    .collect(Collectors.toList());
        }

        files.forEach(file -> {
            try {
                fileConverter.convertFile(file.toAbsolutePath().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
