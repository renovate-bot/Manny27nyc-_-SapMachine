/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.graalvm.compiler.hotspot;

import org.graalvm.compiler.api.runtime.GraalRuntime;
import org.graalvm.compiler.core.common.CompilationIdentifier;
import org.graalvm.compiler.debug.DebugHandlersFactory;
import org.graalvm.compiler.debug.DiagnosticsOutputDirectory;
import org.graalvm.compiler.debug.DebugContext;
import org.graalvm.compiler.hotspot.meta.HotSpotProviders;
import org.graalvm.compiler.options.OptionValues;
import org.graalvm.compiler.replacements.SnippetCounter.Group;
import org.graalvm.compiler.runtime.RuntimeProvider;

import jdk.vm.ci.code.TargetDescription;
import jdk.vm.ci.meta.ResolvedJavaMethod;

//JaCoCo Exclude

/**
 * Configuration information for the HotSpot Graal runtime.
 */
public interface HotSpotGraalRuntimeProvider extends GraalRuntime, RuntimeProvider, Group.Factory {

    default TargetDescription getTarget() {
        return getHostBackend().getTarget();
    }

    HotSpotProviders getHostProviders();

    @Override
    default String getName() {
        return getClass().getSimpleName();
    }

    @Override
    HotSpotBackend getHostBackend();

    GraalHotSpotVMConfig getVMConfig();

    /**
     * Opens a debug context for compiling {@code compilable}. The {@link DebugContext#close()}
     * method should be called on the returned object once the compilation is finished.
     *
     * @param compilationOptions the options used to configure the compilation debug context
     * @param compilationId a system wide unique compilation id
     * @param compilable the input to the compilation
     */
    DebugContext openDebugContext(OptionValues compilationOptions, CompilationIdentifier compilationId, Object compilable, Iterable<DebugHandlersFactory> factories);

    /**
     * Gets the option values associated with this runtime.
     */
    OptionValues getOptions();

    /**
     * Gets the option values associated with this runtime that are applicable for a given method.
     *
     * @param forMethod the method we are seeking for options for
     * @return the options applicable for compiling {@code method}
     */
    OptionValues getOptions(ResolvedJavaMethod forMethod);

    /**
     * Determines if the VM is currently bootstrapping the JVMCI compiler.
     */
    boolean isBootstrapping();

    /**
     * This runtime has been requested to shutdown.
     */
    boolean isShutdown();

    /**
     * Gets a directory into which diagnostics such crash reports and dumps should be written.
     */
    DiagnosticsOutputDirectory getOutputDirectory();
}
