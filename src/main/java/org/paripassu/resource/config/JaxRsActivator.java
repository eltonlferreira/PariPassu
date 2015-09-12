/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.paripassu.resource.config;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Somente para ativação do REST-WS
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
		resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
		resources.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
		addRestResourceClasses(resources);
		return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(org.paripassu.resource.TicketRestController.class);
	}

}
