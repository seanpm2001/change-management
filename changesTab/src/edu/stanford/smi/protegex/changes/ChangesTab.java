package edu.stanford.smi.protegex.changes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.DefaultKnowledgeBase;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.model.framestore.FrameStoreManager;
import edu.stanford.smi.protege.resource.Icons;
import edu.stanford.smi.protege.server.RemoteProjectManager;
import edu.stanford.smi.protege.server.RemoteServer;
import edu.stanford.smi.protege.server.RemoteSession;
import edu.stanford.smi.protege.server.framestore.RemoteClientFrameStore;
import edu.stanford.smi.protege.ui.HeaderComponent;
import edu.stanford.smi.protege.util.ComponentFactory;
import edu.stanford.smi.protege.util.LabeledComponent;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.util.SelectableTable;
import edu.stanford.smi.protege.util.ViewAction;
import edu.stanford.smi.protege.widget.AbstractTabWidget;
import edu.stanford.smi.protegex.changes.action.AnnotationShowAction;
import edu.stanford.smi.protegex.changes.action.ChangesSearchClear;
import edu.stanford.smi.protegex.changes.action.ChangesSearchExecute;
import edu.stanford.smi.protegex.changes.listeners.ChangesListener;
import edu.stanford.smi.protegex.changes.ui.ChangeMenu;
import edu.stanford.smi.protegex.changes.ui.ColoredTableCellRenderer;
import edu.stanford.smi.protegex.changes.ui.JTreeTable;
import edu.stanford.smi.protegex.server_changes.ChangesDb;
import edu.stanford.smi.protegex.server_changes.ChangesProject;
import edu.stanford.smi.protegex.server_changes.GetAnnotationProjectName;
import edu.stanford.smi.protegex.server_changes.model.AnnotationCreationComparator;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel;
import edu.stanford.smi.protegex.server_changes.model.ChangeModel.AnnotationCls;
import edu.stanford.smi.protegex.server_changes.model.generated.Annotation;
import edu.stanford.smi.protegex.server_changes.model.generated.Change;

/**
 * Change Management Tab widget
 * 
 */
public class ChangesTab extends AbstractTabWidget {
	// Main UI tables
	public static final String HEADERCOMP_NAME_CHANGE_VIEWER = "Change Viewer";
	public static final String HEADERCOMP_NAME_ANNOTATE_VIEWER = "Annotation Viewer";
	public static final String LABELCOMP_NAME_CHANGE_HIST = "Change History";
	public static final String LABELCOMP_NAME_ANNOTATIONS = "Annotations";
	public static final String LABELCOMP_NAME_ASSOC_CHANGES = "Associated Changes";
	public static final String LABELCOMP_NAME_DETAIL_CHANGES = "Detailed Changes";
	public static final String ACTION_NAME_CREATE_ANNOTATE = "Create Annotation";
	public static final String ACTION_NAME_REMOVE_ANNOTATE = "Remove Annotation";
	public static final String ACTION_NAME_EDIT_ANNOTATE = "Edit Annotation";
	public static final String FILTER_NAME_DETAIL_VIEW = "Detailed View";
	public static final String FILTER_NAME_SUMMARY_VIEW = "Summary View";

	// Search Panel
	private static final String SEARCH_PANEL_TITLE = "Search";
	private static final String SEARCH_PANEL_BUTTON_GO = "Go";
	private static final String SEARCH_PANEL_BUTTON_CLEAR = "Clear"; 

	private static final String ANNOT_PANEL_TITLE = "Create Annotation";

	private static final String CHANGES_TAB_NAME = "Changes";

	private Project changes_project;
	private ChangeModel model;
	private KnowledgeBase changes_kb;
	private KnowledgeBase currentKB;
	private ChangeCreateUtil changeCreateUtil;
	
	private SelectableTable annotationsTable;
	private SelectableTable annotationChangesTable;

	private JComboBox annotationTypes;
	private ChangeTableModel annotationChangesTableModel;

	private AnnotationTableModel annotationsTableModel;

	private Annotation annotateInst;
	private Instance instToEdit;
	private String OWL_KB_INDICATOR = "OWL";

	private ChangeMenu changesMenu;
	private RemoveInstanceAction removeAnnotationAction;
	private EditInstanceAction editAnnotationAction;
	private AddInstanceAction addAnnotationAction;

	private JTreeTable changesTreeTable;
	private ChangeTreeTableModel changesTreeTableModel;
	
	private ChangesListener changesListener;

	private boolean inRemoveAnnotation = false;
	
	
	public boolean getInRemoveAnnotation() {
		return inRemoveAnnotation;
	}

	public void setInRemoveAnnotation(boolean val) {
		inRemoveAnnotation = val;
	}

	public boolean kbInOwl(KnowledgeBase kb) {
		int index = (kb.getClass().getName().indexOf(OWL_KB_INDICATOR));
		return (index > 0);
	}

	public KnowledgeBase getChangesKB() {
		return changes_kb;
	}

	public Project getChangesProj() {
		return changes_project;
	}


	public void initialize() {
		setLabel(CHANGES_TAB_NAME);

		currentKB = getKnowledgeBase();

		getChangeProject();
 
		initTables();
		loadExistingData();

		changesListener = new ChangesListener(model, this);
		changes_kb.addFrameListener(changesListener);

		buildGUI();	    
	}

	private void buildGUI() {
		// Create menu item
		changesMenu = new ChangeMenu(getKnowledgeBase(), changes_kb);
		JMenuBar menuBar = getMainWindowMenuBar();		
		menuBar.add (changesMenu);

		annotationsTable.addMouseListener(new AnnotationShowAction(annotationsTable, annotationsTableModel, changes_project));
		JScrollPane scroll = ComponentFactory.createScrollPane(changesTreeTable);

		JScrollPane scroll2 = ComponentFactory.createScrollPane(annotationsTable);
		JScrollPane scroll3 = ComponentFactory.createScrollPane(annotationChangesTable);
	
		LabeledComponent changeHistLC = new LabeledComponent(LABELCOMP_NAME_CHANGE_HIST, scroll,true);
		changeHistLC.setFooterComponent(initSearchPanel());
		
		changeHistLC.doLayout();
		changeHistLC.addHeaderSeparator();
		addAnnotationAction = new AddInstanceAction(changeHistLC, ACTION_NAME_CREATE_ANNOTATE);
		addAnnotationAction.setEnabled(false);

		changeHistLC.setHeaderComponent(initAnnotPanel(), BorderLayout.EAST);
		changeHistLC.addHeaderButton(addAnnotationAction);
		
		changeHistLC.addHeaderButton(new ViewAction("View change details", null) {
			@Override			
			public void onView() {
				TreePath[] selectedTreePaths = changesTreeTable.getTree().getSelectionPaths();

				for (TreePath treePath : selectedTreePaths) {
					Object lastPathComp = treePath.getLastPathComponent();
					try {
						if (lastPathComp instanceof ChangeTreeTableNode) {
							Change change = ((ChangeTreeTableNode)lastPathComp).getChange();
							changes_kb.getProject().show(change);
						} 
					} catch (Exception e) {
						Log.getLogger().warning("Error at getting change table row " + treePath);
					}
				}					
			}
		});
			

		LabeledComponent annotLC = new LabeledComponent(LABELCOMP_NAME_ANNOTATIONS, scroll2, true);
		annotLC.doLayout();
		annotLC.addHeaderSeparator();
		
		editAnnotationAction = new EditInstanceAction(ACTION_NAME_EDIT_ANNOTATE);
		editAnnotationAction.setEnabled(false);
		annotLC.addHeaderButton(editAnnotationAction);
		
		removeAnnotationAction = new RemoveInstanceAction(ACTION_NAME_REMOVE_ANNOTATE);		
		removeAnnotationAction.setEnabled(false);		
		annotLC.addHeaderButton(removeAnnotationAction);		
		
		LabeledComponent assocLC = new LabeledComponent(LABELCOMP_NAME_ASSOC_CHANGES, scroll3, true);
		assocLC.doLayout();
		assocLC.addHeaderSeparator();

		assocLC.addHeaderButton(new ViewAction("View Change", annotationChangesTable) {
			@Override
			public void onView() {
				int[] selRows = annotationChangesTable.getSelectedRows(); 
				for (int i = 0; i < selRows.length; i++) {
					Instance instance = (Instance) annotationChangesTableModel.getObjInRow(selRows[i]);
					changes_kb.getProject().show(instance);
				}				
			}
		});
		
		JSplitPane splitPanel = ComponentFactory.createTopBottomSplitPane(false);
		splitPanel.setResizeWeight(0.75);
		splitPanel.setDividerLocation(0.75);
		splitPanel.setTopComponent(annotLC);
		splitPanel.setBottomComponent(assocLC);

		HeaderComponent changeView = new HeaderComponent(HEADERCOMP_NAME_CHANGE_VIEWER, null, changeHistLC);
		HeaderComponent annotView = new HeaderComponent(HEADERCOMP_NAME_ANNOTATE_VIEWER, null, splitPanel);

		JSplitPane splitPanelBig = ComponentFactory.createTopBottomSplitPane(false);
		splitPanelBig.setTopComponent(changeView);
		splitPanelBig.setBottomComponent(annotView);
		splitPanelBig.setResizeWeight(0.5);
		splitPanelBig.setDividerLocation(0.5);

		add(splitPanelBig);

		changesTreeTable.getTree().expandPath(changesTreeTableModel.getRootPath());

	}

	private JPanel initAnnotPanel() {
		JPanel annotPanel = ComponentFactory.createPanel();
		JLabel annotLabel = new JLabel(ANNOT_PANEL_TITLE);
		String[] annotFields = {	AnnotationTableModel.TYPE_COMMENT, 
				AnnotationTableModel.TYPE_EXPLANATION,
				AnnotationTableModel.TYPE_EXAMPLE,
				AnnotationTableModel.TYPE_QUESTION,
				AnnotationTableModel.TYPE_ADVICE,
				AnnotationTableModel.TYPE_SEEALSO,
		};

		annotationTypes = new JComboBox(annotFields);
		annotationTypes.setSelectedIndex(0);


		annotPanel.add(annotLabel);
		annotPanel.add(annotationTypes);
		return annotPanel;
	}

	private JPanel initSearchPanel() {
		JPanel searchPanel = ComponentFactory.createPanel();
		JLabel searchLabel = new JLabel(SEARCH_PANEL_TITLE);
		String[] searchFields = {	ChangeTableColumn.CHANGE_COLNAME_AUTHOR.getName(),
				ChangeTableColumn.CHANGE_COLNAME_ACTION.getName(), 
				ChangeTableColumn.CHANGE_COLNAME_DESCRIPTION.getName(),
				ChangeTableColumn.CHANGE_COLNAME_CREATED.getName()
		};

		JComboBox cbox = new JComboBox(searchFields);
		cbox.setSelectedIndex(0);

		JTextField searchText = new JTextField(25);
		JButton searchButton = new JButton(SEARCH_PANEL_BUTTON_GO);

		ActionListener searchExecute = new ChangesSearchExecute(cbox, searchText, changesTreeTableModel);
		searchButton.addActionListener(searchExecute);

		JButton clearButton = new JButton(SEARCH_PANEL_BUTTON_CLEAR);

		ActionListener searchClear = new ChangesSearchClear(changesTreeTableModel);
		clearButton.addActionListener(searchClear);
		
		//FIXME: The following lines should be deleted when the search is implemented
		searchButton.setEnabled(false);
		clearButton.setEnabled(false);
		searchButton.setToolTipText("Search functionality not implemented yet");
		clearButton.setToolTipText("Search functionality not implemented yet");
		
		searchPanel.add(searchLabel);
		searchPanel.add(cbox);
		searchPanel.add(searchText);
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);

		searchPanel.setLayout(new FlowLayout());

		return searchPanel;
	}

	
	private void initTables() {
		// Create Tables

		annotationChangesTableModel = new ChangeTableModel(model);

		annotationsTableModel = new AnnotationTableModel(changes_kb);
		changesTreeTableModel = new ChangeTreeTableModel(model);

		annotationChangesTable = new SelectableTable();
		annotationChangesTable.setModel(annotationChangesTableModel);

		annotationsTable = new SelectableTable();
		annotationsTable.setModel(annotationsTableModel);
		changesTreeTable = new JTreeTable(changesTreeTableModel);

		ComponentFactory.configureTable(annotationsTable);
		ComponentFactory.configureTable(annotationChangesTable);

		annotationsTable.setShowGrid(false);
		annotationsTable.setIntercellSpacing(new Dimension(0, 0));
		annotationsTable.setColumnSelectionAllowed(false);
		annotationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		annotationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		annotationChangesTable.setShowGrid(false);
		annotationChangesTable.setIntercellSpacing(new Dimension(0, 0));
		annotationChangesTable.setColumnSelectionAllowed(false);
		annotationChangesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		annotationChangesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		annotationChangesTable.setDefaultRenderer(Object.class, new ColoredTableCellRenderer());

		changesTreeTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		changesTreeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		ListSelectionModel lsm = annotationsTable.getSelectionModel();
		lsm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()){
					return;
				}

				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if(!lsm.isSelectionEmpty()) {
					removeAnnotationAction.setEnabled(true);
					editAnnotationAction.setEnabled(true);
					int selectedRow = lsm.getMinSelectionIndex();
					String instName = annotationsTableModel.getInstanceName(selectedRow);
					Instance selectedInst = changes_kb.getInstance(instName);
					annotationChangesTableModel.setChanges(((Annotation) selectedInst).getAnnotates());
				} 
			}
		});

		ListSelectionModel tlsm = changesTreeTable.getSelectionModel();
		tlsm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()){
					return;
				}

				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				if(!lsm.isSelectionEmpty()) {
					addAnnotationAction.setEnabled(true);

				} 
			}
		});
	}


	private  void getChangeProject(){
		Project currentProj = currentKB.getProject(); 

		// NEED TO ADD IMPLEMENTATION FOR SERVER MODE
		// But this project must "essentially" be the same as the project that the project plugin is using
		// same events, contents etc.
		// it also runs after the changes project plugin has initialized.
		if (currentProj.isMultiUserClient()) {
			getServerSideChangeProject();
		}
		else {
			if (ChangesProject.getChangesProj(currentKB) == null) { // the tab has just been configured so the
				new ChangesProject().afterLoad(currentProj);  // project plugin is not initialized                           
			}
			changes_project = ChangesProject.getChangesProj(currentKB);
			changes_kb = changes_project.getKnowledgeBase();
			ChangesDb changes_db = ChangesProject.getChangesDb(currentKB);
			model = changes_db.getModel();			
		}
		
		changeCreateUtil = new ChangeCreateUtil(currentKB, model);
				
	}

	private  void getServerSideChangeProject() {
		String annotationName = (String) new GetAnnotationProjectName(currentKB).execute();
		if (annotationName == null) {
			Log.getLogger().warning("annotation project not configured (use " +
					GetAnnotationProjectName.METAPROJECT_ANNOTATION_PROJECT_SLOT +
			" slot)");
		}
		RemoteProjectManager project_manager = RemoteProjectManager.getInstance();
		FrameStoreManager framestore_manager = ((DefaultKnowledgeBase) currentKB).getFrameStoreManager();
		RemoteClientFrameStore remote_frame_store = (RemoteClientFrameStore) framestore_manager.getFrameStoreFromClass(RemoteClientFrameStore.class);
		RemoteServer server = remote_frame_store.getRemoteServer();
        RemoteSession session = remote_frame_store.getSession();
        try {
            session = server.cloneSession(session);
        } catch (RemoteException e) {
            Log.getLogger().info("Could not find server side change project " + e);
            return;
        }
        changes_project = project_manager.connectToProject(server, session, annotationName);
		changes_kb = changes_project.getKnowledgeBase();

		model = new ChangeModel(changes_kb);
	}


	private void loadExistingData() {      
		Collection<Instance> annotateInsts = model.getInstances(AnnotationCls.Annotation);
		loadChanges();
		loadAnnotations(annotateInsts);
	}

	private void loadChanges() {
		for (Change aInst : model.getSortedTopLevelChanges()) {
			changesTreeTableModel.addChangeData(aInst);
		}
	}
	
	public void createChange(Change aChange) {
		changesTreeTableModel.addChangeData(aChange);
		changesMenu.setEnabledLastChange(true);
		changesMenu.setChange(aChange);
	}

	public void modifyChange(Change aChange, Slot slot, List oldValues) {
		changesTreeTableModel.update(aChange, slot, oldValues);
	}
	
	private void loadAnnotations(Collection<Instance> annotateInsts) {
		List<Instance> annotationList = new ArrayList<Instance>(annotateInsts);
		Collections.sort(annotationList, new AnnotationCreationComparator());

		for (Iterator iter = annotationList.iterator(); iter.hasNext();) {
			Instance aInst = (Instance) iter.next();
			annotationsTableModel.addAnnotationData((Annotation) aInst);
		}
	}

	public void updateAnnotationTable() {
		annotationsTableModel.update();
	}

	public void createAnnotationItemInTable(Annotation annotateInst) {
		String body = ((Annotation) annotateInst).getBody();
		if (body == null || body.length() == 0) {
			changes_kb.deleteInstance(annotateInst);
		}
		else{			
			//annotateInst = createUtil.updateAnnotation(annotateInst);
			annotationsTableModel.addAnnotationData((Annotation) annotateInst);
		}
	}

	public String getTimeStamp() {
		Date currTime = new Date();

		String datePattern = "MM/dd/yyyy HH:mm:ss zzz";
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		String time = format.format(currTime);

		return time;
	}

	public static void main(String[] args) {
		edu.stanford.smi.protege.Application.main(args);
	}

	public class AddInstanceAction extends AbstractAction {

		Component myComp;

		public AddInstanceAction(Component c, String prompt) {
			super(prompt, Icons.getCreateClsNoteIcon());
			myComp = c;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {

			if (changesTreeTable.getSelectedRowCount() > 0) {
				final Collection chngInstSelected = new ArrayList();
				
				TreePath[] selectedTreePaths = changesTreeTable.getTree().getSelectionPaths();

				for (TreePath treePath : selectedTreePaths) {
					Object lastPathComp = treePath.getLastPathComponent();
					try {
						if (lastPathComp instanceof ChangeTreeTableNode) {
							Change change = ((ChangeTreeTableNode)lastPathComp).getChange();
							chngInstSelected.add(change);
						} 
					} catch (Exception e) {
						Log.getLogger().warning("Error at getting change table row " + treePath);
					}
				}		

				String annotTypeName = (String)annotationTypes.getSelectedItem();
				
				Cls annotType = changes_kb.getCls(annotTypeName);
				
				annotateInst = changeCreateUtil.createAnnotation(annotType, chngInstSelected);
				
				//this does not work in the client
				//ChangesDb changesDb = ChangesProject.getChangesDb(currentKB); 
				//annotateInst = changesDb.createAnnotation(annotType);
				//changesDb.finalizeAnnotation(annotateInst, chngInstSelected, "");
				
				JFrame edit = changes_project.show(annotateInst);

				edit.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent arg0) {
						createAnnotationItemInTable((Annotation) annotateInst);
					}
				});
				edit.setVisible(true);
			}
		}
	}
	
	@Override
	public void dispose() {
		//TODO: This will be reimplemented once we will have a start/stop model for the ChangesProject
		// This is just a quick fix
			
		//remove the menu item
		JMenuBar menuBar = getMainWindowMenuBar();
		menuBar.remove(changesMenu);
		
		if (changes_kb != null) {
			changes_kb.removeFrameListener(changesListener);
		}
	
		//TODO: remove other listeners
		
		if (getProject().isMultiUserClient()) {

			if (changes_kb != null) {
				Project changesProject = changes_kb.getProject();

				try {
					changesProject.dispose();
				} catch (Exception e) {
					Log.getLogger().warning("Errors at disposing changes project " + changesProject + " of project " + changes_kb);
				}
			}
		}
			
		super.dispose();
	}
	

	public class RemoveInstanceAction extends AbstractAction {

		public RemoveInstanceAction(String prompt) {
			super(prompt, Icons.getDeleteClsNoteIcon());
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			int numSelect = annotationsTable.getSelectedRowCount();

			if (numSelect > 1) { 
				annotationsTableModel.removeAnnotationData(annotationsTable.getSelectedRows());
			} else if (numSelect == 1) {
				String delName = annotationsTableModel.getInstanceName(annotationsTable.getSelectedRow());
				Instance instToDel = changes_kb.getInstance(delName);
				changes_kb.deleteInstance(instToDel);
				annotationsTableModel.removeAnnotationData(annotationsTable.getSelectedRow());
			}

			annotationsTable.clearSelection();
			removeAnnotationAction.setEnabled(false);
			editAnnotationAction.setEnabled(false);
		}
	}

	public class EditInstanceAction extends AbstractAction {
		public EditInstanceAction(String prompt) {
			super(prompt, Icons.getClsNoteIcon());
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int numSelect = annotationsTable.getSelectedRowCount();

			if (numSelect == 1) {
				String instEditName = annotationsTableModel.getInstanceName(annotationsTable.getSelectedRow());

				instToEdit = changes_kb.getInstance(instEditName);

				JFrame edit = changes_project.show(instToEdit);
				edit.addWindowListener(new WindowListener() {
					public void windowOpened(WindowEvent arg0) {
					}

					public void windowClosing(WindowEvent arg0) {
					}

					public void windowClosed(WindowEvent arg0) {
						annotationsTableModel.editAnnotationData(instToEdit, annotationsTable.getSelectedRow());
						removeAnnotationAction.setEnabled(false);
						editAnnotationAction.setEnabled(false);
					}

					public void windowIconified(WindowEvent arg0) {
					}

					public void windowDeiconified(WindowEvent arg0) {
					}

					public void windowActivated(WindowEvent arg0) {
					}

					public void windowDeactivated(WindowEvent arg0) {
					}
				});

				edit.setVisible(true);
			}
		}
	}


}

