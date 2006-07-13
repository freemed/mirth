package com.webreach.mirth.client.ui;

import java.awt.datatransfer.*;

/**
 * Package Database Variables for movement.
 */
public class VariableTransferable implements Transferable {

   private static DataFlavor[] flavors = null;
   private String data = null;
   private String _prefix = "msg['";
   private String _suffix = "']";

   /**
    * @param data the type of Ant element being transferred, e.g., target, task,
    * type, etc.
    */
   public VariableTransferable( String data, String prefix, String suffix ) {
      this.data = data;
      _prefix = prefix;
      _suffix = suffix;
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
        

         return _prefix + data + _suffix;
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