package controllers

import domain.model.{Account, Email, Password}
import domain.service._
import javax.inject._
import play.api.mvc._

@Singleton
class AuthController @Inject() (authService: AuthService, cc: ControllerComponents) extends AbstractController(cc) {

  def login(email: String, password: String): Action[AnyContent] = Action { request =>
    authService.login(LoginRequest(Account(Email(email), Password(password)))) match {
      case LoginSuccess(sessionId) => Ok("Вы успешно вошли в систему.").withCookies(Cookie("Authentication", sessionId.value))
      case EmailNotFound           => BadRequest("Такой email не найден.")
      case PasswordIncorrect       => BadRequest("Неверный пароль.")
    }
  }

}
