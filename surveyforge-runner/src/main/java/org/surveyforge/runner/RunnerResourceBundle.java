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
public class RunnerResourceBundle extends ListResourceBundle
  {
  private static Object[][] CONTENTS = 
    {
      // General icons
      {"icon.dialog.error", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/status/dialog-error.png" ) ) },
      {"icon.dialog.information", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/status/dialog-information.png" ) ) },
      {"icon.dialog.warning", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/status/dialog-warning.png" ) ) },
          
      // Sample menu
      {"sampleMenu.title", "Sample"},
      {"sampleMenu.mnemonic", KeyEvent.VK_S},
      
      // Modified sample dialog
      {Controller.MODIFIED_SAMPLE_DIALOG + ".question.title", "Modified sample"},
      {Controller.MODIFIED_SAMPLE_DIALOG + ".question.message", "The sample has been modified, do you want to save it?"},
      {Controller.MODIFIED_SAMPLE_DIALOG + ".button.yes", "Yes"},
      {Controller.MODIFIED_SAMPLE_DIALOG + ".button.no", "No"},
      {Controller.MODIFIED_SAMPLE_DIALOG + ".button.cancel", "Cancel"},

      // New sample action
      {Controller.NEW_SAMPLE_ACTION_NAME + ".name", "New"},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".shortDescription", "Creates a new sample"},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".longDescription", "Creates a new sample"},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/document-new.png" ) )},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.NEW_SAMPLE_ACTION_NAME},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK )},
      {Controller.NEW_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_N )},

      // Save sample action
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".name", "Save"},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".shortDescription", "Save current sample"},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".longDescription", "Save current sample"},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/document-save.png" ) )},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.SAVE_SAMPLE_ACTION_NAME},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK )},
      {Controller.SAVE_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_S )},

      // First sample action
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".name", "First"},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".shortDescription", "Go to first sample"},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".longDescription", "Go to first sample"},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/go-first.png" ) )},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.FIRST_SAMPLE_ACTION_NAME},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_F, ActionEvent.CTRL_MASK )},
      {Controller.FIRST_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_F )},

      // Last sample action
      {Controller.LAST_SAMPLE_ACTION_NAME + ".name", "Last"},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".shortDescription", "Go to last sample"},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".longDescription", "Go to last sample"},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/go-last.png" ) )},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.LAST_SAMPLE_ACTION_NAME},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_L, ActionEvent.CTRL_MASK )},
      {Controller.LAST_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_L )},

      // Next sample action
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".name", "Next"},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".shortDescription", "Go to next sample"},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".longDescription", "Go to next sample"},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/go-next.png" ) )},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.NEXT_SAMPLE_ACTION_NAME},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK )},
      {Controller.NEXT_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_E )},

      // Previous sample action
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".name", "Previous"},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".shortDescription", "Go to previous sample"},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".longDescription", "Go to previous sample"},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".smallIcon", new ImageIcon( ClassLoader.getSystemResource( "org/tango-project/tango-icon-theme/16x16/actions/go-previous.png" ) )},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".actionCommandKey", Controller.PREVIOUS_SAMPLE_ACTION_NAME},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".acceleratorKey", KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.CTRL_MASK )},
      {Controller.PREVIOUS_SAMPLE_ACTION_NAME + ".mnemonicKey", new Integer( KeyEvent.VK_P )},

    };

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListResourceBundle#getContents()
   */
  @Override
  public Object[][] getContents( )
    {
    return RunnerResourceBundle.CONTENTS;
    }
  }
