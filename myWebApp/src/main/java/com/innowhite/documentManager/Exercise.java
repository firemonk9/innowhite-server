/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.innowhite.documentManager;

/**
 * Class Exercise.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Exercise implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name.
     */
    private java.lang.String _name;

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
     * Field _imageFolderSeq.
     */
    private int _imageFolderSeq;

    /**
     * keeps track of state for field: _imageFolderSeq
     */
    private boolean _has_imageFolderSeq;

    /**
     * Field _documentName.
     */
    private java.lang.String _documentName;

    /**
     * Field _imageID.
     */
    private int _imageID;

    /**
     * keeps track of state for field: _imageID
     */
    private boolean _has_imageID;


      //----------------/
     //- Constructors -/
    //----------------/

    public Exercise() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteImageFolderSeq(
    ) {
        this._has_imageFolderSeq= false;
    }

    /**
     */
    public void deleteImageID(
    ) {
        this._has_imageID= false;
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
     * Returns the value of field 'imageFolderSeq'.
     * 
     * @return the value of field 'ImageFolderSeq'.
     */
    public int getImageFolderSeq(
    ) {
        return this._imageFolderSeq;
    }

    /**
     * Returns the value of field 'imageID'.
     * 
     * @return the value of field 'ImageID'.
     */
    public int getImageID(
    ) {
        return this._imageID;
    }

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
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
     * Method hasImageFolderSeq.
     * 
     * @return true if at least one ImageFolderSeq has been added
     */
    public boolean hasImageFolderSeq(
    ) {
        return this._has_imageFolderSeq;
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
     * Sets the value of field 'imageFolderSeq'.
     * 
     * @param imageFolderSeq the value of field 'imageFolderSeq'.
     */
    public void setImageFolderSeq(
            final int imageFolderSeq) {
        this._imageFolderSeq = imageFolderSeq;
        this._has_imageFolderSeq = true;
    }

    /**
     * Sets the value of field 'imageID'.
     * 
     * @param imageID the value of field 'imageID'.
     */
    public void setImageID(
            final int imageID) {
        this._imageID = imageID;
        this._has_imageID = true;
    }

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
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
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.innowhite.documentManager.Exercis
     */
    public static com.innowhite.documentManager.Exercise unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.innowhite.documentManager.Exercise) org.exolab.castor.xml.Unmarshaller.unmarshal(com.innowhite.documentManager.Exercise.class, reader);
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
