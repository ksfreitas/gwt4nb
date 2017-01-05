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

import javax.swing.SwingUtilities;

/**
 * Replacement for SwingWorker in JDK 1.5
 */
abstract class GWT4NBSwingWorker {
   private Object value;  // see getValue(), setValue()

   /**
    * Class to maintain reference to current worker thread
    * under separate synchronization control.
    */
   private static class ThreadVar {
       private Thread thread;
       ThreadVar(Thread t) { thread = t; }
       synchronized Thread get() { return thread; }
       synchronized void clear() { thread = null; }
   }

   private ThreadVar threadVar;

   /**
    * Get the value produced by the worker thread, or null if it
    * hasn't been constructed yet.
    */
   protected synchronized Object getValue() {
       return value;
   }

   /**
    * Set the value produced by worker thread
    */
   private synchronized void setValue(Object x) {
       value = x;
   }

   /**
    * Compute the value to be returned by the <code>get</code> method.
    */
   public abstract Object construct();

   /**
    * Called on the event dispatching thread (not on the worker thread)
    * after the <code>construct</code> method has returned.
    */
   public void finished() {
   }

   /**
    * A new method that interrupts the worker thread.  Call this method
    * to force the worker to stop what it's doing.
    */
   public void interrupt() {
       Thread t = threadVar.get();
       if (t != null) {
           t.interrupt();
       }
       threadVar.clear();
   }

   /**
    * Return the value created by the <code>construct</code> method.
    * Returns null if either the constructing thread or the current
    * thread was interrupted before a value was produced.
    *
    * @return the value created by the <code>construct</code> method
    */
   public Object get() {
       while (true) {             Thread t = threadVar.get();
           if (t == null) {
               return getValue();
           }
           try {
               t.join();
           }
           catch (InterruptedException e) {
               Thread.currentThread().interrupt(); // propagate
               return null;
           }
       }
   }


   /**
    * Start a thread that will call the <code>construct</code> method
    * and then exit.
    */
   public GWT4NBSwingWorker() {
       final Runnable doFinished = new Runnable() {
          public void run() { finished(); }
       };

       Runnable doConstruct = new Runnable() {
           public void run() {
               try {
                   setValue(construct());
               }
               finally {
                   threadVar.clear();
               }

               SwingUtilities.invokeLater(doFinished);
           }
       };

       Thread t = new Thread(doConstruct);
       threadVar = new ThreadVar(t);
   }

   /**
    * Start the worker thread.
    */
   public void start() {
       Thread t = threadVar.get();
       if (t != null) {
           t.start();
       }
   }
}