package com.playlife.legcoresult.persistence.domainobjects;


public enum Type_UserRole {
	UNKNOWN(-1), USER(0), ADMIN(1);
	
	Type_UserRole(int value){
	}
	
	public static Type_UserRole fromInt(int value) {    
         switch(value) {
             case 0: return USER;
             case 1: return ADMIN;
             default:
                     return UNKNOWN;
         }
    }
	
	public static Type_UserRole fromString(String s){
		if (s.equalsIgnoreCase("admin"))
			return ADMIN;
		else if (s.equalsIgnoreCase("user"))
			return USER;
		else
			return UNKNOWN;
	}
}