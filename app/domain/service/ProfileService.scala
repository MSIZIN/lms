package domain.service

import com.google.inject.ImplementedBy
import domain.service.messages.{ProfileRequest, ProfileResponse}

@ImplementedBy(classOf[ProfileServiceImpl])
trait ProfileService {
  def profile(request: ProfileRequest): ProfileResponse
  def anotherProfile(request: ProfileRequest): ProfileResponse
}
