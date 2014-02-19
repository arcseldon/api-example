import net.nosegrind.apitoolkit.Role
import net.nosegrind.apitoolkit.Person
import net.nosegrind.apitoolkit.PersonRole

import net.nosegrind.apitoolkit.ApiDescriptor;
import net.nosegrind.apitoolkit.ParamsDescriptor;
import net.nosegrind.apitoolkit.ErrorCodeDescriptor;
import net.nosegrind.apitoolkit.ApiStatuses;
import net.nosegrind.apitoolkit.ApiParams;
import grails.util.Environment

class BootStrap {

	def grailsApplication
	def apiCacheService

	
    def init = { servletContext ->
		environments {
			production {}
			development {
				Role rootRole = Role.findByAuthority('ROLE_ADMIN')?: new Role(authority:'ROLE_ADMIN').save(faileOnError:true)
				Role userRole = Role.findByAuthority('ROLE_USER')?: new Role(authority:'ROLE_USER').save(faileOnError:true)
		
				// DEFAULT ADMIN
				Person user = Person.findByUsername("${grailsApplication.config.root.login}")
				PersonRole.withTransaction(){ status ->
					Role adminRole = Role.findByAuthority("ROLE_ADMIN")
					if(!user?.id){
						user = new Person(username:"${grailsApplication.config.root.login}",password:"${grailsApplication.config.root.password}",email:"${grailsApplication.config.root.email}")
						user.save(flush:true,failOnError:true)
					}
				
					if(!user?.authorities?.contains(adminRole)){
						PersonRole.create user,adminRole
					}
					
					status.isCompleted()
				}
		
				/*
				 * BOOTSTRAPPING FOR APIDOCS/OPTIONS
				 */
				ApiStatuses error = new ApiStatuses()
				ApiParams param = new ApiParams()
		
				def test_show = new ApiDescriptor(
					"name":"Show Test Data",
					"description":'Show Test Data based upon given ID',
					"receives":[
						param._PKEY('id','Test Id','test').toObject(),
						param._STRING('testdata','Testdata').hasMockData('JSON').toObject(),
					],
					"errorcodes": [
						error._304_NOT_MODIFIED().toObject(),
					]
				)
				apiCacheService.setApiCache('test','show',test_show)
			}
			test{}
		}

    }
    def destroy = {
    }
}
