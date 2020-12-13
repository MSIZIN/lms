package controllers

import controllers.ControllerHelpers._
import domain.model._
import domain.service._
import domain.service.messages._
import javax.inject._
import play.api.mvc._

@Singleton
class ProfileController @Inject() (authService: AuthService, profileService: ProfileService, cc: ControllerComponents)
    extends AbstractController(cc) {

  def profile: Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.profile(ProfileRequest(creds.email)) match {
        case ProfileSuccess(content) =>
          Ok(s"""Ваш профиль:
            |
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

  def updatePhone(phone: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.updatePhone(UpdatePhoneRequest(phone, creds.email)) match {
        case UpdateSuccess    => Ok("Телефон успешно изменён.")
        case WrongPhoneFormat => BadRequest("Формат введёного номера должен быть таким: 9.........")
      }
    }
  }

  def updateHomeTown(town: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.updateHomeTown(UpdateHomeTownRequest(town, creds.email)) match {
        case UpdateSuccess => Ok("Родной город успешно изменён.")
      }
    }
  }

  def updatePersonInfo(info: String): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.updatePersonInfo(UpdatePersonInfoRequest(info, creds.email)) match {
        case UpdateSuccess => Ok("Информация о себе успешно изменена.")
      }
    }
  }

  def updateVkLink(link: String): Action[AnyContent] = updateLink(VkLink(link))

  def updateFacebookLink(link: String): Action[AnyContent] = updateLink(FacebookLink(link))

  def updateLinkedinLink(link: String): Action[AnyContent] = updateLink(LinkedinLink(link))

  def updateInstagramLink(link: String): Action[AnyContent] = updateLink(InstagramLink(link))

  def groupmates: Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.groupmates(GroupmatesRequest(creds.email)) match {
        case GroupmatesSuccess(students) =>
          Ok(
            "Студенты из вашей группы:\n" +
              students
                .map(student => (student.lastName, student.firstName, student.middleName))
                .sorted
                .map(fio => s"${fio._1} ${fio._2} ${fio._3}")
                .mkString("\n")
          )
        case UserIsNotStudent => BadRequest("Одногруппники могут быть только у студента")
      }
    }
  }

  private def updateLink(link: SocialNetworkLink): Action[AnyContent] = Action { request =>
    withAuthenticatedUser(request, authService) { creds =>
      profileService.updateLink(UpdateLinkRequest(link, creds.email)) match {
        case UpdateSuccess => Ok(s"Ссылка успешно изменена.")
        case WrongLinkFormat(socialNetwork, requiredLinkPrefix) =>
          BadRequest(s"Ссылка на профиль в $socialNetwork должна начинаться с $requiredLinkPrefix")
      }
    }
  }

}
