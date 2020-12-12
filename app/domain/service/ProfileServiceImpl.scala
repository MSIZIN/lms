package domain.service

import dao.ProfileDao
import domain.model.{EducationInfo, EducationalGroup, Email, Person, Profile}
import domain.service.ProfileServiceImpl.{decorateAnotherProfile, decorateProfile}
import domain.service.messages.{ProfileNotFound, ProfileRequest, ProfileResponse, ProfileSuccess}
import javax.inject._

@Singleton
class ProfileServiceImpl @Inject() (profileDao: ProfileDao) extends ProfileService {

  override def profile(request: ProfileRequest): ProfileResponse =
    profileDao.findProfileByEmail(request.email) match {
      case Some(profile) => ProfileSuccess(decorateProfile(profile, request.email))
      case None          => ProfileNotFound
    }

  override def anotherProfile(request: ProfileRequest): ProfileResponse =
    profileDao.findProfileByEmail(request.email) match {
      case Some(profile) => ProfileSuccess(decorateAnotherProfile(profile, request.email))
      case None          => ProfileNotFound
    }

}
object ProfileServiceImpl {

  private def decorateProfile(profile: Profile, email: Email): String =
    s"""${decoratePerson(profile.person, email)}
      |
      |${profile.educationInfo.fold("")(decorateEducationInfo)}""".stripMargin

  private def decorateAnotherProfile(profile: Profile, email: Email): String =
    s"""${decorateAnotherPerson(profile.person, email)}
       |
       |${profile.educationInfo.fold("")(decorateAnotherEducationInfo)}""".stripMargin

  private def decoratePerson(person: Person, email: Email): String =
    s"""Личная информация:
       |ФИО: ${person.lastName} ${person.firstName} ${person.middleName}
       |E-mail: ${email.value}
       |Телефон: ${decorateOptField(person.phone)}
       |Родной город: ${decorateOptField(person.homeTown)}
       |Информация о себе: ${decorateOptField(person.info)}
       |
       |Ссылки на профили в социальных сетях:
       |VK: ${decorateOptField(person.vkLink)}
       |Facebook: ${decorateOptField(person.facebookLink)}
       |LinkedIn: ${decorateOptField(person.linkedinLink)}
       |Instagram: ${decorateOptField(person.instagramLink)}""".stripMargin

  private def decorateAnotherPerson(person: Person, email: Email): String =
    s"""Личная информация:
       |ФИО: ${person.lastName} ${person.firstName} ${person.middleName}
       |E-mail: ${email.value}
       |Телефон: ${decorateAnotherOptField(person.phone)}
       |Родной город: ${decorateAnotherOptField(person.homeTown)}
       |Информация о себе: ${decorateAnotherOptField(person.info)}
       |
       |Ссылки на профили в социальных сетях:
       |VK: ${decorateAnotherOptField(person.vkLink)}
       |Facebook: ${decorateAnotherOptField(person.facebookLink)}
       |LinkedIn: ${decorateAnotherOptField(person.linkedinLink)}
       |Instagram: ${decorateAnotherOptField(person.instagramLink)}""".stripMargin

  private def decorateOptField(field: Option[String]): String =
    s"${field.fold("отсутствует (можно добавить)")(field => field + " (можно изменить)")}"

  private def decorateAnotherOptField(field: Option[String]): String =
    s"${field.fold("отсутствует")(identity)}"

  private def decorateEducationInfo(educationInfo: EducationInfo): String =
    s"""Информация о получаемом образовании:
      |${decorateEducationalGroup(educationInfo.group)}
      |Год поступления: ${educationInfo.admissionYear}
      |Степень: ${educationInfo.degree}
      |Форма обучения: ${educationInfo.educationalForm}
      |Основа обучения: ${educationInfo.educationalBasis}""".stripMargin

  private def decorateAnotherEducationInfo(educationInfo: EducationInfo): String =
    s"""Информация о получаемом образовании:
       |${decorateEducationalGroup(educationInfo.group)}
       |Год поступления: ${educationInfo.admissionYear}
       |Степень: ${educationInfo.degree}
       |Форма обучения: ${educationInfo.educationalForm}""".stripMargin

  private def decorateEducationalGroup(educationalGroup: EducationalGroup): String =
    s"""Факультет: ${educationalGroup.department}
      |Группа: ${educationalGroup.name}
      |Номер курса: ${educationalGroup.courseNumber}""".stripMargin

}
