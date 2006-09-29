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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.GenericGenerator;
import org.surveyforge.core.metadata.ValueDomain;

// TODO: Elaborate on comments
/**
 * @author jsegura
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractValueDomain implements Serializable, Cloneable, ValueDomain
  {
  private static final long serialVersionUID = -83487445078388347L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  protected String          id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  protected int             lockingVersion;


  protected AbstractValueDomain( )
    {}


  public List<AbstractValueDomain> getSubDomains( )
    {
    return new ArrayList<AbstractValueDomain>( );
    }


  @Override
  public AbstractValueDomain clone( )
    {
    try
      {
      AbstractValueDomain copy = (AbstractValueDomain) super.clone( );
      copy.id = null;
      copy.lockingVersion = 0;
      return copy;
      }
    catch( CloneNotSupportedException exc )
      {
      throw new InternalError( exc.getLocalizedMessage( ) );
      }
    }
  }
