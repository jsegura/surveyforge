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
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import org.surveyforge.core.survey.Questionnaire;

/**
 * @author jgonzalez
 */
public class QuestionnaireRunner
  {
  public static void main( String[] args ) throws IOException
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

    // Default selection color
    UIDefaults defaults = UIManager.getDefaults( );
    defaults.put( "TextField.selectionBackground", new ColorUIResource( Color.ORANGE ) );
    defaults.put( "FormattedTextField.selectionBackground", new ColorUIResource( Color.ORANGE ) );

    EntityManager entityManager = null;
    EntityTransaction transaction = null;
    Frame frame = null;
    try
      {
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "surveyforge" );
      entityManager = entityManagerFactory.createEntityManager( );
      entityManager.setFlushMode( FlushModeType.COMMIT );
      transaction = entityManager.getTransaction( );
      transaction.begin( );

      Query questionnaireQuery = entityManager.createQuery( "FROM Questionnaire q WHERE q.identifier = :questionnaireIdentifier" );
      questionnaireQuery.setParameter( "questionnaireIdentifier", args[0] );
      if( !questionnaireQuery.getResultList( ).isEmpty( ) )
        {
        frame = new Frame( entityManager, (Questionnaire) questionnaireQuery.getResultList( ).get( 0 ) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack( );
        }
      }
    finally
      {
      if( transaction != null && transaction.isActive( ) ) transaction.commit( );
      }

    if( frame != null ) frame.setVisible( true );
    }
  }
