/*
 * Copyright (C) 2011 Harald Wellmann
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
package org.ops4j.pax.exam.regression.multi.inject;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.url;
import static org.ops4j.pax.exam.regression.multi.RegressionConfiguration.regressionDefaults;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.regression.pde.HelloService;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.exam.util.Filter;
import org.ops4j.pax.exam.util.PathUtils;
import org.ops4j.pax.swissbox.framework.ServiceLookup;
import org.osgi.framework.BundleContext;

@RunWith( JUnit4TestRunner.class )
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class FilterTest
{

    @Inject
    private BundleContext bundleContext;

    @Inject @Filter("(language=la)")
    private HelloService latinService;

    @Inject @Filter("(language=en)")
    private HelloService englishService;

    @Configuration( )
    public Option[] config()
    {
        return options(
            regressionDefaults(),
            url( "reference:file:" + PathUtils.getBaseDir() +
                    "/target/regression-pde-bundle.jar" ),
            systemProperty("osgi.console").value("6666"),
            junitBundles() );
    }

    @Test
    public void getServiceFromInjectedBundleContext()
    {
        assertThat( bundleContext, is( notNullValue() ) );
        Object service = ServiceLookup.getService( bundleContext, HelloService.class );
        assertThat( service, is( notNullValue() ) );
    }

    @Test
    public void getInjectedService()
    {
        assertThat( latinService, is( notNullValue() ) );
        assertThat( latinService.getMessage(), is( equalTo( "Pax Vobiscum!" ) ) );
        assertThat( englishService, is( notNullValue() ) );
        assertThat( englishService.getMessage(), is( equalTo( "Hello Pax!" ) ) );
    }
}
