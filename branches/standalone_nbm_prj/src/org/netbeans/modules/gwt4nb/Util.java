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
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.openide.ErrorManager;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;

/**
 *
 * @author tomslot
 */
public class Util {
    public static void replaceInFile(FileObject file, String searchedString, String replacement){
        replaceInFile(file, new String[]{searchedString}, new String[]{replacement});
    }
    
    public static void replaceInFile(FileObject file, String searchedStrings[], String replacements[]){
        //TODO: provide a better implementation
        if (searchedStrings.length != replacements.length){
            throw new IllegalArgumentException();
        }
        
        String orgFileContent = null;
        
        InputStream is = null;
        OutputStream os = null;
        try     {
            is = file.getInputStream();
            byte rawContent[] = new byte[is.available()];
            is.read(rawContent);
            orgFileContent = new String(rawContent);
            
            is.close();
            
            String alteredContent = orgFileContent;
            
            for (int i = 0; i < searchedStrings.length; i ++){
                alteredContent = alteredContent.replace(searchedStrings[i], replacements[i]);
            }
            
            FileLock lock = file.lock();
            PrintWriter writer = new PrintWriter(file.getOutputStream(lock));
            writer.print(alteredContent);
            writer.close();
            lock.releaseLock();
        } catch (IOException ex) {
            ErrorManager.getDefault().notify(ex);
        } finally {
            try {
                if (is != null){
                    is.close();
                }
            } catch (IOException ex) {
                ErrorManager.getDefault().notify(ex);
            }
        }
    }
    
    /**
     *XXX: Replace with apache velocity?
     */
    public static void copyResource(final String res, final FileObject to, final String[] mapFrom, final String[] mapTo) throws IOException {
        assert res != null;
        assert to != null;
        assert mapFrom != null;
        assert mapTo != null;
        assert mapFrom.length == mapTo.length;
        InputStream _in = GWTFrameworkProvider.class.getClassLoader().getResourceAsStream(res);
        assert _in != null;
        BufferedReader in = new BufferedReader(new InputStreamReader(_in));
        try {
            FileLock lock = to.lock();
            try {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(to.getOutputStream(lock)));
                try {
                    copyStream(in,out,mapFrom,mapTo);
                } finally {
                    out.close();
                }
            } finally {
                lock.releaseLock();
            }
        } finally {
            in.close();
        }
    }
    
    public static void copyStream(final BufferedReader in , final PrintWriter out, final String[] mapFrom, String[] mapTo) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            for (int i=0; i<mapFrom.length; i++) {
                line = line.replaceAll(mapFrom[i],mapTo[i]);
            }
            out.println(line);
        }
    }
    
    public static boolean isValidJavaIdentifier(String txt){
        if (txt.length() == 0 || !Character.isJavaIdentifierStart(txt.charAt(0))) {
            return false;
        }
        
        for (int i=1; i < txt.length(); i++) {
            if (!Character.isJavaIdentifierPart(txt.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
}
