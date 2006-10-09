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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.JTextComponent;

import org.surveyforge.core.data.ObjectData;
import org.surveyforge.core.data.RegisterData;
import org.surveyforge.core.metadata.ValueDomain;
import org.surveyforge.core.survey.Questionnaire;
import org.surveyforge.core.survey.QuestionnaireElement;
import org.surveyforge.util.beans.Observable;

import com.openinput.tools.swing.ArrayActionMap;
import com.openinput.tools.swing.ResourceBundleBackedAction;

/**
 * @author jgonzalez
 */
@Observable
public class Controller
  {
  private static final ResourceBundle           RESOURCE_BUNDLE             = ResourceBundle
                                                                                .getBundle( "org.surveyforge.runner.RunnerResourceBundle" );

  public static final String                    MODIFIED_SAMPLE_DIALOG      = "org.surveyforge.runner.Controller.modifiedSampleDialog";

  public static final String                    NEW_SAMPLE_ACTION_NAME      = "org.surveyforge.runner.Controller.newSampleAction";
  public static final String                    SAVE_SAMPLE_ACTION_NAME     = "org.surveyforge.runner.Controller.saveSampleAction";

  public static final String                    FIRST_SAMPLE_ACTION_NAME    = "org.surveyforge.runner.Controller.firstSampleAction";
  public static final String                    LAST_SAMPLE_ACTION_NAME     = "org.surveyforge.runner.Controller.lastSampleAction";
  public static final String                    NEXT_SAMPLE_ACTION_NAME     = "org.surveyforge.runner.Controller.nextSampleAction";
  public static final String                    PREVIOUS_SAMPLE_ACTION_NAME = "org.surveyforge.runner.Controller.proviousSampleAction";

  private Action[]                              supportedActionsArray;
  private ActionMap                             supportedActions;

  private ValidationController                  validationController        = new Controller.ValidationController( );
  private SelectionController                   selectionController         = new Controller.SelectionController( );
  private RoutingController                     routingController           = new Controller.RoutingController( );

  private EntityManager                         entityManager;
  private Frame                                 frame;
  private Questionnaire                         questionnaire;
  private int                                   currentSampleIndex;

  private Map<QuestionnaireElement, JComponent> elementToComponentMap;
  private Map<JComponent, QuestionnaireElement> componentToElementMap;


  public Controller( EntityManager entityManager, Frame frame )
    {
    this.entityManager = entityManager;
    this.frame = frame;
    this.questionnaire = this.frame.getQuestionnaire( );

    this.elementToComponentMap = this.frame.getElementToComponentMap( );
    this.componentToElementMap = this.frame.getComponentToElementMap( );

    this.supportedActionsArray = new Action[] {new Controller.NewSampleAction( ), new Controller.SaveSampleAction( ),
        new Controller.FirstSampleAction( ), new Controller.PreviousSampleAction( ), new Controller.NextSampleAction( ),
        new Controller.LastSampleAction( )};
    this.supportedActions = new ArrayActionMap( this.supportedActionsArray );
    }

  public ActionMap getActions( )
    {
    return this.supportedActions;
    }

  public InputVerifier getValidationController( )
    {
    return this.validationController;
    }

  public FocusListener getSelectionController( )
    {
    return this.selectionController;
    }

  public ActionListener getRoutingController( )
    {
    return this.routingController;
    }

  public int getCurrentSampleIndex( )
    {
    return this.currentSampleIndex;
    }

  public void setCurrentSampleIndex( int currentSampleIndex )
    {
    RegisterData registerData = this.questionnaire.getRegister( ).getRegisterData( );
    this.entityManager.refresh( registerData );
    if( currentSampleIndex != registerData.getObjectData( ).size( ) )
      this.frame.setObjectData( registerData.getObjectData( ).get( currentSampleIndex ) );
    this.currentSampleIndex = currentSampleIndex;
    }

  /**
   * @return <code>true</code> if the calling operation may go on, <code>false</code> otherwise.
   */
  private boolean savePossiblyModifiedSample( ) throws RuntimeException
    {
    String questionTitle = RESOURCE_BUNDLE.getString( MODIFIED_SAMPLE_DIALOG + ".question.title" );
    String questionMessage = RESOURCE_BUNDLE.getString( MODIFIED_SAMPLE_DIALOG + ".question.message" );
    Object[] options = {RESOURCE_BUNDLE.getString( MODIFIED_SAMPLE_DIALOG + ".button.yes" ),
        RESOURCE_BUNDLE.getString( MODIFIED_SAMPLE_DIALOG + ".button.no" ),
        RESOURCE_BUNDLE.getString( MODIFIED_SAMPLE_DIALOG + ".button.cancel" )};

    boolean sampleChanged = Controller.this.frame.getDataModel( ).isChanged( );
    int selectedOption;
    if( sampleChanged )
      {
      selectedOption = JOptionPane.showOptionDialog( Controller.this.frame, questionMessage, questionTitle,
          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, (Icon) Controller.RESOURCE_BUNDLE
              .getObject( "icon.dialog.warning" ), options, options[2] );
      }
    else
      selectedOption = 1;

    switch( selectedOption )
      {
      case 0:
        ActionMap actions = Controller.this.getActions( );
        SaveSampleAction saveSampleAction = (SaveSampleAction) actions.get( Controller.SAVE_SAMPLE_ACTION_NAME );
        saveSampleAction.saveSample( );
        return true;

      case 1:
        ObjectData objectData = Controller.this.frame.getObjectData( );
        if( sampleChanged && objectData.getRegisterData( ) != null )
          {
          Controller.this.entityManager.refresh( objectData );
          Controller.this.frame.setObjectData( objectData );
          }
        return true;

      default:
        return false;
      }
    }

  /**
   * @author jgonzalez
   */
  class NewSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -7347987080988646268L;

    public NewSampleAction( )
      {
      super( Controller.NEW_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      }

    public void actionPerformed( ActionEvent e )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      try
        {
        boolean proceed = Controller.this.savePossiblyModifiedSample( );
        if( proceed ) this.newSample( );
        }
      catch( RuntimeException exc )
        {
        // Do nothing, a dialog showing an error message has been already shown
        // TODO: Confirm this
        }
      finally
        {
        Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
        }
      }

    public void newSample( )
      {
      Questionnaire questionnaire = Controller.this.questionnaire;
      RegisterData registerData = questionnaire.getRegister( ).getRegisterData( );
      ObjectData emptyObjectData = registerData.createEmptyObjectData( );
      Controller.this.frame.setObjectData( emptyObjectData );
      Controller.this.setCurrentSampleIndex( registerData.getObjectData( ).size( ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class SaveSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -7347987080988646268L;

    public SaveSampleAction( )
      {
      super( Controller.SAVE_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      Controller.this.frame.getDataModel( ).addPropertyChangeListener( "changed", new PropertyChangeListener( )
        {
          public void propertyChange( PropertyChangeEvent event )
            {
            SaveSampleAction.this.setEnabled( (Boolean) event.getNewValue( ) );
            }
        } );
      this.setEnabled( false );
      }

    public void actionPerformed( ActionEvent e )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      this.setEnabled( false );
      try
        {
        this.saveSample( );
        ((NewSampleAction) Controller.this.getActions( ).get( Controller.NEW_SAMPLE_ACTION_NAME )).newSample( );
        }
      catch( RuntimeException exc )
        {
        exc.printStackTrace( );
        this.setEnabled( true );
        }
      finally
        {
        Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
        }
      }

    public void saveSample( ) throws RuntimeException
      {
      try
        {
        EntityTransaction transaction = Controller.this.entityManager.getTransaction( );
        transaction.begin( );

        // TODO: We should make all the checks, and include this in RegisterData... so just testing
        ObjectData objectData = Controller.this.frame.getObjectData( );
        RegisterData registerData = Controller.this.questionnaire.getRegister( ).getRegisterData( );
        if( objectData.getRegisterData( ) == null )
          {
          // New sample
          Controller.this.entityManager.refresh( registerData );
          registerData.addObjectData( objectData );
          }
        else
          {
          // Modified sample
          // TODO: Check correct values
          }
        transaction.commit( );
        }
      catch( RuntimeException exc )
        {
        JOptionPane.showMessageDialog( Controller.this.frame, "ERROR SAVING!!!!", "title", JOptionPane.ERROR_MESSAGE,
            (Icon) Controller.RESOURCE_BUNDLE.getObject( "icon.dialog.error" ) );
        throw exc;
        }
      }
    }

  /**
   * @author jgonzalez
   */
  class FirstSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -6986950258409902794L;

    public FirstSampleAction( )
      {
      super( Controller.FIRST_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      Controller.this.addPropertyChangeListener( new PropertyChangeListener( )
        {
          public void propertyChange( PropertyChangeEvent evt )
            {
            Controller.FirstSampleAction.this.setEnabled( Controller.this.getCurrentSampleIndex( ) != 0 );
            }
        } );
      }

    public void actionPerformed( ActionEvent event )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      boolean proceed = Controller.this.savePossiblyModifiedSample( );
      if( proceed ) Controller.this.setCurrentSampleIndex( 0 );
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class LastSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -4626085247870941473L;

    public LastSampleAction( )
      {
      super( Controller.LAST_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      Controller.this.addPropertyChangeListener( new PropertyChangeListener( )
        {
          public void propertyChange( PropertyChangeEvent evt )
            {
            int registerDataSize = Controller.this.questionnaire.getRegister( ).getRegisterData( ).getObjectData( ).size( );
            Controller.LastSampleAction.this.setEnabled( Controller.this.getCurrentSampleIndex( ) != registerDataSize - 1 );
            }
        } );
      }

    public void actionPerformed( ActionEvent event )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      boolean proceed = Controller.this.savePossiblyModifiedSample( );
      if( proceed )
        {
        RegisterData registerData = Controller.this.questionnaire.getRegister( ).getRegisterData( );
        Controller.this.entityManager.refresh( registerData );
        int lastIndex = registerData.getObjectData( ).size( ) - 1;
        Controller.this.setCurrentSampleIndex( lastIndex );
        }
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class NextSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -2126442792714281158L;

    public NextSampleAction( )
      {
      super( Controller.NEXT_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      Controller.this.addPropertyChangeListener( new PropertyChangeListener( )
        {
          public void propertyChange( PropertyChangeEvent evt )
            {
            int registerDataSize = Controller.this.questionnaire.getRegister( ).getRegisterData( ).getObjectData( ).size( );
            Controller.NextSampleAction.this.setEnabled( Controller.this.getCurrentSampleIndex( ) < registerDataSize - 1 );
            }
        } );
      }

    public void actionPerformed( ActionEvent event )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      boolean proceed = Controller.this.savePossiblyModifiedSample( );
      if( proceed ) Controller.this.setCurrentSampleIndex( Controller.this.getCurrentSampleIndex( ) + 1 );
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class PreviousSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -9057003601613857118L;

    public PreviousSampleAction( )
      {
      super( Controller.PREVIOUS_SAMPLE_ACTION_NAME, Controller.RESOURCE_BUNDLE );
      Controller.this.addPropertyChangeListener( new PropertyChangeListener( )
        {
          public void propertyChange( PropertyChangeEvent evt )
            {
            Controller.PreviousSampleAction.this.setEnabled( Controller.this.getCurrentSampleIndex( ) != 0 );
            }
        } );
      }

    public void actionPerformed( ActionEvent event )
      {
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
      boolean proceed = Controller.this.savePossiblyModifiedSample( );
      if( proceed ) Controller.this.setCurrentSampleIndex( Controller.this.getCurrentSampleIndex( ) - 1 );
      Controller.this.frame.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class ValidationController extends InputVerifier
    {
    // private EntityManagerFactory entityManagerFactory;
    private Color                        errorColor        = new Color( 1.0f, 0.625f, 0.625f );
    private Map<JComponent, ValueDomain> validationCache   = new HashMap<JComponent, ValueDomain>( );
    private Map<JComponent, Color>       defaultBackground = new HashMap<JComponent, Color>( );

    protected ValueDomain getValueDomain( JComponent component )
      {
      if( !this.validationCache.containsKey( component ) )
        {
        // EntityManager entityManager = entityManagerFactory.createEntityManager( );

        QuestionnaireElement questionnaireElement = Controller.this.componentToElementMap.get( component );
        // entityManager.refresh( questionnaireElement );
        this.validationCache.put( component, questionnaireElement.getRegisterDataElement( ).getValueDomain( ) );

        // entityManager.clear( );
        }

      return this.validationCache.get( component );
      }

    @Override
    public boolean verify( JComponent component )
      {
      // Get value from component
      Object value = null;
      if( component instanceof JFormattedTextField )
        {
        // We must format the value manually, as the change still hasn't been committed
        JFormattedTextField formattedComponent = (JFormattedTextField) component;
        AbstractFormatter formatter = formattedComponent.getFormatter( );
        String textValue = formattedComponent.getText( );
        try
          {
          if( formatter != null )
            value = formatter.stringToValue( textValue );
          else
            value = textValue;
          }
        catch( ParseException exc )
          {
          // Cannot parse the value -> invalid value
          this.showErrorStatus( component );
          return false;
          }
        }
      else if( component instanceof JTextComponent )
        {
        JTextComponent textComponent = (JTextComponent) component;
        value = textComponent.getText( );
        }

      // Validate value using value domain
      if( this.getValueDomain( component ).isValid( (Serializable) value ) )
        {
        // We hava a valid value
        this.showValidStatus( component );
        return true;
        }
      else
        {
        this.showErrorStatus( component );
        return false;
        }
      }

    /**
     * @param component
     */
    protected void showErrorStatus( JComponent component )
      {
      if( !this.defaultBackground.containsKey( component ) ) this.defaultBackground.put( component, component.getBackground( ) );
      component.setBackground( this.errorColor );
      Toolkit.getDefaultToolkit( ).beep( );

      // TODO: Include error messages in value domain
      JOptionPane.showMessageDialog( component.getRootPane( ), "El valor introducido no es correcto.", "Error",
          JOptionPane.ERROR_MESSAGE, (Icon) Controller.RESOURCE_BUNDLE.getObject( "icon.dialog.error" ) );
      }

    /**
     * @param component
     */
    protected void showValidStatus( JComponent component )
      {
      if( this.defaultBackground.containsKey( component ) ) component.setBackground( this.defaultBackground.remove( component ) );
      }
    }

  /**
   * @author jgonzalez
   */
  class SelectionController implements FocusListener
    {
    public void focusGained( FocusEvent event )
      {
      Component component = event.getComponent( );
      if( component instanceof JTextComponent )
        {
        JTextComponent textComponent = (JTextComponent) component;
        textComponent.selectAll( );
        }
      }

    public void focusLost( FocusEvent event )
      {}
    }

  /**
   * @author jgonzalez
   */
  public class RoutingController implements ActionListener
    {
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed( ActionEvent event )
      {}
    }
  }
