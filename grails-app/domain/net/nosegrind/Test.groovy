package net.nosegrind

import java.io.Serializable;

class Test implements Serializable {

	String testdata
	
    static constraints = {
		testdata blank: false
    }
}
