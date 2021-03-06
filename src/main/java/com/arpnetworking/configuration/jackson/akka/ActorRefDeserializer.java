/**
 * Copyright 2015 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arpnetworking.configuration.jackson.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.arpnetworking.logback.annotations.LogValue;
import com.arpnetworking.steno.LogValueMapFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Deserializer for an Akka ActorRef.
 *
 * @author Brandon Arp (brandonarp at gmail dot com)
 */
public class ActorRefDeserializer extends JsonDeserializer<ActorRef> {
    /**
     * Public constructor.
     *
     * @param system actor system used to resolve references
     */
    public ActorRefDeserializer(final ActorSystem system) {
        _system = system;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActorRef deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        return _system.provider().resolveActorRef(p.getValueAsString());
    }

    /**
     * Generate a Steno log compatible representation.
     *
     * @return Steno log compatible representation.
     */
    @LogValue
    public Object toLogValue() {
        return LogValueMapFactory.builder(this)
                .put("actorSystem", _system)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toLogValue().toString();
    }

    private final ActorSystem _system;
}
