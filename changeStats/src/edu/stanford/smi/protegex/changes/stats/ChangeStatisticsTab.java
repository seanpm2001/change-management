package edu.stanford.smi.protegex.changes.stats;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.stanford.bmir.protegex.chao.ChAOKbManager;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.resource.ResourceKey;
import edu.stanford.smi.protege.ui.FrameRenderer;
import edu.stanford.smi.protege.util.AllowableAction;
import edu.stanford.smi.protege.util.ComponentFactory;
import edu.stanford.smi.protege.util.ComponentUtilities;
import edu.stanford.smi.protege.util.DefaultRenderer;
import edu.stanford.smi.protege.util.LabeledComponent;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.util.SelectableTable;
import edu.stanford.smi.protege.widget.AbstractTabWidget;

public class ChangeStatisticsTab extends AbstractTabWidget {

	private KnowledgeBase changesKb;
	private StatsTableModel statsTableModel;
	private SelectableTable statsTable;

	public void initialize() {
		setLabel("Change Statistics");

		changesKb = ChAOKbManager.getChAOKb(getKnowledgeBase());

		statsTableModel = new StatsTableModel(changesKb);
		statsTableModel.generateStatsTable();

		buildGUI();
	}

	private void buildGUI() {

		statsTable = ComponentFactory.createSelectableTable(null);
		//statsTable = new SelectableTable();
		statsTable.setModel(statsTableModel);

		DefaultRenderer renderer = new FrameRenderer();
		for (String columnName : StatsTableModel.columnNames) {
			ComponentUtilities.addColumn(statsTable, renderer);
		}

		statsTable.setShowGrid(true);

		LabeledComponent labeledComp = new LabeledComponent("Change Statistics", new JScrollPane(statsTable), true);

		JButton generateStatsButton = ComponentFactory.createButton(getGenerateStatsButton());

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.add(generateStatsButton);

		labeledComp.setFooterComponent(buttonPanel);

		add(labeledComp);
	}

	private AllowableAction getGenerateStatsButton() {
		return new AllowableAction(new ResourceKey("Generate change statistics")) {

			public void actionPerformed(ActionEvent arg0) {
				statsTableModel.generateStatsTable();
			}

		};
	}


	@Override
	public void dispose() {
		if (getProject().isMultiUserClient()) {
			if (changesKb != null) {
				Project changesProject = changesKb.getProject();

				try {
					//FIXME: This should be handled by the ChAOKbManager
					//changesProject.dispose();
				} catch (Exception e) {
					Log.getLogger().warning("Errors at disposing changes project " + changesProject + " of project " + changesKb);
				}
			}
		}

		super.dispose();
	}

}
