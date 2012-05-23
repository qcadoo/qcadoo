package com.qcadoo.plugin.internal.aop;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.plugin.api.PluginStateResolver;
import com.qcadoo.plugin.api.RunIfEnabled;
import com.qcadoo.plugin.internal.PluginUtilsService;

public class RunIfEnabledTest {

    private static final String PLUGIN_NAME = "somePlugin";

    @Mock
    private DependencyMock dependencyMock;

    private PluginStateResolver pluginStateResolver;

    private interface DependencyMock {

        void run();
    }

    private static class MethodLevelAnnotatedClass {

        private DependencyMock dependencyMock;

        public MethodLevelAnnotatedClass(final DependencyMock dependencyMock) {
            this.dependencyMock = dependencyMock;
        }

        @RunIfEnabled(PLUGIN_NAME)
        public void run() {
            dependencyMock.run();
        }
    }

    @RunIfEnabled(PLUGIN_NAME)
    private static class ClassLevelAnnotatedClass {

        private DependencyMock dependencyMock;

        public ClassLevelAnnotatedClass(final DependencyMock dependencyMock) {
            this.dependencyMock = dependencyMock;
        }

        @RunIfEnabled(PLUGIN_NAME)
        public void run() {
            dependencyMock.run();
        }
    }

    @SuppressWarnings("deprecation")
    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);
        final PluginUtilsService pluginUtilsService = new PluginUtilsService();
        pluginStateResolver = mock(PluginStateResolver.class);
        given(pluginStateResolver.isEnabled(Mockito.anyString())).willReturn(false);
        given(pluginStateResolver.isEnabledOrEnabling(Mockito.anyString())).willReturn(false);
        ReflectionTestUtils.setField(pluginUtilsService, "pluginStateResolver", pluginStateResolver);
        pluginUtilsService.init();
    }

    @Test
    public final void shouldNotRunWithMethodLevelAnnotation() {
        // given
        MethodLevelAnnotatedClass object = new MethodLevelAnnotatedClass(dependencyMock);

        // when
        object.run();

        // then
        verify(pluginStateResolver).isEnabled(PLUGIN_NAME);
        verify(dependencyMock, never()).run();
    }

    @Test
    public final void shouldRunWithMethodLevelAnnotation() {
        // given
        enablePlugin(PLUGIN_NAME);
        MethodLevelAnnotatedClass object = new MethodLevelAnnotatedClass(dependencyMock);

        // when
        object.run();

        // then
        verify(pluginStateResolver).isEnabled(PLUGIN_NAME);
        verify(dependencyMock).run();
    }

    @Test
    public final void shouldNotRunWithClassLevelAnnotation() {
        // given
        ClassLevelAnnotatedClass object = new ClassLevelAnnotatedClass(dependencyMock);

        // when
        object.run();

        // then
        verify(pluginStateResolver).isEnabled(PLUGIN_NAME);
        verify(dependencyMock, never()).run();
    }

    @Test
    public final void shouldRunWithClassLevelAnnotation() {
        // given
        enablePlugin(PLUGIN_NAME);
        ClassLevelAnnotatedClass object = new ClassLevelAnnotatedClass(dependencyMock);

        // when
        object.run();

        // then
        verify(pluginStateResolver).isEnabled(PLUGIN_NAME);
        verify(dependencyMock).run();
    }

    @SuppressWarnings("deprecation")
    private void enablePlugin(final String pluginName) {
        given(pluginStateResolver.isEnabled(pluginName)).willReturn(true);
        given(pluginStateResolver.isEnabledOrEnabling(pluginName)).willReturn(true);
    }

}
