package com.zaxsoft.zax.zmachine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class ZFileLoaderTest {
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
        ZStory story = loader.load("somefile.txt");

        assertThat(story, equalTo(ZStory.NOT_FOUND));
    }

    @Test
    public void handleDirectory() {
        ZStory story = loader.load(".");

        assertThat(story, equalTo(ZStory.NOT_FOUND));
    }
}
