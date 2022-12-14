/*
 *  Copyright 2017-present the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.springframework.data.gemfire.tests.mock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.geode.cache.GemFireCache;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.AfterTestClassEvent;

/**
 * The {@link EnableGemFireMockObjects} annotation enables mocking of GemFire Objects in Unit Tests.
 *
 * @author John Blum
 * @see java.lang.annotation.Documented
 * @see java.lang.annotation.Inherited
 * @see java.lang.annotation.Retention
 * @see java.lang.annotation.Target
 * @see org.apache.geode.cache.GemFireCache
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.annotation.Import
 * @since 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(GemFireMockObjectsConfiguration.class)
@SuppressWarnings("unused")
public @interface EnableGemFireMockObjects {

	/**
	 * Configures the {@link Class type} of {@link ApplicationEvent ApplicationEvents} that will trigger all currently
	 * allocated GemFire/Geode {@link Object Mock Objects} to be destroyed.
	 *
	 * @return an array of {@link ApplicationEvent} {@link Class types} that will trigger all currently allocated
	 * GemFire/Geode {@link Object Mock Objects} to be destroyed.
	 * @see org.springframework.context.ApplicationEvent
	 * @see java.lang.Class
	 */
	Class<? extends ApplicationEvent>[] destroyOnEvents() default { AfterTestClassEvent.class };

	/**
	 * Configures whether the mock {@link GemFireCache} created for Unit Testing is a Singleton.
	 *
	 * Defaults to {@literal false}.
	 *
	 * @return a boolean value indicating whether the mock {@link GemFireCache} created for Unit Testing
	 * is a Singleton.
	 */
	boolean useSingletonCache() default GemFireMockObjectsConfiguration.DEFAULT_USE_SINGLETON_CACHE;

}
