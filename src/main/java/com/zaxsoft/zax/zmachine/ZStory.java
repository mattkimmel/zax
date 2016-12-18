package com.zaxsoft.zax.zmachine;

public class ZStory {
    static final ZStory NOT_FOUND = new ZStory();
    private final byte[] story;

    private ZStory() {
        story = new byte[]{};
    }

    ZStory(byte[] story) {
        this.story = story;
    }

    public byte[] getStory() {
        return story;
    }
}
