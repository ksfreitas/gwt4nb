/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 */
package org.netbeans.modules.gwt4nb.common;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author markiewb
 */
public class IOUtils {

    public interface Predicate<T> {

        boolean accept(T item);
    }

    public static Collection<File> findRecursive(File dir, Predicate<File> predicate) {
        if (null == dir || !dir.isDirectory()) {
            return Collections.emptyList();
        }
        final Set<File> fileTree = new HashSet<File>();
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                if (predicate.accept(entry)) {
                    fileTree.add(entry);
                }
            } else {
                fileTree.addAll(findRecursive(entry, predicate));
            }
        }
        return fileTree;
    }

    public static class FileNameExtensionPredicate implements Predicate<File> {

        private final String extension;

        public FileNameExtensionPredicate(String extension) {
            this.extension = extension;
        }

        @Override
        public boolean accept(File item) {
            return item.getName().toLowerCase().endsWith(extension);
        }
    }
}
