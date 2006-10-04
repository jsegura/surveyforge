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

import javax.persistence.EntityManagerFactory;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.JTextComponent;

import org.surveyforge.core.data.ObjectData;
import org.surveyforge.core.metadata.ValueDomain;
import org.surveyforge.core.survey.Questionnaire;
import org.surveyforge.core.survey.QuestionnaireElement;

import com.openinput.tools.swing.ArrayActionMap;
import com.openinput.tools.swing.ResourceBundleBackedAction;

/**
 * @author jgonzalez
 */
public class QuestionnaireRunnerController
  {
  private static final ResourceBundle           RESOURCE_BUNDLE         = ResourceBundle
                                                                            .getBundle( "org.surveyforge.runner.QuestionnaireRunnerResourceBundle" );

  public static final String                    NEW_SAMPLE_ACTION_NAME  = "org.surveyforge.runner.QuestionnaireRunnerController.newSampleAction";
  public static final String                    SAVE_SAMPLE_ACTION_NAME = "org.surveyforge.runner.QuestionnaireRunnerController.saveSampleAction";

  private Action[]                              supportedActionsArray;
  private ActionMap                             supportedActions;

  private ValidationController                  validationController    = new QuestionnaireRunnerController.ValidationController( );
  private SelectionController                   selectionController     = new QuestionnaireRunnerController.SelectionController( );
  private RoutingController                     routingController       = new QuestionnaireRunnerController.RoutingController( );

  private EntityManagerFactory                  entityManagerFactory;
  private QuestionnaireFrame                    questionnaireFrame;
  private Questionnaire                         questionnaire;

  private Map<QuestionnaireElement, JComponent> elementToComponentMap;
  private Map<JComponent, QuestionnaireElement> componentToElementMap;


  public QuestionnaireRunnerController( EntityManagerFactory entityManagerFactory, QuestionnaireFrame questionnaireFrame )
    {
    this.entityManagerFactory = entityManagerFactory;
    this.questionnaireFrame = questionnaireFrame;
    this.questionnaire = this.questionnaireFrame.getQuestionnaire( );

    this.elementToComponentMap = this.questionnaireFrame.getElementToComponentMap( );
    this.componentToElementMap = this.questionnaireFrame.getComponentToElementMap( );

    this.supportedActionsArray = new Action[] {new QuestionnaireRunnerController.NewSampleAction( ),
        new QuestionnaireRunnerController.SaveSampleAction( )};
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

  /**
   * @author jgonzalez
   */
  class NewSampleAction extends ResourceBundleBackedAction
    {
    private static final long serialVersionUID = -7347987080988646268L;

    private final String      questionTitle    = RESOURCE_BUNDLE.getString( NEW_SAMPLE_ACTION_NAME + ".question.title" );
    private final String      questionMessage  = RESOURCE_BUNDLE.getString( NEW_SAMPLE_ACTION_NAME + ".question.message" );
    private final Object[]    options          = {RESOURCE_BUNDLE.getString( NEW_SAMPLE_ACTION_NAME + ".button.yes" ),
                                                   RESOURCE_BUNDLE.getString( NEW_SAMPLE_ACTION_NAME + ".button.no" ),
                                                   RESOURCE_BUNDLE.getString( NEW_SAMPLE_ACTION_NAME + ".button.cancel" )};

    public NewSampleAction( )
      {
      super( QuestionnaireRunnerController.NEW_SAMPLE_ACTION_NAME, QuestionnaireRunnerController.RESOURCE_BUNDLE );
      }

    public void actionPerformed( ActionEvent e )
      {
      int selectedOption = 1;
      if( QuestionnaireRunnerController.this.questionnaireFrame.getDataModel( ).isChanged( ) )
        {
        selectedOption = JOptionPane.showOptionDialog( QuestionnaireRunnerController.this.questionnaireFrame, this.questionMessage,
            this.questionTitle, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[2] );
        }

      switch( selectedOption )
        {
        case 0:
          JOptionPane.showMessageDialog( QuestionnaireRunnerController.this.questionnaireFrame, "SAVING SAMPLE -- TODO!!!!" );

        case 1:
          Questionnaire questionnaire = QuestionnaireRunnerController.this.questionnaire;
          ObjectData emptyObjectData = questionnaire.getRegister( ).getRegisterData( ).createEmptyObjectData( );
          QuestionnaireRunnerController.this.questionnaireFrame.setObjectData( emptyObjectData );
          break;

        default:
          // Do nothing
          break;
        }
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
      super( QuestionnaireRunnerController.SAVE_SAMPLE_ACTION_NAME, QuestionnaireRunnerController.RESOURCE_BUNDLE );
      QuestionnaireRunnerController.this.questionnaireFrame.getDataModel( ).addPropertyChangeListener( "changed",
          new PropertyChangeListener( )
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
      JOptionPane.showMessageDialog( QuestionnaireRunnerController.this.questionnaireFrame, "SAVING SAMPLE -- TODO!!!!" );
      QuestionnaireRunnerController.this.questionnaireFrame.getDataModel( ).resetChanged( );
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

        QuestionnaireElement questionnaireElement = QuestionnaireRunnerController.this.componentToElementMap.get( component );
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
          JOptionPane.ERROR_MESSAGE );
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
