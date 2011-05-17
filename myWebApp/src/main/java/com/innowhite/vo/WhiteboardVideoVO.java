package com.innowhite.vo;

import java.io.DataOutputStream;

public class WhiteboardVideoVO {

    
    private DataOutputStream os;
    private int id;
    
    public WhiteboardVideoVO(DataOutputStream os,int id ){
	this.os = os;
	this.id=id;
    }
    
    public DataOutputStream getOs() {
        return os;
    }
   
    public int getId() {
        return id;
    }
   
  
}
