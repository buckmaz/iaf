/*
   Copyright 2020, 2021 WeAreFrank!

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
package nl.nn.adapterframework.core;

import java.util.Map;
import java.util.Set;

/**
 * Interface that can be implemented by Listeners that provide their own management of
 * messages processed and in error.
 * 
 * @see IUsesInProcessState
 */
public interface IHasProcessState<M> {

	/**
	 * Provides the set of ProcessStates used by this listener.
	 */
	public Set<ProcessState> knownProcessStates();
	
	/**
	 * Provides the set of ProcessStates that a message in the specified state can be moved to, e.g. from a MessageBrowser for that state.
	 */
	public Map<ProcessState,Set<ProcessState>> targetProcessStates();

	/**
	 * Change the processState of the message to the specified state, if that state is supported. If it is not supported, nothing changes, and <code>false</code> is returned.
	 * If it returns <code>true</code>, this signals that the active transaction must be committed to make other threads aware of the state change.
	 */
	public boolean changeProcessState(M message, ProcessState toState, Map<String,Object> context) throws ListenerException;

}