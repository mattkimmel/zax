package com.zaxsoft.zax.zmachine;

class ZStory {
    private final byte[] story;

    ZStory(byte[] story) {
        this.story = story;
    }

    byte[] getStory() {
        return story;
    }
}
