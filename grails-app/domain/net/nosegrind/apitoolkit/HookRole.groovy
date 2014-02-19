package net.nosegrind.apitoolkit

import java.io.Serializable
import java.util.Date

class HookRole implements Serializable {
	Hook hook
	Role role
	Date dateCreated
	Date lastModified = new Date()

	static constraints = {
		hook(nullable:false)
		role(nullable:false)
	}
	
	static mapping = {
		datasource 'user'
	}
}
