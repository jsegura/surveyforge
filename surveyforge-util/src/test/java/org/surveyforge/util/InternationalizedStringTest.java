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
package org.surveyforge.util;

import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

/**
 * Tests related to {@link org.surveyforge.util.InternationalizedString}.
 * 
 * @author jgonzalez
 */
public class InternationalizedStringTest
  {
  private InternationalizedString i15dString;
  private static Locale           ES             = new Locale( "ES" );
  private static Locale           UK_WIN         = new Locale( "EN", "UK", "WIN" );

  private static String           ESPAÑOL        = "ESPAÑOL";
  private static String           ENGLISH        = "ENGLISH";
  private static String           ENGLISH_UK_WIN = "ENGLISH-UK-WIN";

  @Configuration(beforeTestClass = true)
  public void setupString( )
    {
    this.i15dString = new InternationalizedString( InternationalizedStringTest.ES );
    this.i15dString.setString( InternationalizedStringTest.ES, InternationalizedStringTest.ESPAÑOL );
    this.i15dString.setString( Locale.ENGLISH, InternationalizedStringTest.ENGLISH );
    this.i15dString.setString( InternationalizedStringTest.UK_WIN, InternationalizedStringTest.ENGLISH_UK_WIN );
    }

  @Test
  public void useOfDefaultLocale( )
    {
    Assert.assertEquals( new InternationalizedString( ).getDefaultLocale( ), Locale.getDefault( ) );
    Assert.assertEquals( new InternationalizedString( (Locale) null ).getDefaultLocale( ), Locale.getDefault( ) );
    }

  @Test
  public void lookupStrings( )
    {
    Locale ES_es = new Locale( "ES", "es" );
    Locale ES_es_win = new Locale( "ES", "es", "WIN" );

    Assert.assertEquals( this.i15dString.getString( ), InternationalizedStringTest.ESPAÑOL );
    Assert.assertEquals( this.i15dString.getString( ES_es ), InternationalizedStringTest.ESPAÑOL );
    Assert.assertEquals( this.i15dString.getString( ES_es_win ), InternationalizedStringTest.ESPAÑOL );

    Assert.assertEquals( this.i15dString.getString( Locale.ENGLISH ), InternationalizedStringTest.ENGLISH );
    Assert.assertEquals( this.i15dString.getString( Locale.UK ), InternationalizedStringTest.ENGLISH );
    Assert.assertEquals( this.i15dString.getString( Locale.US ), InternationalizedStringTest.ENGLISH );
    Assert.assertEquals( this.i15dString.getString( InternationalizedStringTest.UK_WIN ), InternationalizedStringTest.ENGLISH_UK_WIN );

    Assert.assertEquals( this.i15dString.getString( Locale.FRENCH ), InternationalizedStringTest.ESPAÑOL );
    }

  // TODO: Add tests for setString with null parameters, removeString and getLocales
  }
