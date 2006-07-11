package com.webreach.mirth.client.ui;

import java.awt.datatransfer.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import javax.swing.tree.TreeNode;

/**
 * Package TreeNodes for movement.
 */
public class TreeTransferable implements Transferable {

   private static DataFlavor[] flavors = null;
   private TreeNode data = null;
   private String prefix = "msg";
   /**
    * @param data the type of Ant element being transferred, e.g., target, task,
    * type, etc.
    */
   public TreeTransferable( TreeNode data, String prefix ) {
      this.data = data;
      this.prefix = prefix;
      init();
   }

   /**
    * Set up the supported flavors: DataFlavor.stringFlavor for a raw string containing
    * an Ant element name (e.g. task, target, etc), or an ElementFlavor containing
    * an ElementPanel.
    */
   private void init() {
      try {
         flavors = new DataFlavor[ 1 ];
         flavors[ 0 ] = DataFlavor.stringFlavor;
      }
      catch ( Exception e ) {
         e.printStackTrace();
      }
   }

   /**
    * @param df the flavor type desired for the data. Acceptable value is
    * DataFlavor.stringFlavor.
    * @return if df is DataFlavor.stringFlavor, returns a raw string containing
    * an Ant element name.
    */
   public Object getTransferData( DataFlavor df ) {
      if ( df == null )
         return null;
   
      if ( data != null ){
         
        StringBuilder sb = new StringBuilder();
        sb.insert(0, prefix);
    	 TreeNode parent = data.getParent();
    	 LinkedList<String> nodeQ = new LinkedList<String>();
         while(parent != null){
        	 nodeQ.add(parent.toString());
        	 parent = parent.getParent();
         }
         if (!nodeQ.isEmpty())
        	 nodeQ.removeLast();
        // if (!nodeQ.isEmpty())
        //	 nodeQ.removeLast();
         while(!nodeQ.isEmpty()) {
        	 sb.append("['" + nodeQ.removeLast() + "']");
         }

         return sb.toString();
      }
      return null;
   }

   /**
    * @return an array containing a single ElementFlavor.   
    */
   public DataFlavor[] getTransferDataFlavors() {
      return flavors;
   }

   /**
    * @param df the flavor to check
    * @return true if df is an ElementFlavor
    */
   public boolean isDataFlavorSupported( DataFlavor df ) {
      if ( df == null )
         return false;
      for ( int i = 0; i < flavors.length; i++ ) {
         if ( df.equals( flavors[ i ] ) ) {
            return true;
         }
      }
      return false;
   }
}