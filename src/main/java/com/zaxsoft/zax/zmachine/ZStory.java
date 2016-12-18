package com.zaxsoft.zax.zmachine;

class ZStory {
    static final ZStory NOT_FOUND = new ZStory();
    private final byte[] story;

    private ZStory() {
        story = new byte[]{};
    }

    ZStory(byte[] story) {
        this.story = story;
    }

    byte[] getStory() {
        return story;
    }
}
