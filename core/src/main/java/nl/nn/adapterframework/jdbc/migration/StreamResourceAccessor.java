/*
Copyright 2021 WeAreFrank!

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package nl.nn.adapterframework.jdbc.migration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import liquibase.resource.ResourceAccessor;

/**
 * @author alisihab
 *
 */
public class StreamResourceAccessor implements ResourceAccessor {

	private InputStream stream;
	
	public StreamResourceAccessor(InputStream stream) throws IOException {
		super();
		this.stream = stream;
	}
	
	@Override
	public Set<InputStream> getResourcesAsStream(String path) throws IOException {
		if(path.endsWith(".xsd")) {
			return null;
		}
		Set<InputStream> returnSet = new HashSet<>();
		returnSet.add(stream);
		return returnSet;
	}

	@Override
	public Set<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories, boolean recursive) throws IOException {
		return null;
	}

	@Override
	public ClassLoader toClassLoader() {
		return null;
	}

}