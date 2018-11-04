/*
 * Copyright 2009-2018 the original author or authors.
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
package org.codehaus.groovy.eclipse.adapters;

import org.codehaus.groovy.eclipse.editor.GroovyEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.groovy.core.GroovyResourceAdapter;
import org.eclipse.ui.IEditorInput;

public class GroovyEditorAdapterFactory implements IAdapterFactory {

    @Override
    public Class<?>[] getAdapterList() {
        return new GroovyResourceAdapter().getAdapterList();
    }

    @Override
    public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
        Assert.isLegal(adaptable instanceof GroovyEditor, "adaptable is not the GroovyEditor");
        IEditorInput editorInput = ((GroovyEditor) adaptable).getEditorInput();

        // delegate to GroovyIFileEditorInputAdapterFactory?
        T adapter = Adapters.adapt(editorInput, adapterType);
        if (adapter != null) {
            return adapter;
        }

        // delegate to GroovyResourceAdapter?
        IFile file = Adapters.adapt(editorInput, IFile.class);
        if (file != null) {
            return Adapters.adapt(file, adapterType);
        }

        return null;
    }
}
