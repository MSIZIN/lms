package domain.service

import dao.AccountDao
import domain.model.{Email, SessionId}
import javax.inject._

@Singleton
class AuthServiceImpl @Inject() (accDao: AccountDao) extends AuthService {

  private var sessions = Map[SessionId, Email]()

  override def login(request: LoginRequest): LoginResponse = {
    val reqAcc = request.account
    accDao.findAccByEmail(reqAcc.email) match {
      case Some(acc) if reqAcc.password == acc.password => {
        val sessionId = SessionId(java.util.UUID.randomUUID().toString)
        sessions += (sessionId -> acc.email)
        LoginSuccess(sessionId)
      }
      case Some(_) => PasswordIncorrect
      case None    => EmailNotFound
    }
  }

}
