# ZAX: Z-code Application Executor

- Version 0.92
- Written by Matt Kimmel (matt at infinite-jest dot net) and contributors

## Legal Stuff

As of version 0.91, Zax is open source, licensed under the MIT License.  Please see the accompanying LICENSE.txt file for legal details.

## Disclaimer

Zax was my first major Java project, and most of it was written in 1996 and 1997. Hence, there are a lot of idioms in the code that are more C-like and less Java-like. Please bear with me; I'm fixing them as I work on the code.

## About

Zax is a Java implementation of the Z-Machine. The Z-Machine is a virtual machine that runs programs written in Z-code, which includes all of Infocom's text adventures and a large number of text adventures and other programs written using the Inform compiler. Information about all of this is available at the [Interactive Fiction Archive](https://www.ifarchive.org/) and the [IFWiki](http://www.ifwiki.org/index.php/Main_Page).

Zax is capable of running Z-code programs of versions 1 through 8, with the exception of version 6 programs.  It complies with the Z-Machine standard v0.9 as specified by Graham Nelson (with a couple of exceptions; see below).  It runs all of the non-graphical Infocom games except Beyond Zork (which exhibits some trouble with its on-screen map), and quite a lot of the more modern games written with Inform.

Zax is written entirely in Java, and should run on any Java Virtual Machine of version 1.5 or higher.

Please note that Zax is a Java application, not an applet, and is therefore not suitable for running in a browser.  Zax was developed independently of ZPlet, which is a Java applet implementation of the Z-Machine released around the same time as Zax's first release (circa 1997).


## Running Zax

If you have cloned the source code and want to run Zax, you can use the Gradle run command:

gradlew run


## Building Zax

Zax comes with a Gradle build script.  You can build all the distribution jar using a command like this:

gradlew clean build

You can also create an Eclipse or IntelliJ IDEA or Netbeans (or IDE of your choice) project around the source files and build that way.  Zax currently has no dependencies outside of the standard Java libraries, which makes things easy.


## Zax Internals

Zax is composed of three components: A GUI front-end (Zax); a generic Z-Machine implementation (package ZMachine); and a text-screen widget used by the front-end.  The ZMachine package is implemented in such a way that the user interface is completely abstracted.  When the CPU is initialized, it is passed an instance of an object that implements the interface ZUserInterface; this interface is a set of generic methods for various fundamental user-interface functions (such as text output and Z-Machine window management) which can be implemented in any way that the interface programmer finds desirable. My minimal front-end uses a custom AWT widget designed to emulate an IBM Text-Mode-style screen, but pretty much any user interface is possible.

The Zax user interface consists of two classes: Zax, which implements both a high-level interface (providing functions such as "Play Story"), and ZaxWindow, which is used by Zax to keep track of logical Z-Machine windows.  Zax is a subclass of java.awt.Frame.  Zax uses the com.zaxsoft.zax.awt.TextScreen class to display and manipulate text.

The ZMachine package consists of several classes.  ZMachine.ZCPU is the heart of the Z-Machine; it implements the CPU and all of its opcodes.  ZMemory is the Z-Machine's memory manager; it is responsible for initializing memory, encapsulating access to it, and dumping it to and reading it from a stream.  ZIOCard encapsulates the Z-Machine I/O streams (in most cases passing read and write requests to and from the user interface).  ZObjectTable encapsulates the Z-Machine's object table structures (including properties and attributes); it relies heavily on ZMemory to access the internal Z-Machine data associated with these structures.  ZCallFrame is a class which represents a frame on the Z-Machine call stack.  Finally, as mentioned above, ZUserInterface is an interface which must be implemented by user interface code.


## Bugs

I know of a few bugs and missing features in Zax.  These include:

- Does not implement transcript or command script I/O streams (this falls short of Nelson's specification).
- Problems with window handling in the user interface make "Beyond Zork" unplayable.  Every other game I've tried has been fine, but then, I haven't tried everything that's out there.
- Sometimes when the Zax window goes out of focus and then comes back into focus, it will not accept keystrokes.  This seems to be a bug in the Java AWT; usually it can be fixed by clicking on the text window a few times.
- Calling the user interface "minimal" would be an understatement. But it works. If you'd like to contribute a better UI, I would be more than happy to include it!

If you find any bugs, I'd like to hear about them.  Please open an issue on Github. Please include the game the bug occurs in, the version number of the game, a link to the z-code file, if possible, and details on how to reproduce the bug.


## Future Plans:
The code could use plenty of refactoring, cleanup, and maybe even some unit tests.  Please don't judge me too harshly--Zax was the first major program I wrote in Java and I've learned an awful lot in the years since it was released.

Things have changed a lot in the IF and Z-code world since Zax was originally released in 1997.  Zax needs a lot of work to make it a "modern" Z-code interpreter.  It should be made Z-machine standard 1.0 compliant.  It needs Quetzal support.  It could use Blorb support.  It may need some modification to accommodate story files generated by Inform 7.
