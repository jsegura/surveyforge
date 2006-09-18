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
import org.testng.annotations.DataProvider;

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
  }
