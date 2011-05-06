/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.innowhite.documentManager;

/**
 * Class Presentation.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Presentation implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _userName.
     */
    private java.lang.String _userName;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Field _source.
     */
    private java.lang.String _source;

    /**
     * Field _thumbNailsource.
     */
    private java.lang.String _thumbNailsource;

    /**
     * Field _documentName.
     */
    private java.lang.String _documentName;

    /**
     * Field _documentTitle.
     */
    private java.lang.String _documentTitle;

    /**
     * Field _imageID.
     */
    private long _imageID;

    /**
     * keeps track of state for field: _imageID
     */
    private boolean _has_imageID;

    /**
     * Field _size.
     */
    private long _size;

    /**
     * keeps track of state for field: _size
     */
    private boolean _has_size;

    /**
     * Field _uploadedDate.
     */
    private java.util.Date _uploadedDate;

    /**
     * Field _photoList.
     */
    private java.util.Vector<Photo> _photoList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Presentation() {
        super();
        this._photoList = new java.util.Vector<Photo>();
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
            final Photo vPhoto)
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
            final Photo vPhoto)
    throws java.lang.IndexOutOfBoundsException {
        this._photoList.add(index, vPhoto);
    }

    /**
     */
    public void deleteImageID(
    ) {
        this._has_imageID= false;
    }

    /**
     */
    public void deleteSize(
    ) {
        this._has_size= false;
    }

    /**
     * Method enumeratePhoto.
     * 
     * @return an Enumeration over all
     * Photo elements
     */
    public java.util.Enumeration<? extends Photo> enumeratePhoto(
    ) {
        return this._photoList.elements();
    }

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'documentName'.
     * 
     * @return the value of field 'DocumentName'.
     */
    public java.lang.String getDocumentName(
    ) {
        return this._documentName;
    }

    /**
     * Returns the value of field 'documentTitle'.
     * 
     * @return the value of field 'DocumentTitle'.
     */
    public java.lang.String getDocumentTitle(
    ) {
        return this._documentTitle;
    }

    /**
     * Returns the value of field 'imageID'.
     * 
     * @return the value of field 'ImageID'.
     */
    public long getImageID(
    ) {
        return this._imageID;
    }

    /**
     * Method getPhoto.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * Photo at the given index
     */
    public Photo getPhoto(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._photoList.size()) {
            throw new IndexOutOfBoundsException("getPhoto: Index value '" + index + "' not in range [0.." + (this._photoList.size() - 1) + "]");
        }

        return (Photo) _photoList.get(index);
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
    public Photo[] getPhoto(
    ) {
        Photo[] array = new Photo[0];
        return (Photo[]) this._photoList.toArray(array);
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
     * Returns the value of field 'size'.
     * 
     * @return the value of field 'Size'.
     */
    public long getSize(
    ) {
        return this._size;
    }

    /**
     * Returns the value of field 'source'.
     * 
     * @return the value of field 'Source'.
     */
    public java.lang.String getSource(
    ) {
        return this._source;
    }

    /**
     * Returns the value of field 'thumbNailsource'.
     * 
     * @return the value of field 'ThumbNailsource'.
     */
    public java.lang.String getThumbNailsource(
    ) {
        return this._thumbNailsource;
    }

    /**
     * Returns the value of field 'uploadedDate'.
     * 
     * @return the value of field 'UploadedDate'.
     */
    public java.util.Date getUploadedDate(
    ) {
        return this._uploadedDate;
    }

    /**
     * Returns the value of field 'userName'.
     * 
     * @return the value of field 'UserName'.
     */
    public java.lang.String getUserName(
    ) {
        return this._userName;
    }

    /**
     * Method hasImageID.
     * 
     * @return true if at least one ImageID has been added
     */
    public boolean hasImageID(
    ) {
        return this._has_imageID;
    }

    /**
     * Method hasSize.
     * 
     * @return true if at least one Size has been added
     */
    public boolean hasSize(
    ) {
        return this._has_size;
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
            final Photo vPhoto) {
        boolean removed = _photoList.remove(vPhoto);
        return removed;
    }

    /**
     * Method removePhotoAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public Photo removePhotoAt(
            final int index) {
        java.lang.Object obj = this._photoList.remove(index);
        return (Photo) obj;
    }

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'documentName'.
     * 
     * @param documentName the value of field 'documentName'.
     */
    public void setDocumentName(
            final java.lang.String documentName) {
        this._documentName = documentName;
    }

    /**
     * Sets the value of field 'documentTitle'.
     * 
     * @param documentTitle the value of field 'documentTitle'.
     */
    public void setDocumentTitle(
            final java.lang.String documentTitle) {
        this._documentTitle = documentTitle;
    }

    /**
     * Sets the value of field 'imageID'.
     * 
     * @param imageID the value of field 'imageID'.
     */
    public void setImageID(
            final long imageID) {
        this._imageID = imageID;
        this._has_imageID = true;
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
            final Photo vPhoto)
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
            final Photo[] vPhotoArray) {
        //-- copy array
        _photoList.clear();

        for (int i = 0; i < vPhotoArray.length; i++) {
                this._photoList.add(vPhotoArray[i]);
        }
    }

    /**
     * Sets the value of field 'size'.
     * 
     * @param size the value of field 'size'.
     */
    public void setSize(
            final long size) {
        this._size = size;
        this._has_size = true;
    }

    /**
     * Sets the value of field 'source'.
     * 
     * @param source the value of field 'source'.
     */
    public void setSource(
            final java.lang.String source) {
        this._source = source;
    }

    /**
     * Sets the value of field 'thumbNailsource'.
     * 
     * @param thumbNailsource the value of field 'thumbNailsource'.
     */
    public void setThumbNailsource(
            final java.lang.String thumbNailsource) {
        this._thumbNailsource = thumbNailsource;
    }

    /**
     * Sets the value of field 'uploadedDate'.
     * 
     * @param uploadedDate the value of field 'uploadedDate'.
     */
    public void setUploadedDate(
            final java.util.Date uploadedDate) {
        this._uploadedDate = uploadedDate;
    }

    /**
     * Sets the value of field 'userName'.
     * 
     * @param userName the value of field 'userName'.
     */
    public void setUserName(
            final java.lang.String userName) {
        this._userName = userName;
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
     * Presentation
     */
    public static Presentation unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (Presentation) org.exolab.castor.xml.Unmarshaller.unmarshal(Presentation.class, reader);
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
