package org.specs2
package io

class FromSourceSpec extends SpecificationWithJUnit with FromSource { def is = 
  
  "an expression can be read from a source file" ! {
    (1 must_== 1).desc.toString must contain("(1 must_== 1)")
  }                                                                                       ^
                                                                                          end
}