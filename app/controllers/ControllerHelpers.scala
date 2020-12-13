package controllers

import domain.model.SessionId
import domain.service.AuthService
import domain.service.messages.{Credentials, SessionNotFound}
import play.api.mvc.Results.Redirect
import play.api.mvc.{Call, Request, Result}

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

}
