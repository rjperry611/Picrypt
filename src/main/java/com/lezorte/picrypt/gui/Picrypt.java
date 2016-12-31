package com.lezorte.picrypt.gui;

import com.lezorte.picrypt.transform.Hider;
import com.lezorte.picrypt.transform.Seeker;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Created by lezorte on 12/3/16.
 */
public class Picrypt extends JFrame{
	
	private static final long serialVersionUID = -8795429647646551627L;
	private JPanel contentPane;
	private JTextField fileET;
	private JTextField imageET;
	private JLabel DL;
	private JTextField imageDT;
	private JButton imageDB;
	private JPasswordField passET;
	private JPasswordField passDT;
	private JLabel passDL;
	private JButton EB;
	private JButton DB;
	private JTextField saveET;
	private JButton saveEB;
	private JTextField saveDT;
	private JButton saveDB;
	private JSeparator separator;

	private void fileButtonE() {
		File file = chooseLoad(null);
		if(file!=null) {
			fileET.setText(file.getPath());
		}
	}

	private void imageButtonE() {
		File file = chooseLoad("PNG Only", "png");
		if(file!=null) {
			imageET.setText(file.getPath());
		}
	}

	private void imageButtonD() {
		File file = chooseLoad("PNG Only", "png");
		if(file!=null) {
			imageDT.setText(file.getPath());
		}
	}

	private void saveButtonE() {
		File file = chooseSave("PNG Only", "png");
		if(file!=null) {
			saveET.setText(file.getPath());
			if(!saveET.getText().endsWith(".png")) {
				saveET.setText(saveET.getText() + ".png");
			}
		}
	}

	private void saveButtonD() {
		File file = chooseSave("Directory");
		if(file!=null) {
			saveDT.setText(file.getPath());
		}
	}

	private void ButtonE() {
		if(eReady()) {
			Hider.hide(fileET.getText(), imageET.getText(), saveET.getText(), new String(passET.getPassword()));
		}
	}
	
	private void ButtonD() {
		if(dReady()) {
			Seeker.seek(imageDT.getText(), saveDT.getText(),new String(passDT.getPassword()));
		}
	}
	
	private File chooseLoad(String filterName, String... filterStrings) {
		JFileChooser chooser = new JFileChooser();
		
		//set up the filter
		FileNameExtensionFilter filter = null;
		if(filterStrings.length!=0) {
			filter = new FileNameExtensionFilter(filterName, filterStrings);
			chooser.setFileFilter(filter);
		}
		
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	private File chooseSave(String filterName, String... filterStrings) {
		JFileChooser chooser = new JFileChooser();
		
		//set up the filter
		FileFilter filter = null;
		if(filterStrings.length!=0) {
			filter = new FileNameExtensionFilter(filterName, filterStrings);
			chooser.setFileFilter(filter);
		}
		
		if(filterName.equals("Directory")) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		int returnVal = chooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
	}

	private boolean eReady() {
		if (fileET.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No file has been selected for encryption", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (imageET.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No image has been selected to encrypt file into", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (saveET.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No save file has been selected", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (passET.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "No password has been entered", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean dReady() {
		if (imageDT.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No image has been selected for decryption", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (passDT.getPassword().equals("")) {
			JOptionPane.showMessageDialog(this, "No password has been entered", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (saveDT.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No image has been selected for decryption", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public Picrypt() {
		//FileManager.frame = this;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setTitle("Picrypt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(310, 470);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel EL = new JLabel("Encrypt");
		GridBagConstraints gbc_EL = new GridBagConstraints();
		gbc_EL.insets = new Insets(0, 0, 5, 0);
		gbc_EL.gridwidth = 9;
		gbc_EL.gridx = 0;
		gbc_EL.gridy = 0;
		contentPane.add(EL, gbc_EL);

		fileET = new JTextField();
		fileET.setEditable(false);
		GridBagConstraints gbc_fileET = new GridBagConstraints();
		gbc_fileET.gridwidth = 5;
		gbc_fileET.insets = new Insets(0, 0, 5, 5);
		gbc_fileET.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileET.gridx = 0;
		gbc_fileET.gridy = 1;
		contentPane.add(fileET, gbc_fileET);
		fileET.setColumns(10);

		JButton fileEB = new JButton("File");
		fileEB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileButtonE();
			}
		});
		GridBagConstraints gbc_fileEB = new GridBagConstraints();
		gbc_fileEB.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileEB.insets = new Insets(0, 0, 5, 0);
		gbc_fileEB.gridwidth = 4;
		gbc_fileEB.gridx = 5;
		gbc_fileEB.gridy = 1;
		contentPane.add(fileEB, gbc_fileEB);

		imageET = new JTextField();
		imageET.setEditable(false);
		GridBagConstraints gbc_imageET = new GridBagConstraints();
		gbc_imageET.gridwidth = 5;
		gbc_imageET.insets = new Insets(0, 0, 5, 5);
		gbc_imageET.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageET.gridx = 0;
		gbc_imageET.gridy = 2;
		contentPane.add(imageET, gbc_imageET);
		imageET.setColumns(10);

		JButton imageEB = new JButton("Image");
		imageEB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageButtonE();
			}
		});
		GridBagConstraints gbc_imageEB = new GridBagConstraints();
		gbc_imageEB.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageEB.insets = new Insets(0, 0, 5, 0);
		gbc_imageEB.gridwidth = 4;
		gbc_imageEB.gridx = 5;
		gbc_imageEB.gridy = 2;
		contentPane.add(imageEB, gbc_imageEB);

		passET = new JPasswordField();
		GridBagConstraints gbc_passET = new GridBagConstraints();
		gbc_passET.gridwidth = 5;
		gbc_passET.insets = new Insets(0, 0, 5, 5);
		gbc_passET.fill = GridBagConstraints.HORIZONTAL;
		gbc_passET.gridx = 0;
		gbc_passET.gridy = 3;
		contentPane.add(passET, gbc_passET);

		JLabel passEL = new JLabel("Password");
		GridBagConstraints gbc_passEL = new GridBagConstraints();
		gbc_passEL.insets = new Insets(0, 0, 5, 0);
		gbc_passEL.gridwidth = 4;
		gbc_passEL.gridx = 5;
		gbc_passEL.gridy = 3;
		contentPane.add(passEL, gbc_passEL);

		saveET = new JTextField();
		saveET.setEditable(false);
		GridBagConstraints gbc_saveET = new GridBagConstraints();
		gbc_saveET.gridwidth = 5;
		gbc_saveET.insets = new Insets(0, 0, 5, 5);
		gbc_saveET.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveET.gridx = 0;
		gbc_saveET.gridy = 4;
		contentPane.add(saveET, gbc_saveET);
		saveET.setColumns(10);

		saveEB = new JButton("Save");
		saveEB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveButtonE();
			}
		});
		GridBagConstraints gbc_saveEB = new GridBagConstraints();
		gbc_saveEB.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveEB.gridwidth = 4;
		gbc_saveEB.insets = new Insets(0, 0, 5, 0);
		gbc_saveEB.gridx = 5;
		gbc_saveEB.gridy = 4;
		contentPane.add(saveEB, gbc_saveEB);

		EB = new JButton("Encrypt");
		EB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonE();
			}
		});
		GridBagConstraints gbc_EB = new GridBagConstraints();
		gbc_EB.gridwidth = 9;
		gbc_EB.insets = new Insets(0, 0, 5, 0);
		gbc_EB.gridx = 0;
		gbc_EB.gridy = 7;
		contentPane.add(EB, gbc_EB);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.ipady = 20;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 8;
		contentPane.add(separator, gbc_separator);

		DL = new JLabel("Decrypt");
		GridBagConstraints gbc_DL = new GridBagConstraints();
		gbc_DL.insets = new Insets(0, 0, 5, 0);
		gbc_DL.gridwidth = 9;
		gbc_DL.gridx = 0;
		gbc_DL.gridy = 9;
		contentPane.add(DL, gbc_DL);

		imageDT = new JTextField();
		imageDT.setEditable(false);
		GridBagConstraints gbc_imageDT = new GridBagConstraints();
		gbc_imageDT.gridwidth = 5;
		gbc_imageDT.insets = new Insets(0, 0, 5, 5);
		gbc_imageDT.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageDT.gridx = 0;
		gbc_imageDT.gridy = 10;
		contentPane.add(imageDT, gbc_imageDT);
		imageDT.setColumns(10);

		imageDB = new JButton("Image");
		imageDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imageButtonD();
			}
		});
		GridBagConstraints gbc_imageDB = new GridBagConstraints();
		gbc_imageDB.fill = GridBagConstraints.HORIZONTAL;
		gbc_imageDB.insets = new Insets(0, 0, 5, 0);
		gbc_imageDB.gridwidth = 4;
		gbc_imageDB.gridx = 5;
		gbc_imageDB.gridy = 10;
		contentPane.add(imageDB, gbc_imageDB);

		passDT = new JPasswordField();
		GridBagConstraints gbc_passDT = new GridBagConstraints();
		gbc_passDT.gridwidth = 5;
		gbc_passDT.insets = new Insets(0, 0, 5, 5);
		gbc_passDT.fill = GridBagConstraints.HORIZONTAL;
		gbc_passDT.gridx = 0;
		gbc_passDT.gridy = 11;
		contentPane.add(passDT, gbc_passDT);

		passDL = new JLabel("Password");
		GridBagConstraints gbc_passDL = new GridBagConstraints();
		gbc_passDL.insets = new Insets(0, 0, 5, 0);
		gbc_passDL.gridwidth = 4;
		gbc_passDL.gridx = 5;
		gbc_passDL.gridy = 11;
		contentPane.add(passDL, gbc_passDL);

		saveDT = new JTextField();
		saveDT.setEditable(false);
		GridBagConstraints gbc_saveDT = new GridBagConstraints();
		gbc_saveDT.gridwidth = 5;
		gbc_saveDT.insets = new Insets(0, 0, 5, 5);
		gbc_saveDT.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveDT.gridx = 0;
		gbc_saveDT.gridy = 12;
		contentPane.add(saveDT, gbc_saveDT);
		saveDT.setColumns(10);

		saveDB = new JButton("Save");
		GridBagConstraints gbc_saveDB = new GridBagConstraints();
		gbc_saveDB.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveDB.gridwidth = 4;
		gbc_saveDB.insets = new Insets(0, 0, 5, 0);
		gbc_saveDB.gridx = 5;
		gbc_saveDB.gridy = 12;
		saveDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveButtonD();
			}
		});
		contentPane.add(saveDB, gbc_saveDB);

		DB = new JButton("Decrypt");
		GridBagConstraints gbc_DB = new GridBagConstraints();
		gbc_DB.insets = new Insets(0, 0, 5, 0);
		gbc_DB.gridwidth = 9;
		gbc_DB.gridx = 0;
		gbc_DB.gridy = 14;
		contentPane.add(DB, gbc_DB);
		DB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ButtonD();
			}
		});
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Picrypt frame = new Picrypt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
