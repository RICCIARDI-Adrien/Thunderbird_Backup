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

import java.io.File;
import javax.swing.JOptionPane;

/** Entry point for ThunderbirdBackup.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public class Main 
{
    public static void main(String args[])
    {
        // Is the operating system Windows ?
        String operatingSystemName = System.getProperty("os.name");
        if (!operatingSystemName.equalsIgnoreCase("mac os x"))
        {
            JOptionPane.showMessageDialog(null, "This program can run only on Mac OS X for now.", "Error", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        
        // Is the Thunderbird directory existing ?
        File directory = new File(System.getProperty("user.home") + "/Library/Thunderbird");
        System.out.println(System.getProperty("user.home") + "/Library/Thunderbird");
        if (!directory.exists() || !directory.isDirectory())
        {
            JOptionPane.showMessageDialog(null, "Can't find Thunderbird data to backup.", "Error", JOptionPane.ERROR_MESSAGE); 
            return;
        }
                
        WizardFrame frame = new WizardFrame("ThunderbirdBackup");
    }    
}
