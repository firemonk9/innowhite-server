package com.innowhite.red5.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class ShapeEventsVO implements Serializable {
	String actionType;
	String objName;
	String objType;
	float xpos;
	float ypos;
	float x1;
	float y1;
	float x2;
	float y2;
	float shpHeight;
	float shpWidth;
	float ma;
	float mb;
	float mc;
	float md;
	float mtx;
	float mty;
	float mainscalex;
	float mainscaley;
	String bordercolor;
	String fillcolor;
	String secondName;
	int rotation;
	

	long objDate;
	int penthickness;
	int versionNumber;
	ArrayList elems;
	String sprText;
	String txtType;
	String txtFont;
	String imageURL;
	int seq;
	int secondSeq;
	int wbNumber;
	String userId;

	
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getWbNumber() {
		return wbNumber;
	}

	public void setWbNumber(int wbNumber) {
		this.wbNumber = wbNumber;
	}

	public int getSecondSeq() {
		return secondSeq;
	}

	public void setSecondSeq(int secondSeq) {
		this.secondSeq = secondSeq;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	int txtSize;
	boolean txtBold;
	boolean txtItalic;
	boolean txtBorder;

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public long getObjDate() {
		return objDate;
	}

	public void setObjDate(long objDate) {
		this.objDate = objDate;
	}

	public float getMainscalex() {
		return mainscalex;
	}

	public void setMainscalex(float mainscalex) {
		this.mainscalex = mainscalex;
	}

	public float getMainscaley() {
		return mainscaley;
	}

	public void setMainscaley(float mainscaley) {
		this.mainscaley = mainscaley;
	}

	public void setElems(ArrayList value) {
		this.elems = value;
	}

	public ArrayList getElems() {
		return this.elems;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getTxtType() {
		return txtType;
	}

	public void setTxtType(String type) {
		txtType = type;
	}

	public String getSprText() {
		return sprText;
	}

	public void setSprText(String sprText) {
		this.sprText = sprText;
	}

	public boolean isTxtBold() {
		return txtBold;
	}

	public void setTxtBold(boolean txtBold) {
		this.txtBold = txtBold;
	}

	public boolean isTxtBorder() {
		return txtBorder;
	}

	public void setTxtBorder(boolean txtBorder) {
		this.txtBorder = txtBorder;
	}

	public String getTxtFont() {
		return txtFont;
	}

	public void setTxtFont(String txtFont) {
		this.txtFont = txtFont;
	}

	public boolean isTxtItalic() {
		return txtItalic;
	}

	public void setTxtItalic(boolean txtItalic) {
		this.txtItalic = txtItalic;
	}

	public int getTxtSize() {
		return txtSize;
	}

	public void setTxtSize(int txtSize) {
		this.txtSize = txtSize;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public float getShpHeight() {
		return shpHeight;
	}

	public void setShpHeight(float shpHeight) {
		this.shpHeight = shpHeight;
	}

	public float getShpWidth() {
		return shpWidth;
	}

	public void setShpWidth(float shpWidth) {
		this.shpWidth = shpWidth;
	}

	public String getBordercolor() {
		return bordercolor;
	}

	public void setBordercolor(String bordercolor) {
		this.bordercolor = bordercolor;
	}

	public String getFillcolor() {
		return fillcolor;
	}

	public void setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public int getPenthickness() {
		return penthickness;
	}

	public void setPenthickness(int penthickness) {
		this.penthickness = penthickness;
	}

	public float getMa() {
		return ma;
	}

	public void setMa(float ma) {
		this.ma = ma;
	}

	public float getMb() {
		return mb;
	}

	public void setMb(float mb) {
		this.mb = mb;
	}

	public float getMc() {
		return mc;
	}

	public void setMc(float mc) {
		this.mc = mc;
	}

	public float getMtx() {
		return mtx;
	}

	public void setMtx(float mtx) {
		this.mtx = mtx;
	}

	public float getMty() {
		return mty;
	}

	public void setMty(float mty) {
		this.mty = mty;
	}

	public float getMd() {
		return md;
	}

	public void setMd(float md) {
		this.md = md;
	}

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}

	public void setXpos(float xpos) {
		this.xpos = xpos;
	}

	public void setYpos(float ypos) {
		this.ypos = ypos;
	}

	public float getXpos() {
		return xpos;
	}

	public float getYpos() {
		return ypos;
	}

	

}
