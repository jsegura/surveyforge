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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.surveyforge.core.survey.Questionnaire;

/**
 * @author jgonzalez
 */
public class QuestionnaireRunner
  {
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

    EntityManager entityManager = null;
    EntityTransaction transaction = null;
    QuestionnaireFrame questionnaireFrame = null;
    try
      {
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "hivudvp" );
      entityManager = entityManagerFactory.createEntityManager( );
      transaction = entityManager.getTransaction( );
      transaction.begin( );

      Query questionnaireQuery = entityManager.createQuery( "FROM Questionnaire q WHERE q.identifier = :questionnaireIdentifier" );
      questionnaireQuery.setParameter( "questionnaireIdentifier", args[0] );
      if( !questionnaireQuery.getResultList( ).isEmpty( ) )
        {
        questionnaireFrame = new QuestionnaireFrame( (Questionnaire) questionnaireQuery.getResultList( ).get( 0 ) );
        questionnaireFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        questionnaireFrame.pack( );
        }
      }
    finally
      {
      if( transaction != null ) transaction.commit( );
      if( entityManager != null ) entityManager.close( );
      }

    if( questionnaireFrame != null ) questionnaireFrame.setVisible( true );
    }
  }
