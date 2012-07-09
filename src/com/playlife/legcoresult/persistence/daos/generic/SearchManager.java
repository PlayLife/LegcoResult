package com.playlife.legcoresult.persistence.daos.generic;

import com.google.appengine.api.search.Consistency;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;
import com.playlife.legcoresult.persistence.domainobjects.AppUser;

public class SearchManager {
	public static Document createUserDocument(AppUser user){
		Document doc = Document.newBuilder().setId(user.getUserId().toString())
						.addField(Field.newBuilder().setName("email").setText(user.getEmail()))
						.addField(Field.newBuilder().setName("username").setText(user.getUsername()))
						.addField(Field.newBuilder().setName("userRole").setText(user.getUserRole().toString())).build();
		return doc;
	}
	
	public static Index getUserIndex(){
		IndexSpec indexSpec = IndexSpec.newBuilder()
								.setName("user")
								.setConsistency(Consistency.PER_DOCUMENT)
								.build();
		return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}
}
