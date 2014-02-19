package net.nosegrind.apitoolkit



import org.apache.commons.lang.builder.HashCodeBuilder


class PersonRole implements Serializable {

	static transactional = true
	
	Person person
	Role role

	boolean equals(other) {
		if (!(other instanceof PersonRole)) {
			return false
		}

		other.person?.id == person?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (person) builder.append(person.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static PersonRole get(long userId, long roleId) {
		find 'from PersonRole where person.id=:userId and role.id=:roleId',
			[userId: userId, roleId: roleId]
	}

	static PersonRole create(Person user, Role role, boolean flush = true) {
		new PersonRole(person: user, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Person user, Role role, boolean flush = false) {
		PersonRole instance = PersonRole.findByPersonAndRole(user, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Person user) {
		executeUpdate 'DELETE FROM PersonRole WHERE person=:user', [user: user]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM PersonRole WHERE role=:role', [role: role]
	}

	static mapping = {
		datasource 'user'
	}
	
	/*
	static mapping = {
		id composite: ['role', 'user']
		version false
	}
	*/
}
