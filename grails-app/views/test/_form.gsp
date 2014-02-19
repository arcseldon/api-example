<%@ page import="net.nosegrind.Test" %>



<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'testdata', 'error')} required">
	<label for="testdata">
		<g:message code="test.testdata.label" default="Testdata" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="testdata" required="" value="${testInstance?.testdata}"/>
</div>

