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

import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.surveyforge.util.InternationalizedString;

/**
 * @author jgonzalez
 */
@Entity
public class SectionFeed extends Feed
  {
  private static final long       serialVersionUID = 983150580473588830L;

  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString title            = new InternationalizedString( );

  public SectionFeed( QuestionnaireElement firstElement )
    {
    this( firstElement, null );
    }

  public SectionFeed( QuestionnaireElement firstElement, InternationalizedString title )
    {
    super( firstElement );
    if( title != null ) this.title = new InternationalizedString( title );
    }

  public InternationalizedString getInternationalizedTitle( )
    {
    return this.title;
    }

  /**
   * Returns the title of this section for the default language.
   * 
   * @return the title of this section for the default language.
   * @see InternationalizedString#getString()
   */
  public String getTitle( )
    {
    return this.title.getString( );
    }

  /**
   * Returns the title of this section for the given language.
   * 
   * @param locale the language of the title to be returned.
   * @return the title of this section for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getTitle( Locale locale )
    {
    return this.title.getString( locale );
    }

  /**
   * Sets the title of this section. The title must be non <code>null</code>, otherwise a {@link NullPointerException} is thrown.
   * 
   * @param title the title of this section.
   * @throws NullPointerException if the title is <code>null</code>.
   */
  public void setTitle( String title )
    {
    if( title != null )
      this.title.setString( title );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the title of this section for the given language. The language and title must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the title to be set.
   * @param title the title of this section.
   * @throws NullPointerException if the language or title are <code>null</code>.
   */
  public void setTitle( Locale locale, String title )
    {
    if( title != null )
      this.title.setString( locale, title );
    else
      throw new NullPointerException( );
    }
  }
