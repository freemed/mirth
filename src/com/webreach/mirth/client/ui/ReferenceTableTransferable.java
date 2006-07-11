package com.webreach.mirth.client.ui;

import java.awt.datatransfer.*;

/**
 * Package Database Variables for movement.
 */
public class ReferenceTableTransferable implements Transferable {

   private static DataFlavor[] flavors = null;
   private String data = null;

   /**
    * @param data the type of Ant element being transferred, e.g., target, task,
    * type, etc.
    */
   public ReferenceTableTransferable( String data ) {
      this.data = data;
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
        

         return "msg['" + data + "']";
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