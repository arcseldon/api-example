package net.nosegrind.apitoolkit


import java.io.Serializable
import java.util.Date

class Person implements Serializable {
	
	transient hasBeforeInsert = false
	transient hasBeforeValidate = false
	transient hasBeforeUpdate = false
	transient springSecurityService
	
	String username
	String password
	boolean enabled = true
	boolean accountExpired=false
	boolean accountLocked=false
	boolean passwordExpired=false
	String email
	
	static transients = ['springSecurityService']
	
	static constraints = {
		username(blank:false,unique:true,maxSize:150)
		password(blank:false,maxSize:100)
		email(nullable:false,email:true, unique: true,maxSize:100)
	}
	
	static mapping = {
		datasource 'user'
		password column: '`password`'
	}
	
	Set<Role> getAuthorities() {
		PersonRole.findAllByPerson(this).collect { it.role } as Set
	}

	def beforeInsert() {
        if (!hasBeforeInsert) {
            hasBeforeInsert = true
			encodePassword()
        }
	}
	
	def afterInsert() {
		hasBeforeInsert = false
	}

	def beforeUpdate() {
		if (!hasBeforeUpdate) {
			if (isDirty('password')) {
				hasBeforeUpdate = true
				encodePassword()
			}
		}
	}

	def afterUpdate() {
		hasBeforeUpdate = false
	}
	
	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}

