package net.nosegrind

import org.springframework.security.access.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import net.nosegrind.apitoolkit.*
import net.nosegrind.apitoolkit.Api
import net.nosegrind.apitoolkit.ApiStatuses

@Secured(['ROLE_ADMIN','ROLE_USER'])
@Transactional(readOnly = true)
class TestController {

    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def apiToolkitService
	ApiStatuses error = new ApiStatuses()
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Test.list(params), model:[testInstanceCount: Test.count()]
    }

	@Secured(['ROLE_ADMIN','ROLE_USER'])
	@Api(method=['OPTIONS','HEAD','GET'],apiRoles=['ROLE_ADMIN','ROLE_USER'],hookRoles=['ROLE_ADMIN','ROLE_USER'])
	def fred() {
		if(apiToolkitService.isApiCall()){
			respond Test.get(params.id)
			return
		}
	}
	
	@Secured(['ROLE_ADMIN','ROLE_USER'])
	@Api(method=['OPTIONS','HEAD','GET'],apiRoles=['ROLE_ADMIN','ROLE_USER'],hookRoles=['ROLE_ADMIN','ROLE_USER'])
    def show() {
		println("show called...")
		respond Test.get(params.id)
		return
    }

    def create() {
        respond new Test(params)
    }

    @Transactional
    def save(Test testInstance) {
        if (testInstance == null) {
            notFound()
            return
        }

        if (testInstance.hasErrors()) {
            respond testInstance.errors, view:'create'
            return
        }

        testInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'testInstance.label', default: 'Test'), testInstance.id])
                redirect testInstance
            }
            '*' { respond testInstance, [status: CREATED] }
        }
    }

    def edit(Test testInstance) {
        respond testInstance
    }

    @Transactional
	@Secured(['ROLE_ADMIN','ROLE_USER'])
	@Api(method=['OPTIONS','HEAD','PUT'],apiRoles=['ROLE_ADMIN','ROLE_USER'],hookRoles=['ROLE_ADMIN','ROLE_USER'])
    def update(Test testInstance) {
		println("update called...")
		if(apiToolkitService.isApiCall()){
			println("is api...")
			testInstance.testdata = params.testdata

	        if (testInstance == null){
				error._404_NOT_FOUND().send()
				return
	        }
	
	        if (testInstance.hasErrors()) {
				error._404_NOT_FOUND().send()
				return
	        }
	
	        testInstance.save flush:true
			println("instance saved...")
			apiToolkitService.callHook('test',testInstance,'update')
			respond Test.get(testInstance.id)
			return
		}

    }

    @Transactional
    def delete() {
		Test testInstance = new Test(params)
        if (testInstance == null) {
            notFound()
            return
        }

        testInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Test.label', default: 'Test'), testInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'testInstance.label', default: 'Test'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	@Secured('permitAll')
	def test(){
		println(params)
	}
}
