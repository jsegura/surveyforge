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

import java.util.Arrays;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.surveyforge.classification.Classification;
import org.surveyforge.classification.Family;
import org.surveyforge.classification.Item;
import org.surveyforge.classification.Level;
import org.surveyforge.classification.Version;
import org.surveyforge.core.metadata.Register;
import org.surveyforge.core.metadata.RegisterDataElement;
import org.surveyforge.core.metadata.domain.AbstractValueDomain;
import org.surveyforge.core.metadata.domain.ClassificationValueDomain;
import org.surveyforge.core.metadata.domain.QuantityValueDomain;
import org.surveyforge.core.metadata.domain.StructuredValueDomain;
import org.surveyforge.core.survey.Question;
import org.surveyforge.core.survey.Questionnaire;
import org.surveyforge.core.survey.QuestionnaireElement;
import org.surveyforge.core.survey.Study;

/**
 * @author jgonzalez
 */
public class QuestionnaireRunner
  {
  public static Classification SEXO;

  static
    {
    GregorianCalendar calendar = new GregorianCalendar( );
    calendar.clear( );
    calendar.set( 2006, 0, 1 );

    Family ceescat_hivudvp = new Family( "ceescat-hivudvp" );

    QuestionnaireRunner.SEXO = new Classification( ceescat_hivudvp, "hivudvp-sexo" );
    Version sexoV = new Version( QuestionnaireRunner.SEXO, "sexo V1", calendar.getTime( ) );
    Level sexoL = new Level( sexoV, "sexo L1" );
    new Item( sexoL, null, "1", "Hombre" );
    new Item( sexoL, null, "2", "Mujer" );
    new Item( sexoL, null, "3", "Transexual" );
    }

  public static void main( String[] args )
    {
    try
      {
      // PlasticLookAndFeel.setPlasticTheme( new com.jgoodies.looks.plastic.theme.DesertBlue( ) );
      UIManager.setLookAndFeel( "com.jgoodies.looks.plastic.Plastic3DLookAndFeel" );
      }
    catch( Exception e )
      {
      // Likely PlasticXP is not in the class path; ignore.
      }

     QuestionnaireFrame questionnaireFrame = new QuestionnaireFrame( QuestionnaireRunner.getQuestionnaire( ) );
     questionnaireFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
     questionnaireFrame.pack( );
     questionnaireFrame.setVisible( true );
    }

  public static Questionnaire getQuestionnaire( )
    {
    Level sexoL = QuestionnaireRunner.SEXO.getCurrentVersion( ).getLevels( ).get( 0 );

    Register register = new Register( "register" );
    register.setIdentifier( "registroPrueba" );

    AbstractValueDomain mesNacimientoValueDomain = new QuantityValueDomain( 2, 0 );
    AbstractValueDomain anyoNacimientoValueDomain = new QuantityValueDomain( 4, 0 );
    StructuredValueDomain fechaNacimientoValueDomain = new StructuredValueDomain( );
    fechaNacimientoValueDomain.addSubDomain( mesNacimientoValueDomain );
    fechaNacimientoValueDomain.addSubDomain( anyoNacimientoValueDomain );

    RegisterDataElement mesNacimiento = new RegisterDataElement( "mesNacimiento", mesNacimientoValueDomain );
    RegisterDataElement anyoNacimiento = new RegisterDataElement( "anyoNacimiento", anyoNacimientoValueDomain );
//    RegisterDataElement fechaNacimiento = new RegisterDataElement( "fechaNacimiento", fechaNacimientoValueDomain );
//    fechaNacimiento.addComponentElement( mesNacimiento );
//    fechaNacimiento.addComponentElement( anyoNacimiento );
    RegisterDataElement sexo = new RegisterDataElement( "sexo", new ClassificationValueDomain( sexoL ) );
    
//    register.setComponentElements( Arrays.asList( new RegisterDataElement[] {fechaNacimiento, sexo} ) );
    register.setComponentElements( Arrays.asList( new RegisterDataElement[] {mesNacimiento, anyoNacimiento, sexo} ) );

    Study study = new Study( "study" );
    Questionnaire questionnaire = new Questionnaire( study, register, "questionnaire" );

    Question pMesNacimiento = new Question( "pMesNacimiento" );
    pMesNacimiento.setText( "En qué mes naciste?" );
    QuestionnaireElement qMesNacimiento = new QuestionnaireElement( mesNacimiento );
    qMesNacimiento.setQuestion( pMesNacimiento );
    questionnaire.addElement( qMesNacimiento );

    Question pAnyoNacimiento = new Question( "pAnyoNacimiento" );
    pAnyoNacimiento.setText( "En qué año naciste?" );
    QuestionnaireElement qAnyoNacimiento = new QuestionnaireElement( anyoNacimiento );
    qAnyoNacimiento.setQuestion( pAnyoNacimiento );
    questionnaire.addElement( qAnyoNacimiento );

    Question pSexo = new Question( "pSexo" );
    pSexo.setText( "Sexo" );
    QuestionnaireElement qSexo = new QuestionnaireElement( sexo );
    qSexo.setQuestion( pSexo );
    questionnaire.addElement( qSexo );

    return questionnaire;
    }

  public static class TempQuestionnaire
    {
    private int    mesNacimiento  = 1;
    private int    anyoNacimiento = 1985;
    // private QuestionnaireRunner.TempSexo sexo = new QuestionnaireRunner.TempSexo( );

    private String sexo           = QuestionnaireRunner.SEXO.getCurrentVersion( ).getLevels( ).get( 0 ).getItems( ).get( 2 ).getCode( );

    public String getSexo( )
      {
      System.out.println( "Devolviendo valor sexo: " + sexo );
      return sexo;
      }

    public void setSexo( String sexo )
      {
      System.out.println( "Nuevo valor sexo: " + sexo );
      this.sexo = sexo;
      }

    public int getMesNacimiento( )
      {
      System.out.println( "Devolviendo valor mes: " + mesNacimiento );
      return mesNacimiento;
      }

    public void setMesNacimiento( int mesNacimiento )
      {
      System.out.println( "Nuevo valor mes: " + mesNacimiento );
      this.mesNacimiento = mesNacimiento;
      }

    public int getAnyoNacimiento( )
      {
      System.out.println( "Devolviendo valor anyo: " + anyoNacimiento );
      return anyoNacimiento;
      }

    public void setAnyoNacimiento( int anyoNacimiento )
      {
      System.out.println( "Nuevo valor anyo: " + anyoNacimiento );
      this.anyoNacimiento = anyoNacimiento;
      }

    // public QuestionnaireRunner.TempSexo getSexo( )
    // {
    // return sexo;
    // }
    //
    // public void setSexo( QuestionnaireRunner.TempSexo sexo )
    // {
    // this.sexo = sexo;
    // }
    }

  public static class TempSexo
    {
    private String sexo = QuestionnaireRunner.SEXO.getCurrentVersion( ).getLevels( ).get( 0 ).getItems( ).get( 2 ).getCode( );

    public String getSexo( )
      {
      System.out.println( "Devolviendo valor sexo: " + sexo );
      return sexo;
      }

    public void setSexo( String sexo )
      {
      System.out.println( "Nuevo valor sexo: " + sexo );
      this.sexo = sexo;
      }
    }
  }
