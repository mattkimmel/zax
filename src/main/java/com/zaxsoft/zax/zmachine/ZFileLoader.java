package com.zaxsoft.zax.zmachine;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

class ZFileLoader {
    ZStory load(String storyFilePath) {
        Path path = Paths.get(storyFilePath);
        try {
            return new ZStory(Files.readAllBytes(path));
        } catch (AccessDeniedException|NoSuchFileException exception) {
            return ZStory.NOT_FOUND;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
