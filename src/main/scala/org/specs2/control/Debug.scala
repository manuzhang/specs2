package org.specs2
package control

private[specs2] trait Debug {
  implicit def printAndPass[T](t: =>T) = new { 
    def pp = Console.println(t); t 
  } 
}