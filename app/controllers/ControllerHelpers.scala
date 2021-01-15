package controllers

import java.time.LocalDate

import domain.model.SessionId
import domain.service.AuthService
import domain.service.messages.{Credentials, SessionNotFound}
import play.api.mvc.Results.Redirect
import play.api.mvc.{Call, Request, Result}

import scala.util.{Failure, Success, Try}

object ControllerHelpers {

  def withAuthenticatedUser(request: Request[Any], authService: AuthService)(func: Credentials => Result): Result =
    request.cookies.get("Authentication") match {
      case Some(sesid) =>
        authService.whoami(SessionId(sesid.value)) match {
          case creds: Credentials => func(creds)
          case _: SessionNotFound => Redirect(Call("GET", "/"))
        }
      case None => Redirect(Call("GET", "/"))
    }

  def parseDate(date: String): Option[LocalDate] =
    Try(LocalDate.parse(date)) match {
      case Success(localDate) => Some(localDate)
      case Failure(_)         => None
    }

}
