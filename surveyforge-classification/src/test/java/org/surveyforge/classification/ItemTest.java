/* 
 * surveyforge-classification - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.classification;

import java.util.Date;

import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * Tests related to {@link org.surveyforge.classification.Item}.
 * 
 * @author jgonzalez
 */
public class ItemTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void itemCreationWithNullLevel( )
    {
    new Item( null, null, null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void itemCreationWithNullCode( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );
    Level level = new Level( version, "level" );

    new Item( level, null, null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void itemCreationWithNullOficialTitle( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );
    Level level = new Level( version, "level" );

    new Item( level, null, "code", null );
    }

  @Test
  @ExpectedExceptions( {IllegalArgumentException.class})
  public void itemCreationWithParentItemInSameLevel( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );
    Level level = new Level( version, "level" );
    Item parentItem = new Item( level, null, "parentCode", "parentTitle" );

    new Item( level, parentItem, "code", "parentTitle" );
    }

  @Test
  @ExpectedExceptions( {IllegalArgumentException.class})
  public void itemCreationWithParentItemInNotAdjacentLevel( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );
    Level level1 = new Level( version, "level1" );
    new Level( version, "level2" );
    Level level3 = new Level( version, "level3" );
    Item parentItem = new Item( level1, null, "parentCode", "parentTitle" );

    new Item( level3, parentItem, "code", "parentTitle" );
    }

  @Test
  @ExpectedExceptions( {UnsupportedOperationException.class})
  public void unmodifiableSubItems( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );
    Level level = new Level( version, "level" );
    Item item = new Item( level, null, "code", "title" );
    item.getSubItems( ).add( null );
    }
  }
