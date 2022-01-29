package com.wcc17;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

/**
 * Created by wcc17 on 1/15/17.
 */
public class Window {

	static JFrame vineViewerFrame;
	static JPanel vineListPanel = new JPanel();
	static JPanel vineInfoPanel = new JPanel();
	static VLCPanel videoSurfacePanel;
	
    static JScrollPane vineScrollPane;
    static JLabel vineLabel;
    static JButton watchVineButton;
    static JTextField userInputTextField;

    Vine selectedVine = null;
    Map<Vine, String> vineFileMap = new HashMap<Vine, String>();

    public Window() {
        initialize();
    }

    public void initialize() {
    	vineViewerFrame = new JFrame("Vine Viewer");
        vineViewerFrame.setPreferredSize(new Dimension(550, 900));
        vineViewerFrame.setResizable(false);
    	vineViewerFrame.setLayout(new GridBagLayout());
    	vineViewerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeVineJList();
        initializeVineJLabel();
        initializeWatchVineJButton();
        videoSurfacePanel = new VLCPanel();

        setupMainGridLayout();

        //Display the window
        vineViewerFrame.pack();
        vineViewerFrame.setVisible(true);
    }

    public void setupMainGridLayout() {
        GridBagConstraints c = new GridBagConstraints();

        //set up video surface
        c.gridx = 0;
        c.gridy = 0;
        vineViewerFrame.add(videoSurfacePanel, c);

        //set up list of vines
        c.gridx = 0;
        c.gridy = 1;
        vineListPanel.add(vineScrollPane);
        vineViewerFrame.add(vineListPanel, c);

        //set up window to show vine info
//        c.weightx = 1.0;
//        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        initializeVineInfoPanel();
        vineViewerFrame.add(vineInfoPanel, c);
    }

    public void initializeVineInfoPanel() {
        GridBagConstraints c = new GridBagConstraints();
        vineInfoPanel.setLayout(new GridBagLayout());

        c.gridx = 0;
        c.gridy = 0;
        vineInfoPanel.add(watchVineButton, c);

        c.gridx = 0;
        c.gridy = 1;
        vineInfoPanel.add(vineLabel, c);
    }

    public void initializeVineJList() {
    	//set up Vine JList
        List<Vine> vines = VineService.parseVineList();
        Vine[] vineData = new Vine[vines.size()];
        for(int i = 0; i < vines.size(); i++) {
            vineData[i] = vines.get(i);
        }
        final JList<Vine> vineJList = new JList<Vine>(vineData);
        vineScrollPane = new JScrollPane(vineJList);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = vineJList.locationToIndex(e.getPoint());
                System.out.println("Clicked on Item " + index);

                selectedVine = vineJList.getModel().getElementAt(index);
                changeVineLabel(selectedVine);
            }
        };
        vineJList.addMouseListener(mouseListener);

        vineFileMap = VineService.buildVineFileMap(vines);
    }

    public void initializeVineJLabel() {
        vineLabel = new JLabel("No Vine selected");

        //to initialize
        //TODO: this is a big problem. for some reason the indexing of vines is off.
        //TODO: Uncommenting this will expose the problem. Need to look into
        //TODO: may only be happnening in debug mode. not sure whats up with that
        selectedVine = vineFileMap.keySet().iterator().next();
        changeVineLabel(selectedVine);
    }
    
    public void initializeWatchVineJButton() {
    	watchVineButton = new JButton("Watch");
    	watchVineButton.setHorizontalTextPosition(SwingConstants.CENTER);
    	
    	watchVineButton.addActionListener(new ActionListener() {

    	    @Override
    		public void actionPerformed(ActionEvent e) {
                if(selectedVine != null) {
                    videoSurfacePanel.getMediaPlayerComponent()
                            .getMediaPlayer().playMedia("vines/" + vineFileMap.get(selectedVine));
                }
    		}
    	});
    }

    public void changeVineLabel(Vine vine) {
        vineLabel.setText(LabelBuilder.buildLabel(vine));
    }
}
