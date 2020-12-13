package controllers

import java.util.UUID

import controllers.ControllerHelpers._
import domain.model.{Account, Email, Password}
import domain.service._
import domain.service.messages._
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

  def updatePassword(oldPassword: String, newPassword: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { cred =>
      authService.updatePassword(UpdatePasswordRequest(Account(cred.email, Password(oldPassword)), Password(newPassword))) match {
        case UpdatePasswordSuccess => Ok("Пароль успешно изменён.")
        case AccountNotFound       => InternalServerError("Произошла ошибка на сервере. Ваш аккаунт не найден")
        case WrongOldPassword      => BadRequest("Старый пароль введён неверно.")
        case InsecureNewPassword   => BadRequest("""Новый пароль недостаточно безопасный, он должен соотвествовать следующим условиям:
            |- длина не менее 6 символов
            |- содержит цифры и буквы(латиница) в верхнем и нижнем регистре
            |""".stripMargin)
      }
    }
  }

}
