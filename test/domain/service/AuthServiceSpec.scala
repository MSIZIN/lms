package domain.service

import dao.AccountDao
import domain.model.{Account, Email, Password}
import org.scalatestplus.play.PlaySpec

class AuthServiceSpec extends PlaySpec {

  val accDaoMock: AccountDao = new AccountDao {

    val accStorage = Map(
      Email("test1@email.com") -> Password("pasw1"),
      Email("test2@email.com") -> Password("pasw2")
    )

    override def findAccByEmail(email: Email): Option[Account] =
      accStorage.get(email).map(psw => Account(email, psw))

  }

  val authService = new AuthServiceImpl(accDaoMock)

  "login method" must {

    "recognise known usernames" in {
      authService.login(LoginRequest(Account(Email("test1@email.com"), Password("pasw1")))) match {
        case LoginSuccess(sessionId) => sessionId.value.length must equal(36)
        case other                   => sys.error(s"Unexpected login result: $other")
      }
    }

    "not recognise unknown usernames" in {
      authService.login(LoginRequest(Account(Email("oops@email.com"), Password("pasw2")))) must
        equal(EmailNotFound)
    }

    "not recognise invalid passwords" in {
      authService.login(LoginRequest(Account(Email("test2@email.com"), Password("oopsw")))) must
        equal(PasswordIncorrect)
    }

  }

}
