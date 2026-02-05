package com.ramanifamily.data.datastore.mapper

import com.ramanifamily.data.entity.BannersItem
import com.ramanifamily.data.entity.Data
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.entity.ProfileResponse
import com.ramanifamily.data.entity.User
import com.ramanifamily.datastore.BannerProto
import com.ramanifamily.datastore.LoginResponseProto
import com.ramanifamily.datastore.UserProto

// --------------------------------------------------
// Primitive safe helpers
// --------------------------------------------------
private fun String?.safe() = this ?: ""
private fun Int?.safe() = this ?: 0
private fun Long?.safe() = this ?: 0L
private fun Boolean?.safe() = this ?: false

// --------------------------------------------------
// User → UserProto
// --------------------------------------------------
fun User.toProto(): UserProto =
    UserProto.newBuilder()
        .setId(id.safe())
        .setName(name.safe())
        .setFirstName(firstName.safe())
        .setMiddleName(middleName.safe())
        .setSurname(surname.safe())

        .setStateId(stateId.safe())
        .setStateName(stateName.safe())
        .setDistrictId(districtId.safe())
        .setDistrictName(districtName.safe())
        .setSubDistrictId(subDistrictId.safe())
        .setSubDistrictName(subDistrictName.safe())

        .setDistrict(district.safe())
        .setTaluka(taluka.safe())
        .setVillage(village.safe())
        .setAddress(address.safe())

        .setDob(dob.safe())
        .setMobile(mobile.safe())
        .setEmail(email.safe())

        .setShowNumber(showNumber.safe())
        .setGender(gender.safe())
        .setProfileImg(profileImg.safe())

        .setMaritalStatus(maritalStatus.safe())
        .setLastDonated(lastDonated.safe())
        .setBloodGroup(bloodGroup.safe())
        .setDonateBlood(donateBlood.safe())

        .setCreatedAt(createdAt.safe())
        .setUpdatedAt(updatedAt.safe())

        .setBusinessName(businessName.safe())
        .setBusinessAddress(businessAddress.safe())
        .setBusinessContact(businessContact.safe())

        .setOtherDetail(otherDetail.safe())
        .setHeight(height.safe())
        .setWeight(weight.safe())
        .setZodiac(zodiac.safe())

        .setEducation(education.safe())
        .setOccupation(occupation.safe())

        .setBrother(brother.safe())
        .setSister(sister.safe())

        .setMaternalDetail(maternalDetail.safe())
        .setPropertyDetail(propertyDetail.safe())

        .setIsAdmin(isAdmin.safe())
        .build()

// --------------------------------------------------
// Data → UserProto (from ProfileResponse)
// --------------------------------------------------
fun Data.toProto(): UserProto =
    UserProto.newBuilder()
        .setId(id.safe())
        .setName(name.safe())
        .setFirstName(firstName.safe())
        .setMiddleName(middleName.safe())
        .setSurname(surname.safe())

        .setStateId(stateId.safe())
        .setStateName(stateName.safe())
        .setDistrictId(districtId.safe())
        .setDistrictName(districtName.safe())
        .setSubDistrictId(subDistrictId.safe())
        .setSubDistrictName(subDistrictName.safe())

        .setDistrict(district.safe())
        .setTaluka(taluka.safe())
        .setVillage(village.safe())
        .setAddress(address.safe())

        .setDob(dob.safe())
        .setMobile(mobile.safe())
        .setEmail(email.safe())

        .setShowNumber(showNumber.safe())
        .setGender(gender.safe())
        .setProfileImg(profileImg.safe())

        .setMaritalStatus(maritalStatus.safe())
        .setLastDonated(lastDonated.safe())
        .setBloodGroup(bloodGroup.safe())
        .setDonateBlood(donateBlood.safe())

        .setCreatedAt(createdAt.safe())
        .setUpdatedAt(updatedAt.safe())

        .setBusinessName(businessName.safe())
        .setBusinessAddress(businessAddress.safe())
        .setBusinessContact(businessContact.safe())

        .setOtherDetail(otherDetail.safe())
        .setHeight(height.toString())
        .setWeight(weight.toString())
        .setZodiac(zodiac.safe())

        .setEducation(education.safe())
        .setOccupation(occupation.safe())

        .setBrother(brother.safe())
        .setSister(sister.safe())

        .setMaternalDetail(maternalDetail.safe())
        .setPropertyDetail(propertyDetail.safe())

        .setIsAdmin(isAdmin == 1)
        .build()

// --------------------------------------------------
// Banner → BannerProto
// --------------------------------------------------
fun BannersItem.toProto(): BannerProto =
    BannerProto.newBuilder()
        .setId(id.safe())
        .setImage(image.safe())
        .build()

// --------------------------------------------------
// LoginResponse → LoginResponseProto
// --------------------------------------------------
fun LoginResponse.toProto(): LoginResponseProto {
    val builder = LoginResponseProto.newBuilder()
        .setStatus(status.safe())
        .setMessage(message.safe())
        .setToken(token.safe())

    user?.let {
        builder.setUser(it.toProto())
    }

    banners?.forEach {
        builder.addBanners(it.toProto())
    }

    return builder.build()
}

// --------------------------------------------------
// ProfileResponse → LoginResponseProto
// --------------------------------------------------
fun ProfileResponse.toProto(): LoginResponseProto {
    val builder = LoginResponseProto.newBuilder()
        .setStatus(status.safe())
        .setMessage(message.safe())

    data?.let {
        builder.setUser(it.toProto())
    }

    return builder.build()
}
