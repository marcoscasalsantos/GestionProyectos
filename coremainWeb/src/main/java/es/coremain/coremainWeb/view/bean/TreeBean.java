package es.coremain.coremainWeb.view.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;  
import org.primefaces.model.DefaultTreeNode;

@ManagedBean(name = "treeBean")
@SessionScoped
public class TreeBean {  
      
    private TreeNode root;  
  
    public TreeBean() {  
    	
    	System.out.println("*****************Tree Bean****************************************************");
        root = new DefaultTreeNode("Root", null);  
        TreeNode node0 = new DefaultTreeNode("PROYECTOS", root);  
        TreeNode node1 = new DefaultTreeNode("proyecto1 1", root);  
        TreeNode node2 = new DefaultTreeNode("proyecto2 2", root);  
          
        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);  
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);  
          
        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);  
        TreeNode node11 = new DefaultTreeNode("Node 1.1", node1);  
          
        TreeNode node000 = new DefaultTreeNode("Node 0.0.0", node00);  
        TreeNode node001 = new DefaultTreeNode("Node 0.0.1", node00);  
        TreeNode node010 = new DefaultTreeNode("Node 0.1.0", node01);  
          
        TreeNode node100 = new DefaultTreeNode("Node 1.0.0", node10);  
    }  
  
    public TreeNode getRoot() {  
        return root;  
    }  
} 