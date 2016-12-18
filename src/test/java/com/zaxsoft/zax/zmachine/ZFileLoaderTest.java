package com.zaxsoft.zax.zmachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ZFileLoaderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private static final String PATH = "src/test/resources/Test.z8";
    private final ZFileLoader loader = new ZFileLoader();

    @Test
    public void neverReturnNull() {
        ZStory story = loader.load(PATH);

        assertThat(story, notNullValue());
    }

    @Test
    public void loadAZMachineStory() {
        ZStory story = loader.load(PATH);

        assertThat(story.getStory().length, equalTo(317952));
    }

    @Test
    public void handleFileNotFound() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(NoSuchFileException.class));

        loader.load("somefile.txt");
    }

    @Test
    public void handleDirectory() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(AccessDeniedException.class));

        loader.load(".");
    }
}
