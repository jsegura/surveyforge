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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author jgonzalez
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "feedType")
public class Feed implements Serializable
  {
  private static final long    serialVersionUID = -7937801343328085145L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String               id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                  lockingVersion;

  @ManyToOne
  private QuestionnaireElement firstElement;

  private Feed( )
    {
    // TODO Auto-generated constructor stub
    }

  public Feed( QuestionnaireElement firstElement )
    {
    if( firstElement != null )
      this.firstElement = firstElement;
    else
      throw new NullPointerException( );
    }

  public QuestionnaireElement getFirstElement( )
    {
    return firstElement;
    }

  @Override
  public boolean equals( Object object )
    {
    return this.firstElement.equals( ((Feed) object).firstElement );
    }

  @Override
  public int hashCode( )
    {
    return this.firstElement.hashCode( );
    }
  }
