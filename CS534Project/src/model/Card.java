package model;

import java.io.Serializable;

public class Card implements Serializable{
	private static final long serialVersionUID = -1060057733364115011L;
	private Face frontFace;
    private Face backFace;
    
    public Card(Face frontFace, Face backFace) {
    	this.frontFace = frontFace;
    	this.backFace = backFace;
    }

	public Face getFrontFace() {
		return frontFace;
	}

	public void setFrontFace(Face frontFace) {
		this.frontFace = frontFace;
	}

	public Face getBackFace() {
		return backFace;
	}

	public void setBackFace(Face backFace) {
		this.backFace = backFace;
	}
}
