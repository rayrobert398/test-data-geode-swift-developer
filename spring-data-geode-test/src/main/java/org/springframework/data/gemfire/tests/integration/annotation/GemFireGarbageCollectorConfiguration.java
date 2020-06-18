/*
 *  Copyright 2019 the original author or authors.
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
package org.springframework.data.gemfire.tests.integration.annotation;

import java.lang.annotation.Annotation;
import java.util.Optional;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.gemfire.config.annotation.support.AbstractAnnotationConfigSupport;
import org.springframework.data.gemfire.tests.integration.context.event.GemFireGarbageCollectorApplicationListener;
import org.springframework.data.gemfire.util.ArrayUtils;
import org.springframework.lang.NonNull;
import org.springframework.test.context.event.AfterTestClassEvent;

/**
 * Spring {@link Configuration} class used to register beans that collect garbage and other resources irresponsibly
 * left behind by Apache Geode when its processes shutdown, even in a test context.
 *
 * @author John Blum
 * @see java.lang.annotation.Annotation
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.ImportAware
 * @see org.springframework.core.type.AnnotationMetadata
 * @see org.springframework.data.gemfire.config.annotation.support.AbstractAnnotationConfigSupport
 * @see org.springframework.data.gemfire.tests.integration.context.event.GemFireGarbageCollectorApplicationListener
 * @since 0.0.17
 */
@Configuration
@SuppressWarnings("unused")
public class GemFireGarbageCollectorConfiguration extends AbstractAnnotationConfigSupport implements ImportAware {

	public static final boolean DEFAULT_CLEAN_DISK_STORE_FILES = false;

	private boolean tryCleanDiskStoreFiles = DEFAULT_CLEAN_DISK_STORE_FILES;

	@SuppressWarnings("unchecked")
	private Class<? extends ApplicationEvent>[] gemfireGarbageCollectorEventTypes =
		new Class[] { AfterTestClassEvent.class };

	@Override
	@SuppressWarnings("unchecked")
	public void setImportMetadata(@NonNull AnnotationMetadata importMetadata) {

		Optional.of(importMetadata)
			.filter(this::isAnnotationPresent)
			.map(this::getAnnotationAttributes)
			.ifPresent(enableGemFireGarbageCollectorAttributes -> {

				this.gemfireGarbageCollectorEventTypes = (Class<? extends ApplicationEvent>[])
					enableGemFireGarbageCollectorAttributes.getClassArray("collectOnEvents");

				this.tryCleanDiskStoreFiles =
					enableGemFireGarbageCollectorAttributes.getBoolean("tryCleanDiskStoreFiles");
			});
	}

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return EnableGemFireGarbageCollector.class;
	}

	@SuppressWarnings("unchecked")
	protected @NonNull Class<? extends ApplicationEvent>[] getGemFireGarbageCollectorEventTypes() {
		return ArrayUtils.nullSafeArray(this.gemfireGarbageCollectorEventTypes, Class.class);
	}

	protected boolean isTryCleanDiskStoreFiles() {
		return this.tryCleanDiskStoreFiles;
	}

	@Bean
	ApplicationListener<ApplicationEvent> gemfireGarbageCollectorApplicationListener() {
		return GemFireGarbageCollectorApplicationListener.create(getGemFireGarbageCollectorEventTypes())
			.tryCleanDiskStoreFiles(isTryCleanDiskStoreFiles());
	}
}
