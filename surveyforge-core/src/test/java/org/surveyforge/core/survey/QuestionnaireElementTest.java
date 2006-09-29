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
package org.surveyforge.core.survey;

import java.lang.reflect.Method;

import org.surveyforge.core.metadata.ConceptualDataElement;
import org.surveyforge.core.metadata.GlobalVariable;
import org.surveyforge.core.metadata.ObjectVariable;
import org.surveyforge.core.metadata.RegisterDataElement;
import org.surveyforge.core.metadata.StatisticalObjectType;
import org.surveyforge.core.metadata.domain.LogicalValueDomain;
import org.surveyforge.core.metadata.domain.StructuredValueDomain;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class QuestionnaireElementTest
  {
  @DataProvider(name = "dp")
  public Object[][] createData( Method m )
    {
    StatisticalObjectType objectType = new StatisticalObjectType( "objectType" );
    GlobalVariable globalVariable = new GlobalVariable( "globalVariable" );
    ObjectVariable objectVariable = new ObjectVariable( objectType, globalVariable, "objectVariable" );
    ConceptualDataElement conceptualDataElement = new ConceptualDataElement( "conceptualDataElement", new LogicalValueDomain( ),
        objectVariable );
    RegisterDataElement registerDataElement = new RegisterDataElement( "registerDataElement", conceptualDataElement );
    return new Object[][] {new Object[] {registerDataElement}};
    }

  @Test
  public void Test( )
    {


    QuestionnaireElement upperQE = new QuestionnaireElement( "upperQE", new StructuredValueDomain( ) );
    QuestionnaireElement q1 = new QuestionnaireElement( "qe1", new LogicalValueDomain( ) );
    QuestionnaireElement q2 = new QuestionnaireElement( "qe2", new LogicalValueDomain( ) );
    QuestionnaireElement q3 = new QuestionnaireElement( "qe3", new LogicalValueDomain( ) );
    QuestionnaireElement q4 = new QuestionnaireElement( "qe4", new LogicalValueDomain( ) );
    QuestionnaireElement q5 = new QuestionnaireElement( "qe5", new LogicalValueDomain( ) );
    upperQE.addComponentElement( q1 );
    upperQE.addComponentElement( q2 );
    upperQE.addComponentElement( q3 );
    upperQE.addComponentElement( q4 );
    upperQE.addComponentElement( q5 );
    Assert.assertEquals( 5, upperQE.getComponentElements( ).size( ) );
    Assert.assertEquals( 5, upperQE.getRegisterDataElement( ).getComponentElements( ).size( ) );
    Assert.assertEquals( 5, upperQE.getRegisterDataElement( ).getValueDomain( ).getSubDomains( ).size( ) );


    }
  }
