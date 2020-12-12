package domain.service

import dao.ProfileDao
import domain.model.{Account, EducationInfo, EducationalGroup, Email, Password, Person, Profile}
import domain.service.messages._
import org.scalatestplus.play.PlaySpec

class ProfileServiceImplSpec extends PlaySpec {

  val teacher: Profile =
    Profile(
      Person(
        "Иван",
        "Хрусталёв",
        "Николаевич",
        Some("+7 988 737 7846"),
        Some("Калуга"),
        Some("Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante."),
        Some("https://vk.com/id119489714"),
        None,
        None,
        Some("https://instagram.com/id859213456")
      ),
      None
    )

  val student: Profile =
    Profile(
      Person("Ирина", "Федотова", "Антоновна", None, None, None, None, None, None, None),
      Some(EducationInfo(EducationalGroup("551", "ИНКБИСТ", 3), 2018, "специалист", "очная", "бюджетная"))
    )

  val profileDaoMock: ProfileDao = new ProfileDao {

    private val emailToProfile =
      Map(
        Email("teacher@email.com") -> teacher,
        Email("student@email.com") -> student
      )

    override def findProfileByEmail(email: Email): Option[Profile] = emailToProfile.get(email)

  }

  val profileService = new ProfileServiceImpl(profileDaoMock)

  "profile method" must {

    "return decorated profile of teacher" in {
      profileService.profile(ProfileRequest(Email("teacher@email.com"))) match {
        case ProfileSuccess(content) =>
          content must include(
            """Личная информация:
              |ФИО: Хрусталёв, Иван, Николаевич
              |E-mail: teacher@email.com
              |Телефон: +7 988 737 7846(можно изменить)
              |Родной город: Калуга(можно изменить)
              |Информация о себе: Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.(можно изменить)
              |
              |Ссылки на профили в социальных сетях:
              |VK: https://vk.com/id119489714(можно изменить)
              |Facebook: отстутсвует (можно добавить)
              |LinkedIn: отстутсвует (можно добавить)
              |Instagram: https://instagram.com/id859213456(можно изменить)
              |""".stripMargin
          )
        case other => sys.error(s"Unexpected profile result: $other")
      }
    }

    "return decorated profile of student" in {
      profileService.profile(ProfileRequest(Email("student@email.com"))) match {
        case ProfileSuccess(content) =>
          content must include(
            """
            |Личная информация:
            |ФИО: Федотова, Ирина, Антоновна
            |E-mail: student@email.com
            |Телефон: отстутсвует (можно добавить)
            |Родной город: отстутсвует (можно добавить)
            |Информация о себе: отстутсвует (можно добавить)
            |
            |Ссылки на профили в социальных сетях:
            |VK: отстутсвует (можно добавить)
            |Facebook: отстутсвует (можно добавить)
            |LinkedIn: отстутсвует (можно добавить)
            |Instagram: отстутсвует (можно добавить)
            |
            |Информация о получаемом образовании:
            |Факультет: ИНКБИСТ
            |Группа: 551
            |Номер курса: 3
            |
            |Год поступления: 2018
            |Степень: специалист
            |Форма обучения: очная
            |Основа обучения: бюджетная
            |""".stripMargin
          )
        case other => sys.error(s"Unexpected profile result: $other")
      }
    }

    "not return decorated profile of unknown email" in {
      profileService.profile(ProfileRequest(Email("oops@email.com"))) must equal(ProfileNotFound)
    }

  }

}
