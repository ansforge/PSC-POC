package fr.ans.psc.client.democlientdam.model;

import lombok.Getter;
import lombok.Setter;

//@Setter
@Getter
public class Token {
	
	private HeaderToken header;
	private BodyToken body;
	private String signature;
	
	
	
	public Token(HeaderToken header, BodyToken body, String signature) {
		super();
		this.body = body;
		this.header = header;
		this.signature = signature;
	}



	@Override
	public String toString() {
		return "Token [body=" + body + ", header=" + header + ", signature=" + signature + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
