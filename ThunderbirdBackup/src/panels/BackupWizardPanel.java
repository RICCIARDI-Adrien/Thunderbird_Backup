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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

/** This wizard does the backup.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public class BackupWizardPanel extends WizardPanel
{
    /** The thread executing the backup. */
    private class BackupThread extends Thread
    {
        /** Do backup. */
        @Override
        public void run()
        {
            // Check data to backup size
            File sourceFile = new File(System.getProperty("user.home") + "/Library/Thunderbird");
            long sourceSize = getDirectorySize(sourceFile);

            // Check remaining space on destination
            File tempFile = new File(_backupPath);
            long destinationRemainingSpace = tempFile.getUsableSpace();

            // Check if there is enough space to hold backup
            if (sourceSize > destinationRemainingSpace)
            {
                JOptionPane.showMessageDialog(null, "There is not enough space on the device to store the backup.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            
            // Copy directories
            Date currentDate = new Date();
            int day = currentDate.getDate();
            int month = currentDate.getMonth() + 1;
            int year = currentDate.getYear() - 100;
            
            File destinationFile = new File(_backupPath + "/Thunderbird_Backup_20" + year + "_" + month + "_" + day);
            destinationFile.mkdir();
            _sourcePath = sourceFile.getPath();
            _destinationPath = destinationFile.getPath();
            copyDirectories(sourceFile, destinationFile);
            
            // Update interface
            _statusLabel.setText("Backup successfully terminated.");
            _frame.getNextButton().setEnabled(true);
        }
    }
        
    /** The wizard frame. */
    private WizardFrame _frame;
    
    /** The backup path. */
    private String _backupPath;
    
    /** The status label. */
    private JLabel _statusLabel;
    
    /** The source path. */
    private String _sourcePath;
    /** The destination path. */
    private String _destinationPath;
    
    /** Create the backup panel.
     * @param frame The owner frame.
     * @param backupPath Path to the device in which to store the backup.
     */
    public BackupWizardPanel(WizardFrame frame, String backupPath)
    {
        _frame = frame; 
        _backupPath = backupPath;
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        // Add label
        _statusLabel = new JLabel("Please wait...");
        layout.putConstraint(SpringLayout.WEST, _statusLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, _statusLabel, 10, SpringLayout.NORTH, this);
        add(_statusLabel);
                
        // Configure buttons
        _frame.getCancelButton().setEnabled(false);
        JButton buttonNext = _frame.getNextButton();
        buttonNext.setText("Quit");
        buttonNext.setEnabled(false);
        
        // Start thread
        BackupThread thread = new BackupThread();
        thread.start();
    }
    
    /** Get the size in bytes of a directory and all of its subdirectories.
     * @param directory The directory.
     * @return The size in bytes.
     */
    private long getDirectorySize(File directory)
    {
        long size = 0;
        
        // Get a list of all files and directories present at this level of the directories tree
        File[] filesList = directory.listFiles();
        if (filesList.length == 0) return size;
        
        // Check each file at this level of the directories tree
        for (int i = 0; i < filesList.length; i++)
        {
            if (filesList[i].isFile()) size += filesList[i].length();
            else size += getDirectorySize(filesList[i]);
        }
        return size;
    }
    
    /** Convert a source path to a destination path by changing the path begining.
     * @param sourcePath The path to convert.
     * return A string where the beginning as been substituted to point to the destination directory.
     */ 
    private String convertToDestinationPath(String sourcePath)
    {
        return _destinationPath + sourcePath.substring(_sourcePath.length());
    }
    
    /** Copy a whole directories tree into another.
     * @param source The source directory.
     * @param destination The destination directory.
     */
    private void copyDirectories(File sourceDirectory, File destinationDirectory)
    {
        // Get a list of all files and directories present at this level of the directories tree
        File[] filesList = sourceDirectory.listFiles();
        if (filesList.length == 0) return;
        
        for (int i = 0; i < filesList.length; i++)
        {
            File sourceFile = filesList[i];
            
            // Substitute source path by destination path
            String currentPath = sourceFile.getPath();
            currentPath = convertToDestinationPath(currentPath);
            
            if (sourceFile.isDirectory())
            {
                // Create directory
                destinationDirectory = new File(currentPath);
                destinationDirectory.mkdir();
                copyDirectories(sourceFile, destinationDirectory);
            }
            else
            {
                // Copy files
                File destinationFile = new File(currentPath);
                copyFile(sourceFile, destinationFile);
            }
        }
    }
    
    /** Copy a file.
     * @param sourceFile The source file.
     * @param destinationFile The destination file.
     * @return How many bytes were copied.
     */
    private long copyFile(File sourceFile, File destinationFile)
    {
        final int BUFFER_SIZE = 16384;
        long copiedBytes = 0;
        
        try
        {
            FileInputStream input = new FileInputStream(sourceFile);
            FileOutputStream output = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            
            int readBytes;
            while (input.available() > 0)
            {
                // Read buffer by buffer
                readBytes = input.read(buffer);
                // Write to destination
                output.write(buffer, 0, readBytes);
                copiedBytes += readBytes;
            } 
            
            input.close();
            output.close();
        }
        catch (FileNotFoundException exception)
        {
            throw new RuntimeException("Fatal error : " + exception.getMessage());
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Fatal error : " + exception.getMessage());
        }
        
        return copiedBytes;
    }
    
    @Override
    public String getTitle() 
    {
        return "Backuping...";
    }

    @Override
    public WizardPanel getNextPanel() 
    {
        // Signal the end of the wizard
        return null;
    }
    
}
