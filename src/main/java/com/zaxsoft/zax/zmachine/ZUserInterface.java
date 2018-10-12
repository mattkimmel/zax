/*
 * Copyright (c) 2008 Matthew E. Kimmel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.zaxsoft.zax.zmachine;

import java.awt.*;
import java.util.Vector;

/**
 * ZUserInterface - This interface must be implemented by a programmer
 * using the ZMachine classes, to provide various I/O routines and
 * other routines that will differ with different user interfaces.
 *
 * @author Matt Kimmel
 */
public interface ZUserInterface {
    // This method is called when a fatal error is encountered.
    // It should never return.
    void fatal(String message);

    // This method is called when the game starts or restarts,
    // to initialize windows and such.  The version number of
    // the current game is passed.
    void initialize(int version);

    // This method sets the additional terminating characters for
    // READ operations.  It should be called after initialize, and
    // should be passed a Vector of Integer objects containing
    // the Z-Characters that should be treated as terminating characters.
    void setTerminatingCharacters(Vector characters);
    
    // This method returns true if the user interface supports a
    // status line.
    boolean hasStatusLine();

    // This method returns true if the user interface supports an
    // upper window.
    boolean hasUpperWindow();

    // This method returns true if the default font is variable-width.
    boolean defaultFontProportional();

	// This method returns true if the user interface supports colors.
	boolean hasColors();

	// This method returns true if boldface styles are available.
	boolean hasBoldface();

	// This method returns true if italic styles are available.
	boolean hasItalic();

	// This method returns true if fixed-width styles are available.
	boolean hasFixedWidth();

    // This method returns true if timed input is supported.
    boolean hasTimedInput();
    
	// This method returns the size of the "screen" in lines/columns.
	Dimension getScreenCharacters();

	// This method returns the size of the "screen" in "units".
	Dimension getScreenUnits();

	// This method returns the size of the current font in "units".
	Dimension getFontSize();

    // This method returns the size of the specified window, in characters.
    Dimension getWindowSize(int window);
    
	// These methods return the default foreground and background colors
	// (as Z-Machine color numbers).
	int getDefaultForeground();
	int getDefaultBackground();

	// This method returns the current cursor position.
	Point getCursorPosition();

    // This method shows the status bar.  This is handled entirely
    // by the user interface, rather than in the CPU or the I/O card,
    // in order to give the user interface programmer some flexibility
    // about where to put the information (in the window title bar,
    // for example).
    void showStatusBar(String s, int a, int b, boolean flag);

    // Split the screen, as per SPLIT_SCREEN instruction
    void splitScreen(int lines);
    
    // This method sets the current window, to which all output
    // will be directed.  In V1-3, this implies clearing the window;
    // in V4+, it implies homing the cursor.
    void setCurrentWindow(int window);

	// This method sets the cursor to position x,y (or turns the cursor on or off--
	// see Z-Machine spec).
	void setCursorPosition(int x, int y);

	// This method sets the foreground and background colors. 0 means no change.
	void setColor(int foreground, int background);

	// Set the text style (see Z-Machine spec for meaning of parameter)
	void setTextStyle(int style);

	// Set the font--again, see Z-Spec.
	void setFont(int font);

    // This method reads a line of user input and appends it to the
    // supplied StringBuffer; it returns the terminating character.
    // If time is nonzero, the readLine will time out if a terminating
    // character has not been encountered after time tenths of a second.
    // If a timeout occurs, the return value will be 0.
    int readLine(StringBuffer buffer, int time);

    // This method reads a character from the user and returns it.  If time is
    // nonzero, it times out after time/10 seconds.
    int readChar(int time);

    // Print a string
    void showString(String string);

    // This method scrolls the current window by the specified
    // number of lines.
    void scrollWindow(int lines);

    // This method erases a line in the current window.
    void eraseLine(int size);

	// This method erases the current window.
	void eraseWindow(int window);

	// This method gets a filename from the user, possibly using a FileDialog.
	// A title for a dialog box is supplied.  If saveFlag is true, then we
	// are saving a file; otherwise, we're loading a file.  The method should
	// return null if there was an error or the user canceled.
	String getFilename(String title, String suggested, boolean saveFlag);

    // This function is called when the Z-Machine halts.  It
    // should not return.
    void quit();

	// This function is called when the Z-Machine is about to
	// restart.  The UI should prepare by resetting itself to
	// an initial state.  The function should return.
	void restart();
}
