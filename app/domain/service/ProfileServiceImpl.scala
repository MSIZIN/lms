package domain.service

import dao.ProfileDao
import domain.model.{EducationInfo, EducationalGroup, Email, Profile}
import domain.service.ProfileServiceImpl.decorateProfile
import domain.service.messages.{ProfileNotFound, ProfileRequest, ProfileResponse, ProfileSuccess}
import javax.inject._

@Singleton
class ProfileServiceImpl @Inject() (profileDao: ProfileDao) extends ProfileService {

  override def profile(request: ProfileRequest): ProfileResponse =
    profileDao.findProfileByEmail(request.email) match {
      case Some(profile) => ProfileSuccess(decorateProfile(profile, request.email))
      case None          => ProfileNotFound
    }

}
object ProfileServiceImpl {

  private def decorateProfile(profile: Profile, email: Email): String =
    s"""
      |Личная информация:
      |ФИО: ${profile.person.lastName}, ${profile.person.firstName}, ${profile.person.middleName}
      |E-mail: ${email.value}
      |Телефон: ${decorateOptField(profile.person.phone)}
      |Родной город: ${decorateOptField(profile.person.homeTown)}
      |Информация о себе: ${decorateOptField(profile.person.info)}
      |
      |Ссылки на профили в социальных сетях:
      |VK: ${decorateOptField(profile.person.vkLink)}
      |Facebook: ${decorateOptField(profile.person.facebookLink)}
      |LinkedIn: ${decorateOptField(profile.person.linkedinLink)}
      |Instagram: ${decorateOptField(profile.person.instagramLink)}
      |${profile.educationInfo.fold("")(decorateEducationInfo)}
      |""".stripMargin

  private def decorateOptField(field: Option[String]): String =
    s"${field.fold("отстутсвует (можно добавить)")(field => field + "(можно изменить)")}"

  private def decorateEducationInfo(educationInfo: EducationInfo): String =
    s"""
      |Информация о получаемом образовании:${decorateEducationalGroup(educationInfo.group)}
      |Год поступления: ${educationInfo.admissionYear}
      |Степень: ${educationInfo.degree}
      |Форма обучения: ${educationInfo.educationalForm}
      |Основа обучения: ${educationInfo.educationalBasis}
      |""".stripMargin

  private def decorateEducationalGroup(educationalGroup: EducationalGroup): String =
    s"""
      |Факультет: ${educationalGroup.department}
      |Группа: ${educationalGroup.name}
      |Номер курса: ${educationalGroup.courseNumber}
      |""".stripMargin

}
