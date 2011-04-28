/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.innowhite.documentManager;

/**
 * Class Photos.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Photos implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _photoList.
     */
    private java.util.Vector<com.innowhite.documentManager.Photo> _photoList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Photos() {
        super();
        this._photoList = new java.util.Vector<com.innowhite.documentManager.Photo>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPhoto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPhoto(
            final com.innowhite.documentManager.Photo vPhoto)
    throws java.lang.IndexOutOfBoundsException {
        this._photoList.addElement(vPhoto);
    }

    /**
     * 
     * 
     * @param index
     * @param vPhoto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPhoto(
            final int index,
            final com.innowhite.documentManager.Photo vPhoto)
    throws java.lang.IndexOutOfBoundsException {
        this._photoList.add(index, vPhoto);
    }

    /**
     * Method enumeratePhoto.
     * 
     * @return an Enumeration over all
     * com.innowhite.documentManager.Photo elements
     */
    public java.util.Enumeration<? extends com.innowhite.documentManager.Photo> enumeratePhoto(
    ) {
        return this._photoList.elements();
    }

    /**
     * Method getPhoto.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the com.innowhite.documentManager.Photo
     * at the given index
     */
    public com.innowhite.documentManager.Photo getPhoto(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._photoList.size()) {
            throw new IndexOutOfBoundsException("getPhoto: Index value '" + index + "' not in range [0.." + (this._photoList.size() - 1) + "]");
        }

        return (com.innowhite.documentManager.Photo) _photoList.get(index);
    }

    /**
     * Method getPhoto.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public com.innowhite.documentManager.Photo[] getPhoto(
    ) {
        com.innowhite.documentManager.Photo[] array = new com.innowhite.documentManager.Photo[0];
        return (com.innowhite.documentManager.Photo[]) this._photoList.toArray(array);
    }

    /**
     * Method getPhotoCount.
     * 
     * @return the size of this collection
     */
    public int getPhotoCount(
    ) {
        return this._photoList.size();
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
    public void removeAllPhoto(
    ) {
        this._photoList.clear();
    }

    /**
     * Method removePhoto.
     * 
     * @param vPhoto
     * @return true if the object was removed from the collection.
     */
    public boolean removePhoto(
            final com.innowhite.documentManager.Photo vPhoto) {
        boolean removed = _photoList.remove(vPhoto);
        return removed;
    }

    /**
     * Method removePhotoAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public com.innowhite.documentManager.Photo removePhotoAt(
            final int index) {
        java.lang.Object obj = this._photoList.remove(index);
        return (com.innowhite.documentManager.Photo) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPhoto
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPhoto(
            final int index,
            final com.innowhite.documentManager.Photo vPhoto)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._photoList.size()) {
            throw new IndexOutOfBoundsException("setPhoto: Index value '" + index + "' not in range [0.." + (this._photoList.size() - 1) + "]");
        }

        this._photoList.set(index, vPhoto);
    }

    /**
     * 
     * 
     * @param vPhotoArray
     */
    public void setPhoto(
            final com.innowhite.documentManager.Photo[] vPhotoArray) {
        //-- copy array
        _photoList.clear();

        for (int i = 0; i < vPhotoArray.length; i++) {
                this._photoList.add(vPhotoArray[i]);
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
     * @return the unmarshaled com.innowhite.documentManager.Photos
     */
    public static com.innowhite.documentManager.Photos unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.innowhite.documentManager.Photos) org.exolab.castor.xml.Unmarshaller.unmarshal(com.innowhite.documentManager.Photos.class, reader);
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
