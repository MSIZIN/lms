package domain.service

import java.util.UUID

import dao.AuthDao
import domain.model.{Account, Email, Password, Person}
import org.scalatestplus.play.PlaySpec

class AuthServiceSpec extends PlaySpec {

  val acc1 = Account(Email("test1@email.com"), Password("pasw1"))
  val acc2 = Account(Email("test2@email.com"), Password("pasw2"))

  val uuid1 = UUID.fromString("2563d25c-851b-4d2b-a9e7-5946035119f3")
  val uuid2 = UUID.fromString("edd8b362-215f-4abf-994a-d3fa9fc92f73")
  val uuid3 = UUID.fromString("ef9c5278-1afa-4779-901f-14ec32939f74")

  val person1 = Person("Елизавета", "Балтинова", "Игоревна")
  val person2 = Person("Иван", "Хрусталёв", "Николаевич")
  val person3 = Person("Николай", "Дубровский", "Фёдорович")

  val accDaoMock: AuthDao = new AuthDao {

    private val emailToAcc = Map(
      Email("test1@email.com") -> acc1,
      Email("test2@email.com") -> acc2
    )

    private val vercodeToAcc = Map(
      uuid1 -> acc1,
      uuid2 -> acc2
    )

    private val vercodeToPerson = Map(
      uuid1 -> person1,
      uuid2 -> person2,
      uuid3 -> person3
    )

    override def findAccByEmail(email: Email): Option[Account] = emailToAcc.get(email)

    override def findAccByVerCode(verificationCode: UUID): Option[Account] = vercodeToAcc.get(verificationCode)

    override def findPersonByVerCode(verificationCode: UUID): Option[Person] = vercodeToPerson.get(verificationCode)

    override def insertAcc(verificationCode: UUID, account: Account): Boolean = true

  }

  val authService = new AuthServiceImpl(accDaoMock)

  "login method" must {

    "recognise known emails" in {
      authService.login(LoginRequest(Account(Email("test1@email.com"), Password("pasw1")))) match {
        case LoginSuccess(sessionId) => sessionId.value.length must equal(36)
        case other                   => sys.error(s"Unexpected login result: $other")
      }
    }

    "not recognise unknown emails" in {
      authService.login(LoginRequest(Account(Email("oops@email.com"), Password("pasw2")))) must equal(EmailNotFound)
    }

    "not recognise invalid passwords" in {
      authService.login(LoginRequest(Account(Email("test2@email.com"), Password("oopsw")))) must equal(PasswordIncorrect)
    }

  }

  "signup method" must {

    "signup unregistered person" in {
      authService.signup(SignupRequest(uuid3, Account(Email("test3@email.com"), Password("abcDE1")))) must equal(SignupSuccess)
    }

    "not signup unknown person" in {
      authService.signup(
        SignupRequest(UUID.fromString("7bac6a27-60a4-4da4-883d-82070cb8b024"), Account(Email("test3@email.com"), Password("abcDE1")))
      ) must equal(UserNotFound)
    }

    "not signup registered person" in {
      authService.signup(SignupRequest(uuid1, acc1)) must equal(AccountExists)
    }

    "not signup existing email" in {
      authService.signup(SignupRequest(uuid3, acc2)) must equal(EmailExists)
    }

    "not signup insecure password" in {
      authService.signup(SignupRequest(uuid3, Account(Email("test3@email.com"), Password("1234")))) must equal(InsecurePassword)
    }

  }

}
