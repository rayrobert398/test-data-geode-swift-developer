/*
 *  Copyright 2018 the original author or authors.
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
package org.springframework.data.gemfire;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.Pool;
import org.apache.geode.cache.client.PoolManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit Tests for registering the {@link ClientCache} {@literal DEFAULT} {@link Pool} with the {@link PoolManager}.
 *
 * @author John Blum
 * @see org.apache.geode.cache.client.ClientCache
 * @see org.apache.geode.cache.client.Pool
 * @see org.apache.geode.cache.client.PoolManager
 * @see org.springframework.data.gemfire.config.annotation.ClientCacheApplication
 * @see org.springframework.data.gemfire.config.annotation.EnablePool
 * @see org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects
 * @see org.springframework.test.annotation.DirtiesContext
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringRunner
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@SuppressWarnings("unused")
public class MockClientCacheDefaultPoolRegisteredWithPoolManagerUnitTests {

	@Autowired
	private Pool defaultPool;

	@AfterClass
	public static void tearDown() {
		//assertThat(PoolManager.find("DEFAULT")).isNull();
	}

	@Before
	public void setup() {

		assertThat(this.defaultPool).isNotNull();
		assertThat(this.defaultPool.getName()).isEqualTo("DEFAULT");
	}

	@Test
	@DirtiesContext
	@Ignore("Apache Geode/Pivotal GemFire does not support Mock Pools")
	public void defaultPoolRegisteredWithPoolManager() {

		Pool geodeDefaultPool = PoolManager.find("DEFAULT");

		assertThat(geodeDefaultPool).isNotNull();
		assertThat(geodeDefaultPool.getName()).isEqualTo("DEFAULT");
		assertThat(geodeDefaultPool).isSameAs(this.defaultPool);
	}

	@ClientCacheApplication
	@EnableGemFireMockObjects
	@EnablePool(name = "DEFAULT")
	static class TestConfiguration { }

}
