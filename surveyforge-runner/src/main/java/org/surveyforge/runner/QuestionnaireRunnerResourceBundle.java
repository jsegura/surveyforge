/* 
 * surveyforge-runner - Copyright (C) 2006 OPEN input - http://www.openinput.com/
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation; either version 2 of the License, or (at your 
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to 
 *   the Free Software Foundation, Inc., 
 *   59 Temple Place, Suite 330, 
 *   Boston, MA 02111-1307 USA
 *   
 * $Id$
 */
package org.surveyforge.runner;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ListResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * @author jgonzalez
 */
public class QuestionnaireRunnerResourceBundle extends ListResourceBundle
  {
  private static Object[][] CONTENTS = 
    {
    // Sample menu
    {"sampleMenu.title", "Sample"},
    {"sampleMenu.mnemonic", KeyEvent.VK_S},
      
    // New sample action
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".name", "New"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".shortDescription", "Creates a new sample"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".longDescription", "Creates a new sample"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "toolbarButtonGraphics/general/New16.gif" ) )},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".actionCommandKey", QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK )}, 
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_N )},
    
    // New sample action messages
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".question.title", "Modified sample"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".question.message", "The sample has been modified, do you want to save it?"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".button.yes", "Yes"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".button.no", "No"},
    {QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME + ".button.cancel", "Cancel"},
    
    // Save sample action
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".name", "Save"},
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".shortDescription", "Save current sample"},
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".longDescription", "Save current sample"},
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "toolbarButtonGraphics/general/Save16.gif" ) )},
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".actionCommandKey", QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME},
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK )}, 
    {QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_S )},
    
    };

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListResourceBundle#getContents()
   */
  @Override
  public Object[][] getContents( )
    {
    return QuestionnaireRunnerResourceBundle.CONTENTS;
    }
  }
