/*
 * Copyright 2004-2013 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.scaffolding.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.web.pages.GroovyPageTemplate;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
import org.codehaus.groovy.grails.web.servlet.view.GroovyPageView;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.Assert;

/**
 * A special Spring View for scaffolding that renders an in-memory scaffolded view to the response.
 *
 * @author Graeme Rocher
 * @since 0.5
 */
public class ScaffoldedGroovyPageView extends GroovyPageView {

	private String contents;
	protected static final Log log = LogFactory.getLog(ScaffoldedGroovyPageView.class);

	public ScaffoldedGroovyPageView(String uri, String contents) {
		Assert.hasLength(contents, "Argument [contents] cannot be blank or null");
		Assert.hasLength(uri, "Argument [uri] cannot be blank or null");

		this.contents = contents;
		setUrl(uri);
	}

	/**
	 * Used for debug reporting.
	 *
	 * @return The URL of the view
	 */
	@Override
	public String getBeanName() {
		return getUrl();
	}

	/**
	 * Overrides the default implementation to render a GSP view using an in-memory representation
	 * held in the #contents property.
	 *
	 * @param model The model
	 * @param response The HttpServletResponse instance
	 */
    @Override
    protected void renderTemplate(Map<String, Object> model, GrailsWebRequest webRequest, HttpServletRequest request,
            HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Rendering scaffolded view [" + getUrl() + "] with model [" + model + "]");
		}
		super.renderTemplate(model, webRequest, request, response);
	}

	@Override
	protected void initTemplate() throws IOException {
		String pageName = getUrl();
		template = templateEngine.createTemplate(new ByteArrayResource(contents.getBytes(StandardCharsets.UTF_8), pageName), false);
		if (template instanceof GroovyPageTemplate) {
			((GroovyPageTemplate)template).setAllowSettingContentType(true);
		}
	}
}
