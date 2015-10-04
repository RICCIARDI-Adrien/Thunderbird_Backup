/*
 * (C) Adrien RICCIARDI, 2013
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import panels.WelcomeWizardPanel;

/** The first frame of the program.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public class WizardFrame extends JFrame
{
    /** Width of the frame in pixels. */
    private final int FRAME_WIDTH = 500;
    /** Height of the frame in pixels. */
    private final int FRAME_HEIGHT = 300;
    
    /** Panel containing frame's specific content. */
    private WizardPanel _currentWizardPanel;    
    /** Panel containing the buttons. */
    private JPanel _buttonsPanel;
    
    /** Cancel button. */
    private JButton _cancelButton;
    /** Next button. */
    protected JButton _nextButton;
    
    /** Title of the wizard application. */
    private String _applicationTitle;
    
    /** Exit from program when Cancel button is clicked. */
    private class CancelButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }
    
    /** Handle Next button click. */
    private class NextButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            WizardPanel newPanel = _currentWizardPanel.getNextPanel();
            if (newPanel == null) System.exit(0); // Exit program if there is no more panel
            
            // Delete current panel
            remove(_currentWizardPanel);
            // Add new panel
            add(newPanel, BorderLayout.CENTER);
            
            // Initialize content
            _currentWizardPanel = newPanel;
            initializePanel(newPanel);
            
            repaint();
        }
    }
        
    /** Create a centered base window. 
     * @param applicationTitle Title of the wizard application.
     */
    public WizardFrame(String applicationTitle)
    {
        super(applicationTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Center frame
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = ((int) screenResolution.getWidth() - FRAME_WIDTH) / 2;
        int frameY = ((int) screenResolution.getHeight() - FRAME_HEIGHT) / 2;
        setBounds(frameX, frameY, FRAME_WIDTH, FRAME_HEIGHT);
        
        // Create cancel button
        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(new CancelButtonListener());
        // Create next button
        _nextButton = new JButton();
        _nextButton.addActionListener(new NextButtonListener());
        
        // Create buttons panel
        _buttonsPanel = new JPanel();
        _buttonsPanel.add(_cancelButton);
        _buttonsPanel.add(_nextButton);
        add(_buttonsPanel, BorderLayout.SOUTH);
        
        _applicationTitle = applicationTitle;
        
        // Set first panel
        _currentWizardPanel = new WelcomeWizardPanel(this);
        initializePanel(_currentWizardPanel);
        
        setVisible(true);
    }
    
    /** Initialize frame properties according to loaded wizard panel.
     * @param panel The loaded panel.
     */
    private void initializePanel(WizardPanel panel)
    {
        // Initialize wizard components
        setTitle(_applicationTitle + " - " + panel.getTitle());
        // Set panel
        add(panel, BorderLayout.CENTER);
    }
    
    /** Get the wizard Cancel button.
     * @return The Cancel button.
     */
    public JButton getCancelButton()
    {
        return _cancelButton;
    }
    
    /** Get the wizard Next button.
     * @return The Next button.
     */
    public JButton getNextButton()
    {
        return _nextButton;
    }
}
