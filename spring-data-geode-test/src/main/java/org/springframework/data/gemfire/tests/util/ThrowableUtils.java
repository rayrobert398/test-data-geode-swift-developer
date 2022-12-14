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

package org.springframework.data.gemfire.tests.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The {@link ThrowableUtils} class is a utility class for working with {@link Throwable},
 * {@link Exception} and {@link Error} objects.
 *
 * @author John Blum
 * @see java.lang.Error
 * @see java.lang.Exception
 * @see java.lang.Throwable
 * @since 0.0.1
 */
@SuppressWarnings("unused")
public abstract class ThrowableUtils {

	public static String toString(Throwable throwable) {

		StringWriter writer = new StringWriter();

		throwable.printStackTrace(new PrintWriter(writer));

		return writer.toString();
	}
}
