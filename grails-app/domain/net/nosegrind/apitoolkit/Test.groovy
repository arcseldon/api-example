package net.nosegrind.apitoolkit

import java.io.Serializable;

class Test implements Serializable {

	String testdata
	
	static mapping = {
		datasource 'user'
	}
	
    static constraints = {
		testdata blank: false
    }
}