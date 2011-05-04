package com.innowhite.PlayBackWebApp.service;






//@Entity
//@Table(name="whiteboard_data")
public class WhiteboardData {

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	String roomName;
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
	
	float mainscalex;
	float mainscaley;
	String bordercolor;
	String fillcolor;
	String secondName;
	int rotation;
	

	long objDate;
	int penthickness;
	int versionNumber;
	
	// points is a string delimited with # and ,
	// the format is x,y#x,y#...
	//@Column(length=3000)
	String points;
	String sprText;
	String txtType;
	String txtFont;
	String imageURL;
	int seq;
	int secondSeq;
	int wbNumber;
	String userId;
	
	
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public float getXpos() {
		return xpos;
	}
	public void setXpos(Float xpos) {
		if(xpos.isNaN())
			xpos=1.0f;
		this.xpos = xpos;
	}
	public float getYpos() {
		return ypos;
	}
	public void setYpos(Float ypos) {
		if(ypos.isNaN())
			ypos=1.0f;
		this.ypos = ypos;
	}
	public float getX1() {
		return x1;
	}
	public void setX1(Float x1) {
		if(x1.isNaN())
			x1=1.0f;
		this.x1 = x1;
	}
	public float getY1() {
		return y1;
	}
	public void setY1(Float y1) {
		if(y1.isNaN())
			y1=1.0f;
		this.y1 = y1;
	}
	public float getX2() {
		return x2;
	}
	public void setX2(Float x2) {
		if(x2.isNaN())
			x2=1.0f;
		this.x2 = x2;
	}
	public float getY2() {
		return y2;
	}
	public void setY2(Float y2) {
		if(y2.isNaN())
			y2=1.0f;
		this.y2 = y2;
	}
	public float getShpHeight() {
		return shpHeight;
	}
	public void setShpHeight(Float shpHeight) {
		if(shpHeight.isNaN())
			shpHeight=1.0f;
		this.shpHeight = shpHeight;
	}
	public float getShpWidth() {
		return shpWidth;
	}
	public void setShpWidth(Float shpWidth) {
		if(shpWidth.isNaN())
			shpWidth=1.0f;
		this.shpWidth = shpWidth;
	}
	public float getMainscalex() {
		return mainscalex;
	}
	
	
	// setting rules if NaN set to 1
	public void setMainscalex(Float mainscalex) {
		if(mainscalex.isNaN())
			mainscalex=1.0f;
		
		System.err.println("mainscalex::"+mainscalex);
		this.mainscalex = mainscalex;
	}
	public float getMainscaley() {
		return mainscaley;
	}
	
	
	// setting rules if NaN set to 1
	public void setMainscaley(Float mainscaley) {
		if(mainscaley.isNaN())
			mainscaley=1.0f;
		this.mainscaley = mainscaley;
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
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public long getObjDate() {
		return objDate;
	}
	public void setObjDate(long objDate) {
		this.objDate = objDate;
	}
	public int getPenthickness() {
		return penthickness;
	}
	public void setPenthickness(int penthickness) {
		this.penthickness = penthickness;
	}
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	
	//@Column(length=3000)
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getSprText() {
		return sprText;
	}
	public void setSprText(String sprText) {
		this.sprText = sprText;
	}
	public String getTxtType() {
		return txtType;
	}
	public void setTxtType(String txtType) {
		this.txtType = txtType;
	}
	public String getTxtFont() {
		return txtFont;
	}
	public void setTxtFont(String txtFont) {
		this.txtFont = txtFont;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getSecondSeq() {
		return secondSeq;
	}
	public void setSecondSeq(int secondSeq) {
		this.secondSeq = secondSeq;
	}
	public int getWbNumber() {
		return wbNumber;
	}
	public void setWbNumber(int wbNumber) {
		this.wbNumber = wbNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	

	
}
