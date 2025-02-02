/*
   Copyright 2013 Nationale-Nederlanden, 2020, 2022 WeAreFrank!

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
package nl.nn.adapterframework.receivers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import nl.nn.adapterframework.core.IListener;
import nl.nn.adapterframework.core.IMessageWrapper;
import nl.nn.adapterframework.core.ListenerException;
import nl.nn.adapterframework.stream.Message;

/**
 * Wrapper for messages that are not serializable.
 *
 * @author  Gerrit van Brakel
 * @since   4.3
 */
public class MessageWrapper<M> implements Serializable, IMessageWrapper {

	static final long serialVersionUID = -8251009650246241025L;

	private @Getter Map<String,Object> context = new LinkedHashMap<>();
	private @Getter Message message;
	private @Getter String id;

	public MessageWrapper() {
		super();
	}

	public MessageWrapper(Message message, String messageId) {
		this();
		this.message = message;
		this.id = messageId;
	}

	public MessageWrapper(M rawMessage, IListener<M> listener) throws ListenerException {
		this();
		message = listener.extractMessage(rawMessage, context);
		context.remove("originalRawMessage"); //PushingIfsaProviderListener.THREAD_CONTEXT_ORIGINAL_RAW_MESSAGE_KEY);
		id = listener.getIdFromRawMessage(rawMessage, context);
	}

	public void setId(String string) {
		id = string;
	}

	public void setMessage(Message message) {
		this.message = message;
	}


	/*
	 * this method is used by Serializable, to serialize objects to a stream.
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		message.preserve();
		if (message.isBinary()) {
			if (!(message.asObject() instanceof byte[])) {
				message = new Message(message.asByteArray(), message.getContext());
			}
		} else {
			if (!(message.asObject() instanceof String)) {
				message = new Message(message.asString(), message.getContext());
			}
		}
		stream.writeObject(context);
		stream.writeObject(id);
		stream.writeObject(message);
	}

}
