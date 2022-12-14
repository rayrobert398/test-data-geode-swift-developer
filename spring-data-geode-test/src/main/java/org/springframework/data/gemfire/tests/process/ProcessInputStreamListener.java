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

package org.springframework.data.gemfire.tests.process;

import java.util.EventListener;

/**
 * The {@link ProcessInputStreamListener} is a callback interface that gets called when input arrives from either a
 * {@link Process process's} standard output steam or standard error stream.
 *
 * @author John Blum
 * @see java.util.EventListener
 * @since 0.0.1
 */
public interface ProcessInputStreamListener extends EventListener {

	/**
	 * Callback method that gets called when the {@link Process} sends output from either its standard out
	 * or standard error streams.
	 *
	 * @param input {@link String} containing output from the {@link Process} that this listener is listening to.
	 * @see java.lang.Process#getErrorStream()
	 * @see java.lang.Process#getInputStream()
	 */
	void onInput(String input);

}
