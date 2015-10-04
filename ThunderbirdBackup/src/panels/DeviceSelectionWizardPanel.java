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
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/** Device selection panel.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public class DeviceSelectionWizardPanel extends WizardPanel
{
    /** The wizard frame. */
    private WizardFrame _frame;
    
    /** The list of the available devices. */
    private JComboBox _comboList;
    
    /** Device root directory (to concatenate with selected device). */
    private String _deviceRootDirectory;
    
    /** Create the panel. 
     * @param frame The owner frame.
     */
    public DeviceSelectionWizardPanel(WizardFrame frame)
    {
        _frame = frame;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        // Add description label
        JLabel label = new JLabel("Select the device which will be used to store backup :");
        layout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, this);
        add(label);
        
        // Add volumes list combo box
        _comboList = new JComboBox(getDevices());
        layout.putConstraint(SpringLayout.WEST, _comboList, 0, SpringLayout.WEST, label);
        layout.putConstraint(SpringLayout.NORTH, _comboList, 10, SpringLayout.SOUTH, label);
        add(_comboList);
        
        // Configure buttons
        frame.getNextButton().setText("Start backup");
    }
    
    /** List plugged devices.
     * @return An array containing all plugged devices.
     */
    private String[] getDevices()
    {
        String operatingSystem = System.getProperty("os.name");
        File file;
        
        // Linux
        if (operatingSystem.equalsIgnoreCase("linux")) _deviceRootDirectory = "/media";
        // Mac OS
        else if (operatingSystem.equalsIgnoreCase("mac os x")) _deviceRootDirectory = "/Volumes";
        // Not implemented for Windows
        else _deviceRootDirectory = "/";
        
        file = new File(_deviceRootDirectory);
        return file.list();
    }
    
    @Override
    public String getTitle() 
    {
        return "Select device";
    }

    @Override
    public WizardPanel getNextPanel() 
    {
        String selectedDevice = (String) _comboList.getSelectedItem();
        return new BackupWizardPanel(_frame, _deviceRootDirectory + '/' + selectedDevice);
    }
}
