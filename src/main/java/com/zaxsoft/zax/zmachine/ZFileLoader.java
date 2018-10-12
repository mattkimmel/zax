package com.zaxsoft.zax.zmachine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ZFileLoader {
    /**
     * Returns a {@link ZStory} containing the z-machine story loaded from
     * the file specified by storyFilePath.
     *
     * @param storyFilePath an absolute URL giving the base location of the image
     * @return {@link ZStory} the z-machine story
     * @throws RuntimeException with a cause of NoSuchFileException when file is not found
     * @throws RuntimeException when any other IOException occurs.
     * @see ZStory
     */
    ZStory load(String storyFilePath) {
        Path path = Paths.get(storyFilePath);
        try {
            return new ZStory(Files.readAllBytes(path));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
