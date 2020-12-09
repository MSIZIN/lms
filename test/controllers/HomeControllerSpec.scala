package controllers

import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status.OK
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout, status}

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerSuite with ScalaFutures {

  val homeController: HomeController = app.injector.instanceOf(classOf[HomeController])

  "HomeController" must {

    "contain welcome message" in {
      val result = homeController.index(FakeRequest())

      status(result) must equal(OK)
      contentAsString(result) must include("Добро пожаловать в систему управления обучением!")
    }

  }

}
