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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import org.surveyforge.classification.Item;
import org.surveyforge.classification.Level;
import org.surveyforge.core.data.ObjectData;
import org.surveyforge.core.metadata.DataElement;
import org.surveyforge.core.metadata.RegisterDataElement;
import org.surveyforge.core.metadata.ValueDomain;
import org.surveyforge.core.metadata.domain.ClassificationValueDomain;
import org.surveyforge.core.metadata.domain.QuantityValueDomain;
import org.surveyforge.core.survey.Feed;
import org.surveyforge.core.survey.Questionnaire;
import org.surveyforge.core.survey.QuestionnaireElement;
import org.surveyforge.core.survey.SectionFeed;
import org.surveyforge.util.jgoodies.JXPathPresentationModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author jgonzalez
 */
public class Frame extends JFrame
  {
  private static final long                     serialVersionUID      = -2135625525102812393L;

  private static final ResourceBundle           RESOURCE_BUNDLE       = ResourceBundle
                                                                          .getBundle( "org.surveyforge.runner.RunnerResourceBundle" );

  private Controller                            controller;

  private Map<QuestionnaireElement, JComponent> elementToComponentMap = new HashMap<QuestionnaireElement, JComponent>( );
  private Map<JComponent, QuestionnaireElement> componentToElementMap = new HashMap<JComponent, QuestionnaireElement>( );

  private Questionnaire                         questionnaire;
  private ObjectData                            objectData;
  private PresentationModel                     dataModel;

  /**
   * @param entityManagerFactory
   * @param questionnaire
   * @throws IOException
   */
  public Frame( EntityManager entityManager, Questionnaire questionnaire ) throws IOException
    {
    super( "QuestionnaireRunner - " + questionnaire.getTitle( ) );

    this.questionnaire = questionnaire;

    this.setObjectData( null ); // Create a presentation model for correct action instantiation

    this.controller = new Controller( entityManager, this );
    ActionMap actions = this.controller.getActions( );
    Controller.NewSampleAction newSampleAction = (Controller.NewSampleAction) actions.get( Controller.NEW_SAMPLE_ACTION_NAME );
    newSampleAction.newSample( );

    this.createUI( );
    }

  /**
   * @return
   */
  protected Questionnaire getQuestionnaire( )
    {
    return this.questionnaire;
    }

  /**
   * @return
   */
  protected ObjectData getObjectData( )
    {
    return this.objectData;
    }

  /**
   * @param objectData
   */
  protected void setObjectData( ObjectData objectData )
    {
    this.objectData = objectData;
    if( this.dataModel == null )
      this.dataModel = new JXPathPresentationModel( this.objectData );
    else
      this.dataModel.setBean( this.objectData );
    }

  /**
   * @return
   */
  protected PresentationModel getDataModel( )
    {
    return this.dataModel;
    }

  /**
   * @return
   */
  protected Map<QuestionnaireElement, JComponent> getElementToComponentMap( )
    {
    return this.elementToComponentMap;
    }

  /**
   * @return
   */
  protected Map<JComponent, QuestionnaireElement> getComponentToElementMap( )
    {
    return this.componentToElementMap;
    }

  /**
   * 
   */
  private void createUI( )
    {
    this.createMenuBar( );
    this.createToolBar( );
    this.createQuestionnairePanel( );
    }

  private void createMenuBar( )
    {
    JMenuBar menuBar = new JMenuBar( );

    // Sample menu
    JMenu sampleMenu = new JMenu( Frame.RESOURCE_BUNDLE.getString( "sampleMenu.title" ) );
    sampleMenu.setMnemonic( (Integer) Frame.RESOURCE_BUNDLE.getObject( "sampleMenu.mnemonic" ) );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.NEW_SAMPLE_ACTION_NAME ) ) );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.SAVE_SAMPLE_ACTION_NAME ) ) );
    menuBar.add( sampleMenu );
    sampleMenu.addSeparator( );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.FIRST_SAMPLE_ACTION_NAME ) ) );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.PREVIOUS_SAMPLE_ACTION_NAME ) ) );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.NEXT_SAMPLE_ACTION_NAME ) ) );
    sampleMenu.add( new JMenuItem( this.controller.getActions( ).get( Controller.LAST_SAMPLE_ACTION_NAME ) ) );


    this.setJMenuBar( menuBar );
    }

  private void createToolBar( )
    {
    JToolBar toolbar = new JToolBar( );

    JButton newButton = new JButton( this.controller.getActions( ).get( Controller.NEW_SAMPLE_ACTION_NAME ) );
    newButton.setText( "" );
    toolbar.add( newButton );
    JButton saveButton = new JButton( this.controller.getActions( ).get( Controller.SAVE_SAMPLE_ACTION_NAME ) );
    saveButton.setText( "" );
    toolbar.add( saveButton );

    toolbar.addSeparator( );

    JButton firstButton = new JButton( this.controller.getActions( ).get( Controller.FIRST_SAMPLE_ACTION_NAME ) );
    firstButton.setText( "" );
    toolbar.add( firstButton );
    JButton previousButton = new JButton( this.controller.getActions( ).get( Controller.PREVIOUS_SAMPLE_ACTION_NAME ) );
    previousButton.setText( "" );
    toolbar.add( previousButton );
    JButton nextButton = new JButton( this.controller.getActions( ).get( Controller.NEXT_SAMPLE_ACTION_NAME ) );
    nextButton.setText( "" );
    toolbar.add( nextButton );
    JButton lastButton = new JButton( this.controller.getActions( ).get( Controller.LAST_SAMPLE_ACTION_NAME ) );
    lastButton.setText( "" );
    toolbar.add( lastButton );

    this.getContentPane( ).add( toolbar, BorderLayout.NORTH );
    }

  private void createQuestionnairePanel( )
    {
    JTabbedPane pagesPane = new JTabbedPane( );
    this.getContentPane( ).add( pagesPane, BorderLayout.CENTER );

    int pageNumber = 1;
    for( Feed pageFeed : questionnaire.getPageFeeds( ) )
      {
      pagesPane.addTab( "Page " + pageNumber, this.createPagePanel( questionnaire, pageFeed ) );
      pageNumber++;
      }
    }

  private JScrollPane createPagePanel( Questionnaire questionnaire, Feed pageFeed )
    {
    JScrollPane pageScrollPane = new JScrollPane( );
    JPanel pagePanel = new JPanel( );
    pageScrollPane.setViewportView( pagePanel );
    pagePanel.setLayout( new BoxLayout( pagePanel, BoxLayout.PAGE_AXIS ) );

    for( SectionFeed sectionFeed : questionnaire.getSectionsInPage( pageFeed ) )
      {
      pagePanel.add( this.createSectionPanel( questionnaire, pageFeed, sectionFeed ) );
      }
    pagePanel.add( Box.createVerticalGlue( ) );

    return pageScrollPane;
    }

  private JPanel createSectionPanel( Questionnaire questionnaire, Feed pageFeed, SectionFeed sectionFeed )
    {
    StringBuffer rowSpecification = new StringBuffer( );
    for( int elementIndex = 0; elementIndex <= questionnaire.getComponentElements( ).size( ); elementIndex++ )
      rowSpecification.append( "3dlu, top:pref, 3dlu, pref, " );
    FormLayout sectionLayout = new FormLayout( "3dlu, max(150dlu;pref):grow, 5dlu, left:pref, 3dlu:grow", rowSpecification.toString( ) );
    PanelBuilder pageSectionPanelBuilder = new PanelBuilder( sectionLayout );
    pageSectionPanelBuilder.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.DARK_GRAY, 1 ),
        sectionFeed.getTitle( ), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.DARK_GRAY ) );

    boolean firstElement = true;
    for( QuestionnaireElement element : questionnaire.getElementsInPageAndSection( pageFeed, sectionFeed ) )
      {
      if( !firstElement )
        {
        pageSectionPanelBuilder.nextLine( 2 );
        pageSectionPanelBuilder.addSeparator( "" );
        pageSectionPanelBuilder.nextLine( 2 );
        }
      else
        {
        pageSectionPanelBuilder.nextLine( );
        firstElement = false;
        }

      pageSectionPanelBuilder.nextColumn( ); // Needed to place cursor in correct column
      if( element.getQuestion( ) != null )
        {
        JTextArea questionText = new JTextArea( element.getQuestion( ).getText( ) );
        questionText.setFocusable( false );
        questionText.setEditable( false );
        questionText.setLineWrap( true );
        questionText.setWrapStyleWord( true );

        pageSectionPanelBuilder.add( questionText );
        }
      else
        pageSectionPanelBuilder.nextColumn( 1 );
      pageSectionPanelBuilder.nextColumn( 2 );
      pageSectionPanelBuilder.add( this.createDataEntryComponent( element ) );
      }

    // JButton cambioModelo = new JButton( "Cambio" );
    // cambioModelo.addActionListener( new ActionListener( )
    // {
    // public void actionPerformed( ActionEvent e )
    // {
    // QuestionnaireRunner.TempQuestionnaire newData = new QuestionnaireRunner.TempQuestionnaire( );
    // newData.setAnyoNacimiento( 1950 );
    // newData.setMesNacimiento( 8 );
    // Frame.this.dataModel.setBean( newData );
    // }
    // } );
    // pageSectionPanelBuilder.nextLine( );
    // pageSectionPanelBuilder.nextLine( );
    // pageSectionPanelBuilder.nextColumn( 3 );
    // pageSectionPanelBuilder.add( cambioModelo );

    return pageSectionPanelBuilder.getPanel( );
    }

  private JComponent createDataEntryComponent( QuestionnaireElement element )
    {
    if( element.getRegisterDataElement( ).getComponentElements( ).isEmpty( ) )
      {
      // Simple question
      String propertyName = Frame.computePropertyName( element.getRegisterDataElement( ) );
      ValueDomain valueDomain = element.getRegisterDataElement( ).getValueDomain( );
      if( valueDomain instanceof QuantityValueDomain )
        {
        QuantityValueDomain quantityValueDomain = (QuantityValueDomain) valueDomain;
        // JTextField field = new JTextField( quantityValueDomain.getPrecision( ) );
        JFormattedTextField field = BasicComponentFactory.createIntegerField( this.getDataModel( ).getModel( propertyName ) );
        field.setColumns( quantityValueDomain.getPrecision( ) );
        field.addFocusListener( this.controller.getSelectionController( ) );
        field.setInputVerifier( this.controller.getValidationController( ) );

        this.elementToComponentMap.put( element, field );
        this.componentToElementMap.put( field, element );
        return field;
        }
      else if( valueDomain instanceof ClassificationValueDomain )
        {
        final ClassificationValueDomain classificationValueDomain = (ClassificationValueDomain) valueDomain;
        JPanel classificationPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        // JComboBox classificationCombo = new JComboBox( classificationValueDomain.getLevel( ).getItems( ).toArray( ) );
        JComboBox classificationCombo = new JComboBox( new ComboBoxAdapter( classificationValueDomain.getLevel( ).getItems( ),
            new Frame.ClassificationConverter( classificationValueDomain.getLevel( ), this.dataModel.getModel( propertyName ) ) ) );

        // JTextField classificationCode = new JTextField( 5 );
        JTextField classificationCode = BasicComponentFactory.createTextField( this.dataModel.getModel( propertyName ) );
        classificationCode.setColumns( 5 );

        classificationCode.addFocusListener( this.controller.getSelectionController( ) );
        classificationCode.setInputVerifier( this.controller.getValidationController( ) );

        classificationPanel.add( classificationCombo );
        classificationPanel.add( classificationCode );
        this.elementToComponentMap.put( element, classificationPanel );
        this.componentToElementMap.put( classificationCode, element );
        return classificationPanel;
        }
      else
        {
        // TODO: Throw an exception?
        return new JTextField( "TODO", 10 );
        }
      }
    else
      {
      // Compuond question
      return new JTextField( "TODO", 10 );
      }
    }

  private static String computePropertyName( RegisterDataElement registerDataElement )
    {
    List<String> identifiers = new LinkedList<String>( );

    DataElement root = registerDataElement.getRootDataElement( );
    DataElement currentDataElement = registerDataElement;
    while( !currentDataElement.equals( root ) )
      {
      identifiers.add( 0, currentDataElement.getIdentifier( ) );
      currentDataElement = currentDataElement.getVariableStructure( );
      }

    StringBuffer propertyName = new StringBuffer( identifiers.get( 0 ) );
    for( String identifier : identifiers.subList( 1, identifiers.size( ) ) )
      {
      propertyName.append( "/" );
      propertyName.append( identifier );
      }

    return propertyName.toString( );
    }

  public static class ClassificationConverter extends AbstractConverter
    {
    private static final long serialVersionUID = 5430851351776185423L;

    private Level             level;

    public ClassificationConverter( Level level, ValueModel subject )
      {
      super( subject );
      this.level = level;
      }

    @Override
    public Object convertFromSubject( Object subjectValue )
      {
      return this.level.getItem( (String) subjectValue, false );
      }

    public void setValue( Object value )
      {
      this.subject.setValue( value != null ? ((Item) value).getCode( ) : null );
      }
    }
  }
