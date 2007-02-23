package edu.stanford.smi.protegex.changes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protegex.server_changes.ServerChangesUtil;

public class ChangeTableModel extends AbstractTableModel {

	public static final int FILTER_TRANS = 0;
	public static final int FILTER_TRANS_INFO = 1;
	public static final int SHOW_ALL = 2;
	
	public static final String CHANGE_COLNAME_AUTHOR ="Author";
	public static final String CHANGE_COLNAME_CREATED ="Created";
	public static final String CHANGE_COLNAME_ACTION ="Action";
	public static final String CHANGE_COLNAME_DESCRIPTION ="Description";
	//static protected Class[]  cTypes = {String.class, String.class, String.class, Icon.class, String.class};
	private String[] colNames;
	private List<Instance> completeData;
	private List<Instance> workingData;
	
	// Used for coloring the table
	private ArrayList colorList;
	private Integer currColor = new Integer(-1);
	
	private int filterMethod = FILTER_TRANS;
	private KnowledgeBase changeKB;
	
	public ChangeTableModel(KnowledgeBase changeKB) {
		this.changeKB = changeKB;
		init();
	}
	
	public ChangeTableModel(KnowledgeBase changeKB, int filter) {
		this.changeKB = changeKB;
		filterMethod = filter;
		init();
	}
	
	// init the column names, data structures
	private void init() {
		colNames = new String[4];
	
		colNames[2] = CHANGE_COLNAME_AUTHOR;
		colNames[3] = CHANGE_COLNAME_CREATED;
		colNames[0] = CHANGE_COLNAME_ACTION;
		//colNames[3] = "    ";
		colNames[1] = CHANGE_COLNAME_DESCRIPTION;
		
		workingData = new ArrayList<Instance>();
		completeData = new ArrayList<Instance>();
		colorList = new ArrayList();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return workingData.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return colNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return colNames[0];
		case 1:
			return colNames[1];
		case 2:
			return colNames[2];
		case 3:
			return colNames[3];
		//case 4:
			//return colNames[4];
	
		
		}
		
		return "";
	}
	
	/*public Class getColumnClass(int c) {
		return cTypes[c];
        //return getValueAt(0, c).getClass();
    }*/
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		
		// This is the instance object,
		// get the particular piece of info out of it.
		Instance aInst = workingData.get(row);

		
		Object ctxt = null;
		
		switch(col) {
		case 2:
			ctxt = ChangeCreateUtil.getAuthor(changeKB, aInst);
		    break;
		case 3: 
			ctxt = ChangeCreateUtil.getCreated(changeKB, aInst);
			break;
		case 0: 
			ctxt = ChangeCreateUtil.getActionDisplay(changeKB, aInst);
			break;
		/*case 3:  
			//Cls changeInstType = aInst.getDirectType();
			//if (changeInstType.getName().equals(ServerChangesUtil.CHANGETYPE_TRANS_CHANGE))
			  String actionType = ChangeCreateUtil.getType(changeKB, aInst);	
			  if(actionType.equals(ServerChangesUtil.CHANGE_LEVEL_DISP_TRANS))
			   ctxt = Icons.getViewClsIcon();
			break; */ 	
		case 1: 
			ctxt = ChangeCreateUtil.getContext(changeKB, aInst);
			break;
		}
		
		//Integer colorInt = (Integer) colorList.get(row);
		//Object[] wrapper = new Object[2];
		//wrapper[0] = colorInt;
		//wrapper[1] = ctxt;
		
		//return wrapper;
		return ctxt;
	}
	
	public Object getObjInRow(int row) {
		Instance aInst = workingData.get(row);
		return aInst;
	}
	
	/**
	 * @param annotate
	 * Add the given annotation to the internal data structure
	 */
	public void addChangeData(Instance changeInst) {
		
		addChangeData(changeInst, true);
		fireTableRowsInserted(workingData.size()-1, workingData.size()-1);
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	private void addChangeData(Instance changeInst, boolean completeUpdate) {
		boolean isTrans = false;
		boolean added = false;
		String actionType = ChangeCreateUtil.getType(changeKB, changeInst);
			
		if (!"ROOT".equals(actionType)) {
			if (filterMethod == FILTER_TRANS) {
				if (actionType.equals(ServerChangesUtil.CHANGE_LEVEL_INFO) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_DISP_TRANS)) {
					workingData.add(changeInst);
					colorList.add(currColor);
					added = true;
				}
				
			} else if (filterMethod == FILTER_TRANS_INFO) {
				if (actionType.equals(ServerChangesUtil.CHANGE_LEVEL_INFO) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS_INFO)) {
					workingData.add(changeInst);
					colorList.add(currColor);
					added = true;
				}
				
			} else if (filterMethod == SHOW_ALL) {
				workingData.add(changeInst);
				colorList.add(currColor);
				added = true;
			}

			// If we have a transaction change, add the list of changes
			Cls changeInstType = changeInst.getDirectType();
			if (changeInstType.getName().equals(ServerChangesUtil.CHANGETYPE_TRANS_CHANGE)) {
				isTrans = true;
				Collection relChanges = ChangeCreateUtil.getChanges(changeKB, changeInst);
				addChangeData(relChanges);
			} 
			
			if (!isTrans && added) {
				updateCurrColor();
			}
			
			if (completeUpdate) {
				completeData.add(changeInst);
			}
		}
	}
	
	private void addChangeData(Collection changeInsts) {
		
		for (Iterator iter = changeInsts.iterator(); iter.hasNext();) {
			Instance aInst = (Instance) iter.next();
			String actionType = ChangeCreateUtil.getType(changeKB, aInst);
			
			if (filterMethod == FILTER_TRANS) {
				if (actionType.equals(ServerChangesUtil.CHANGE_LEVEL_INFO) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS)) {
					workingData.add(aInst);
					colorList.add(currColor);
				}
				
			} else if (filterMethod == FILTER_TRANS_INFO) {
				if (actionType.equals(ServerChangesUtil.CHANGE_LEVEL_INFO) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS) || 
						actionType.equals(ServerChangesUtil.CHANGE_LEVEL_TRANS_INFO)) {
					workingData.add(aInst);
					colorList.add(currColor);
				}
				
			} else if (filterMethod == SHOW_ALL) {
				workingData.add(aInst);
				colorList.add(currColor);
			}
		}
		updateCurrColor();
	}
	
	
	
	
	/**
	 * @param changeInsts
	 * Set the given data structure to the given collectin of instances
	 */
	public void setChanges(Collection changeInsts) {
		workingData.clear();
		colorList.clear();
		for (Iterator iter = changeInsts.iterator(); iter.hasNext();) {
			Instance cInst = (Instance) iter.next();
			workingData.add(cInst);
			colorList.add(currColor);
			updateCurrColor();
		}
		
		completeData.clear();
		completeData.addAll(changeInsts);
		
		fireTableDataChanged();
	}
	
	/**
	 * Clear all instances in the current data structure
	 */
	public void clearData() {
		workingData.clear();
		colorList.clear();
		completeData.clear();
		
		fireTableDataChanged();
	}
	
	public void cancelQuery() {
		setNewFilter();
	}
	
	public void setSearchQuery(String field, String text) {
		
		setNewSearch(field, text);
	}
	
	public void filterTrans() {
		filterMethod = FILTER_TRANS;
		setNewFilter();
	}
	
	public void filterTransInfo() {
		filterMethod = FILTER_TRANS_INFO;
		setNewFilter();
	}

	public void filterAll() {
		filterMethod = SHOW_ALL;
		setNewFilter();
	}
	
	private void initCurrColor() {
		currColor = new Integer(-1);
	}
	
	private void updateCurrColor() {
		currColor = new Integer(-currColor.intValue());
	}
	
	private void setNewSearch(String field, String text) {
		workingData.clear();
		colorList.clear();
		
		Slot author = changeKB.getSlot(ServerChangesUtil.SLOT_NAME_AUTHOR);
		Slot created = changeKB.getSlot(ServerChangesUtil.SLOT_NAME_CREATED);
		Slot action = changeKB.getSlot(ServerChangesUtil.SLOT_NAME_ACTION);
		Slot desc = changeKB.getSlot(ServerChangesUtil.SLOT_NAME_CONTEXT);
		
		Slot sltToSearch = null;
		if (field.equals(CHANGE_COLNAME_AUTHOR)) {
			sltToSearch = author;
		} else if (field.equals(CHANGE_COLNAME_CREATED)) {
			sltToSearch = created;
		} else if (field.equals(CHANGE_COLNAME_ACTION)) {
			sltToSearch = action;
		} else if (field.equals(CHANGE_COLNAME_DESCRIPTION)) {
			sltToSearch = desc;
		}
		Collection results = changeKB.getMatchingFrames(sltToSearch, null, false, text, 1000);
		
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (element instanceof Instance) {
				Instance someInst = (Instance) element;
				addChangeData(someInst, false);
			}
		}
		
		fireTableDataChanged();
	}
	
	private void setNewFilter() {
		workingData.clear();
		colorList.clear();
		
		//initCurrColor();
		for (Iterator iter = completeData.iterator(); iter.hasNext();) {
			Instance aInst = (Instance) iter.next();
			addChangeData(aInst, false);
		}
		
		fireTableDataChanged();
	}
}

