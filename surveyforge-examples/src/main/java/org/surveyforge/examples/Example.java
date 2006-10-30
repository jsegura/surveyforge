/* 
 * surveyforge-core - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.examples;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.surveyforge.classification.Classification;
import org.surveyforge.classification.Family;
import org.surveyforge.classification.Item;
import org.surveyforge.classification.Level;
import org.surveyforge.classification.Version;
import org.surveyforge.core.metadata.domain.ClassificationValueDomain;
import org.surveyforge.core.metadata.domain.LogicalValueDomain;
import org.surveyforge.core.metadata.domain.QuantityValueDomain;
import org.surveyforge.core.metadata.domain.StringValueDomain;
import org.surveyforge.core.survey.Question;
import org.surveyforge.core.survey.Questionnaire;
import org.surveyforge.core.survey.QuestionnaireElement;
import org.surveyforge.core.survey.Study;
import org.surveyforge.util.InternationalizedString;

/**
 * @author jsegura
 */
public class Example
  {

  /**
   * @param args
   */
  public static void main( String[] args )
    {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "surveyforge" );
    EntityManager entityManager = entityManagerFactory.createEntityManager( );


    /* Creation of an example family */
    Family exampleFamily = new Family( "exampleFamily" );
    Classification sexClassification = new Classification( exampleFamily, "sex Classification" );
    Version sexVersion = new Version( sexClassification, "sex Version", Calendar.getInstance( ).getTime( ) );
    Level sexLevel = new Level( sexVersion, "sex Level" );
    new Item( sexLevel, null, "1", "Male" );
    new Item( sexLevel, null, "2", "Female" );

    Classification hobbyClassification = new Classification( exampleFamily, "hobby Classification" );
    Version hobbyVersion = new Version( hobbyClassification, "hobby Version", Calendar.getInstance( ).getTime( ) );
    Level hobbyLevel = new Level( hobbyVersion, "hobby Level" );
    new Item( hobbyLevel, null, "1", "Sports" );
    new Item( hobbyLevel, null, "2", "Computers" );
    new Item( hobbyLevel, null, "3", "Animals" );


    Study study = new Study( "exampleStudy" );
    study.setTitle( "SurveyForge example study" );
    study.setDescription( "This is an example study created to show SurveyForge" );

    Questionnaire questionnaire = new Questionnaire( "exampleQuestionnaire", study );
    questionnaire.setTitle( "SurveyForge example questionnaire" );
    questionnaire.setDescription( "This is an example questionnaire created to show SurveyForge" );

    QuestionnaireElement qe1 = new QuestionnaireElement( "NAME", new StringValueDomain( 0, 20 ) );
    qe1.setQuestion( new Question( "NAME", "Name" ) );
    questionnaire.addComponentElement( qe1 );

    questionnaire.getSectionFeeds( ).get( 0 ).setTitle( "First Section" );


    QuestionnaireElement qe2 = new QuestionnaireElement( "SEX", new ClassificationValueDomain( sexLevel ) );
    qe2.setQuestion( new Question( "SEX", "Sex" ) );
    questionnaire.addComponentElement( qe2 );

    QuestionnaireElement qe3 = new QuestionnaireElement( "AGE", new QuantityValueDomain( 2, 0 ) );
    qe3.setQuestion( new Question( "AGE", "Age" ) );
    questionnaire.addComponentElement( qe3 );

    QuestionnaireElement qe4 = new QuestionnaireElement( "HOBBY", new ClassificationValueDomain( hobbyLevel ) );
    qe4.setQuestion( new Question( "HOBBY", "Hobby" ) );
    questionnaire.addComponentElement( qe4 );

    InternationalizedString sectionTitle2 = new InternationalizedString( );
    sectionTitle2.setString( "Second section" );
    questionnaire.createSectionFeed( qe4, sectionTitle2 );

    QuestionnaireElement qe5 = new QuestionnaireElement( "NUMBER", new QuantityValueDomain( 1, 0 ) );
    qe5.setQuestion( new Question( "NUMBER", "What's your preferred number?" ) );
    questionnaire.addComponentElement( qe5 );


    QuestionnaireElement qe6 = new QuestionnaireElement( "HAPPY", new LogicalValueDomain( ) );
    qe6.setQuestion( new Question( "HAPPY", "Are you happy?" ) );
    questionnaire.addComponentElement( qe6 );

    questionnaire.createPageFeed( qe6 );

    QuestionnaireElement qe7 = new QuestionnaireElement( "SAD", new LogicalValueDomain( ) );
    qe7.setQuestion( new Question( "SAD", "Are you sad?" ) );
    questionnaire.addComponentElement( qe7 );
    InternationalizedString sectionTitle3 = new InternationalizedString( );
    sectionTitle3.setString( "Third section" );
    questionnaire.createSectionFeed( qe7, sectionTitle3 );


    EntityTransaction transaction = entityManager.getTransaction( );
    transaction.begin( );

    Query deleteFamily = entityManager.createQuery( "SELECT f FROM Family f WHERE f.identifier = :identifier" );
    deleteFamily.setParameter( "identifier", "exampleFamily" );
    if( !deleteFamily.getResultList( ).isEmpty( ) ) entityManager.remove( deleteFamily.getSingleResult( ) );
    Query deleteStudy = entityManager.createQuery( "SELECT s FROM Study s WHERE s.identifier = :identifier" );
    deleteStudy.setParameter( "identifier", "exampleStudy" );
    if( !deleteStudy.getResultList( ).isEmpty( ) ) entityManager.remove( deleteStudy.getSingleResult( ) );

    entityManager.flush( );
    entityManager.persist( exampleFamily );
    entityManager.persist( study );
    transaction.commit( );
    entityManager.close( );


    }
  }
