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

import javax.swing.JPanel;

/** The base from which all content panels come.
 * @author Adrien RICCIARDI
 * @version 1.0 09/02/2013
 */
public abstract class WizardPanel extends JPanel
{
    /** Get the title of the panel.
     * @return The panel's title.
     */
    public abstract String getTitle();
    
    /** Get the panel following these.
     * @return The next panel.
     */
    public abstract WizardPanel getNextPanel();
}
