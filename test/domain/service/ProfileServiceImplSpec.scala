package domain.service

import dao.ProfileDao
import domain.model._
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

    override def updatePersonFieldByEmail(fieldName: String, fieldValue: String, email: Email): Unit = ()

    override def findGroupmatesByEmail(email: Email): Option[List[Student]] =
      if (email == Email("student@email.com"))
        Some(List(Student(student.person.firstName, student.person.lastName, student.person.middleName)))
      else
        None

  }

  val profileService = new ProfileServiceImpl(profileDaoMock)

  "profile method" must {

    "return decorated profile of teacher" in {
      profileService.profile(ProfileRequest(Email("teacher@email.com"))) match {
        case ProfileSuccess(content) =>
          content must include(
            """Личная информация:
              |ФИО: Хрусталёв Иван Николаевич
              |E-mail: teacher@email.com
              |Телефон: +7 988 737 7846 (можно изменить)
              |Родной город: Калуга (можно изменить)
              |Информация о себе: Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. (можно изменить)
              |
              |Ссылки на профили в социальных сетях:
              |VK: https://vk.com/id119489714 (можно изменить)
              |Facebook: отсутствует (можно добавить)
              |LinkedIn: отсутствует (можно добавить)
              |Instagram: https://instagram.com/id859213456 (можно изменить)""".stripMargin
          )
        case other => sys.error(s"Unexpected profile result: $other")
      }
    }

    "return decorated profile of student" in {
      profileService.profile(ProfileRequest(Email("student@email.com"))) match {
        case ProfileSuccess(content) =>
          content must include(
            """Личная информация:
              |ФИО: Федотова Ирина Антоновна
              |E-mail: student@email.com
              |Телефон: отсутствует (можно добавить)
              |Родной город: отсутствует (можно добавить)
              |Информация о себе: отсутствует (можно добавить)
              |
              |Ссылки на профили в социальных сетях:
              |VK: отсутствует (можно добавить)
              |Facebook: отсутствует (можно добавить)
              |LinkedIn: отсутствует (можно добавить)
              |Instagram: отсутствует (можно добавить)
              |
              |Информация о получаемом образовании:
              |Факультет: ИНКБИСТ
              |Группа: 551
              |Номер курса: 3
              |Год поступления: 2018
              |Степень: специалист
              |Форма обучения: очная
              |Основа обучения: бюджетная""".stripMargin
          )
        case other => sys.error(s"Unexpected profile result: $other")
      }
    }

    "return decorated profile of another student" in {
      profileService.anotherProfile(ProfileRequest(Email("student@email.com"))) match {
        case ProfileSuccess(content) =>
          content must include(
            """Личная информация:
              |ФИО: Федотова Ирина Антоновна
              |E-mail: student@email.com
              |Телефон: отсутствует
              |Родной город: отсутствует
              |Информация о себе: отсутствует
              |
              |Ссылки на профили в социальных сетях:
              |VK: отсутствует
              |Facebook: отсутствует
              |LinkedIn: отсутствует
              |Instagram: отсутствует
              |
              |Информация о получаемом образовании:
              |Факультет: ИНКБИСТ
              |Группа: 551
              |Номер курса: 3
              |Год поступления: 2018
              |Степень: специалист
              |Форма обучения: очная""".stripMargin
          )
        case other => sys.error(s"Unexpected profile result: $other")
      }
    }

    "not return decorated profile of unknown email" in {
      profileService.profile(ProfileRequest(Email("oops@email.com"))) must equal(ProfileNotFound)
    }

  }

  "update methods" must {

    "update phone" in {
      profileService.updatePhone(UpdatePhoneRequest("9876543210", Email("teacher@email.com"))) must equal(UpdateSuccess)
    }

    "not update phone in the wrong format" in {
      profileService.updatePhone(UpdatePhoneRequest("876543210", Email("teacher@email.com"))) must equal(WrongPhoneFormat)
    }

    "update home town" in {
      profileService.updateHomeTown(UpdateHomeTownRequest("Moscow", Email("teacher@email.com"))) must equal(UpdateSuccess)
    }

    "update person info" in {
      profileService.updatePersonInfo(UpdatePersonInfoRequest("I am teacher.", Email("teacher@email.com"))) must equal(UpdateSuccess)
    }

    "update vk link" in {
      profileService.updateLink(UpdateLinkRequest(VkLink("https://vk.com/teacher"), Email("teacher@email.com"))) must equal(UpdateSuccess)
    }

    "not update vk link in the wrong format" in {
      profileService.updateLink(UpdateLinkRequest(VkLink("https://instagram.com/teacher"), Email("teacher@email.com"))) must equal(
        WrongLinkFormat("Vk", "https://vk.com/")
      )
    }

    "update facebook link" in {
      profileService.updateLink(UpdateLinkRequest(FacebookLink("https://facebook.com/teacher"), Email("teacher@email.com"))) must equal(
        UpdateSuccess
      )
    }

    "not update facebook link in the wrong format" in {
      profileService.updateLink(UpdateLinkRequest(FacebookLink("https://oops.com/teacher"), Email("teacher@email.com"))) must equal(
        WrongLinkFormat("Facebook", "https://facebook.com/")
      )
    }

    "update linkedin link" in {
      profileService.updateLink(UpdateLinkRequest(LinkedinLink("https://linkedin.com/teacher"), Email("teacher@email.com"))) must equal(
        UpdateSuccess
      )
    }

    "not update linkedin link in the wrong format" in {
      profileService.updateLink(UpdateLinkRequest(LinkedinLink("https://oops.com/teacher"), Email("teacher@email.com"))) must equal(
        WrongLinkFormat("Linkedin", "https://linkedin.com/")
      )
    }

    "update instagram link" in {
      profileService.updateLink(UpdateLinkRequest(InstagramLink("https://instagram.com/teacher"), Email("teacher@email.com"))) must equal(
        UpdateSuccess
      )
    }

    "not update instagram link in the wrong format" in {
      profileService.updateLink(UpdateLinkRequest(InstagramLink("https://oops.com/teacher"), Email("teacher@email.com"))) must equal(
        WrongLinkFormat("Instagram", "https://instagram.com/")
      )
    }

  }

  "groupmates method" must {

    "return groupmates of student" in {
      profileService.groupmates(GroupmatesRequest(Email("student@email.com"))) must equal(
        GroupmatesSuccess("Студенты из вашей группы:\n1. Федотова Ирина Антоновна")
      )
    }

    "not return groupmates of teacher" in {
      profileService.groupmates(GroupmatesRequest(Email("teacher@email.com"))) must equal(UserIsNotStudent)
    }

  }

}
