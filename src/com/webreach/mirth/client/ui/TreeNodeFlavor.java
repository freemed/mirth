package com.webreach.mirth.client.ui;

import java.awt.datatransfer.*;

public class TreeNodeFlavor extends DataFlavor {
   
   
   public TreeNodeFlavor() {
      super(javax.swing.tree.TreeNode.class, "TreeNode");
   }
   
}