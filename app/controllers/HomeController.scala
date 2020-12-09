package controllers

import javax.inject._

import play.api.mvc._

@Singleton
class HomeController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { request =>
    Ok("""
        |Добро пожаловать в систему управления обучением!
        |Введите email и пароль или зарегистрируйтесь по верификационному коду.
        |""".stripMargin)
  }

}
