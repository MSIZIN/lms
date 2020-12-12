package controllers

import domain.model.{Email, SessionId}
import domain.service._
import domain.service.messages._
import javax.inject._
import play.api.mvc._

@Singleton
class ProfileController @Inject() (authService: AuthService, profileService: ProfileService, cc: ControllerComponents)
    extends AbstractController(cc) {

  def profile: Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request) { creds =>
      profileService.profile(ProfileRequest(creds.email)) match {
        case ProfileSuccess(content) =>
          Ok(s"""
            |Ваш профиль:
            |$content
            |""".stripMargin)
        case ProfileNotFound => InternalServerError("Произошла ошибка на сервере при получении вашего профиля.")
      }
    }
  }

  def anotherProfile(email: String): Action[AnyContent] = Action { request =>
    profileService.anotherProfile(ProfileRequest(Email(email))) match {
      case ProfileSuccess(content) => Ok(content)
      case ProfileNotFound         => BadRequest("Пользователь с таким email не найден.")
    }
  }

  private def withAuthenticatedUser(request: Request[Any])(func: Credentials => Result): Result =
    request.cookies.get("Authentication") match {
      case Some(sesid) =>
        authService.whoami(SessionId(sesid.value)) match {
          case creds: Credentials => func(creds)
          case _: SessionNotFound => Redirect(Call("GET", "/"))
        }
      case None => Redirect(Call("GET", "/"))
    }

}
