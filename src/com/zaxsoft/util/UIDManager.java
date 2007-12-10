// Definition of the UIDManager class, which manages unique positive integer
// IDs.
//
// $Log: UIDManager.java,v $
// Revision 1.2  2000/07/22 03:33:05  matt
// Initial functionality
//
// Revision 1.1  2000/07/18 01:32:56  matt
// Initial commit
//
//
// Copyright (c) 2000, ZaxSoft.  All Rights Reserved.
package com.zaxsoft.util;

/** This class manages positive integer unique IDs.  Each unique ID generated
  * by an instance of this class is guaranteed to be unique within that
  * instance.
  *
  * In the current implementation, an instance of the class keeps track of
  * the value of the highest ID in use. IDs may be reported when loaded.  When an ID
  * is requested, the logic is simple: if the highest ID is less than the
  * highest possible Integer value, then the UID selected becomes the highest
  * ID plus one.  The highest UID is then incremented.  If the highest ID
  * is the highest integer value, UID_Invalid is returned.
  *
  * TODO: Find an implementation that doesn't limit us to 2^32 UIDs.
  */
public class UIDManager
{
    ///////////////////////////////////////////////////////////////
    // Public constants
    ///////////////////////////////////////////////////////////////
    public static final int UID_Invalid = 0xffffffff; // Constant representing invalid ID - the lowest possible value according to the Java spec
    
    
    ///////////////////////////////////////////////////////////////
    // Protected variables
    ///////////////////////////////////////////////////////////////
    protected int _highestUID;  // The highest UID in use

    
    ///////////////////////////////////////////////////////////////
    // Constructor
    ///////////////////////////////////////////////////////////////
    public UIDManager()
    {
        _highestUID = UID_Invalid + 1;
    } // UIDManager
    
    ///////////////////////////////////////////////////////////////
    // Public methods
    ///////////////////////////////////////////////////////////////
    
    /** Informs the object that the specified UID is in use.  If the
      * UID is greater than the current highest UID, than that UID
      * becomes the highest UID.
      *
      * @param newUID The UID to add.
      */
    public void addUID(int newUID)
    {
        if (newUID > _highestUID)
            _highestUID = newUID;
    } // addUID
    
    /** Returns a new UID, or UID_Invalid if no UIDs are available.
      *
      * @return New UID or UID_Invalid.
      */
    public int getUID()
    {
        if (_highestUID < 0x7fffffff) // 0x7fffffff is highest possible int according to Java spec
        {
            _highestUID++;
            return _highestUID;
        }
        else
            return UID_Invalid;
    } // getUID
}
