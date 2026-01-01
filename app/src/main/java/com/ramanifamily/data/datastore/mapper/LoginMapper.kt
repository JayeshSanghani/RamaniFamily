package com.ramanifamily.data.datastore.mapper

import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.entity.User
import com.ramanifamily.datastore.LoginResponseProto
import com.ramanifamily.datastore.UserProto


// ---------- Primitive safe helpers ----------
fun String?.safe() = this ?: ""
fun Int?.safe() = this ?: 0
fun Long?.safe() = this ?: 0L
fun Boolean?.safe() = this ?: false

// User → UserProto
fun User?.toProto(): UserProto =
    UserProto.newBuilder()
        .setId(this?.id.safe())
        .setName(this?.name.safe())
        .setFirstName(this?.firstName.safe())
        .setMiddleName(this?.middleName.safe())
        .setSurname(this?.surname.safe())

        .setGender(this?.gender.safe())
        .setDob(this?.dob.safe())
        .setHeight(this?.height.safe())
        .setWeight(this?.weight.safe())
        .setBloodGroup(this?.bloodGroup.safe())
        .setZodiac(this?.zodiac.safe())

        .setEducation(this?.education.safe())
        .setOccupation(this?.occupation.safe())
        .setMobile(this?.mobile.safe())

        .setShowNumber(this?.showNumber.safe())
        .setDonateBlood(this?.donateBlood.safe())

        .setAddress(this?.address.safe())
        .setBusinessName(this?.businessName.safe())
        .setBusinessAddress(this?.businessAddress.safe())
        .setBusinessContact(this?.businessContact.safe())
        .setPropertyDetail(this?.propertyDetail.safe())
        .setOtherDetail(this?.otherDetail.safe())

        .setVillage(this?.village.safe())
        .setTaluka(this?.taluka.safe())
        .setDistrict(this?.district.safe())
        .setMaternalDetail(this?.maternalDetail.safe())

        .setBrother(this?.brother.safe())
        .setSister(this?.sister.safe())
        .setMaritalStatus(this?.maritalStatus.safe())

        .setCreatedAt(this?.createdAt.safe())
        .setUpdatedAt(this?.updatedAt.safe())
        .setLastDonated(this?.lastDonated.safe())

        .build()

// LoginResponse → LoginResponseProto
fun LoginResponse?.toProto(): LoginResponseProto =
    LoginResponseProto.newBuilder()
        .setStatus(this?.status.safe())
        .setMessage(this?.message.safe())
        .setToken(this?.token.safe())
        .setUser(this?.user.toProto())
        .build()