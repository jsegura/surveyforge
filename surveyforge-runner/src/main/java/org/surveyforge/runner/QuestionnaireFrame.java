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
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
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
public class QuestionnaireFrame extends JFrame
  {
  private static final long                    serialVersionUID        = -2135625525102812393L;

  private Questionnaire                        questionnaire;
  private Map<QuestionnaireElement, Component> questionnaireComponents = new HashMap<QuestionnaireElement, Component>( );
  private ObjectData                           objectData;
  private PresentationModel                    dataModel;

  public QuestionnaireFrame( Questionnaire questionnaire )
    {
    super( "QuestionnaireRunner" );

    this.questionnaire = questionnaire;
    this.setObjectData( questionnaire.getRegister( ).getRegisterData( ).createEmptyObjectData( ) );

    JTabbedPane pagesPane = new JTabbedPane( );
    this.getContentPane( ).add( pagesPane );

    for( Feed pageFeed : questionnaire.getPageFeeds( ) )
      {
      pagesPane.addTab( "Page", this.createPagePanel( questionnaire, pageFeed ) );
      }
    }

  protected ObjectData getObjectData( )
    {
    return this.objectData;
    }

  protected void setObjectData( ObjectData objectData )
    {
    this.objectData = objectData;
    this.dataModel = new JXPathPresentationModel( this.objectData );
    }

  protected PresentationModel getDataModel( )
    {
    return this.dataModel;
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

    return pageScrollPane;
    }

  private JPanel createSectionPanel( Questionnaire questionnaire, Feed pageFeed, SectionFeed sectionFeed )
    {
    StringBuffer rowSpecification = new StringBuffer( );
    for( int elementIndex = 0; elementIndex <= questionnaire.getElements( ).size( ); elementIndex++ )
      rowSpecification.append( "3dlu, top:pref, 3dlu, pref, " );
    FormLayout sectionLayout = new FormLayout( "3dlu, pref, 5dlu, left:pref, 3dlu:grow", rowSpecification.toString( ) );

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
        pageSectionPanelBuilder.addLabel( element.getQuestion( ).getText( ) );
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
    // QuestionnaireFrame.this.dataModel.setBean( newData );
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
      String propertyName = QuestionnaireFrame.computePropertyName( element.getRegisterDataElement( ) );
      ValueDomain valueDomain = element.getRegisterDataElement( ).getValueDomain( );
      if( valueDomain instanceof QuantityValueDomain )
        {
        QuantityValueDomain quantityValueDomain = (QuantityValueDomain) valueDomain;
        // JTextField field = new JTextField( quantityValueDomain.getPrecision( ) );
        JTextField field = BasicComponentFactory.createIntegerField( this.getDataModel( ).getModel( propertyName ) );
        field.setColumns( quantityValueDomain.getPrecision( ) );
        this.questionnaireComponents.put( element, field );
        return field;
        }
      else if( valueDomain instanceof ClassificationValueDomain )
        {
        final ClassificationValueDomain classificationValueDomain = (ClassificationValueDomain) valueDomain;
        JPanel classificationPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
        // JComboBox classificationCombo = new JComboBox( classificationValueDomain.getLevel( ).getItems( ).toArray( ) );
        JComboBox classificationCombo = new JComboBox( new ComboBoxAdapter( classificationValueDomain.getLevel( ).getItems( ),
            new QuestionnaireFrame.ClassificationConverter( classificationValueDomain.getLevel( ), this.dataModel
                .getModel( propertyName ) ) ) );

        // JTextField classificationCode = new JTextField( 5 );
        final JTextField classificationCode = BasicComponentFactory.createTextField( this.dataModel.getModel( propertyName ) );
        classificationCode.setColumns( 5 );
        classificationCode.setInputVerifier( new InputVerifier( )
          {
            Color defaultBackground = classificationCode.getBackground( );

            @Override
            public boolean verify( JComponent component )
              {
              if( classificationValueDomain.getLevel( ).includes( classificationCode.getText( ), false ) )
                {
                classificationCode.setBackground( defaultBackground );
                return true;
                }
              else
                {
                classificationCode.setBackground( Color.RED );
                Toolkit.getDefaultToolkit( ).beep( );
                JOptionPane.showMessageDialog( classificationCode.getRootPane( ),
                    "El c\u00f3digo introducido no se encuentra entre las opciones disponibles.", "C\u00f3digo err\u00f3neo",
                    JOptionPane.ERROR_MESSAGE );
                return false;
                }
              }

            @Override
            public boolean shouldYieldFocus( JComponent input )
              {
              return verify( input );
              }
          } );

        classificationPanel.add( classificationCombo );
        classificationPanel.add( classificationCode );
        this.questionnaireComponents.put( element, classificationPanel );
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

  // public static class ClassificationFormatter extends JFormattedTextField.AbstractFormatter
  // {
  // private Level level;
  //
  // public ClassificationFormatter( Level level )
  // {
  // this.level = level;
  // }
  //
  // @Override
  // public Object stringToValue( String text ) throws ParseException
  // {
  // // TODO Auto-generated method stub
  // return this.level.getItem( text, false );
  // }
  //
  // @Override
  // public String valueToString( Object value ) throws ParseException
  // {
  // return value != null ? ((Item) value).getCode( ) : null;
  // }
  // }
  //
  public static class ClassificationConverter extends AbstractConverter
    {
    private Level level;

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
