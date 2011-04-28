/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3.1</a>, using an XML
 * Schema.
 * $Id$
 */

package com.innowhite.documentManager;

/**
 * Class Gallery.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Gallery implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _photos.
     */
    private com.innowhite.documentManager.Photos _photos;

    /**
     * Field _exercises.
     */
    private com.innowhite.documentManager.Exercises _exercises;

    /**
     * Field _presentations.
     */
    private com.innowhite.documentManager.Presentations _presentations;


      //----------------/
     //- Constructors -/
    //----------------/

    public Gallery() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'exercises'.
     * 
     * @return the value of field 'Exercises'.
     */
    public com.innowhite.documentManager.Exercises getExercises(
    ) {
        return this._exercises;
    }

    /**
     * Returns the value of field 'photos'.
     * 
     * @return the value of field 'Photos'.
     */
    public com.innowhite.documentManager.Photos getPhotos(
    ) {
        return this._photos;
    }

    /**
     * Returns the value of field 'presentations'.
     * 
     * @return the value of field 'Presentations'.
     */
    public com.innowhite.documentManager.Presentations getPresentations(
    ) {
        return this._presentations;
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
     * Sets the value of field 'exercises'.
     * 
     * @param exercises the value of field 'exercises'.
     */
    public void setExercises(
            final com.innowhite.documentManager.Exercises exercises) {
        this._exercises = exercises;
    }

    /**
     * Sets the value of field 'photos'.
     * 
     * @param photos the value of field 'photos'.
     */
    public void setPhotos(
            final com.innowhite.documentManager.Photos photos) {
        this._photos = photos;
    }

    /**
     * Sets the value of field 'presentations'.
     * 
     * @param presentations the value of field 'presentations'.
     */
    public void setPresentations(
            final com.innowhite.documentManager.Presentations presentations) {
        this._presentations = presentations;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled com.innowhite.documentManager.Gallery
     */
    public static com.innowhite.documentManager.Gallery unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (com.innowhite.documentManager.Gallery) org.exolab.castor.xml.Unmarshaller.unmarshal(com.innowhite.documentManager.Gallery.class, reader);
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
