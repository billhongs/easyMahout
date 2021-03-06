package easyMahout.GUI.recommender;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import easyMahout.utils.Constants;

import javax.swing.JCheckBox;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class SimilarityRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultComboBoxModel userSimilarity, itemSimilarity;

	private JComboBox comboBoxSimilarity;

	private JCheckBox chckbxWeighted;

	private final static Logger log = Logger.getLogger(SimilarityRecommenderPanel.class);

	public SimilarityRecommenderPanel() {
		// super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(236, 11, 483, 382);

		JLabel labelSelectSimilarity = new JLabel("Select similarity metric:");
		labelSelectSimilarity.setBounds(21, 11, 216, 14);
		add(labelSelectSimilarity);

		userSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.SPEARMAN,
				Constants.Similarity.TANIMOTO, Constants.Similarity.COSINE });

		itemSimilarity = new DefaultComboBoxModel(new String[] { Constants.Similarity.PEARSON, Constants.Similarity.EUCLIDEAN,
				Constants.Similarity.CITYBLOCK, Constants.Similarity.LOGARITHMIC, Constants.Similarity.TANIMOTO,
				Constants.Similarity.COSINE });

		comboBoxSimilarity = new JComboBox();
		comboBoxSimilarity.setMaximumRowCount(16);

		String type = RecommenderJPanel.getTypePanel().getSelectedType();
		if (type.equals(Constants.RecommType.ITEMBASED)) {
			comboBoxSimilarity.setModel(itemSimilarity);
		} else if (type.equals(Constants.RecommType.USERBASED)) {
			comboBoxSimilarity.setModel(userSimilarity);
		}

		comboBoxSimilarity.setBounds(38, 36, 197, 20);
		add(comboBoxSimilarity);

		chckbxWeighted = new JCheckBox("Weighted");
		chckbxWeighted.setBounds(256, 35, 97, 23);
		add(chckbxWeighted);

		comboBoxSimilarity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String similarity = (String) ((JComboBox) e.getSource()).getSelectedItem();
				if (similarity.equals(Constants.Similarity.EUCLIDEAN) || similarity.equals(Constants.Similarity.PEARSON)
						|| similarity.equals(Constants.Similarity.COSINE)) {
					chckbxWeighted.setEnabled(true);
				} else {
					chckbxWeighted.setEnabled(false);
				}
			}
		});

	}

	public String getSelectedSimilarity() {
		return (String) comboBoxSimilarity.getSelectedItem();
	}

	public boolean isWeighted() {
		return chckbxWeighted.isSelected();
	}

	public void setModelSimilarity(String type) {
		if (type.equals(Constants.RecommType.ITEMBASED)) {
			comboBoxSimilarity.setModel(itemSimilarity);
		} else if (type.equals(Constants.RecommType.USERBASED)) {
			comboBoxSimilarity.setModel(userSimilarity);
		}
	}

	public UserSimilarity getUserSimilarity(DataModel dataModel) {
		try {
			if (chckbxWeighted.isSelected()) {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel, Weighting.WEIGHTED);
					default:
						return null;
				}
			} else {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel);
					case Constants.Similarity.CITYBLOCK:
						return new CityBlockSimilarity(dataModel);
					case Constants.Similarity.LOGARITHMIC:
						return new LogLikelihoodSimilarity(dataModel);
					case Constants.Similarity.SPEARMAN:
						return new SpearmanCorrelationSimilarity(dataModel);
					case Constants.Similarity.TANIMOTO:
						return new TanimotoCoefficientSimilarity(dataModel);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel);
					default:
						return null;
				}
			}
		} catch (TasteException e) {
			// TODO: Taste exception error
			log.error("error creating user similarity");
			return null;
		}
	}
	
	public ItemSimilarity getItemSimilarity(DataModel dataModel) {
		try {
			if (chckbxWeighted.isSelected()) {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel, Weighting.WEIGHTED);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel, Weighting.WEIGHTED);
					default:
						return null;
				}
			} else {
				String similarity = (String) comboBoxSimilarity.getSelectedItem();
				switch (similarity) {
					case Constants.Similarity.PEARSON:
						return new PearsonCorrelationSimilarity(dataModel);
					case Constants.Similarity.EUCLIDEAN:
						return new EuclideanDistanceSimilarity(dataModel);
					case Constants.Similarity.CITYBLOCK:
						return new CityBlockSimilarity(dataModel);
					case Constants.Similarity.LOGARITHMIC:
						return new LogLikelihoodSimilarity(dataModel);					
					case Constants.Similarity.TANIMOTO:
						return new TanimotoCoefficientSimilarity(dataModel);
					case Constants.Similarity.COSINE:
						return new UncenteredCosineSimilarity(dataModel);
					default:
						return null;
				}
			}
		} catch (TasteException e) {
			// TODO: Taste exception error
			log.error("error creating item similarity");
			return null;
		}
	}

}
