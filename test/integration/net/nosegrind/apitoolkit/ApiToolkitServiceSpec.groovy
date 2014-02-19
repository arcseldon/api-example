package net.nosegrind.apitoolkit

import grails.test.mixin.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpSession
import org.springframework.mock.web.MockServletContext

import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.context.request.RequestContextHolder as RCH
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.codehaus.groovy.grails.test.support.GrailsTestAutowirer
import org.codehaus.groovy.grails.test.support.GrailsTestTransactionInterceptor
import org.codehaus.groovy.grails.test.support.GrailsTestRequestEnvironmentInterceptor

import grails.util.Holders
import org.springframework.web.util.WebUtils

import javax.annotation.Resource
import spock.lang.*
import spock.lang.Specification
import grails.test.spock.IntegrationSpec

import net.nosegrind.apitoolkit.ApiToolkitService

//@TestFor(net.nosegrind.TestController)
class ApiToolkitServiceSpec extends IntegrationSpec {

	//def grailsApplication
	ApiToolkitService apiToolkitService
	
	def "Test api call"() {
		setup:
			//grails.util.Holders.getGrailsApplication().config.apitoolkit.apiName = 'api'
			//grails.util.Holders.getGrailsApplication().config.grails.app.context = '/'
			
			String uri = "api_0.1/test/show/1" as String
			
			//controller.show()
			
			MockHttpServletRequest request = new MockHttpServletRequest();
			request.setScheme("http");
			request.setServerName("localhost");
			request.setServerPort(8080);
			request.setContextPath(uri);
			request.setRequestURI(uri);
			//request.setQueryString(queryString != null ? queryString : "");
			request.setRemoteAddr("127.0.0.1");
			request.setServletPath(uri);
			request.setContentType("application/json");
		 
			request.setMethod("GET");
			//request.setBodyContent(body);
		 
	        //MockServletContext context = new MockServletContext();
	        //MockHttpSession session = new MockHttpSession();
	        //session.setupServletContext(context);
			//request.setSession(session);
		 
			//request.setUserPrincipal(null);
			//def servletContext = RequestContextHolder.requestAttributes.servletContext
			def mockContext = new MockServletContext()
			ServletContextHolder.servletContext = mockContext
			//servletContextService.servletContext = mockContext
			
			GrailsWebRequest webRequest = new GrailsWebRequest(request, new MockHttpServletResponse(), ServletContextHolder.servletContext)
			request.setAttribute(GrailsApplicationAttributes.WEB_REQUEST, webRequest)
			//request.setForwardURI(uri);
			RCH.setRequestAttributes(webRequest)
		when:
			def api=apiToolkitService.isApiCall()
		then:
			api==true
    }
	
	def "Test bad api call with id"(){}
	
	def "Test good api cAll with path"(){}
	
	def "Test bad api call with path"(){}

	
}
