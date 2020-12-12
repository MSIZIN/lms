package controllers

import java.util.UUID

import domain.model.{Account, Email, Password}
import domain.service._
import domain.service.messages.{AccountExists, EmailExists, EmailNotFound, InsecurePassword, LoginRequest, LoginSuccess, PasswordIncorrect, SignupRequest, SignupSuccess, UserNotFound}
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

  def signup(vercode: String, email: String, password: String): Action[AnyContent] = Action { request =>
    authService.signup(SignupRequest(UUID.fromString(vercode), Account(Email(email), Password(password)))) match {
      case SignupSuccess    => Ok("Вы успешно зарегестрировались. Теперь войдите в систему.")
      case UserNotFound     => BadRequest("Пользователь с таким верификационным кодом не найден.")
      case AccountExists    => BadRequest("Пользователь с таким верификационным кодом уже зарегистрирован")
      case EmailExists      => BadRequest("Такой email уже существует")
      case InsecurePassword => BadRequest("""
          |Пароль должен соотвествовать следующим условиям:
          |- длина не менее 6 символов
          |- содержит цифры и буквы(латиница) в верхнем и нижнем регистре
          |""".stripMargin)
    }
  }

}
