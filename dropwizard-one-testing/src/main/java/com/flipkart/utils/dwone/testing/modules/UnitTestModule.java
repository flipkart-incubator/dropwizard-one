package com.flipkart.utils.dwone.testing.modules;

import com.flipkart.utils.dwone.testing.junit.AbstractUnitTest;
import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnitTestModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(AbstractUnitTest.class);
    }
}
