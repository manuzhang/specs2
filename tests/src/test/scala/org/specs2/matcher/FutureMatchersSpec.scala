package org.specs2
package matcher

import specification.Environment
import specification.core.Env

import org.specs2.concurrent._
import scala.concurrent._
import duration._

class FutureMatchersSpec extends Specification with ResultMatchers with Retries with Environment { def is(env: Env) = {
 val timeFactor = env.arguments.execute.timeFactor
 val sleep = 50 * timeFactor
 implicit val ee = env.executionEnv

 section("travis") ^
 s2"""

 In this specification `Future` means `scala.concurrent.Future`

 Any `Matcher[T]` can be transformed into a `Matcher[Future[T]]` with the `await` method
 ${ Future.apply(1) must be_>(0).await }

 with a retries number and timeout
 ${ Future { Thread.sleep(sleep); 1 } must be_>(0).await(retries = 3, timeout = 100.millis) }
 ${ (Future { Thread.sleep(sleep * 3); 1 } must be_>(0).await(retries = 4, timeout = 10.millis)) returns "Timeout" }

 with a retries number only
 ${ Future { Thread.sleep(sleep); 1 } must be_>(0).retryAwait(2) }

 with a timeout only
 ${ Future { Thread.sleep(sleep); 1 } must be_>(0).awaitFor(200.millis) }

 A `Future` returning a `Matcher[T]` can be transformed into a `Result`
 ${ Future(1 === 1).await }

 A `throwA[T]` matcher can be used to match a failed future with the `await` method
 ${ Future.failed[Int](new RuntimeException) must throwA[RuntimeException].await }
 ${ { Future.failed[Int](new RuntimeException) must be_===(1).await } must throwA[RuntimeException] }

"""
}
}