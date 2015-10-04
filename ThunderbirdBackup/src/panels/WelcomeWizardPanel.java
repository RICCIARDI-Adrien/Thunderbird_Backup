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
package panels;

import base.WizardFrame;
import base.WizardPanel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/** The first panel of the program.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public class WelcomeWizardPanel extends WizardPanel
{
    /** The wizard frame. */
    private WizardFrame _frame;
    
    /** Create the panel. 
     * @param frame The owner frame.
     */
    public WelcomeWizardPanel(WizardFrame frame)
    {
        _frame = frame;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        // Add first label
        JLabel firstLabel = new JLabel("1. Quit Thunderbird.");
        layout.putConstraint(SpringLayout.WEST, firstLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, firstLabel, 10, SpringLayout.NORTH, this);
        add(firstLabel);
        
        // Add second label
        JLabel secondLabel = new JLabel("2. Plug in the device used to store the backup.");
        layout.putConstraint(SpringLayout.WEST, secondLabel, 0, SpringLayout.WEST, firstLabel);
        layout.putConstraint(SpringLayout.NORTH, secondLabel, 5, SpringLayout.SOUTH, firstLabel);
        add(secondLabel);
        
        // Configure buttons
        frame.getNextButton().setText("Next >");
    }
    
    @Override
    public String getTitle() 
    {
        return "Welcome";
    }
    
    @Override
    public WizardPanel getNextPanel()
    {
        return new DeviceSelectionWizardPanel(_frame);
    }
}
