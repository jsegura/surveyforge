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
package org.surveyforge.core.metadata.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.surveyforge.classification.Level;
import org.surveyforge.core.metadata.ValueDomain;

/**
 * @author jgonzalez
 */
@Entity
public class ClassificationValueDomain extends ValueDomain
  {
  private static final long serialVersionUID = 2295375925240989153L;

  @ManyToOne
  private Level             level;
  private boolean           sublevelsAllowed = true;

  public ClassificationValueDomain( Level level )
    {
    this( level, true );
    }

  public ClassificationValueDomain( Level level, boolean sublevelsAllowed )
    {
    this.setLevel( level );
    this.setSublevelsAllowed( sublevelsAllowed );
    }

  public Level getLevel( )
    {
    return this.level;
    }

  public void setLevel( Level level )
    {
    if( level != null )
      this.level = level;
    else
      throw new NullPointerException( );
    }

  public boolean isSublevelsAllowed( )
    {
    return this.sublevelsAllowed;
    }

  public void setSublevelsAllowed( boolean sublevelsAllowed )
    {
    this.sublevelsAllowed = sublevelsAllowed;
    }

  @Override
  public boolean isValid( Serializable object )
    {
    if( object instanceof String )
      {
      String code = (String) object;
      return this.getLevel( ).isIncludedInLevel( code, this.isSublevelsAllowed( ) );
      }
    else
      return false;
    }

  @Override
  public ClassificationValueDomain clone( )
    {
    ClassificationValueDomain copy = (ClassificationValueDomain) super.clone( );
    return copy;
    }
  }
