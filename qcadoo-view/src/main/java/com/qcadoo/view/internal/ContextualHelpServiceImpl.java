package com.qcadoo.view.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.ContextualHelpService;
import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.components.window.WindowComponentPattern;
import com.qcadoo.view.internal.components.window.WindowTabComponentPattern;

@Service
public class ContextualHelpServiceImpl implements ContextualHelpService {

    @Autowired
    private ContextualHelpPropertyConfigurer contextualHelpSource;

    @Value("${showContextualHelpPaths}")
    private boolean showContextualHelpPaths;

    @Override
    public String getHelpUrl(final ComponentPattern componentPattern) {
        return getHelpUrl(getContextualHelpKey(componentPattern));
    }

    @Override
    public String getHelpUrl(final String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        return contextualHelpSource.getProperty(code, LocaleContextHolder.getLocale());
    }

    private String getHelpKeyForWindowTab(final WindowTabComponentPattern windowTabComponentPattern) {
        StringBuilder helpKey = new StringBuilder();
        ViewDefinition viewDefinition = windowTabComponentPattern.getViewDefinition();
        helpKey.append(viewDefinition.getPluginIdentifier());
        helpKey.append('.');
        helpKey.append(viewDefinition.getName());
        helpKey.append('.');
        helpKey.append(windowTabComponentPattern.getName());
        return helpKey.toString();
    }

    private String getHelpKeyForWindow(final WindowComponentPattern windowComponentPattern) {
        StringBuilder helpKey = new StringBuilder();
        ViewDefinition viewDefinition = windowComponentPattern.getViewDefinition();
        helpKey.append(viewDefinition.getPluginIdentifier());
        helpKey.append('.');
        helpKey.append(viewDefinition.getName());
        return helpKey.toString();
    }

    @Override
    public String getContextualHelpKey(final ComponentPattern componentPattern) {
        if (componentPattern instanceof WindowTabComponentPattern) {
            return getHelpKeyForWindowTab((WindowTabComponentPattern) componentPattern);
        }
        if (componentPattern instanceof WindowComponentPattern) {
            return getHelpKeyForWindow((WindowComponentPattern) componentPattern);
        }
        return null;
    }

    @Override
    public boolean isContextualHelpPathsVisible() {
        return showContextualHelpPaths;
    }

}
