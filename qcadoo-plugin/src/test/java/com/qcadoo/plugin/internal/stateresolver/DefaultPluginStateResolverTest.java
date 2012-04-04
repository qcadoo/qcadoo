package com.qcadoo.plugin.internal.stateresolver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginAccessor;
import com.qcadoo.plugin.api.PluginState;

public class DefaultPluginStateResolverTest {

    private static final String PLUGIN_IDENTIFIER = "somePlugin";

    private DefaultPluginStateResolver stateResolverImpl;

    private Plugin plugin;

    private PluginAccessor pluginAccessor;

    @Before
    public final void init() {
        stateResolverImpl = new DefaultPluginStateResolver();

        pluginAccessor = mock(PluginAccessor.class);
        ReflectionTestUtils.setField(stateResolverImpl, "pluginAccessor", pluginAccessor);

        plugin = mock(Plugin.class);
        given(plugin.getIdentifier()).willReturn(PLUGIN_IDENTIFIER);
        given(pluginAccessor.getPlugin(PLUGIN_IDENTIFIER)).willReturn(plugin);
    }

    @Test(expected = NullPointerException.class)
    public final void shouldIsEnabledThrowException() throws Exception {
        // when
        stateResolverImpl.isEnabled("phantomPlugin");
    }

    @Test
    public final void shouldIsEnabledReturnTrue() throws Exception {
        // given
        setMockPluginEnabled();

        // when
        boolean isEnabled = stateResolverImpl.isEnabled(PLUGIN_IDENTIFIER);

        // then
        Assert.assertTrue(isEnabled);
    }

    @Test
    public final void shouldIsEnabledReturnFalse() throws Exception {
        // given
        setMockPluginDisabled();

        // when
        boolean isEnabled = stateResolverImpl.isEnabled(PLUGIN_IDENTIFIER);

        // then
        Assert.assertFalse(isEnabled);
    }

    private void setMockPluginEnabled() {
        given(plugin.getState()).willReturn(PluginState.ENABLED);
    }

    private void setMockPluginDisabled() {
        given(plugin.getState()).willReturn(PluginState.DISABLED);
    }
}
