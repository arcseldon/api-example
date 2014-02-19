package net.nosegrind.apitoolkit

import java.io.Serializable
import java.util.Date

class Hook implements Serializable {

	static transactional = true
	static hasMany = [roles:HookRole]
	
	Person user
	String callback
	String format = 'JSON'
	String service
	Long attempts = 0
	Date dateCreated
	Date lastModified = new Date()

	static mapping = {
		datasource 'user'
	}
	
	static constraints = {
		user()
		callback()
		format()
		service()
		attempts()
	}
}
