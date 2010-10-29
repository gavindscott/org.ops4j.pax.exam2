/*
 * Copyright (C) 2010 Okidokiteam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.exam.spi.probesupport;

import java.io.IOException;
import java.io.InputStream;
import org.ops4j.pax.exam.TestAddress;
import org.ops4j.pax.exam.TestProbeProvider;
import org.ops4j.store.Handle;
import org.ops4j.store.Store;

/**
 * Static local provider.
 */
public class DefaultTestProbeProvider implements TestProbeProvider
{

    private TestAddress[] m_tests;
    private Handle m_probe;
    private Store<InputStream> m_store;

    public DefaultTestProbeProvider( TestAddress[] tests, Store<InputStream> store, Handle probe )
    {
        m_tests = tests;
        m_store = store;
        m_probe = probe;
    }

    public TestAddress[] getTests()
    {
        return m_tests;
    }

    public InputStream getStream()
        throws IOException
    {
        return m_store.load( m_probe );
    }
}