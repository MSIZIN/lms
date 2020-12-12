package domain.service

import com.google.inject.ImplementedBy
import domain.service.messages._

@ImplementedBy(classOf[ProfileServiceImpl])
trait ProfileService {
  def profile(request: ProfileRequest): ProfileResponse
  def anotherProfile(request: ProfileRequest): ProfileResponse
  def updatePhone(request: UpdatePhoneRequest): UpdateResponse
  def updateHomeTown(request: UpdateHomeTownRequest): UpdateResponse
  def updatePersonInfo(request: UpdatePersonInfoRequest): UpdateResponse
  def updateLink(request: UpdateLinkRequest): UpdateResponse
}
