package domain.service

import dao.AuthDao
import domain.model.{Email, Password, SessionId}
import domain.service.messages.{AccountExists, Credentials, EmailExists, EmailNotFound, InsecurePassword, LoginRequest, LoginResponse, LoginSuccess, PasswordIncorrect, SessionNotFound, SignupRequest, SignupResponse, SignupSuccess, UserNotFound, WhoamiResponse}
import javax.inject._

@Singleton
class AuthServiceImpl @Inject() (authDao: AuthDao) extends AuthService {

  import AuthServiceImpl._

  private var sessions = Map[SessionId, Email]()

  override def login(request: LoginRequest): LoginResponse = {
    val reqAcc = request.account
    authDao.findAccByEmail(reqAcc.email) match {
      case Some(acc) if reqAcc.password == acc.password => {
        val sessionId = SessionId(java.util.UUID.randomUUID().toString)
        sessions += (sessionId -> acc.email)
        LoginSuccess(sessionId)
      }
      case Some(_) => PasswordIncorrect
      case None    => EmailNotFound
    }
  }

  override def signup(request: SignupRequest): SignupResponse =
    authDao.findPersonByVerCode(request.vercode) match {
      case Some(_) =>
        authDao.findAccByVerCode(request.vercode) match {
          case Some(_)                                                        => AccountExists
          case None if authDao.findAccByEmail(request.account.email).nonEmpty => EmailExists
          case None if !isPasswordSecure(request.account.password)            => InsecurePassword
          case None => {
            authDao.insertAcc(request.vercode, request.account)
            SignupSuccess
          }
        }
      case None => UserNotFound
    }

  override def whoami(sessionId: SessionId): WhoamiResponse =
    if (sessions.contains(sessionId))
      Credentials(sessionId, sessions(sessionId))
    else
      SessionNotFound(sessionId)

}
object AuthServiceImpl {

  private def isPasswordSecure(password: Password): Boolean =
    "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}".r.matches(password.value)

}
