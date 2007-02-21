package edu.stanford.smi.protegex.changes;

import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protegex.changes.ui.*;


public class TreeTableNode {
    public Instance changeInst;
    public Vector children;

    private KnowledgeBase changeKB;
    

    public TreeTableNode(Instance changeInst, KnowledgeBase changeKB) {
		this.changeInst = changeInst;
		children = new Vector(10,10);
	
		this.changeKB = changeKB;
    }

    
    public String toString() { 
		return ChangeCreateUtil.getActionDisplay(changeKB, changeInst);
    }

    public Object getValueAt(int i) {
    	
		Object ctxt = null;
    	switch (i) {

		case 2:
			ctxt = ChangeCreateUtil.getAuthor(changeKB, changeInst);
			
			break;
		case 3: 
			ctxt = ChangeCreateUtil.getCreated(changeKB, changeInst);
	         
			break;
		case 0: 
			ctxt = ChangeCreateUtil.getActionDisplay(changeKB, changeInst);
		   
			break;
		case 1: 
			ctxt = ChangeCreateUtil.getContext(changeKB, changeInst);
		
			break;
		}
   
		return ctxt;
    }

    public void setValueAt(Object aValue, int i) {
    }

	public int getChildCount() {
		return children.size();
	}
	

	
	public Object[] getChildren(){
		return children.toArray();
	}
	


	public TreeTableNode getChildAt(int i) {
		return (TreeTableNode) children.get(i);
    }

    public void addChild(TreeTableNode child) {
       children.add(child);
       
    }
    
    public Instance getChildInstanceAt(int i){
    	return ((TreeTableNode) children.get(i)).changeInst;
    }
    
    
  
    
    
    
    public void removeChildren(){
    	children.clear();
    }

 
    
  
}


