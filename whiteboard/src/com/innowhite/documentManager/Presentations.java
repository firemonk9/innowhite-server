/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.innowhite.documentManager;

/**
 * Class Presentations.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Presentations implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _presentationList.
     */
    private java.util.Vector<com.innowhite.documentManager.Presentation> _presentationList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Presentations() {
        super();
        this._presentationList = new java.util.Vector<com.innowhite.documentManager.Presentation>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPresentation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPresentation(
            final com.innowhite.documentManager.Presentation vPresentation)
    throws java.lang.IndexOutOfBoundsException {
        this._presentationList.addElement(vPresentation);
    }

    /**
     * 
     * 
     * @param index
     * @param vPresentation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPresentation(
            final int index,
            final com.innowhite.documentManager.Presentation vPresentation)
    throws java.lang.IndexOutOfBoundsException {
        this._presentationList.add(index, vPresentation);
    }

    /**
     * Method enumeratePresentation.
     * 
     * @return an Enumeration over all
     * com.innowhite.documentManager.Presentation elements
     */
    public java.util.Enumeration<? extends com.innowhite.documentManager.Presentation> enumeratePresentation(
    ) {
        return this._presentationList.elements();
    }

    /**
     * Method getPresentation.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.innowhite.documentManager.Presentation at the given index
     */
    public com.innowhite.documentManager.Presentation getPresentation(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._presentationList.size()) {
            throw new IndexOutOfBoundsException("getPresentation: Index value '" + index + "' not in range [0.." + (this._presentationList.size() - 1) + "]");
        }

        return (com.innowhite.documentManager.Presentation) _presentationList.get(index);
    }

    /**
     * Method getPresentation.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.innowhite.documentManager.Presentation[] getPresentation(
    ) {
        com.innowhite.documentManager.Presentation[] array = new com.innowhite.documentManager.Presentation[0];
        return (com.innowhite.documentManager.Presentation[]) this._presentationList.toArray(array);
    }

    /**
     * Method getPresentationCount.
     * 
     * @return the size of this collection
     */
    public int getPresentationCount(
    ) {
        return this._presentationList.size();
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     */
    public void removeAllPresentation(
    ) {
        this._presentationList.clear();
    }

    /**
     * Method removePresentation.
     * 
     * @param vPresentation
     * @return true if the object was removed from the collection.
     */
    public boolean removePresentation(
            final com.innowhite.documentManager.Presentation vPresentation) {
        boolean removed = _presentationList.remove(vPresentation);
        return removed;
    }

    /**
     * Method removePresentationAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.innowhite.documentManager.Presentation removePresentationAt(
            final int index) {
        java.lang.Object obj = this._presentationList.remove(index);
        return (com.innowhite.documentManager.Presentation) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPresentation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPresentation(
            final int index,
            final com.innowhite.documentManager.Presentation vPresentation)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._presentationList.size()) {
            throw new IndexOutOfBoundsException("setPresentation: Index value '" + index + "' not in range [0.." + (this._presentationList.size() - 1) + "]");
        }

        this._presentationList.set(index, vPresentation);
    }

    /**
     * 
     * 
     * @param vPresentationArray
     */
    public void setPresentation(
            final com.innowhite.documentManager.Presentation[] vPresentationArray) {
        //-- copy array
        _presentationList.clear();

        for (int i = 0; i < vPresentationArray.length; i++) {
                this._presentationList.add(vPresentationArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.innowhite.documentManager.Presentations
     */
    public static com.innowhite.documentManager.Presentations unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.innowhite.documentManager.Presentations) org.exolab.castor.xml.Unmarshaller.unmarshal(com.innowhite.documentManager.Presentations.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
