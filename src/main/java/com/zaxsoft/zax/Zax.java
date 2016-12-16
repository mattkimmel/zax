package com.zaxsoft.zax;

import com.zaxsoft.zax.awt.AwtUserInterface;

class Zax {
    public static void main(String... arguments) {
        new Zax(new AwtUserInterface()).run(Zax.class.getPackage().getImplementationVersion());
    }

    private final AwtUserInterface userInterface;
    Zax(AwtUserInterface userInterface) {
        this.userInterface = userInterface;
    }

    void run(String version) {
        userInterface.start(version == null ? "" : version);
    }
}
